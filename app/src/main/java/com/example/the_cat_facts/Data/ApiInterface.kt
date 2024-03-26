package com.example.the_cat_facts.Data

import com.example.the_cat_facts.ui.theme.models.CatFacts
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("/fact")
    suspend fun getRandomFact():Response<CatFacts>


}