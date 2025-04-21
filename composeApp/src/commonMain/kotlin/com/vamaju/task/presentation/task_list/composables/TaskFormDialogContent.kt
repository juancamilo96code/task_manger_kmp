package com.vamaju.task.presentation.task_list.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.vamaju.task.presentation.commons.LocationProvider
import com.vamaju.task.presentation.commons.form.AutoCompleteItem
import com.vamaju.task.presentation.commons.form.TextFieldItem
import com.vamaju.task.presentation.commons.form.TextFieldMultiLineItem
import com.vamaju.task.presentation.commons.form.model.AutoCompleteItemData
import com.vamaju.task.presentation.commons.getLocationProvider
import com.vamaju.task.presentation.commons.stringSearchTag
import com.vamaju.task.presentation.commons.stringTags
import com.vamaju.task.presentation.task_details.model.Tag
import com.vamaju.task.presentation.task_list.model.TaskFormData
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TaskFormDialogContent(
    taskFormData: TaskFormData,
    tags: List<Tag>,
    onDismiss: () -> Unit,
    onSave: (TaskFormData) -> Unit,
    confirmButtonLabel: String,
    cancelButtonLabel: String,

    ) {
    var text by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    val scrollState = rememberScrollState()

    val locationProvider: LocationProvider = getLocationProvider()

    Column(
        modifier = Modifier.padding(16.dp)
            .verticalScroll(scrollState)
            .statusBarsPadding()
    ) {

        TextFieldItem(
            modifier = Modifier
                .fillMaxWidth(),
            value = taskFormData.title.answer.value,
            hasError = taskFormData.title.error.value != null,
            label = taskFormData.title.questionLabel,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            onValueChange = {
                taskFormData.title.answer.value = it
            })

        TextFieldItem(
            modifier = Modifier
                .fillMaxWidth(),
            value = taskFormData.responsible.answer.value,
            hasError = taskFormData.responsible.error.value != null,
            label = taskFormData.responsible.questionLabel,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            onValueChange = {
                taskFormData.responsible.answer.value = it
            })

        TextFieldMultiLineItem(
            modifier = Modifier
                .fillMaxWidth(),
            value = taskFormData.description.answer.value,
            hasError = taskFormData.description.error.value != null,
            label = taskFormData.description.questionLabel,
            onValueChange = {
                taskFormData.description.answer.value = it
            })

        Text(stringTags())
        FlowRow(
            modifier = Modifier,
            verticalArrangement = Arrangement.Top,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            maxItemsInEachRow = 4,
        ) {
            taskFormData.tags.sortedBy { it.name }.forEach { tag ->
                AssistChip(
                    onClick = {},
                    label = {
                        Text(text = tag.name)
                    },
                    trailingIcon = {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = "x",
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .size(16.dp)
                                .clickable { taskFormData.tags.remove(tag) },
                        )
                    },

                    )
            }
        }


        AutoCompleteItem(
            textValue = taskFormData.tag.answer.value,
            hasError = false,
            label = stringSearchTag(),
            showTextInput = true,
            onTextChange = {
                taskFormData.tag.answer.value = it
            },
            options = if (tags.find { it.name == taskFormData.tag.answer.value } != null) {
                tags.map {
                    AutoCompleteItemData(
                        label = it.name,
                        item = it,
                    )
                }
            } else {
                (tags.filter {
                    it.name.contains(taskFormData.tag.answer.value)
                } + Tag(
                    id = 0,
                    name = taskFormData.tag.answer.value,
                )).map {
                    AutoCompleteItemData(
                        label = it.name,
                        item = it,
                    )
                }
            },
            onValueChange = { tag ->
                if (taskFormData.tags.find { it.name == tag.item.name } == null && tag.item.name.isNotBlank()) {
                    taskFormData.tags.add(tag.item)
                }
                taskFormData.tag.answer.value = ""
            })


        Spacer(Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground
                )
            ) {
                Text(cancelButtonLabel)
            }
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                coroutineScope.launch {
                    val location = locationProvider.getCurrentLocation()
                    taskFormData.latitude = location?.latitude
                    taskFormData.longitude = location?.longitude
                    val address = location?.let { locationProvider.getAddressFromLocation(it) }
                    taskFormData.address = address?.fullAddress

                    onSave(taskFormData)
                    //onDismiss()
                }
            }) {
                Text(confirmButtonLabel)
            }
        }
    }
}