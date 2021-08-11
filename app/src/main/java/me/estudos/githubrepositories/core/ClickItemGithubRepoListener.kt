package me.estudos.githubrepositories.core

import me.estudos.githubrepositories.data.model.GithubRepo

interface ClickItemGithubRepoListener {
    fun clickItemGithubRepo(githubRepo: GithubRepo)
}
