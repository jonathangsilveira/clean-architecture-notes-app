package com.example.jonathan.cleanarchitecture.notes.feature.note.domain.util

sealed class NoteOrder(val type: OrderType) {
    class Title(type: OrderType): NoteOrder(type)
    class Date(type: OrderType): NoteOrder(type)
    class Color(type: OrderType): NoteOrder(type)
    fun copy(order: OrderType): NoteOrder {
        return when (this) {
            is Color -> Color(order)
            is Date -> Date(order)
            is Title -> Title(order)
        }
    }
}
