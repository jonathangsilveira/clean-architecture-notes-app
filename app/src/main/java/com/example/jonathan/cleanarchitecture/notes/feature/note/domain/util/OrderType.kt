package com.example.jonathan.cleanarchitecture.notes.feature.note.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
