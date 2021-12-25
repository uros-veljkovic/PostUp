package com.urkeev14.myapplication.feature.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.urkeev14.myapplication.data.source.local.entity.TypicodePostEntity
import com.urkeev14.myapplication.databinding.ViewPostSmallBinding

class PostsAdapter(
    private var list: List<TypicodePostEntity>,
    private val listener: Callback,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface Callback {
        fun onPostClick(id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewPostSmallBinding.inflate(inflater, parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        (holder as PostViewHolder).bind(item)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    fun setList(list: List<TypicodePostEntity>) {
        this.list = list;
        notifyDataSetChanged()
    }

    inner class PostViewHolder(private val binding: ViewPostSmallBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(postEntity: TypicodePostEntity) = with(binding) {
            titleTextView.text = postEntity.title
            contentTextView.text = postEntity.content
            root.setOnClickListener {
                listener.onPostClick(postEntity.id)
            }
        }
    }
}