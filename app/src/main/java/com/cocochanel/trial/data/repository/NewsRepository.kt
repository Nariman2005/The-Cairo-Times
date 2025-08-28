package com.cocochanel.trial.data.repository


import android.util.Log
import com.cocochanel.trial.data.api.NYTimesApiService
import com.cocochanel.trial.data.model.Doc
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class NewsRepository {
    private val loggingInterceptor = HttpLoggingInterceptor { message ->
        Log.d("API_LOG", message)
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl("https://api.nytimes.com/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NYTimesApiService::class.java)

    suspend fun getArticles(): List<Doc> {
        return try {
            Log.d("NewsRepository", "Making API call to NY Times")
            val response = api.getArticles(
                apiKey = "WUnPGjGyVAPmdhYrBlB36q8I61cnBOw1"
            )
            Log.d("NewsRepository", "API call successful. Articles count: ${response.response.docs.size}")
            response.response.docs
        } catch (e: java.net.UnknownHostException) {
            Log.e("NewsRepository", "Network error - cannot resolve host", e)
            throw Exception("Network connection failed. Please check your internet connection.")
        } catch (e: Exception) {
            Log.e("NewsRepository", "API call failed", e)
            throw Exception("Failed to load articles: ${e.localizedMessage}")
        }
    }
}