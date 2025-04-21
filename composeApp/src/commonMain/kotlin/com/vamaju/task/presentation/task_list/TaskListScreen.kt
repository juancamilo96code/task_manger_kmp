package com.vamaju.task.presentation.task_list

import ExpandableCardEmpty
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vamaju.task.isMobilePlatform
import com.vamaju.task.presentation.commons.*
import com.vamaju.task.presentation.navigation.TaskDetails
import com.vamaju.task.presentation.task_details.model.Task
import com.vamaju.task.presentation.task_list.composables.ExpandableCard
import com.vamaju.task.presentation.task_list.composables.TaskFormDialogContent
import com.vamaju.task.presentation.task_list.composables.TaskItemCard
import com.vamaju.task.presentation.task_list.model.TaskFormData
import com.vamaju.task.presentation.task_list.model.TaskItem
import com.vamaju.task.presentation.task_list.model.TaskListIntent
import com.vamaju.task.presentation.task_list.model.buildTaskFormData
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(viewModel: TaskListViewModel = koinViewModel<TaskListViewModel>()) {

    val navigator = LocalNavigator.currentOrThrow

    val screenType = rememberScreenType()
    val isHorizontal = screenType != ScreenType.Small

    val taskListUIState by viewModel.taskListUIState.collectAsState()
    val itemCountPerRow = if (screenType == ScreenType.Large) 2 else 1

    val layoutModifier = Modifier
        .fillMaxSize()
        .padding(16.dp)

    val modalBottomSheetState = rememberModalBottomSheetState()

    val inProgressTasks = taskListUIState.tasks.filter { !it.isCompleted }
    val finishedTasks = taskListUIState.tasks.filter { it.isCompleted }

    var showDialog by remember { mutableStateOf(false) }

    var searchText by remember { mutableStateOf("") }

    lateinit var taskFormData: TaskFormData

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = stringAppName(),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    textAlign = TextAlign.Center
                )

                OutlinedTextField(
                    value = searchText,
                    onValueChange = {
                        searchText = it
                        viewModel.handleIntent(
                            TaskListIntent.SearchTask(
                                query = it
                            )
                        )
                    },
                    placeholder = { Text("${stringSearchTask()}...") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar"
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp)),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                        focusedBorderColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                modifier = Modifier.padding(24.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            if (isHorizontal) {
                Row(
                    modifier = layoutModifier.verticalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ContentTaskList(
                        painterIcon = painterToDoIcon(),
                        titleTaskList = stringTaskInProgress(),
                        taskList = inProgressTasks,
                        itemCountPerRow = itemCountPerRow,
                        modifier = Modifier.weight(1f),
                        onCheckedChange = { id, isChecked ->
                            viewModel.handleIntent(
                                TaskListIntent.UpdateStatusTask(
                                    taskId = id,
                                    isCompleted = isChecked
                                )
                            )
                        },
                        onCardClicked = {
                            navigator.push(TaskDetails(it.id))
                        }
                    )
                    ContentTaskList(
                        painterIcon = painterDoneIcon(),
                        titleTaskList = stringTaskFinished(),
                        taskList = finishedTasks,
                        itemCountPerRow = itemCountPerRow,
                        modifier = Modifier.weight(1f),
                        onCheckedChange = { id, isChecked ->
                            viewModel.handleIntent(
                                TaskListIntent.UpdateStatusTask(
                                    taskId = id,
                                    isCompleted = isChecked
                                )
                            )
                        },
                        onCardClicked = {
                            navigator.push(TaskDetails(it.id))
                        }
                    )
                }
            } else {
                Column(
                    modifier = layoutModifier.verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ContentTaskList(
                        painterIcon = painterToDoIcon(),
                        titleTaskList = stringTaskInProgress(),
                        taskList = inProgressTasks,
                        itemCountPerRow = itemCountPerRow,
                        onCheckedChange = { id, isChecked ->
                            viewModel.handleIntent(
                                TaskListIntent.UpdateStatusTask(
                                    taskId = id,
                                    isCompleted = isChecked
                                )
                            )
                        },
                        onCardClicked = {
                            navigator.push(TaskDetails(it.id))
                        }
                    )
                    ContentTaskList(
                        painterIcon = painterDoneIcon(),
                        titleTaskList = stringTaskFinished(),
                        taskList = finishedTasks,
                        itemCountPerRow = itemCountPerRow,
                        onCheckedChange = { id, isChecked ->
                            viewModel.handleIntent(
                                TaskListIntent.UpdateStatusTask(
                                    taskId = id,
                                    isCompleted = isChecked
                                )
                            )
                        },
                        onCardClicked = {
                            navigator.push(TaskDetails(it.id))
                        }
                    )
                }
            }



            if (showDialog) {
                taskFormData = buildTaskFormData(null)
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
                    ){
                        TaskFormDialogContent(
                            taskFormData = taskFormData,
                            onDismiss = { showDialog = false },
                            tags = taskListUIState.tags,
                            onSave = { taskFormData ->
                                viewModel.handleIntent(
                                    TaskListIntent.ValidateFormTask(
                                        taskFormData
                                    )
                                )
                            },
                            confirmButtonLabel = stringSave(),
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
                                    viewModel.handleIntent(
                                        TaskListIntent.ValidateFormTask(
                                            taskFormData
                                        )
                                    )
                                },
                                confirmButtonLabel = stringSave(),
                                cancelButtonLabel = stringCancel(),
                            )
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.handleIntent(
            TaskListIntent.LoadAllTask
        )
        viewModel.handleIntent(
            TaskListIntent.SearchTags(
                query = ""
            )
        )
    }


    taskListUIState.formDataValidated?.let {
        viewModel.handleIntent(
            TaskListIntent.CreateTask(
                task = Task(
                    id = it.id ?: 0,
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


}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ContentTaskList(
    painterIcon: Painter?,
    titleTaskList: String,
    taskList: List<TaskItem>,
    itemCountPerRow: Int,
    modifier: Modifier = Modifier,
    onCheckedChange: (Long, Boolean) -> Unit,
    onCardClicked: (TaskItem) -> Unit,
) {
    if (taskList.isEmpty()) {
        ExpandableCardEmpty(
            title = titleTaskList,
            icon = painterIcon,
            modifier = modifier,
            onIconClick = {}
        )
    } else {
        ExpandableCard(
            title = titleTaskList,
            icon = painterIcon,
            modifier = modifier
        ) {

            FlowRow(
                modifier = Modifier,
                maxItemsInEachRow = itemCountPerRow,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                content = {
                    taskList.forEach { task ->
                        TaskItemCard(
                            modifier = if (itemCountPerRow > 1) Modifier.weight(1f)
                            else Modifier.fillMaxWidth(),
                            task = task,
                            onCheckedChange = onCheckedChange,
                            onCardClicked = onCardClicked
                        )
                    }
                }
            )
        }
    }
}

