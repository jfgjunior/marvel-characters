package com.jfgjunior.marvel_characters.core

import com.jfgjunior.marvel_characters.data.dto.CharacterDataWrapperDTO
import com.jfgjunior.marvel_characters.data.dto.ComicDataWrapperDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {
    @GET("v1/public/characters")
    suspend fun characters(
        @Query("orderBy") orderBy: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): CharacterDataWrapperDTO

    @GET("v1/public/characters/{characterId}/comics")
    suspend fun comics(
        @Path("characterId") characterId: Int,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): ComicDataWrapperDTO
}