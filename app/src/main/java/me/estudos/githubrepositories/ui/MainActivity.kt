package me.estudos.githubrepositories.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import me.estudos.githubrepositories.R
import me.estudos.githubrepositories.core.ClickItemGithubRepoListener
import me.estudos.githubrepositories.core.createDialog
import me.estudos.githubrepositories.core.createProgressDialog
import me.estudos.githubrepositories.core.hideSoftKeyboard
import me.estudos.githubrepositories.data.model.GithubRepo
import me.estudos.githubrepositories.databinding.ActivityMainBinding
import me.estudos.githubrepositories.presentation.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener,
    ClickItemGithubRepoListener {
    private val viewModel by viewModel<MainViewModel>()
    private val dialog by lazy { createProgressDialog() }
    private val adapter by lazy { GithubRepoListAdapter(this) }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)

        binding.rvRepositories.adapter = adapter

        viewModel.repositories.observe(this) {
            when (it) {
                is MainViewModel.State.Error -> {
                    createDialog {
                        setMessage(it.error.message)
                    }.show()
                    dialog.dismiss()
                }
                MainViewModel.State.Loading -> {
                    dialog.show()
                }
                is MainViewModel.State.Success -> {
                    dialog.dismiss()
                    adapter.submitList(it.list)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.maxWidth = Integer.MAX_VALUE
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { viewModel.getRepositories(it) }
        binding.root.hideSoftKeyboard()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun clickItemGithubRepo(githubRepo: GithubRepo) {
        val intent = Intent(this, GithubRepoPageActivity::class.java)
        intent.putExtra(GithubRepoPageActivity.URL_EXTRA, githubRepo.htmlURL)
        startActivity(intent)
    }
}
