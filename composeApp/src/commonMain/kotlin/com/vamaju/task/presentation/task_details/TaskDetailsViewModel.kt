package com.vamaju.task.presentation.task_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vamaju.task.domain.task.usecases.DeleteTask
import com.vamaju.task.domain.task.usecases.GetTaskById
import com.vamaju.task.domain.task.usecases.UpdateTask
import com.vamaju.task.presentation.task_details.model.Task
import com.vamaju.task.presentation.task_details.model.TaskDetailsIntent
import com.vamaju.task.presentation.task_details.model.TaskDetailsUIState
import com.vamaju.task.presentation.task_details.model.mapToDomain
import com.vamaju.task.presentation.task_details.model.mapToUI
import jdk.internal.org.jline.utils.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskDetailsViewModel(
    private val getTaskById: GetTaskById,
    private val  deleteTask: DeleteTask,
    private val updateTask: UpdateTask
) : ViewModel() {

    private val _taskDetailsUIState = MutableStateFlow(TaskDetailsUIState())
    val taskDetailsUIState: StateFlow<TaskDetailsUIState> =
        _taskDetailsUIState.asStateFlow()

    fun handleIntent(intent: TaskDetailsIntent) {
        when (intent) {
            is TaskDetailsIntent.LoadTaskDetails -> loadTaskById(intent.taskId)
            is TaskDetailsIntent.DeleteTaskDetails -> deleteTaskDetails(intent.taskId)
            is TaskDetailsIntent.UpdateTask -> updateTaskDetails(intent.task)
        }
    }

    private fun loadTaskById(taskId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _taskDetailsUIState.update { it.copy(isLoading = true) }

            val task = getTaskById(taskId)

            _taskDetailsUIState.update { it.copy(isLoading = false) }
            task.onSuccess { task ->

                _taskDetailsUIState.update {
                    it.copy(
                        task = task?.mapToUI(),
                    )
                }
            }.onFailure { error ->
                _taskDetailsUIState.update { it.copy(error = error.message) }
            }
        }
    }

    private fun deleteTaskDetails(taskId: Long){
        viewModelScope.launch(Dispatchers.IO) {
            _taskDetailsUIState.update { it.copy(isLoading = true) }

            val deleteTaskTaskResult: Result<Unit> = deleteTask(taskId)

            _taskDetailsUIState.update { it.copy(isLoading = false) }
            deleteTaskTaskResult.onSuccess {
                _taskDetailsUIState.update {
                    it.copy(
                        isTaskDeleted = true
                    )
                }
            }.onFailure { error ->
                _taskDetailsUIState.update { it.copy(error = error.message) }
            }
        }
    }

    private fun updateTaskDetails(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            _taskDetailsUIState.update { it.copy(isLoading = true) }

            val updateTaskResult: Result<Unit> = updateTask(task.mapToDomain())

            _taskDetailsUIState.update { it.copy(isLoading = false) }
            updateTaskResult.onSuccess {
                _taskDetailsUIState.update {
                    it.copy(
                        task = task
                    )
                }
            }.onFailure { error ->
                _taskDetailsUIState.update { it.copy(error = error.message) }
            }
        }
    }

}