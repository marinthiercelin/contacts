package ch.thiercelin.contacts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import contacts.composeapp.generated.resources.Res
import contacts.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App(openDisplayContactActivity: () -> Unit) {
    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painterResource(Res.drawable.compose_multiplatform), null)
            Text("Welcome to the contact app")
            Button(onClick = { openDisplayContactActivity() }) {
                Text("Open contact")
            }
        }
    }
}