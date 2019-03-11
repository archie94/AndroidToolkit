package prava.arka.com.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.lang.RuntimeException

/**
 * Created by Arka Prava Basu <arka.basu@zomato.com> on 17/12/18.
 */
class RvAdapter<T : IRvData>(private val vrList: List<RvViewRenderer<T>>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val vrMap: HashMap<Int, IRvViewRenderer<T>> = HashMap()

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
        vrMap[vrList[position].getRendererType()]?.onBind(holder, position, data[position])
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
