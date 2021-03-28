package com.arka.prava.util.viewcontroller

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arka.prava.util.core.PullToRefreshScreen

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 15/8/19.
 */
open class BaseFragment : Fragment {
    constructor() : super()
    constructor(@LayoutRes layoutId: Int) : super(layoutId)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpPullToRefresh(view)
    }

    private fun setUpPullToRefresh(view: View) {
        if (this !is PullToRefreshScreen) {
            return
        }
        val resID = resources.getIdentifier("swipe_refresh", "id", activity!!.packageName)
        if (resID <= 0)
            throw IllegalStateException("Pull To refresh Fragment should provide a SwipeRefreshLayout!")
        val refreshLayout = view.findViewById<SwipeRefreshLayout>(resID)
        refreshLayout.setOnRefreshListener {
            refresh()
        }
    }
}
