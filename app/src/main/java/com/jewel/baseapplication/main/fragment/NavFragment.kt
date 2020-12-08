package com.jewel.baseapplication.main.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentManager
import cn.base.BaseFragment
import cn.widget.NavigationButton
import com.jewel.baseapplication.MessageEvent
import com.jewel.baseapplication.R
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by base on 2020/03/20.
 */
class NavFragment : BaseFragment(), View.OnClickListener {
    private var mNavOne: NavigationButton? = null
    private var mNavTwo: NavigationButton? = null
    private var mNavThree: NavigationButton? = null
    private var mContainerId: Int = 0
    private var mFragmentManager: FragmentManager? = null
    private var mCurrentNavButton: NavigationButton? = null
    private var mOnTabSelectedListener: OnTabSelectedListener? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_nav
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        EventBus.getDefault().register(this)

        mNavOne = mContentView.findViewById<View>(R.id.nav_item_home) as NavigationButton
        mNavTwo = mContentView.findViewById<View>(R.id.nav_item_file) as NavigationButton
        mNavThree = mContentView.findViewById<View>(R.id.nav_item_appliance) as NavigationButton

        mNavOne!!.init(
            R.drawable.tab_icon_home,
            R.string.app_name,
                HomeFragment::class.java
        )
        mNavTwo!!.init(
            R.drawable.tab_icon_home,
            R.string.app_name,
            HomeFragment::class.java
        )
        mNavThree!!.init(
            R.drawable.tab_icon_home,
            R.string.app_name,
            HomeFragment::class.java
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    override fun initEvent() {
        mNavOne!!.setOnClickListener(this)
        mNavTwo!!.setOnClickListener(this)
        mNavThree!!.setOnClickListener(this)
    }

    override fun initData() {}

    override fun onClick(view: View) {
        if (view is NavigationButton) {
            doSelect(view)
        }
    }

    fun setup(context: Context, fragmentManager: FragmentManager, contentId: Int, listener: OnTabSelectedListener) {
        mContext = context
        mFragmentManager = fragmentManager
        mContainerId = contentId
        mOnTabSelectedListener = listener

        // do clear
        clearOldFragment()
        // do select first
        doSelect(mNavOne!!)
    }

    @SuppressLint("RestrictedApi")
    private fun clearOldFragment() {
        val transaction = mFragmentManager!!.beginTransaction()
        val fragments = mFragmentManager!!.fragments
        if (fragments.size == 0)
            return
        var doCommit = false
        for (fragment in fragments) {
            if (fragment !== this && fragment != null) {
                transaction.remove(fragment)
                doCommit = true
            }
        }
        if (doCommit)
            transaction.commitNow()
    }

    private fun doSelect(newNavButton: NavigationButton) {
        var oldNavButton: NavigationButton? = null
        if (mCurrentNavButton != null) {
            oldNavButton = mCurrentNavButton
            if (oldNavButton == newNavButton) {  // 第二次点击相同,则执行
                newNavButton.isSelected = true
                onReselect(oldNavButton)
                return
            }
            oldNavButton!!.isSelected = false
        }
        newNavButton.isSelected = true
        doTabChanged(oldNavButton, newNavButton)
        onSelected(newNavButton)
        mCurrentNavButton = newNavButton
    }

    private fun doTabChanged(oldNavButton: NavigationButton?, newNavButton: NavigationButton?) {
        val ft = mFragmentManager!!.beginTransaction()
        if (oldNavButton != null) {
            if (oldNavButton.fragment != null) {
                ft.hide(oldNavButton.fragment)
            }
        }
        if (newNavButton != null) {
            if (newNavButton.fragment == null) {
                val fragment = mFragmentManager!!.fragmentFactory.instantiate(
                        ClassLoader.getSystemClassLoader(),
                        newNavButton.clx.name
                )
                ft.add(mContainerId, fragment, newNavButton.tag)
                newNavButton.fragment = fragment
            } else {
                if (newNavButton.fragment.isAdded) {
                    ft.show(newNavButton.fragment)
                } else {
                    ft.add(mContainerId, newNavButton.fragment, newNavButton.tag)
                }
            }
        }
        ft.commit()
    }

    public fun doMessageDot(boolean: Boolean) {
        mNavThree!!.setNum(boolean)
    }

    private fun onSelected(newNavButton: NavigationButton) {
        val listener = mOnTabSelectedListener
        listener?.onTabSelected(newNavButton)
    }

    private fun onReselect(navigationButton: NavigationButton) {
        val listener = mOnTabSelectedListener
        listener?.onTabReselected(navigationButton)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(messageEvent: MessageEvent?) {
        if (messageEvent != null) {
            when (messageEvent.message) {
                "goto_home" -> {
                    doSelect(mNavOne!!)
                }
                "goto_file" -> {
                    doSelect(mNavTwo!!)
                }
                "goto_appliance" -> {
                    doSelect(mNavThree!!)
                }
            }
        }
    }

    interface OnTabSelectedListener {
        fun onTabSelected(navigationButton: NavigationButton)

        fun onTabReselected(navigationButton: NavigationButton)
    }
}
