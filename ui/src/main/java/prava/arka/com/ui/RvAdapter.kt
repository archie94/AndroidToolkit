package prava.arka.com.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 17/12/18.
 */
class RvAdapter<T : IRvData, VR: RvViewRenderer<in T>>(vrList: List<VR>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val vrMap: HashMap<Int, VR> = HashMap()

    private val data: MutableList<T> = mutableListOf()

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

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        vrMap[data[position].getType()]?.onBind(holder, position, data[position])
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].getType()
    }

    fun setData(list: List<T>) {
        this.data.clear()
        this.data.addAll(list)
        notifyDataSetChanged()
    }

    fun addData(data: T, position: Int = this.data.size) {
        this.data.add(position, data)
    }
}
