package com.cocochanel.trial.ui.features.newspage

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cocochanel.trial.data.model.Doc
import coil.compose.AsyncImage
import com.cocochanel.trial.R
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsPageScreen(navController: NavController, viewModel: NewsViewModel = viewModel()) {
    val fontfamily = FontFamily(Font(R.font.my_custom_font))
    val articles by viewModel.articles.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val newsDesks by viewModel.newsDesks.collectAsState()
    val selectedNewsDesk by viewModel.selectedNewsDesk.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, bottom = 16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                "Cairo Times News",
                fontSize = 35.sp,
                fontFamily = fontfamily
            )
        }

        // News Desk Navigation Bar
        if (newsDesks.isNotEmpty()) {
            NewsDeskNavigationBar(
                selectedNewsDesk = selectedNewsDesk,
                newsDesks = newsDesks,
                onNewsDeskSelected = { newsDesk ->
                    viewModel.filterByNewsDesk(newsDesk)
                }
            )
        }

        when {
            isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Text("Loading articles...", modifier = Modifier.padding(top = 16.dp))
                    }
                }
            }
            error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Error: $error")
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.fetchArticles() }) {
                            Text("Retry")
                        }
                    }
                }
            }
            articles.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        val displayText = if (selectedNewsDesk != null) {
                            "No articles found for '$selectedNewsDesk'"
                        } else {
                            "No articles found"
                        }
                        Text(displayText)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.fetchArticles() }) {
                            Text("Refresh")
                        }
                    }
                }
            }
            else -> {
                PullToRefreshBox(
                    isRefreshing = isLoading,
                    onRefresh = { viewModel.fetchArticles() },
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyColumn(modifier = Modifier.padding(16.dp)) {
                        items(articles) { article ->
                            ArticleItem(
                                article = article,
                                onClick = { selectedArticle ->
                                    val articleJson = Gson().toJson(selectedArticle)
                                    val encodedJson = Uri.encode(articleJson)
                                    navController.navigate("news_details/$encodedJson")
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NewsDeskNavigationBar(
    selectedNewsDesk: String?,
    newsDesks: List<String>,
    onNewsDeskSelected: (String?) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // "All" chip to show all articles
        item {
            NewsDeskChip(
                text = "All",
                isSelected = selectedNewsDesk == null,
                onClick = { onNewsDeskSelected(null) }
            )
        }

        // News desk chips
        items(newsDesks) { newsDesk ->
            NewsDeskChip(
                text = newsDesk.ifBlank { "Unknown" },
                isSelected = selectedNewsDesk == newsDesk,
                onClick = { onNewsDeskSelected(newsDesk) }
            )
        }
    }
}

@Composable
fun NewsDeskChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 6.dp else 2.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun ArticleItem(article: Doc, onClick: (Doc) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(article) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Article Image
            val imageUrl = getArticleImageUrl(article)
            if (imageUrl != null) {
                AsyncImage(
                    model = "https://static01.nyt.com/$imageUrl",
                    contentDescription = "Article image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Article Title
            Text(
                text = article.headline.main,
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Article Abstract
            if (article.`abstract`.isNotBlank()) {
                Text(
                    text = article.`abstract`,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Article Metadata including news_desk
            Text(
                text = "${article.byline.original} • ${article.news_desk} • ${article.section_name}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

fun getArticleImageUrl(article: Doc): String? {
    val multimedia = article.multimedia
    if (multimedia.isEmpty()) return null

    val preferredTypes = listOf("articleLarge", "superJumbo", "jumbo", "mediumThreeByTwo210")

    for (type in preferredTypes) {
        val image = multimedia.find { it.subtype == type || it.crop_name == type }
        if (image != null) return image.url
    }

    return multimedia.firstOrNull()?.url
}