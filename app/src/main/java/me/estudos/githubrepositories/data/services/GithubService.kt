package me.estudos.githubrepositories.data.services

import me.estudos.githubrepositories.data.model.GithubRepo
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @GET("users/{user}/repos")
    suspend fun listRepositories(@Path("user") user: String): List<GithubRepo>
}
