package com.example.jonathan.cleanarchitecture.notes.feature.note.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.jonathan.cleanarchitecture.notes.ui.theme.*

@Entity
data class Note(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int
) {
    companion object {
        val colors: List<Color> = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}
