package com.mt1729.notes.feature.listDetails

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.material.icons.sharp.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mt1729.notes.model.Note

@Preview
@Composable
fun ListDetailsView() {
    val notes = listOf(Note("This is the content of a note.", emptyList()))

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
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
            Button(onClick = {}) { Text(text = "+") }
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
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Sharp.ArrowBack, contentDescription = "Previous note")
                }
                Text(modifier = Modifier.align(Alignment.CenterVertically), text = "1 / X")
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Sharp.ArrowForward, contentDescription = "Next note")
                }
            }

            LazyRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(notes) { note ->
                    Card(
                        modifier = Modifier
                            .fillParentMaxSize(1f)
                            .padding(8.dp),
                        border = BorderStroke(1.dp, Color.Black)
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(24.dp)
                                .align(Alignment.Start),
                            text = note.content,
                            textAlign = TextAlign.Start,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}
