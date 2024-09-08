package com.mt1729.notes.model

import com.mt1729.notes.model.database.NoteEntity
import com.mt1729.notes.model.database.NoteTagJoin
import com.mt1729.notes.model.database.TagEntity

val tmpTags = mutableListOf(
    Tag(name = "Tag 1"),
    Tag(name = "Tag 2"),
    Tag(name = "Tag 3"),
    Tag(name = "Tag 4"),
    Tag(name = "Tag 5"),
    Tag(name = "Tag 6"),
    Tag(name = "Tag with a medium length name 1"),
    Tag(name = "Tag with a medium length name 2"),
    Tag(name = "Tag with a medium length name 3"),
    Tag(name = "Tag with a medium length name 4"),
    Tag(name = "Looooooooooooooooooooooooooooooooooooooooooooooooooong tag name 1"),
    Tag(name = "Loooooooooooooooooooooooooooooooooooooooooooooooooooooonger tag name "),
)

val tmpTagEntities = arrayOf(
    TagEntity(tagId = 1, name = "Tag 1", colorHex = "#FF0000"),
    TagEntity(tagId = 2, name = "Tag 2", colorHex = "#00FF00"),
    TagEntity(tagId = 3, name = "Tag 3", colorHex = "#0000FF"),
    TagEntity(tagId = 4, name = "Tag 4", colorHex = "#FF0000"),
    TagEntity(tagId = 5, name = "Tag 5", colorHex = "#00FF00"),
    TagEntity(tagId = 6, name = "Tag 6", colorHex = "#0000FF"),
    TagEntity(
        tagId = 7,
        name = "Tag with a medium length name 1",
        colorHex = "#FF0000"
    ),
    TagEntity(
        tagId = 8,
        name = "Tag with a medium length name 2",
        colorHex = "#00FF00"
    ),
    TagEntity(
        tagId = 9,
        name = "Tag with a medium length name 3",
        colorHex = "#0000FF"
    ),
    TagEntity(
        tagId = 10,
        name = "Tag with a medium length name 4",
        colorHex = "#FF0000"
    ),
    TagEntity(
        tagId = 11,
        name = "Looooooooooooooooooooooooooooooooooooooooooooooooooong tag name 1",
        colorHex = "#00FF00"
    ),
    TagEntity(
        tagId = 12,
        name = "Loooooooooooooooooooooooooooooooooooooooooooooooooooooonger tag name ",
        colorHex = "#0000FF"
    ),
)

val tmpNoteEntities = arrayOf(
    NoteEntity(
        noteId = 1,
        title = "Title 1",
        content = "This is the content of a short note.",
    ), NoteEntity(
        noteId = 2,
        title = "Title 2",
        content = "QWEL QWLEJK QLWKEJ ASDL JAS POCXAJC OPIW JQWOIEJ QOW PIJACLSKC LKQJWN KJQNSCK" + "AJNSCK AJNSD QNWE IUNQWE UI@#()@#U )(@U#$ )!(U@ )I@H#O @# U$(*E@H WSC(H " + "IOUHAOIUHWDIUVSDVIU HSOI&\$Y *W&H*&H ALKSJD QWO QIWE J ALKSDJ ALSKD JQOWIE JOSIJ " + "OCIQJOCIJQOWICJQOIWJEASKC2034203948203984203984",
    ), NoteEntity(
        noteId = 3,
        title = "Title 3",
        content = "Another note",
    )
)

val tmpNoteTagJoins = arrayOf(
    NoteTagJoin(1, 1),
    NoteTagJoin(1, 2),
    NoteTagJoin(1, 3),
    NoteTagJoin(1, 4),
    NoteTagJoin(1, 5),
    NoteTagJoin(1, 6),

    NoteTagJoin(2, 7),
    NoteTagJoin(2, 8),
    NoteTagJoin(2, 9),
    NoteTagJoin(2, 10),

    NoteTagJoin(3, 11),
    NoteTagJoin(3, 12),
)
