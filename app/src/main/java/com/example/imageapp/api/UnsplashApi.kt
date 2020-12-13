package com.example.imageapp.api

import android.os.Build
import com.example.imageapp.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApi { // needs a retrofit object which Dagger will create and inject where needed

    companion object { // "companion" makes the variables static
        const val BASE_URL = "https://api.unsplash.com/"
        const val CLIENT_ID = BuildConfig.UNSPLASH_ACCESS_KEY
    }

    @Headers("Accept-Version: v1", "Authorization: Client-ID $CLIENT_ID")
    @GET("search/photos")
    suspend fun searchPhotos( // suspending function can be paused which helps handle threading,
        // which will be taken care of by Paging 3 automatically
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UnsplashResponce
}