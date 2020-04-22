package com.example.eateri.foodDetails

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private var fragmentList = ArrayList<Fragment>()
    private var fragmentListTitle = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position)
    }

    override fun getCount(): Int {
        return fragmentListTitle.size
    }

    override fun getPageTitle(position: Int) : CharSequence{
        return fragmentListTitle.get(position)
    }

    fun addFragment(fragment: Fragment, title: String){
        fragmentList.add(fragment)
        fragmentListTitle.add(title)
    }
}