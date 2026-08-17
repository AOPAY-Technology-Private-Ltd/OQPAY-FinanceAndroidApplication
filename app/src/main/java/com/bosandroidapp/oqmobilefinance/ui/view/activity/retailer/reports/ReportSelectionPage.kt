package com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.reports

import android.content.Intent
import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bosandroidapp.oqmobilefinance.internetchecker.BaseActivity
import com.bosandroidapp.oqmobilefinance.R
import com.bosandroidapp.oqmobilefinance.data.model.ReportSelectionItem
import com.bosandroidapp.oqmobilefinance.databinding.ActivityReportSelectionPageBinding
import com.bosandroidapp.oqmobilefinance.ui.view.adapter.ReportSelectionAdapter

class ReportSelectionPage : BaseActivity() {

    lateinit var binding : ActivityReportSelectionPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityReportSelectionPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBarsInsets.left, 0, systemBarsInsets.right, systemBarsInsets.bottom)
            WindowInsetsCompat.CONSUMED
        }

        setupRecyclerView()

        binding.back.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        val reportItems = listOf(
            ReportSelectionItem(1, "Loan Status", R.drawable.loanstatus),
            ReportSelectionItem(2, "Loan Settlement", R.drawable.loansettlementicon),
            ReportSelectionItem(3, "Payout Report", R.drawable.payoutreporticon),
            ReportSelectionItem(4, "Dues EMI", R.drawable.pendingemisicon),
            ReportSelectionItem(5, "Low Cibil Customer", R.drawable.lowcibilscoreicon),
            ReportSelectionItem(6, "Ledger Report", R.drawable.ledger),
            ReportSelectionItem(7, "Customer List", R.drawable.customerdetailsicon)
        )

        binding.rvReports.layoutManager = LinearLayoutManager(this)

        binding.rvReports.adapter = ReportSelectionAdapter(reportItems) { item ->
            handleItemClick(item)
        }
    }

    private fun handleItemClick(item: ReportSelectionItem) {
        when (item.id) {
            1 -> startActivity(Intent(this, RetailerCustomerReportsPage::class.java))
            2 -> startActivity(Intent(this, SettlementLoanReport::class.java))
            3 -> startActivity(Intent(this, PayoutReport::class.java))
            4 -> startActivity(Intent(this, DuesEMIPage::class.java))
            5 -> startActivity(Intent(this, LowCibilScoreCustomerReports::class.java))
            6 -> startActivity(Intent(this, Ledgerreport::class.java))
            7 -> {
                // For Customer List, we can use RetailerCustomerReportsPage with a specific flag if needed
                // or just go to the same page if it shows all customers.
                startActivity(Intent(this, CustomerListForCreatingShortCutLoanProcessPage::class.java))
            }
        }
    }
}
