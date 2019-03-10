package prava.arka.com.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Arka Prava Basu <arka.basu@zomato.com> on 17/12/18.
 */
class RvAdapter<T : RecyclerView.ViewHolder>(private val vrList: List<RvViewRenderer<out IRvData>>)
    : RecyclerView.Adapter<T>() {

    val vrMap: HashMap<Int, IRvViewRenderer> = HashMap()

    private val data: MutableList<IRvData> = mutableListOf()

    init {
        vrList.forEach {
            vrMap[it.getRendererType()] = it
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {
        vrMap[viewType]?.let {

        }
        TODO()
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: T, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun setData(list: List<IRvData>) {
        this.data.clear()
        this.data.addAll(list)
        notifyDataSetChanged()
    }

    fun addData(data: IRvData, position: Int = this.data.size) {
        this.data.add(position, data)
    }
}
