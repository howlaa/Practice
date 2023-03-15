package com.example.mypractice.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.mypractice.base.BaseActivity
import com.example.mypractice.databinding.ActivityMainBinding
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mypractice.R
import com.example.mypractice.ui.main.fragment.*
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class MainActivity : BaseActivity<ActivityMainBinding>() {
    companion object {
        private const val TAG = "MainActivity"
    }

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        binding.navView.setOnItemSelectedListener {
            Log.d(TAG, "initViews: $it")
            when (it.itemId) {
                R.id.navigation_home -> switchFragment(0)
                R.id.navigation_blog -> switchFragment(1)
                R.id.navigation_search -> switchFragment(2)
                R.id.navigation_project_type -> switchFragment(3)
                R.id.navigation_me -> switchFragment(4)
            }
            true
        }
        initViewPager()
    }

    private fun switchFragment(position: Int) = binding.mainViewPager.setCurrentItem(position, true)

    private fun initViewPager() {
        binding.mainViewPager.isUserInputEnabled = false
        binding.mainViewPager.offscreenPageLimit = 2
        binding.mainViewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = 5

            override fun createFragment(position: Int): Fragment = when (position){
                0 -> HomeFragment()
                1 -> BlogFragment()
                2 -> SearchFragment()
                3 -> ProjectFragment()
                4 -> MeFragment()
                else -> HomeFragment()
            }
        }
    }

    override fun initEvents() {
    }



}