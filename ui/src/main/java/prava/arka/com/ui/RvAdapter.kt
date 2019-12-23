package prava.arka.com.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 17/12/18.
 */
class RvAdapter<T : IRvData, VR: RvViewRenderer<in T>>(vrList: List<VR>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
        throw RuntimeException("ViewRenderer not registered for this view type")
    }

    override fun getItemCount(): Int = dataListAsyncListDiffer.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = dataListAsyncListDiffer.currentList[position]
        vrMap[data.getType()]?.onBind(holder, position, data)
    }

    override fun getItemViewType(position: Int): Int = dataListAsyncListDiffer.currentList[position].getType()

    fun setData(list: List<T>) {
        val newList = mutableListOf<T>()
        newList.addAll(list)
        dataListAsyncListDiffer.submitList(newList)
    }
}
