package prava.arka.com.ui

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

data class SimpleToolbarData(
    @Bindable val title: CharSequence,
    @Bindable val subtitle: CharSequence
) : BaseObservable()
