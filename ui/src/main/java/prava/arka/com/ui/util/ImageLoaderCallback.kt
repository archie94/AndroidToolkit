package prava.arka.com.ui.util

import com.squareup.picasso.Callback
import java.lang.Exception

/**
 * Created by Arka Prava Basu <arka@ultrahuman.com> on 10/28/20.
 **/
interface ImageLoaderCallback : Callback {
    override fun onSuccess()

    override fun onError(e: Exception?)
}
