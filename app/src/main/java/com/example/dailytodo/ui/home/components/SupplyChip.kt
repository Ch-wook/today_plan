package com.example.dailytodo.ui.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.dailytodo.domain.model.Supply

@Composable
fun SupplyChip(supply: Supply, onToggle: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = supply.isChecked, onCheckedChange = { onToggle() })
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = supply.name,
            style = MaterialTheme.typography.bodyMedium,
            textDecoration = if (supply.isChecked) TextDecoration.LineThrough else null
        )
    }
}
