package com.arka.prava.util.viewcontroller

import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arka.prava.util.utils.AppExperimental
import com.arka.prava.util.core.PullToRefreshScreen
import com.arka.prava.util.core.toolbar.ToolbarContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 15/8/19.
 */
open class BaseActivity : AppCompatActivity {
    @AppExperimental
    private val jobDelegate = lazy { SupervisorJob() }

    @AppExperimental
    private val activityScopeDelegate = lazy { CoroutineScope(Dispatchers.Main + job) }

    @AppExperimental
    protected val job: Job by jobDelegate

    @AppExperimental
    protected val activityScope: CoroutineScope by activityScopeDelegate

    constructor() : super()
    constructor(@LayoutRes layoutId: Int) : super(layoutId)

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        applyToolbarIfApplicable()
        setUpPullToRefresh()
    }

    override fun setContentView(view: View?) {
        super.setContentView(view)
        applyToolbarIfApplicable()
    }

    override fun onDestroy() {
        if (jobDelegate.isInitialized()) {
            job.cancel()
        }
        super.onDestroy()
    }

    private fun applyToolbarIfApplicable() {
        /**
         * Todo : Check the cost of this method.
         *
         * Not sure if it is wise to have these if checks in Base Activity. The alternative
         * is inheritance which has its cons.
         */
        if (this !is ToolbarContract) {
            return
        }
        val resID = resources.getIdentifier("toolbar", "id", packageName)
        if (resID <= 0)
            throw IllegalStateException("Toolbar Activity should provide a Toolbar!")
        val toolbar = window.decorView.findViewById<Toolbar?>(resID) ?: return
        wireClickListener(toolbar)
    }

    private fun setUpPullToRefresh() {
        if (this !is PullToRefreshScreen) {
            return
        }
        val resID = resources.getIdentifier("swipe_refresh", "id", packageName)
        if (resID <= 0)
            throw IllegalStateException("Pull To refresh Activity should provide a SwipeRefreshLayout!")
        val refreshLayout = window.decorView.findViewById<SwipeRefreshLayout>(resID)
        refreshLayout.setOnRefreshListener {
            refresh()
        }
    }
}
