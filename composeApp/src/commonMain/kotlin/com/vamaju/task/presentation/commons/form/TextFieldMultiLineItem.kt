package com.vamaju.task.presentation.commons.form

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TextFieldMultiLineItem(
    modifier: Modifier = Modifier,
    label: String? = null,
    value: String?,
    hasError: Boolean = false,
    errorMessage: String? = "",
    errorMessageEnable: Boolean = true,
    onValueChange: (String) -> Unit
) {

    val colorScheme = MaterialTheme.colorScheme

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value ?: "",
        label = { Text(text = label ?: "") },
        enabled = true,
        singleLine = false,
        minLines = 5,
        maxLines = 5,
        isError = hasError,
        supportingText = {
            if (hasError && errorMessageEnable) {
                errorMessage?.let {
                    Text(
                        text = it,
                        fontSize = 12.sp,
                        color = colorScheme.error
                    )
                }
            }
        },
        onValueChange = {
            onValueChange(it)
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

@Preview
@Composable
fun TextFieldMultiLineItemPreview() {
    MaterialTheme {
        TextFieldMultiLineItem(
            modifier = Modifier,
            label = "Nombre",
            value = "85",
            hasError = false,
            errorMessage = "Campo obligatorio",
            onValueChange = {}
        )
    }
}

