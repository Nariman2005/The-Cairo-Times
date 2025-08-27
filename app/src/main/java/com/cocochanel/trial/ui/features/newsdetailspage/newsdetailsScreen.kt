package com.cocochanel.trial.ui.features.newsdetails

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.cocochanel.trial.LocalNavController
import com.cocochanel.trial.data.model.Doc
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun newsdetailsScreen( articleJson: String) {

    val navController = LocalNavController.current
    val article = remember { Gson().fromJson(articleJson, Doc::class.java) }
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
//        // Top Bar
//        TopAppBar(
//            title = { Text("Article Details") },
//            navigationIcon = {
//                IconButton(onClick = { navController.navigateUp() }) {
//                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
//                }
//            }
//        )

        // Article Content
        Column(modifier = Modifier.padding(16.dp)) {
            // Article Image
            val imageUrl = getArticleImageUrl(article)
            if (imageUrl != null) {
                AsyncImage(
                    model = "https://static01.nyt.com/$imageUrl",
                    contentDescription = "Article image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Article Title
            Text(
                text = article.headline.main,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Article Metadata
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "By: ${article.byline.original}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Section: ${article.section_name}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Published: ${article.pub_date}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Article Abstract/Content
            if (article.`abstract`.isNotBlank()) {
                Text(
                    text = article.`abstract`,
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Keywords
            if (article.keywords.isNotEmpty()) {
                Text(
                    text = "Keywords:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(8.dp))

                article.keywords.take(5).forEach { keyword ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                    ) {
                        Text(
                            text = keyword.value,
                            modifier = Modifier.padding(12.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Read Full Article Button
            Button(
                onClick = { uriHandler.openUri(article.web_url) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Read Full Article on NY Times")
            }
        }
    }
}

private fun getArticleImageUrl(article: Doc): String? {
    val multimedia = article.multimedia
    if (multimedia.isEmpty()) return null

    val preferredTypes = listOf("articleLarge", "superJumbo", "jumbo", "mediumThreeByTwo210")

    for (type in preferredTypes) {
        val image = multimedia.find { it.subtype == type || it.crop_name == type }
        if (image != null) return image.url
    }

    return multimedia.firstOrNull()?.url
}