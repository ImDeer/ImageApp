package com.example.imageapp.data

import com.example.imageapp.api.UnsplashApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnsplashRepository @Inject constructor(private val unsplashApi:UnsplashApi) {
}