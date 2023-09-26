package com.jfgjunior.marvel_characters.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.jfgjunior.marvel_characters.R
import com.jfgjunior.marvel_characters.ui.model.CharacterModel
import com.jfgjunior.marvel_characters.ui.shimmerEffect
import com.jfgjunior.marvel_characters.ui.theme.Gray1
import com.jfgjunior.marvel_characters.ui.theme.MarvelcharactersTheme
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    onCharacterClick: (characterId: Int, name: String) -> Unit
) {
    val viewModel: HomeViewModel = hiltViewModel()
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = stringResource(id = R.string.app_name))
            })
        }) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (val state = viewModel.state.collectAsState().value) {
                is HomeState.Initializing -> Loading()
                is HomeState.Characters -> CharactersGrid(
                    charactersFlow = state.characters,
                    onCharacterClick = onCharacterClick,
                )
            }
        }
    }
}

@Composable
fun Loading() {
    val config = LocalConfiguration.current
    val columns = config.screenWidthDp / ITEM_WIDTH.dp.value
    val height = ITEM_RATIO * ITEM_WIDTH.dp.value
    val lines = config.screenHeightDp / height
    val count = (columns * lines).toInt()
    LazyVerticalGrid(
        columns = GridCells.Adaptive(ITEM_WIDTH.dp),
        contentPadding = PaddingValues(ITEM_SPACING.dp),
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(ITEM_SPACING.dp),
        horizontalArrangement = Arrangement.spacedBy(ITEM_SPACING.dp),
        content = {
            items(
                count = count,
                itemContent = { _ -> LoadingItem() }
            )
        }
    )
}

@Composable
fun CharactersGrid(
    charactersFlow: Flow<PagingData<CharacterModel>>,
    onCharacterClick: (characterId: Int, name: String) -> Unit
) {
    val characters = charactersFlow.collectAsLazyPagingItems()
    when (characters.loadState.refresh) {
        is LoadState.Loading -> Loading()
        else -> Unit
    }
    val loadingCount = when (characters.loadState.append) {
        is LoadState.Loading -> 3
        else -> 0
    }
    val count = characters.itemCount + loadingCount
    LazyVerticalGrid(
        columns = GridCells.Adaptive(ITEM_WIDTH.dp),
        contentPadding = PaddingValues(ITEM_SPACING.dp),
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(ITEM_SPACING.dp),
        horizontalArrangement = Arrangement.spacedBy(ITEM_SPACING.dp),
        content = {
            items(count = count,
                key = {
                    if (it < characters.itemCount) {
                        characters[it]?.id ?: UUID.randomUUID()
                    } else UUID.randomUUID()
                },
                itemContent = { index ->
                    if (index < characters.itemCount) {
                        characters[index]?.let { model ->
                            CharacterItem(
                                character = model,
                                onCharacterClick = onCharacterClick,
                            )
                        }
                    } else {
                        LoadingItem()
                    }
                }
            )
        }
    )
}

@Composable
fun CharacterItem(
    character: CharacterModel,
    onCharacterClick: (characterId: Int, name: String) -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(ITEM_RATIO)
            .clip(shape = RoundedCornerShape(ITEM_CORNER_RADIUS.dp))
            .clickable { onCharacterClick(character.id, character.name) }
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Gray1),
            contentScale = ContentScale.FillBounds,
            model = character.imageUrl,
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.character_placeholder)
        )
        Text(
            text = character.name,
            textAlign = TextAlign.Center,
            maxLines = 2,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFFFFFFFF),
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(color = Color.Black.copy(0.6f))
                .padding(bottom = 4.dp, start = 4.dp, end = 4.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun LoadingItem() {
    Box(
        modifier = Modifier
            .aspectRatio(ITEM_RATIO)
            .clip(shape = RoundedCornerShape(ITEM_CORNER_RADIUS.dp))
            .shimmerEffect()
    )
}

@Preview
@Composable
fun LoadingItemPreview() {
    MarvelcharactersTheme {
        LoadingItem()
    }
}

const val ITEM_WIDTH = 100
const val ITEM_RATIO = 9.0f / 14
const val ITEM_CORNER_RADIUS = 10
const val ITEM_SPACING = 10