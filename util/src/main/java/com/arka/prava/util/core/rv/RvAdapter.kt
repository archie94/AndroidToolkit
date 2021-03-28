package com.arka.prava.util.core.rv

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 17/12/18.
 */
class RvAdapter<T : IRvData, VR : RvViewRenderer<in T>>(
    vrList: List<VR>,
    private var tracker: RvImpressionTracker? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val vrMap: HashMap<Int, VR> = HashMap()

    private val itemCallback = DiffUtilItemCallback<T>()
    private val dataListAsyncListDiffer: AsyncListDiffer<T> = AsyncListDiffer(this, itemCallback)

    init {
        vrList.forEach {
            vrMap[it.getRendererType()] = it
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        vrMap[viewType]?.let {
            return it.onCreate(parent)
        }
        throw RuntimeException("ViewRenderer not registered for this view type $viewType")
    }

    override fun getItemCount(): Int = dataListAsyncListDiffer.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // do nothing
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        val data = dataListAsyncListDiffer.currentList[position]
        if (payloads.isEmpty()) {
            vrMap[data.getType()]?.onBind(holder, position, data)
        } else {
            vrMap[data.getType()]?.onRebind(holder, position, data, payloads)
        }
    }

    override fun getItemViewType(position: Int): Int = dataListAsyncListDiffer.currentList[position].getType()

    fun setData(list: List<T>) {
        val newList = mutableListOf<T>()
        newList.addAll(list)
        dataListAsyncListDiffer.submitList(newList)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        tracker?.run {
            val item = dataListAsyncListDiffer.currentList.getOrNull(holder.adapterPosition)
            (item as? TrackRvData)?.takeUnless { it.tracked }?.run {
                trackImpression(item, holder.adapterPosition)
                onTracked()
            }
        }
        (holder as? ILifecycleAwareVH)?.onStart()
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        (holder as? ILifecycleAwareVH)?.onStop()
    }
}
