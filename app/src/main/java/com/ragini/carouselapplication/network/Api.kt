package com.ragini.carouselapplication.network

import com.ragini.carouselapplication.model.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "7c561b768ada51e09580318262e98a71",
        @Query("page") page: Int
    ): Call<MoviesResponse>
}