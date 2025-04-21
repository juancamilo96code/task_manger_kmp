package com.vamaju.task.presentation.task_list.model

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.vamaju.task.presentation.commons.form.model.FormQuestion
import com.vamaju.task.presentation.task_details.model.Tag

data class TaskFormData (
    val id: Long?,
    val title: FormQuestion.FormTextQuestion,
    val description: FormQuestion.FormTextQuestion,
    var latitude: Double?,
    var longitude: Double?,
    var address: String?,
    val responsible: FormQuestion.FormTextQuestion,
    val isCompleted: Boolean,
    val tag: FormQuestion.FormTextQuestion,
    val tags: SnapshotStateList<Tag>,
)