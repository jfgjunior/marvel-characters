package com.jfgjunior.marvel_characters.ui.character

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.jfgjunior.marvel_characters.R
import com.jfgjunior.marvel_characters.ui.model.ComicModel
import com.jfgjunior.marvel_characters.ui.shimmerEffect
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterPage(name: String, onBackPressed: () -> Unit) {
    val viewModel: CharacterViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = name) },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                },
            )
        },
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (val state = viewModel.state.collectAsState().value) {
                is CharacterState.Loading -> Loading()
                is CharacterState.Content -> Content(state)
            }
        }
    }
}

@Composable
fun Loading() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun Content(content: CharacterState.Content) {
    Column {
        Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = content.character.description.ifEmpty {
                stringResource(
                    id = R.string.no_character_description
                )
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(bottom = 5.dp),
            text = stringResource(id = R.string.comics_title),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
        ComicList(comicsFlow = content.comicsFlow)
    }
}

@Composable
fun ComicList(comicsFlow: Flow<PagingData<ComicModel>>) {

    val items = comicsFlow.collectAsLazyPagingItems()
    if (items.loadState.refresh is LoadState.Loading) {
        Row(
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
        ) {
            ComicItemLoading()
            ComicItemLoading()
        }
    } else {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            contentPadding = PaddingValues(horizontal = 10.dp)
        ) {
            items(
                count = items.itemCount,
                key = { items[it]?.id ?: 0 }
            ) {
                items[it]?.let { comic -> ComicItem(comic = comic) }
            }
        }
    }
}

@Composable
fun ComicItem(comic: ComicModel) {
    Surface(
        modifier = Modifier
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp)),
    ) {
        Row(
            modifier = Modifier
                .width((LocalConfiguration.current.screenWidthDp * 0.8).dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(150.dp)
                    .aspectRatio(9.0f / 12),
                model = comic.imageUrl,
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(10.dp),
                text = comic.description.ifEmpty {
                    stringResource(
                        id = R.string.no_comic_description
                    )
                },
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                fontSize = 14.sp,
            )
        }
    }
}

@Composable
fun ComicItemLoading() {
    Surface(
        modifier = Modifier
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp)),
    ) {
        Row(
            modifier = Modifier
                .width((LocalConfiguration.current.screenWidthDp * 0.8).dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .aspectRatio(9.0f / 12)
                    .shimmerEffect(),
            )
            Column {
                Box(
                    modifier = Modifier
                        .height(16.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .padding(top = 10.dp)
                        .shimmerEffect()
                )
                Box(
                    modifier = Modifier
                        .height(16.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .padding(top = 10.dp)
                        .shimmerEffect()
                )
                Box(
                    modifier = Modifier
                        .height(16.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .padding(top = 10.dp)
                        .shimmerEffect()
                )
            }
        }
    }
}
