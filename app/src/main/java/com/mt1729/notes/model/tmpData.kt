package com.mt1729.notes.model

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

val tmpNotes = mutableListOf(
    Note(
        content = "This is the content of a short note.",
        tags = listOf(
            tmpTags[0],
            tmpTags[1],
            tmpTags[2],
            tmpTags[3],
            tmpTags[4],
            tmpTags[5],
        )
    ), Note(
        content = "QWEL QWLEJK QLWKEJ ASDL JAS POCXAJC OPIW JQWOIEJ QOW PIJACLSKC LKQJWN KJQNSCK" + "AJNSCK AJNSD QNWE IUNQWE UI@#()@#U )(@U#$ )!(U@ )I@H#O @# U$(*E@H WSC(H " + "IOUHAOIUHWDIUVSDVIU HSOI&\$Y *W&H*&H ALKSJD QWO QIWE J ALKSDJ ALSKD JQOWIE JOSIJ " + "OCIQJOCIJQOWICJQOIWJEASKC2034203948203984203984",
        tags = listOf(
            tmpTags[6],
            tmpTags[7],
            tmpTags[8],
            tmpTags[9],
        )
    ), Note(
        content = "Another note",
        tags = listOf(
            tmpTags[10],
            tmpTags[11],
        )
    )
)