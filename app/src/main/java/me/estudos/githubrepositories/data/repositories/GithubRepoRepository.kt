package me.estudos.githubrepositories.data.repositories

import kotlinx.coroutines.flow.Flow
import me.estudos.githubrepositories.data.model.GithubRepo

interface GithubRepoRepository {
    suspend fun listRepositories(user: String) : Flow<List<GithubRepo>>
}
