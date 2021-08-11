package me.estudos.githubrepositories.ui

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import me.estudos.githubrepositories.core.createProgressDialog
import me.estudos.githubrepositories.databinding.ActivityGithubRepoPageBinding

class GithubRepoPageActivity : AppCompatActivity() {
    private val binding by lazy { ActivityGithubRepoPageBinding.inflate(layoutInflater) }
    private val progressDialog by lazy { createProgressDialog() }
    private var githubRepoUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initToolbar()
        populateGithubRepo()
        startWebView()
    }

    private fun initToolbar() {
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun populateGithubRepo() {
        githubRepoUrl = intent.getStringExtra(URL_EXTRA)
    }

    private fun startWebView() {
        val webView = binding.wvRepoPage
        webView.settings.javaScriptEnabled = true
        progressDialog.show()
        webView.webViewClient = createWebViewClient()
        githubRepoUrl?.let { webView.loadUrl(it) }
    }

    private fun createWebViewClient() = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            if (progressDialog.isShowing) {
                progressDialog.dismiss()
            }
        }

        override fun onReceivedError(
            view: WebView,
            errorCode: Int,
            description: String,
            failingUrl: String
        ) {
            Toast.makeText(this@GithubRepoPageActivity, "Error:$description", Toast.LENGTH_SHORT)
                .show()
        }
    }

    companion object {
        const val URL_EXTRA: String = "REPO_URL"
    }
}
