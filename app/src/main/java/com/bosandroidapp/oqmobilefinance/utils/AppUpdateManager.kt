package com.bosandroidapp.oqmobilefinance.utils

import android.app.DownloadManager
import android.app.PendingIntent
import android.app.admin.DevicePolicyManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageInstaller
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bosandroidapp.oqmobilefinance.BuildConfig
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.data.customerreq.CustomerDeviceLockStatusReq
import com.bosandroidapp.oqmobilefinance.localdb.SharedPreference
import com.bosandroidapp.oqmobilefinance.network.RetrofitClient
import com.bosandroidapp.oqmobilefinance.notification.ApkInstallReceiver
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

object AppUpdateManager {

    private const val TAG = "AppUpdateManager"

    fun hitApiForDeviceLockStatus(context: Context, status: Boolean = true, actionName: String = "APP_UPDATED") {
        val preference = SharedPreference(context)

        val req = CustomerDeviceLockStatusReq(
            deviceModel = Build.MODEL,
            status = status,
            actionType = "DEVICE",
            customerCode = preference.getStringValue(ConstantClass.CustomerCode, ""),
            deviceId = preference.getStringValue(ConstantClass.DEVICEID, ""),
            androidVersion = Build.VERSION.RELEASE,
            retailerCode = preference.getStringValue(ConstantClass.RetailerCode, ""),
            appVersion = BuildConfig.VERSION_NAME,
            clientCode = preference.getStringValue(ConstantClass.ClientCode, ""),
            manufacturer = Build.MANUFACTURER,
            performedAt = ConstantClass.getCurrentUtcTimestamp(),
            appPackageName = context.packageName,
            actionName = actionName
        )

        Log.d("DeviceLockStatusReq", Gson().toJson(req))

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.apiInterface.uploadDeviceLockStatusReq(req)
                Log.d("DeviceLockStatusRes", Gson().toJson(response?.body()))
            } catch (e: Exception) {
                Log.e("DeviceLockStatusError", e.message ?: "Unknown error")
            }
        }
    }

    // Helper function to safely display Toasts from any thread
    private fun showToast(context: Context, message: String, length: Int = Toast.LENGTH_SHORT) {
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(context.applicationContext, message, length).show()
        }
    }

    fun downloadAndInstallApk(context: Context, apkUrl: String = ConstantClass.CUSTOMERPPURLLINK) {

        if (apkUrl.isBlank()) {
            showToast(context, "Invalid update link")
            return
        }

        showToast(context, "Downloading update...")

        val fileName = "app_update.apk"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        val apkFile = File(storageDir, fileName)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (apkFile.exists()) {
                    apkFile.delete()
                }

                val url = URL(apkUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 15000
                connection.readTimeout = 15000
                connection.connect()

                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    connection.inputStream.use { input ->
                        FileOutputStream(apkFile).use { output ->
                            input.copyTo(output)
                        }
                    }
                    Log.d(TAG, "APK Downloaded successfully: ${apkFile.absolutePath}")

                    withContext(Dispatchers.Main) {
                        Toast.makeText(context.applicationContext, "Download complete. Installing update...", Toast.LENGTH_SHORT).show()
                        installApkFile(context, apkFile)
                    }
                } else {
                    Log.e(TAG, "Download failed with HTTP code: ${connection.responseCode}")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context.applicationContext, "Failed to download update (${connection.responseCode})", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error downloading update APK", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context.applicationContext, "Error downloading update: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    fun installApkFile(context: Context, apkFile: File) {
        if (!apkFile.exists() || apkFile.length() == 0L) {
            showToast(context, "APK file does not exist or is corrupted")
            return
        }

        val dpm = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        if (dpm.isDeviceOwnerApp(context.packageName)) {
            Log.d(TAG, "Device Owner detected: Installing APK silently via PackageInstaller")
            installApkSilently(context, apkFile)
        } else {
            Log.d(TAG, "Standard mode: Installing APK via FileProvider")
            installApkViaFileProvider(context, apkFile)
        }
    }

    fun installApkSilently(context: Context, apkFile: File) {
        try {
            val packageManager = context.packageManager
            val packageInstaller = packageManager.packageInstaller

            val params = PackageInstaller.SessionParams(
                PackageInstaller.SessionParams.MODE_FULL_INSTALL
            )

            val sessionId = packageInstaller.createSession(params)
            val session = packageInstaller.openSession(sessionId)

            apkFile.inputStream().use { input ->
                session.openWrite("app_update", 0, apkFile.length()).use { output ->
                    input.copyTo(output)
                    session.fsync(output)
                }
            }

            val intent = Intent(context, ApkInstallReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                sessionId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )

            session.commit(pendingIntent.intentSender)
            session.close()

            Log.d(TAG, "Silent install session committed successfully")

        } catch (e: Exception) {
            Log.e(TAG, "Silent install failed, falling back to FileProvider", e)
            installApkViaFileProvider(context, apkFile)
        }
    }

    fun installApkViaFileProvider(context: Context, apkFile: File) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (!context.packageManager.canRequestPackageInstalls()) {
                    showToast(context, "Please allow 'Install unknown apps' permission to update", Toast.LENGTH_LONG)
                    val permissionIntent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES).apply {
                        data = Uri.parse("package:${context.packageName}")
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    context.startActivity(permissionIntent)
                    return
                }
            }

            val apkUri: Uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                apkFile
            )

            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(apkUri, "application/vnd.android.package-archive")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            context.startActivity(intent)

        } catch (e: Exception) {
            Log.e(TAG, "FileProvider install failed", e)
            showToast(context, "Failed to launch installer: ${e.message}")
        }
    }

}