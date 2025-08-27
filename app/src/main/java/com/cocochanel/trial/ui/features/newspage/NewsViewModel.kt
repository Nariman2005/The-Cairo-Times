package com.cocochanel.trial.ui.features.newspage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cocochanel.trial.data.model.Doc
import com.cocochanel.trial.data.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    private val repository = NewsRepository()

    private val _articles = MutableStateFlow<List<Doc>>(emptyList())
    val articles: StateFlow<List<Doc>> = _articles

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // All articles from API (before filtering)
    private val _allArticles = MutableStateFlow<List<Doc>>(emptyList())

    // Distinct news desk values
    private val _newsDesks = MutableStateFlow<List<String>>(emptyList())
    val newsDesks: StateFlow<List<String>> = _newsDesks

    // Current selected news desk (null means show all)
    private val _selectedNewsDesk = MutableStateFlow<String?>(null)
    val selectedNewsDesk: StateFlow<String?> = _selectedNewsDesk

    init {
        fetchArticles()
    }

    fun fetchArticles() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                Log.d("NewsViewModel", "Fetching articles")

                val articles = repository.getArticles()
                _allArticles.value = articles
                _articles.value = articles

                // Extract distinct news desk values
                extractNewsDesks(articles)

                Log.d("NewsViewModel", "Received ${articles.size} articles")

                if (articles.isEmpty()) {
                    _error.value = "No articles found"
                }
            } catch (e: Exception) {
                Log.e("NewsViewModel", "Error fetching articles", e)
                _error.value = "Failed to load articles: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun filterByNewsDesk(newsDesk: String?) {
        _selectedNewsDesk.value = newsDesk

        if (newsDesk == null) {
            // Show all articles
            _articles.value = _allArticles.value
            Log.d("NewsViewModel", "Showing all ${_allArticles.value.size} articles")
        } else {
            // Filter by news desk
            val filteredArticles = _allArticles.value.filter { article ->
                article.news_desk.equals(newsDesk, ignoreCase = true)
            }
            _articles.value = filteredArticles
            Log.d("NewsViewModel", "Filtered to ${filteredArticles.size} articles for news desk: $newsDesk")
        }
    }

    private fun extractNewsDesks(articles: List<Doc>) {
        val distinctNewsDesks = articles
            .map { it.news_desk.trim() }
            .filter { it.isNotBlank() }
            .distinct()
            .sorted()

        _newsDesks.value = distinctNewsDesks
        Log.d("NewsViewModel", "Extracted news desks: $distinctNewsDesks")
    }
}