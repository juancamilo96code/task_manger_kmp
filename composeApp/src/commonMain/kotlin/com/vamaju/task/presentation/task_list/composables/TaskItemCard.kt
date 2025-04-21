package com.vamaju.task.presentation.task_list.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.vamaju.task.presentation.task_list.model.TaskItem

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TaskItemCard(
    task: TaskItem,
    onCheckedChange: (Long, Boolean) -> Unit,
    onCardClicked: (TaskItem) -> Unit,
    modifier: Modifier = Modifier
) {

    Surface(
        modifier = modifier
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .clickable {
                onCardClicked(task)
            },
        elevation = 2.dp,
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)

        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    task.responsible?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Checkbox(
                    checked = task.isCompleted,
                    onCheckedChange = {
                        task.isCompleted = it
                        onCheckedChange(
                            task.id,
                            it
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            FlowRow(
                modifier = modifier,
                verticalArrangement = Arrangement.Top,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                maxItemsInEachRow = 4,
            ) {
                task.tags.sortedBy { it.name }.forEach { tag ->
                    AssistChip(
                        onClick = {},
                        label = {
                            Text(text = tag.name)
                        }
                    )
                }
            }
        }
    }
}