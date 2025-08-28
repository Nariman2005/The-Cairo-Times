package com.cocochanel.trial.ui.features.newsdetails

import android.R.attr.onClick
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
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
// Removed: import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.cocochanel.trial.LocalNavController
import com.cocochanel.trial.R
import com.cocochanel.trial.data.model.Doc
import com.google.gson.Gson
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale // Added import for java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun newsdetailsScreen( articleJson: String) {

    val navController = LocalNavController.current
    val article = remember { Gson().fromJson(articleJson, Doc::class.java) }
    val uriHandler = LocalUriHandler.current
    val fontFamily = FontFamily(Font(resId = R.font.fanwood_text))
    val fontFamily2 = FontFamily(Font(resId = R.font.tinos))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            // .padding(16.dp) // Removed padding from here
    ) {


        Text(
            modifier = Modifier
                .padding(WindowInsets.safeDrawing.asPaddingValues())
                .padding(horizontal = 16.dp), // Added horizontal padding
            text = article.headline.main,
            fontFamily = fontFamily,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.headlineLarge,
        )


        if (article.`abstract`.isNotBlank()) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp), // Added horizontal padding
                text = article.`abstract`,
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
            contentScale = ContentScale.Crop // Added ContentScale.Crop here
        )
    }else{
        AsyncImage(
            model = R.drawable.error, // your drawable
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
                modifier = Modifier.size(20.dp) // adjust size as you like
            )

            Spacer(modifier = Modifier.width(6.dp)) // space between icon and text

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

//
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp), // Added horizontal padding
                        text = " ${formatPublishDate(article.pub_date)}" // Called formatPublishDate
                    )

            Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier.padding(horizontal = 16.dp), // Added horizontal padding
            text= article.lead_paragraph,
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = fontFamily2
        )

        Spacer(modifier = Modifier.height(16.dp))


//            // Keywords
//            if (article.keywords.isNotEmpty()) {
//                Text(
//                    modifier = Modifier.padding(horizontal = 16.dp), // Added horizontal padding
//                    text = "Keywords:",
//                    style = MaterialTheme.typography.titleMedium,
//                    fontWeight = FontWeight.Medium
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//
//                article.keywords.take(5).forEach { keyword ->
//                    Card(
//                        modifier = Modifier
//                            .padding(horizontal = 16.dp, vertical = 4.dp)
//                            .fillMaxWidth(),
//                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0)),
//                        shape = RoundedCornerShape(8.dp)
//
//                    ) {
//                        Text(
//                            text = keyword.value,
//                            modifier = Modifier.padding(12.dp),
//                            style = MaterialTheme.typography.bodyMedium
//                        )
//                    }
//                }
//                Spacer(modifier = Modifier.height(16.dp))
//            }

            // Read Full Article Button
            Button(
                onClick = { uriHandler.openUri(article.web_url) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp) // Added horizontal padding
                    .padding(bottom = 16.dp), // Optional: add bottom padding if needed
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


fun formatPublishDate(dateString: String): String {
    return try {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ", java.util.Locale.ENGLISH)
        val parsedDate = OffsetDateTime.parse(dateString, inputFormatter)
        val outputFormatter = DateTimeFormatter.ofPattern("MMM. d, yyyy", java.util.Locale.ENGLISH)
        parsedDate.format(outputFormatter)
    } catch (e: Exception) {
        dateString // fallback: just return original if parsing fails
    }
}
