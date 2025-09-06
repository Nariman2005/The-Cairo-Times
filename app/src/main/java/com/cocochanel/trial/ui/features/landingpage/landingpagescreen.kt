package com.cocochanel.trial.ui.features.landingpage


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cocochanel.trial.LocalNavController
import com.cocochanel.trial.R
import kotlinx.coroutines.delay

@Composable
fun CairoTimesLanding(
    viewModel: LandingPageViewModel = viewModel()
) {
    val nav = LocalNavController.current
    val fontfamily = FontFamily(Font(R.font.my_custom_font))
    val offsetY by animateDpAsState(
        targetValue = if (viewModel.startAnimation) 80.dp else 100.dp,
    )

    LaunchedEffect(Unit) {
        delay(5000L)
        nav.navigate("page2")
    }
    Box(modifier = Modifier.fillMaxSize()) {
        // Background image
        AnimatedVisibility(
            visible = viewModel.showImage,
            enter = fadeIn(animationSpec = tween(1000)),
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.image7),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = offsetY),
            contentAlignment = if (viewModel.startAnimation) Alignment.TopCenter else Alignment.Center
        ) {
            Text(
                text = "The Cairo Times",
                fontFamily = fontfamily,
                fontSize = 50.sp,
                color = Color.Black

            )
        }

    }
}