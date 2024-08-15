package ch.thiercelin.contacts

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App(openDisplayContactActivity = {
                this.startActivity(Intent(this, DisplayContactActivity::class.java))
            })
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(openDisplayContactActivity =  {})
}