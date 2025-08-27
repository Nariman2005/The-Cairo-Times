package com.cocochanel.trial.ui.features.loginpage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cocochanel.trial.LocalNavController
import com.cocochanel.trial.R
import com.cocochanel.trial.ui.theme.Typography

@Composable
fun LoginPage(
    viewModel: LoginPageViewModel = viewModel()
) {
    val nav = LocalNavController.current
    val fontfamily = FontFamily(Font(R.font.my_custom_font))
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier.padding(vertical = 50.dp),
            text = "The Cairo Times",
            fontFamily = fontfamily,
            fontSize = 50.sp,
            style = Typography.titleLarge,
            color = Color.Black
        )

        // Rest of your existing code without fontFamily parameter
        Text(
            modifier = Modifier.padding(vertical = 50.dp),
            text = "Log in or create an account ",
            fontSize = 25.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier.padding(20.dp),
            fontSize = 20.sp,
            text = "By continuing, you agree to the Terms of Sale, Terms of Service, and Privacy Policy.",
            color = Color.Black,
        )

        OutlinedTextField(
            value = viewModel.username,
            onValueChange = { viewModel.onUsernameChange(it) },
            label = { Text("Username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (viewModel.onLoginClick()) {
                    nav.navigate("page3")
                }
            },
            modifier = Modifier.size(width = 150.dp, height = 50.dp),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            )

        ) {
            Text("Login", fontSize = 20.sp)
        }
        viewModel.errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}