package prava.arka.com.ui

import android.os.Handler
import android.text.Editable
import android.text.TextWatcher

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 20/10/19.
 */
abstract class DebounceTextWatcher(
    private val delayMS: Long = 300L
) : TextWatcher {

    private var text: Editable? = null
    private val runnable = Runnable {
        textChanged(text)
    }
    private val handler = Handler()

    override fun afterTextChanged(s: Editable?) {
        text = s
        handler.removeCallbacks(runnable)
        handler.postDelayed(runnable, delayMS)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

    abstract fun textChanged(s: Editable?)
}
