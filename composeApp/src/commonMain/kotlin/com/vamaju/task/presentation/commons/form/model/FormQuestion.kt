package com.vamaju.task.presentation.commons.form.model

import androidx.compose.runtime.MutableState

sealed class FormQuestion {

    data class FormDropDownQuestion(
        val id: String,
        val questionLabel: String,
        val answer: MutableState<FormAnswer?>,
        val options: List<FormAnswer>,
        val isRequired: Boolean = true,
        val error: MutableState<String?>
    ) : FormQuestion()

    data class FormTextQuestion(
        val id: String,
        val questionLabel: String = "",
        val answer: MutableState<String>,
        val isRequired: Boolean = true,
        val error: MutableState<String?>
    ) : FormQuestion()

    data class FormDateQuestion(
        val id: String,
        val questionLabel: String,
        val answer: MutableState<String>,
        val isRequired: Boolean = true,
        val error: MutableState<String?>
    ) : FormQuestion()

    data class FormMultiLineTextQuestion(
        val id: String,
        val questionLabel: String = "",
        val answer: MutableState<String>,
        val isRequired: Boolean = true,
        val error: MutableState<String?>
    ) : FormQuestion()

}