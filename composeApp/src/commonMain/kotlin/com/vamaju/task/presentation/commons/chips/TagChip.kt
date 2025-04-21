package com.vamaju.task.presentation.commons.chips

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vamaju.task.presentation.commons.chips.model.ChipData

@Composable
fun TagChip(
    chipData: ChipData,
    onRightIcon: (ChipData) -> Unit = {}
) {

    TextButton(
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        shape = RoundedCornerShape(50),
        border = BorderStroke(
            1.dp,
            color = MaterialTheme.colorScheme.primary
        ),
        onClick = {}
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = chipData.text,
                fontSize = 16.sp, // reemplaza font_size_16 si era una constante
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.width(4.dp)) // reemplaza spacing_xxs
            Icon(
                Icons.Default.Clear,
                contentDescription = "x",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .size(16.dp)
                    .clickable { onRightIcon(chipData) })

        }
    }
}
