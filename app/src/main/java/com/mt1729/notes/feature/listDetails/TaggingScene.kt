package com.mt1729.notes.feature.listDetails

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.material.icons.sharp.ArrowForward
import androidx.compose.material.icons.sharp.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mt1729.notes.model.Note
import com.mt1729.notes.model.Tag
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random.Default.nextBoolean

@Preview
@Composable
fun NoteTaggingPreview() {
    val mockTags = (1..20).map {
        val extraTxt = if (nextBoolean()) " Extra" else ""
        Tag("$extraTxt Tag $it")
    }
    val mockNotes = (1..3).map { Note("Note preview $it", mockTags) }
    val mockSelectedNote = mockNotes.firstOrNull()

    val vm = object : NoteViewModel {
        override val tags = MutableStateFlow(mockTags)
        override val notes = MutableStateFlow(mockNotes)
        override val selectedNote = MutableStateFlow(mockSelectedNote)
    }
    NoteTaggingScene(vm = vm)
}


// Hilt-provided injection / run-time
@Composable
fun NoteTaggingScene(vm: NoteTaggingViewModel = viewModel()) {
    NoteTaggingScene(vm = vm as NoteViewModel)
}

// Preview-compatible overload
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NoteTaggingScene(vm: NoteViewModel) {
    val tags by vm.tags.collectAsState()
    val notes by vm.notes.collectAsState()

    // todo: test in UI / local state
    val coroutineScope = rememberCoroutineScope()
    val notePagerState = rememberPagerState { notes.size }
    val selectedNoteText = "${notePagerState.currentPage + 1} / ${notes.size}"

    LaunchedEffect(key1 = notePagerState) {
        snapshotFlow {
            notePagerState.currentPage
        }.collect { pageIndex -> vm.selectNote(pageIndex) }
    }

    Scaffold(topBar = {
        TopAppBar(navigationIcon = {
            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Sharp.Menu, contentDescription = "Add Tag")
            }
        }, actions = {
            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Sharp.Add, contentDescription = "Menu")
            }
        }, title = {
            SearchBar(query = "",
                onQueryChange = {},
                onSearch = {},
                active = false,
                onActiveChange = {}) {

            }
        })
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            LazyHorizontalStaggeredGrid(modifier = Modifier.height(128.dp),
                horizontalItemSpacing = 8.dp,
                rows = StaggeredGridCells.Fixed(count = 2),
                content = {
                    items(tags) { tag ->
                        if (nextBoolean()) {
                            Button(modifier = Modifier.wrapContentHeight(), onClick = {
                                vm.removeTagFromSelectedNote(tag)
                            }) {
                                Text(text = tag.name)
                            }
                        } else {
                            OutlinedButton(modifier = Modifier.wrapContentHeight(), onClick = {
                                vm.addTagToSelectedNote(tag)
                            }) {
                                Text(text = tag.name)
                            }
                        }
                    }
                })

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            notePagerState.animateScrollToPage(notePagerState.currentPage - 1)
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Sharp.ArrowBack,
                            contentDescription = "Previous note"
                        )
                    }
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = selectedNoteText
                    )
                    IconButton(onClick = {
                        coroutineScope.launch {
                            notePagerState.animateScrollToPage(notePagerState.currentPage + 1)
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Sharp.ArrowForward, contentDescription = "Next note"
                        )
                    }
                }

                HorizontalPager(
                    modifier = Modifier.fillMaxHeight(), state = notePagerState
                ) { pageIndex ->
                    val currNote = notes[pageIndex]

                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        border = BorderStroke(1.dp, Color.Black)
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(24.dp)
                                .align(Alignment.Start),
                            text = currNote.content,
                            textAlign = TextAlign.Start,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}
