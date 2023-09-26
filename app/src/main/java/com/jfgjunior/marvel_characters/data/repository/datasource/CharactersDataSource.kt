package com.jfgjunior.marvel_characters.data.repository.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jfgjunior.marvel_characters.core.MarvelApi
import com.jfgjunior.marvel_characters.data.dto.CharacterDTO
import com.jfgjunior.marvel_characters.data.repository.Cache
import javax.inject.Inject

class CharactersDataSource @Inject constructor(
    private val api: MarvelApi,
    private val cache: Cache,
) : PagingSource<Int, CharacterDTO>() {
    private var totalItems = 0

    override fun getRefreshKey(state: PagingState<Int, CharacterDTO>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterDTO> {
        return try {
            val page = params.key ?: INITIAL_PAGE
            val response = cache.getCharacters(page) ?: api.characters(
                orderBy = "name",
                limit = LIMIT,
                offset = page * LIMIT
            ).also {
                cache.putCharacters(page, it)
            }
            totalItems += response.data?.results?.size ?: 0
            LoadResult.Page(
                data = response.data?.results ?: listOf(),
                prevKey = if (page == INITIAL_PAGE) null else page - 1,
                nextKey = if ((response.data?.total ?: 0) <= totalItems) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    companion object {
        private const val INITIAL_PAGE = 0
        private const val LIMIT = 20
    }
}