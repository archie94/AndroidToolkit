package prava.arka.com.ui

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 6/2/19.
 */
abstract class RvViewRenderer<T : IRvData>: IRvViewRenderer {

    abstract fun getRendererType(): Int
}
