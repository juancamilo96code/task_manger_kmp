package com.vamaju.task.presentation.task_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vamaju.task.domain.tag.usecases.GetTagsByName
import com.vamaju.task.domain.task.model.TaskModel
import com.vamaju.task.domain.task.usecases.CreateTask
import com.vamaju.task.domain.task.usecases.FilterTask
import com.vamaju.task.domain.task.usecases.GetAllTask
import com.vamaju.task.domain.task.usecases.SwitchTaskStatus
import com.vamaju.task.presentation.task_details.model.Task
import com.vamaju.task.presentation.task_details.model.mapToDomain
import com.vamaju.task.presentation.task_details.model.mapToUI
import com.vamaju.task.presentation.task_list.model.TaskFormData
import com.vamaju.task.presentation.task_list.model.TaskListIntent
import com.vamaju.task.presentation.task_list.model.TaskListUIState
import com.vamaju.task.presentation.task_list.model.mapToItemUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TaskListViewModel(
    private val createTask: CreateTask,
    private val getAllTask: GetAllTask,
    private val filterTask: FilterTask,
    private val switchTaskStatus: SwitchTaskStatus,
    private val getTagsByName: GetTagsByName
) : ViewModel() {

    private val _taskListUIState = MutableStateFlow(TaskListUIState())
    val taskListUIState: StateFlow<TaskListUIState> =
        _taskListUIState.asStateFlow()

    fun handleIntent(intent: TaskListIntent) {
        when (intent) {
            is TaskListIntent.CreateTask -> createNewTask(intent.task)
            TaskListIntent.LoadAllTask -> observeAllTask()
            is TaskListIntent.UpdateStatusTask -> updateTaskStatus(
                intent.taskId,
                intent.isCompleted
            )

            is TaskListIntent.SearchTask -> observeFilterTask(intent.query)
            is TaskListIntent.SearchTags -> observeTagsByName(intent.query)
            is TaskListIntent.ValidateFormTask -> validateForm(intent.taskFormData)
        }
    }

    private fun observeAllTask() {
        viewModelScope.launch(Dispatchers.IO) {
            _taskListUIState.update { it.copy(isLoading = true) }

            val allTasks: Flow<Result<List<TaskModel>>> = getAllTask()

            _taskListUIState.update { it.copy(isLoading = false) }
            allTasks.collect { result ->
                result.onSuccess { tasks ->
                    _taskListUIState.update { it ->
                        it.copy(
                            tasks = tasks.map { item ->item.mapToItemUI() },
                        )
                    }
                }.onFailure { error ->
                    _taskListUIState.update { it.copy(error = error.message) }
                }
            }
        }
    }

    private fun observeFilterTask(filter: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _taskListUIState.update { it.copy(isLoading = true) }

            val filterTasks: Flow<Result<List<TaskModel>>> = filterTask(filter)

            _taskListUIState.update { it.copy(isLoading = false) }
            filterTasks.collect { result ->
                result.onSuccess { tasks ->
                    _taskListUIState.update { it ->
                        it.copy(
                            tasks = tasks.map { item ->item.mapToItemUI() },
                        )
                    }
                }.onFailure { error ->
                    _taskListUIState.update { it.copy(error = error.message) }
                }
            }
        }
    }

    private fun updateTaskStatus(taskId: Long, isCompleted: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _taskListUIState.update { it.copy(isLoading = true) }

            val updateStaus: Result<Unit> = switchTaskStatus(taskId, isCompleted)

            _taskListUIState.update { it.copy(isLoading = false) }
            updateStaus.onFailure { error ->
                _taskListUIState.update { it.copy(error = error.message) }
            }
        }
    }

    private fun observeTagsByName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _taskListUIState.update { it.copy(isLoading = true) }

            val tagsList = getTagsByName(name)

            _taskListUIState.update { it.copy(isLoading = false) }
            tagsList.onSuccess { tags ->
                _taskListUIState.update {
                    it.copy(
                        tags = tags.map { item -> item.mapToUI() },
                    )
                }
            }.onFailure { error ->
                _taskListUIState.update { it.copy(error = error.message) }
            }
        }
    }

    private fun createNewTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            _taskListUIState.update {
                it.copy(
                    isLoading = true,
                    formDataValidated = null
                )
            }

            val createTaskResult: Result<Unit> = createTask(task.mapToDomain())

            _taskListUIState.update { it.copy(isLoading = false) }
            createTaskResult.onFailure { error ->
                _taskListUIState.update { it.copy(error = error.message) }
            }
        }
    }

    private fun validateForm(taskFormData: TaskFormData) {
        var isValid = true
        if (taskFormData.title.answer.value.isBlank() && taskFormData.title.isRequired) {
            isValid = false
            taskFormData.title.error.value = "Campo Obligatorio"
        } else taskFormData.title.error.value = null
        if (isValid){
            _taskListUIState.update {
                it.copy(
                    error = null ,
                    formDataValidated = taskFormData
                )
            }
        }else{
            _taskListUIState.update {
                it.copy(
                    error = "Faltan llenar los campos obrigatorios",
                    formDataValidated = null
                )
            }
        }
    }
}