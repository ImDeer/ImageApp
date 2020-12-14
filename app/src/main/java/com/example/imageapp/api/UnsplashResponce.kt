package com.example.imageapp.api

import com.example.imageapp.data.UnsplashPhoto

data class UnsplashResponce( // Unsplash responce envelope model
    val results: List<UnsplashPhoto>
)