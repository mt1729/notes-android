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
import androidx.compose.foundation.layout.padding
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
import kotlinx.coroutines.launch

@Preview
@Composable
fun TaggingScenePreview() {
    TaggingScene(
        notes = listOf(
            Note(
                "Note preview", listOf(
                    Tag("Tag preview 1"),
                    Tag("Tag preview 2")
                )
            )
        ),
        selectedNoteText = "1 / 24",
        onNoteSelect = {}
    )
}

@Composable
fun TaggingScene(viewModel: ListDetailsViewModel = viewModel()) {
    val notes by viewModel.notes.collectAsState()
    val selectedNoteText by viewModel.selectedNoteText.collectAsState()
    val onNoteSelect = { noteIndex: Int -> viewModel.selectNote(noteIndex) }

    TaggingScene(notes, selectedNoteText, onNoteSelect)
}

// Preview-compatible overload
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TaggingScene(notes: List<Note>, selectedNoteText: String, onNoteSelect: (Int) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val notePagerState = rememberPagerState { notes.size }

    LaunchedEffect(key1 = notePagerState) {
        snapshotFlow { notePagerState.currentPage }.collect { pageIndex -> onNoteSelect(pageIndex) }
    }



    Scaffold(
        topBar = {
            TopAppBar(navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Sharp.Menu, contentDescription = "Back")
                }
            }, actions = {
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Sharp.Add, contentDescription = "Back")
                }
            }, title = {
                SearchBar(query = "", onQueryChange = {}, onSearch = {}, active = false, onActiveChange = {}) {

                }
            })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = {}) { Text(text = "Tag 1") }
                Button(onClick = {}) { Text(text = "Tag 2") }
                Button(onClick = {}) { Text(text = "Tag 3") }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = {}) { Text(text = "Tag 4") }
                Button(onClick = {}) { Text(text = "Tag 5") }
                Button(onClick = {}) { Text(text = "Tag 6") }
            }

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
                            imageVector = Icons.Sharp.ArrowForward,
                            contentDescription = "Next note"
                        )
                    }
                }

                HorizontalPager(
                    modifier = Modifier.fillMaxHeight(),
                    state = notePagerState
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
