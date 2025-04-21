package com.vamaju.task.presentation.commons.form

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TextFieldItem(
    modifier: Modifier = Modifier,
    label: String? = null,
    suffix: String? = null,
    value: String?,
    hasError: Boolean = false,
    showTextInput: Boolean = true,
    isAlphaNumeric: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    errorMessage: String? = "",
    errorMessageEnable: Boolean = true,
    onValueChange: (String) -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    AnimatedVisibility(visible = showTextInput) {
        OutlinedTextField(
            modifier = modifier,
            value = value ?: "",
            label = { Text(text = label ?: "") },
            singleLine = true,
            isError = hasError,
            supportingText = {
                if (hasError && errorMessageEnable) {
                    errorMessage?.let {
                        Text(
                            text = it,
                            fontSize = 12.sp,
                            color = colorScheme.error,
                        )
                    }
                }
            },
            keyboardOptions = keyboardOptions,
            suffix = { suffix?.let { Text(text = it) } },
            onValueChange = { textValue ->
                val text = if (isAlphaNumeric) textValue.filter { it.isLetterOrDigit() } else textValue
                onValueChange(text)
            },
            colors = TextFieldDefaults.colors(
                errorIndicatorColor = colorScheme.error,
                focusedContainerColor = colorScheme.onPrimary,
                unfocusedContainerColor = colorScheme.onPrimary,
                disabledContainerColor = colorScheme.onPrimary,
                errorContainerColor = colorScheme.onPrimary,

                focusedTextColor = colorScheme.onBackground,
                unfocusedTextColor = colorScheme.onBackground,
                disabledTextColor = Color.Gray,
                errorTextColor = colorScheme.onBackground,

                focusedLabelColor = colorScheme.onBackground,
                unfocusedLabelColor = colorScheme.onBackground,
                disabledLabelColor = Color.Gray,
                errorLabelColor = colorScheme.onBackground,

                cursorColor = colorScheme.onBackground,
                errorCursorColor = colorScheme.error,

                focusedIndicatorColor = colorScheme.onBackground,
                unfocusedIndicatorColor = colorScheme.onBackground,
                disabledIndicatorColor = Color.Gray,
            )
        )
    }
}

@Preview
@Composable
fun TextFieldItemPreview() {
    MaterialTheme {
        TextFieldItem(
            modifier = Modifier,
            label = "Nombre",
            suffix = "kg",
            value = "85",
            hasError = false,
            errorMessage = "Campo obligatorio",
            onValueChange = {}
        )
    }
}
















