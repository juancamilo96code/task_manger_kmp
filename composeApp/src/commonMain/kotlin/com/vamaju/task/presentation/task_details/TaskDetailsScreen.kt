package com.vamaju.task.presentation.task_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AssistChip
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vamaju.task.isMobilePlatform
import com.vamaju.task.presentation.commons.stringCancel
import com.vamaju.task.presentation.commons.stringUpdate
import com.vamaju.task.presentation.commons.stringEdit
import com.vamaju.task.presentation.commons.stringDelete
import com.vamaju.task.presentation.task_details.model.Task
import com.vamaju.task.presentation.task_details.model.TaskDetailsIntent
import com.vamaju.task.presentation.task_list.TaskListViewModel
import com.vamaju.task.presentation.task_list.composables.TaskFormDialogContent
import com.vamaju.task.presentation.task_list.model.TaskFormData
import com.vamaju.task.presentation.task_list.model.TaskListIntent
import com.vamaju.task.presentation.task_list.model.buildTaskFormData
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun TaskDetailsScreen(
    taskId: Long,
    viewModel: TaskDetailsViewModel = koinViewModel<TaskDetailsViewModel>(),
    sharedViewModel: TaskListViewModel = koinViewModel<TaskListViewModel>()
) {
    val navigator = LocalNavigator.currentOrThrow
    val taskDetailsUIState by viewModel.taskDetailsUIState.collectAsState()
    val taskListUIState by sharedViewModel.taskListUIState.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    var expandedMenu by remember { mutableStateOf(false) }

    val modalBottomSheetState = rememberModalBottomSheetState()

    lateinit var taskFormData: TaskFormData

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(taskDetailsUIState.task?.title ?: "") },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = { expandedMenu = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menú")
                    }
                    DropdownMenu(
                        expanded = expandedMenu,
                        onDismissRequest = { expandedMenu = false }
                    ) {
                        DropdownMenuItem(onClick = {
                            expandedMenu = false
                            showDialog = true
                        }, text = { Text(stringEdit()) })

                        DropdownMenuItem(onClick = {
                            expandedMenu = false
                            viewModel.handleIntent(
                                TaskDetailsIntent.DeleteTaskDetails(
                                    taskId = taskId
                                )
                            )
                        }, text = { Text(stringDelete()) })
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(16.dp)
        ) {
            taskDetailsUIState.task?.let { task ->

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        if (task.title.isNotBlank()) {
                            Text("Nombre", style = MaterialTheme.typography.labelMedium)
                            Text(task.title, style = MaterialTheme.typography.bodyLarge)
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                        if (task.description?.isNotBlank() == true) {
                            Text("Descripción", style = MaterialTheme.typography.labelMedium)
                            Text(task.description, style = MaterialTheme.typography.bodyLarge)
                            Spacer(modifier = Modifier.height(12.dp))
                        }

                        if (task.address?.isNotBlank() == true) {
                            Text("Dirección", style = MaterialTheme.typography.labelMedium)
                            Text(task.address, style = MaterialTheme.typography.bodyLarge)
                            Spacer(modifier = Modifier.height(12.dp))
                        }

                        if (task.responsible?.isNotBlank() == true) {
                            Text("Responsable", style = MaterialTheme.typography.labelMedium)
                            Text(task.responsible, style = MaterialTheme.typography.bodyLarge)
                            Spacer(modifier = Modifier.height(12.dp))
                        }

                        if (task.latitude != null && task.longitude != null) {
                            Text("Ubicación", style = MaterialTheme.typography.labelMedium)
                            Text(
                                "Lat: ${task.latitude}, Lon: ${task.longitude}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }

                        if (task.tags.isNotEmpty()) {
                            Text("Tags", style = MaterialTheme.typography.labelMedium)
                            FlowRow(
                                modifier = Modifier,
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
            }

            if (showDialog) {
                taskFormData = buildTaskFormData(taskDetailsUIState.task)
                if (isMobilePlatform()) {
                    ModalBottomSheet(
                        sheetState = modalBottomSheetState,
                        onDismissRequest = { showDialog = false },
                        dragHandle = {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.primaryContainer)
                            ) {
                                BottomSheetDefaults.DragHandle(
                                    color = Gray,
                                    modifier = Modifier.align(Alignment.Center),
                                )
                            }
                        }
                    ) {
                        TaskFormDialogContent(
                            taskFormData = taskFormData,
                            onDismiss = { showDialog = false },
                            tags = taskListUIState.tags,
                            onSave = { taskFormData ->
                                sharedViewModel.handleIntent(
                                    TaskListIntent.ValidateFormTask(
                                        taskFormData
                                    )
                                )
                            },
                            confirmButtonLabel = stringUpdate(),
                            cancelButtonLabel = stringCancel(),
                        )
                    }
                } else {
                    Dialog(onDismissRequest = { showDialog = false }) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            tonalElevation = 8.dp
                        ) {
                            TaskFormDialogContent(
                                taskFormData = taskFormData,
                                onDismiss = { showDialog = false },
                                tags = taskListUIState.tags,
                                onSave = { taskFormData ->
                                    sharedViewModel.handleIntent(
                                        TaskListIntent.ValidateFormTask(
                                            taskFormData
                                        )
                                    )
                                },
                                confirmButtonLabel = stringUpdate(),
                                cancelButtonLabel = stringCancel(),
                            )
                        }
                    }
                }
            }
        }

    }

    LaunchedEffect(Unit){
        viewModel.handleIntent(
            TaskDetailsIntent.LoadTaskDetails(
                taskId = taskId
            )
        )
        sharedViewModel.handleIntent(
            TaskListIntent.SearchTags(
                query = ""
            )
        )
    }

    taskListUIState.formDataValidated?.let {
        viewModel.handleIntent(
            TaskDetailsIntent.UpdateTask(
                task = Task(
                    id = taskId,
                    title = it.title.answer.value,
                    description = it.description.answer.value,
                    latitude = it.latitude,
                    longitude = it.longitude,
                    address = it.address,
                    responsible = it.responsible.answer.value,
                    tags = it.tags,
                    isCompleted = it.isCompleted
                )
            )
        )
        showDialog = false
    }

    if (taskDetailsUIState.isTaskDeleted){
        navigator.pop()
        taskDetailsUIState.isTaskDeleted = false
    }
}