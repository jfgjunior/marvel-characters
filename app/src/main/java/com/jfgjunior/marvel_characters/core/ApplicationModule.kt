package com.jfgjunior.marvel_characters.core

import com.jfgjunior.marvel_characters.data.repository.Cache
import com.jfgjunior.marvel_characters.data.repository.MarvelCache
import com.jfgjunior.marvel_characters.data.repository.MarvelRepository
import com.jfgjunior.marvel_characters.data.repository.MarvelRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ApplicationModule {
    @Singleton
    @Binds
    fun bindCache(cache: MarvelCache): Cache

    @Singleton
    @Binds
    fun bindsMarvelRepository(repository: MarvelRepositoryImpl): MarvelRepository

    companion object {
        @Singleton
        @Provides
        fun provideMarvelApi(service: MarvelService): MarvelApi = service.provideMarvelApi()
    }
}