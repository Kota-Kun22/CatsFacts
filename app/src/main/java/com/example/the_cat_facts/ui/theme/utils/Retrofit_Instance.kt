package com.example.the_cat_facts.ui.theme.utils

import com.example.the_cat_facts.Data.ApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Retrofit_Instance {
    val api: ApiInterface by lazy {
        Retrofit
            .Builder()
            .baseUrl(Util.BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}