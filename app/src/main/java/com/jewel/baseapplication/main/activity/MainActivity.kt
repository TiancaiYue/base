package com.jewel.baseapplication.main.activity

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.FragmentManager
import cn.base.BaseActivity
import cn.base.OnTabReselectListener
import cn.network.presenter.DemoCloudPresenter
import cn.network.presenter.adapter.IDemoCloudAdapter
import cn.user_db.UserCache
import cn.utils.YZToastUtil
import cn.widget.NavigationButton
import com.jewel.baseapplication.*
import com.jewel.baseapplication.main.fragment.HomeFragment
import com.jewel.baseapplication.main.fragment.NavFragment
import com.mola.cpp.push.PushMain
import java.util.*

class MainActivity : BaseActivity(), NavFragment.OnTabSelectedListener {
    private val EXIT_APP_DELAY = 1000
    private var mContainerMain: FrameLayout? = null
    private var mNavFag: NavFragment? = null
    private var mFragmentManager: FragmentManager? = null
    private var firstEnter = true
    private var lastTime: Long = 0
    private var mDemoCloudPresenter: DemoCloudPresenter? = null
    private var mIDemoCloudAdapter: IDemoCloudAdapter? = null

    override fun onResume() {
        super.onResume()
        if (firstEnter) {
            firstEnter = false
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        mFragmentManager = supportFragmentManager
        mNavFag = mFragmentManager!!.findFragmentById(R.id.fag_nav) as NavFragment
        mContainerMain = findViewById(R.id.fl_main_container)
        mNavFag!!.setup(this, mFragmentManager!!, R.id.fl_main_container, this)
    }

    override fun initData() {
        mDemoCloudPresenter = DemoCloudPresenter(this@MainActivity)
        mIDemoCloudAdapter = object : IDemoCloudAdapter() {
            override fun onDemoInformation(demo: String?) {
                super.onDemoInformation(demo)
            }
        }

        mDemoCloudPresenter!!.attachView(mIDemoCloudAdapter)
    }

    override fun initEvent() {
    }

    override fun onDestroy() {
        super.onDestroy()
        mDemoCloudPresenter!!.detachView()
    }

    /**
     * 监听返回键
     */
    override fun onBackPressed() {
        if (System.currentTimeMillis() - lastTime > EXIT_APP_DELAY) run {
            YZToastUtil.showMessage(this, mContext.getString(R.string.press_twice_exit))
            lastTime = System.currentTimeMillis()
        } else {
            moveTaskToBack(true)
        }
    }

    override fun onTabSelected(navigationButton: NavigationButton) {
        val fragment = navigationButton.fragment
        if (fragment != null) {
            when (fragment) {
                is HomeFragment -> {
                }
                else -> {
                }
            }
        }
    }

    override fun onTabReselected(navigationButton: NavigationButton) {
        val fragment = navigationButton.fragment
        if (fragment != null && fragment is OnTabReselectListener) {
            val listener = fragment as OnTabReselectListener
            listener.onTabReselect()
        }
    }
}
