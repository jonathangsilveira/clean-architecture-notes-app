package com.example.jonathan.cleanarchitecture.notes.feature.note.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.jonathan.cleanarchitecture.notes.R
import com.example.jonathan.cleanarchitecture.notes.feature.note.domain.util.NoteOrder
import com.example.jonathan.cleanarchitecture.notes.feature.note.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    order: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChange: (NoteOrder) -> Unit
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = stringResource(id = R.string.title),
                isSelected = order is NoteOrder.Title,
                onSelect = { onOrderChange(NoteOrder.Title(order.type)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(id = R.string.date),
                isSelected = order is NoteOrder.Date,
                onSelect = { onOrderChange(NoteOrder.Date(order.type)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(id = R.string.color),
                isSelected = order is NoteOrder.Color,
                onSelect = { onOrderChange(NoteOrder.Color(order.type)) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = stringResource(id = R.string.ascending),
                isSelected = order.type is OrderType.Ascending,
                onSelect = { onOrderChange(order.copy(OrderType.Ascending)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(id = R.string.descending),
                isSelected = order.type is OrderType.Descending,
                onSelect = { onOrderChange(order.copy(OrderType.Descending)) }
            )
        }
    }
}