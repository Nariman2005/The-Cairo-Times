package com.cocochanel.trial.data.api

import com.cocochanel.trial.data.model.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NYTimesApiService {
    @GET("svc/archive/v1/2024/1.json")
    suspend fun getArticles(
        @Query("api-key") apiKey: String
    ): Response
}