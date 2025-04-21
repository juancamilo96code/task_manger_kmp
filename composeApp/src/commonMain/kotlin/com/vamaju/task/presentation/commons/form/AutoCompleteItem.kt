package com.vamaju.task.presentation.commons.form

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.vamaju.task.presentation.commons.form.model.AutoCompleteItemData

/**
 * @author Juan Camilo Collantes Tovar on 28/03/2025
 * **/



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> AutoCompleteItem(
    modifier: Modifier = Modifier,
    label: String? = null,
    textValue: String?,
    hasError: Boolean = false,
    showTextInput: Boolean = true,
    errorMessage: String? = null,
    errorMessageEnable: Boolean = true,
    options: List<AutoCompleteItemData<T>?> = emptyList(),
    onTextChange: (String) -> Unit,
    onValueChange: (AutoCompleteItemData<T>) -> Unit
) {


    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {

        AnimatedVisibility(visible = showTextInput) {

            ExposedDropdownMenuBox(
                modifier = Modifier.fillMaxWidth(),
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }) {
                OutlinedTextField(
                    value = textValue ?: "",
                    onValueChange = {
                        onTextChange(it)
                        expanded = true
                    },
                    isError = hasError,
                    readOnly = false,
                    enabled = true,
                    label = { Text(text = label ?: "") },
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
                    trailingIcon = {

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
                    ),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                if (options.isNotEmpty()) {
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {

                        options.map {
                            DropdownMenuItem(
                                text = { it?.let { it1 -> Text(text = it1.label) } },
                                onClick = {
                                    it?.let { it1 -> onValueChange(it1) }
                                    expanded = !expanded
                                })
                        }

                    }
                }

            }
        }
    }
}