package com.jfgjunior.marvel_characters.data.repository.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jfgjunior.marvel_characters.core.MarvelApi
import com.jfgjunior.marvel_characters.data.dto.ComicDTO
import com.jfgjunior.marvel_characters.data.repository.Cache
import javax.inject.Inject

class ComicsDataSource @Inject constructor(
    private val api: MarvelApi,
    private val cache: Cache,
    private val characterId: Int,
) : PagingSource<Int, ComicDTO>() {
    private var totalItems = 0

    override fun getRefreshKey(state: PagingState<Int, ComicDTO>) =
        state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ComicDTO> {
        return try {
            val page = params.key ?: INITIAL_PAGE
            val response = cache.getComics(page, characterId) ?: api.comics(
                characterId = characterId,
                limit = LIMIT,
                offset = page * LIMIT
            ).also {
                cache.putComics(page, characterId, it)
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
