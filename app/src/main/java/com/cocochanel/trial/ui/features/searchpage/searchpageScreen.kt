package com.cocochanel.trial.ui.features.searchpage

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cocochanel.trial.LocalNavController
import com.cocochanel.trial.R
import com.cocochanel.trial.ui.features.newspage.NewsViewModel
import com.cocochanel.trial.ui.features.newspage.ArticleItem
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun Searchpage(viewModel: NewsViewModel = viewModel()) {
    val navController = LocalNavController.current
    val fontfamily = FontFamily(Font(R.font.frank_ruhl_libre_semibold))

    var searchQuery by remember { mutableStateOf("") }
    val allArticles by viewModel.articles.collectAsState()

    val filteredArticles = remember(searchQuery, allArticles) {
        if (searchQuery.isBlank()) {
            emptyList()
        } else {
            allArticles.filter { article ->
                article.headline.main.contains(searchQuery, ignoreCase = true) ||
                        article.snippet.contains(searchQuery, ignoreCase = true) ||
                        article.abstract.contains(searchQuery, ignoreCase = true) ||
                        article.keywords.any { keyword ->
                            keyword.value.contains(searchQuery, ignoreCase = true)
                        }
            }
        }
    }

    // Get popular keywords from all articles
    val popularKeywords = remember(allArticles) {
        allArticles
            .flatMap { it.keywords }
            .groupingBy { it.value }
            .eachCount()
            .toList()
            .sortedByDescending { it.second }
            .take(10)
            .map { it.first }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Header
        Row(
            modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Spacer(modifier = Modifier.width(125.dp))

            Text(
                text = "Search",
                style = MaterialTheme.typography.titleLarge,
                fontFamily = fontfamily,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Search TextField
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Search articles...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search")
            },
            singleLine = true
        )

        // Results
        when {
            searchQuery.isNotBlank() -> {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredArticles) { article ->
                        ArticleItem(
                            article = article,
                            onClick = { selectedArticle ->
                                val articleIndex = allArticles.indexOf(selectedArticle)
                                navController.navigate("newsdetailsScreen/$articleIndex")
                            }
                        )
                    }
                }
            }
            else -> {
                Spacer(modifier = Modifier.weight(1f))
            }
        }

        // FlowRow at bottom
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Popular Keywords",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                popularKeywords.forEach { keyword ->
                    SuggestionChip(
                        onClick = { searchQuery = keyword },
                        label = { Text(keyword) },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = Color(0xFFE3F2FD),
                            labelColor = Color(0xFF1976D2)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                }
            }
        }
    }
}