package me.estudos.githubrepositories.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.estudos.githubrepositories.data.model.GithubRepo
import me.estudos.githubrepositories.databinding.ItemRepositoryBinding

class GithubRepoListAdapter :
    ListAdapter<GithubRepo, GithubRepoListAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GithubRepo) {
            binding.tvRepositoryName.text = item.name
            binding.tvRepositoryDescription.text = item.description
            binding.tvRepositoryLanguage.text = item.language
            binding.chipRepositoryStars.text = item.stargazersCount.toString()

            Glide.with(binding.root.context)
                .load(item.owner.avatarURL)
                .into(binding.ivOwner)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRepositoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DiffCallback : DiffUtil.ItemCallback<GithubRepo>() {
    override fun areItemsTheSame(oldItem: GithubRepo, newItem: GithubRepo) = oldItem == newItem
    override fun areContentsTheSame(oldItem: GithubRepo, newItem: GithubRepo) =
        oldItem.id == newItem.id
}
