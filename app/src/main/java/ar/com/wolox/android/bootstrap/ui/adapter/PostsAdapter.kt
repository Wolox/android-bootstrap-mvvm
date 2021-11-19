package ar.com.wolox.android.bootstrap.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ar.com.wolox.android.bootstrap.databinding.ViewholderPostBinding
import ar.com.wolox.android.bootstrap.model.Post
import ar.com.wolox.android.bootstrap.utils.BindingViewHolder
import ar.com.wolox.android.bootstrap.utils.extensions.removeLineBreaks

class PostsAdapter:
    ListAdapter<Post, BindingViewHolder<Post, ViewholderPostBinding>>(diffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<Post, ViewholderPostBinding> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewholderPostBinding.inflate(layoutInflater)
        return PostViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(
        holder: BindingViewHolder<Post, ViewholderPostBinding>,
        position: Int
    ) {
        with(holder) {
            with(binding.divider) {
                visibility = if (isLastPosition(position)) {
                    View.INVISIBLE
                } else {
                    View.VISIBLE
                }
                bind(getItem(position))
            }
        }
    }

    private fun isLastPosition(position: Int) = (itemCount - 1 == position)

    inner class PostViewHolder(
        binding: ViewholderPostBinding,
        private val context: Context
    ): BindingViewHolder<Post, ViewholderPostBinding>(binding) {

        override fun bind(item: Post) {
            with(binding) {
                title.text = item.title
                body.text = item.body.removeLineBreaks()
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
                oldItem == newItem
        }
    }
}
