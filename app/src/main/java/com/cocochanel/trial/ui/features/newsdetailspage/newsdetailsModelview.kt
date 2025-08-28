//package com.cocochanel.trial.ui.features.newsdetails
//
//import android.util.Log
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.cocochanel.trial.data.model.Doc
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//class newsdetailsModelview : ViewModel() {
//
//    private val _article = MutableStateFlow<Doc?>(null)
//    val article: StateFlow<Doc?> = _article
//
//    private val _isLoading = MutableStateFlow(false)
//    val isLoading: StateFlow<Boolean> = _isLoading
//
//    private val _error = MutableStateFlow<String?>(null)
//    val error: StateFlow<String?> = _error
//
//    fun setArticle(article: Doc) {
//        viewModelScope.launch {
//            try {
//                _isLoading.value = true
//                _error.value = null
//                _article.value = article
//                Log.d("NewsDetailsViewModel", "Article loaded: ${article.headline.main}")
//            } catch (e: Exception) {
//                Log.e("NewsDetailsViewModel", "Error loading article", e)
//                _error.value = "Failed to load article details: ${e.message}"
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }
//}