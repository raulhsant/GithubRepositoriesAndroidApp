package me.estudos.githubrepositories.data.repositories

import kotlinx.coroutines.flow.flow
import me.estudos.githubrepositories.data.services.GithubService

class GithubRepoRepositoryImpl(private val service: GithubService) : GithubRepoRepository {

    override suspend fun listRepositories(user: String) = flow {
        emit(service.listRepositories(user))
    }
}
