package com.mt1729.notes.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

// todo: local persistence DI module / Room
class NoteRepository {
    fun getNotes(): Flow<List<Note>> = flow {
        // todo: move test data
        val notes = listOf(
            Note(
                "This is the content of a short note.", listOf(
                    Tag("Tag 1"),
                    Tag("Tag 2"),
                    Tag("Tag 3"),
                    Tag("Tag 4"),
                    Tag("Tag 5"),
                    Tag("Tag 6"),
                )
            ), Note(
                "QWEL QWLEJK QLWKEJ ASDL JAS POCXAJC OPIW JQWOIEJ QOW PIJACLSKC LKQJWN KJQNSCK" + "AJNSCK AJNSD QNWE IUNQWE UI@#()@#U )(@U#$ )!(U@ )I@H#O @# U$(*E@H WSC(H " + "IOUHAOIUHWDIUVSDVIU HSOI&\$Y *W&H*&H ALKSJD QWO QIWE J ALKSDJ ALSKD JQOWIE JOSIJ " + "OCIQJOCIJQOWICJQOIWJEASKC2034203948203984203984",
                listOf(
                    Tag("Tag with a medium length name 1"),
                    Tag("Tag with a medium length name 2"),
                    Tag("Tag with a medium length name 3"),
                    Tag("Tag with a medium length name 4")
                )
            ), Note(
                "Another note", listOf(
                    Tag("Looooooooooooooooooooooooooooooooooooooooooooooooooong tag name 1"),
                    Tag("Loooooooooooooooooooooooooooooooooooooooooooooooooooooonger tag name ")
                )
            )
        )

        emit(notes.shuffled())
    }

    fun updateNote(note: Note) {}
    fun deleteNote(note: Note) {}
    fun addNote(note: Note) {}
}
