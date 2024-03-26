package com.example.the_cat_facts

import android.net.http.HttpException
import android.os.Build
import android.os.Bundle
import android.os.ext.SdkExtensions
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.the_cat_facts.ui.theme.The_Cat_FactsTheme
import com.example.the_cat_facts.ui.theme.models.CatFacts
import com.example.the_cat_facts.ui.theme.utils.Retrofit_Instance
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class MainActivity : ComponentActivity() {

    private var fact= mutableStateOf(CatFacts())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            The_Cat_FactsTheme {
                // https://catfact.ninja/fact
                Surface(
                    modifier = Modifier.fillMaxSize()
                        .clickable {   sendResponse() },
                    color = MaterialTheme.colorScheme.background
                ){


                    sendResponse()


                    MyUi(fact = fact)

                }
            }
        }
    }



    @OptIn(DelicateCoroutinesApi::class)
    fun sendResponse() {


        GlobalScope.launch(Dispatchers.IO) {


            val response = try {
                Retrofit_Instance.api.getRandomFact()
            } catch (e: HttpException) {
                Toast.makeText(applicationContext, "http error: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
                return@launch
            } catch (e: IOException) {
                Toast.makeText(applicationContext, "app error: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
                return@launch
            }
            if (response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                    fact.value = response.body()!!

                }
            }
        }
    }
}

@Composable
fun MyUi(fact: MutableState<CatFacts>,modifier: Modifier = Modifier) {
    Column(modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Cat Fact:", modifier.padding(bottom = 25.dp), fontSize = 26.sp)
        Text(
            text = fact.value.fact, fontSize = 26.sp,
            lineHeight = 40.sp,
            textAlign = TextAlign.Center
        )
    }
}