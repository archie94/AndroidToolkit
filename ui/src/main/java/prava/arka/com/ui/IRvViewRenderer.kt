package prava.arka.com.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 5/2/19.
 */
interface IRvViewRenderer<T : IRvData> {
    fun onCreate(parent: ViewGroup): RecyclerView.ViewHolder

    fun onBind(holder: RecyclerView.ViewHolder, position: Int, data: T)
}
