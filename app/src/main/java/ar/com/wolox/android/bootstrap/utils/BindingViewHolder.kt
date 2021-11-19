package ar.com.wolox.android.bootstrap.utils

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BindingViewHolder<T, out V : ViewDataBinding>(val binding: V) :
    RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(item: T)
}
