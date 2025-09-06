package com.cocochanel.trial.ui.features.searchpage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cocochanel.trial.LocalNavController
import com.cocochanel.trial.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Searchpage(){
    val navController = LocalNavController.current

    val fontfamily=FontFamily(Font(R.font.frank_ruhl_libre_semibold))
    Column(modifier = Modifier.fillMaxSize()) {
Row(
    modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
    verticalAlignment = Alignment.CenterVertically,
){

    IconButton(onClick = { navController.popBackStack() }) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back"
        )
    }
    // The width of the spacer would ideally match the width of the IconButton
    Spacer(modifier = Modifier.width(125.dp))

    Text(
        text = "Search",
        style = MaterialTheme.typography.titleLarge,
        fontFamily=fontfamily,
        fontSize=25.sp,
        fontWeight= FontWeight.Bold
    )



}

    }
}
