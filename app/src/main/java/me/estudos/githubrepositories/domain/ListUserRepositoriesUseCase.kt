package me.estudos.githubrepositories.domain

import kotlinx.coroutines.flow.Flow
import me.estudos.githubrepositories.core.UseCase
import me.estudos.githubrepositories.data.model.GithubRepo
import me.estudos.githubrepositories.data.repositories.GithubRepoRepository

class ListUserRepositoriesUseCase(private val repository: GithubRepoRepository) :
    UseCase<String, List<GithubRepo>>() {

    override suspend fun execute(param: String): Flow<List<GithubRepo>> {
        return repository.listRepositories(param)
    }
}
