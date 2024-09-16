package com.mt1729.notes.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material.icons.sharp.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mt1729.notes.model.Tag
import com.mt1729.notes.viewmodel.TagSelectionViewModel
import com.mt1729.notes.viewmodel.TagSelectionViewModelI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.random.Random.Default.nextBoolean

@Preview
@Composable
fun TagSelectionPreview() {
    // todo: move test data
    val mockTags = (1..20).map { index ->
        val extraTxt = if (nextBoolean()) " Extra" else ""
        Tag(id = index.toLong(), name = "$extraTxt Tag $index", "#FF0000")
    }

    val vm = object : TagSelectionViewModelI {
        override val tags = MutableStateFlow(mockTags)
        override val selectedNote = MutableStateFlow(null)
        override val filteredTags = MutableStateFlow(mockTags)
        override val tagSearchQuery = MutableStateFlow("")
        override fun selectedNoteContainsTag(tagToFind: Tag) = nextBoolean()
    }
    TagSelectionView(vm = vm)
}

// Hilt-provided injection / run-time
@Composable
fun TagSelectionView(vm: TagSelectionViewModel) {
    TagSelectionView(vm = vm as TagSelectionViewModelI)
}

// Preview-compatible overload
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagSelectionView(vm: TagSelectionViewModelI) {
    val filteredTags by vm.filteredTags.collectAsState()
    val searchTagQuery by vm.tagSearchQuery.collectAsState()

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchBar(modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f),
                query = searchTagQuery,
                onQueryChange = { vm.filterTags(it) },
                onSearch = {},
                active = false,
                leadingIcon = {
                    Icon(imageVector = Icons.Sharp.Search, contentDescription = "Search")
                },
                shape = ShapeDefaults.Medium,
                onActiveChange = {}) {}

            IconButton(modifier = Modifier.padding(top = 8.dp), onClick = {
                vm.createTag(searchTagQuery)
            }) {
                Icon(imageVector = Icons.Sharp.Add, contentDescription = "Add Tag")
            }
        }

        LazyHorizontalStaggeredGrid(modifier = Modifier.height(128.dp),
            horizontalItemSpacing = 8.dp,
            rows = StaggeredGridCells.Fixed(count = 2),
            content = {
                items(filteredTags) { tag ->
                    // todo: test in UI
                    // todo: truncate both (half screen width?)
                    if (vm.selectedNoteContainsTag(tag)) {
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
    }
}
