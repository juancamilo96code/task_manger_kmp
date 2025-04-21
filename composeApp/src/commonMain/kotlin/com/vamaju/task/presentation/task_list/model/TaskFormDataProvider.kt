package com.vamaju.task.presentation.task_list.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import com.vamaju.task.presentation.commons.form.model.FormQuestion
import com.vamaju.task.presentation.task_details.model.Task
import com.vamaju.task.presentation.commons.stringTitleTask
import com.vamaju.task.presentation.commons.stringDescriptionTask
import com.vamaju.task.presentation.commons.stringResponsibleTask
import com.vamaju.task.presentation.commons.stringTag

@Composable
fun buildTaskFormData(task: Task?) = TaskFormData(
    id = task?.id ?: 0,
    title = FormQuestion.FormTextQuestion(
        id = "title",
        isRequired = true,
        questionLabel = stringTitleTask(),
        error = remember { mutableStateOf(null) },
        answer = remember { mutableStateOf(task?.title ?: "") }
    ),
    description = FormQuestion.FormTextQuestion(
        id = "description",
        isRequired = false,
        questionLabel = stringDescriptionTask(),
        error = remember { mutableStateOf(null) },
        answer = remember { mutableStateOf(task?.description ?: "") }
    ),
    latitude = task?.latitude,
    longitude = task?.longitude,
    address = task?.address,
    responsible = FormQuestion.FormTextQuestion(
        id = "responsible",
        isRequired = false,
        questionLabel = stringResponsibleTask(),
        error = remember { mutableStateOf(null) },
        answer = remember { mutableStateOf(task?.responsible ?: "") }
    ),
    tag = FormQuestion.FormTextQuestion(
        id = "tag",
        isRequired = false,
        questionLabel = stringTag(),
        error = remember { mutableStateOf(null) },
        answer = remember { mutableStateOf("") }
    ),
    isCompleted = task?.isCompleted == true,
    tags = remember { task?.tags?.toMutableStateList()?: mutableStateListOf() },

    )
