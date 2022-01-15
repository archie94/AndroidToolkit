package prava.arka.com.ui

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 6/2/19.
 */
abstract class RvViewRenderer<T : IRvData> : IRvViewRenderer<T> {

    abstract fun getRendererType(): Int
}
