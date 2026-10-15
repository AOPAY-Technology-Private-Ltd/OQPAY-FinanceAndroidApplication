package com.bosandroidapp.oqmobilefinance.ui.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bosandroidapp.oqmobilefinance.ui.view.activity.fragment.PayoutPage
import com.bosandroidapp.oqmobilefinance.ui.view.activity.fragment.PayoutReports

private const val NUM_TABS = 2

class WalletPagerAdapter (fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm,lifecycle) {


    override fun getItemCount(): Int {
        return NUM_TABS
    }


    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> PayoutPage()
            1-> PayoutReports()
            else -> PayoutPage()
        }

    }

}