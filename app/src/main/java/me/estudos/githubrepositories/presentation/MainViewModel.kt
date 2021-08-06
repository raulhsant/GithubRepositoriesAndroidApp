package me.estudos.githubrepositories.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import me.estudos.githubrepositories.data.model.GithubRepo
import me.estudos.githubrepositories.domain.ListUserRepositoriesUseCase

class MainViewModel(private val listUserRepositoriesUseCase: ListUserRepositoriesUseCase) :
    ViewModel() {

    private val _repositories = MutableLiveData<State>()
    val repositories: LiveData<State> = _repositories

    fun getRepositories(user: String) {
        viewModelScope.launch {
            listUserRepositoriesUseCase(user).onStart {
                _repositories.postValue(State.Loading)
            }.catch {
                _repositories.postValue(State.Error(it))

            }.collect {
                _repositories.postValue(State.Success(it))
            }
        }
    }

    sealed class State {
        object Loading : State()
        data class Success(val list: List<GithubRepo>) : State()
        data class Error(val error: Throwable) : State()
    }
}
