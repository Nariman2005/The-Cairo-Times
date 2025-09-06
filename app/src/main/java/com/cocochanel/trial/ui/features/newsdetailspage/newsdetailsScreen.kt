package com.cocochanel.trial.ui.features.newsdetails

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.cocochanel.trial.LocalNavController
import com.cocochanel.trial.R
import com.cocochanel.trial.data.model.Doc
import com.cocochanel.trial.ui.features.newspage.NewsViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun newsdetailsScreen(
    articleIndex: Int,
    viewModel: NewsViewModel = viewModel()
) {
    val articles by viewModel.articles.collectAsState()

    if (articleIndex < articles.size) {
        val article = articles[articleIndex]
        val uriHandler = LocalUriHandler.current
        val fontFamily = FontFamily(Font(resId = R.font.fanwood_text))
        val fontFamily2 = FontFamily(Font(resId = R.font.tinos))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                modifier = Modifier
                    .padding(WindowInsets.safeDrawing.asPaddingValues())
                    .padding(horizontal = 16.dp),
                text = article.headline.main,
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.headlineLarge,
            )

            if (article.abstract.isNotBlank()) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = article.abstract,
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = fontFamily2,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            val imageUrl = getArticleImageUrl(article)
            if (imageUrl != null) {
                AsyncImage(
                    model = "https://static01.nyt.com/$imageUrl",
                    contentDescription = "Article image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                AsyncImage(
                    model = R.drawable.error,
                    contentDescription = "No image available",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    fontWeight = FontWeight.Bold,
                    text = buildAnnotatedString {
                        append("By: ")
                        withStyle(
                            style = SpanStyle(
                                textDecoration = TextDecoration.Underline
                            )
                        ) {
                            append(article.byline.original)
                        }
                    }
                )
            }

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = " ${formatPublishDate(article.pub_date)}"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = article.lead_paragraph,
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = fontFamily2
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { uriHandler.openUri(article.web_url) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text("Read Full Article on NY Times")
            }
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

fun formatPublishDate(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("MMM. d, yyyy", Locale.ENGLISH)
        val date = inputFormat.parse(dateString)
        outputFormat.format(date ?: return dateString)
    } catch (e: Exception) {
        dateString // fallback: just return original if parsing fails
    }
}