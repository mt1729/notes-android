package com.mt1729.notes.model.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tag")
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tag_id") val tagId: Long,
    @ColumnInfo(name = "name") val name: String, // todo: unique
    @ColumnInfo(name = "color_hex") val colorHex: String?,
)
