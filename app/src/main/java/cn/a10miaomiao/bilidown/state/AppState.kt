package cn.a10miaomiao.bilidown.state

import android.content.Context
import cn.a10miaomiao.bilidown.shizuku.permission.ShizukuPermission
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

sealed class GlobalEvent {
    object ExportAll : GlobalEvent()
}

class AppState {

    private val _taskStatus = MutableStateFlow<TaskStatus>(TaskStatus.InIdle)
    val taskStatus: StateFlow<TaskStatus> = _taskStatus

    private val _shizukuState = MutableStateFlow(
        ShizukuPermission.ShizukuPermissionState()
    )
    val shizukuState: StateFlow<ShizukuPermission.ShizukuPermissionState> = _shizukuState

    // 全局事件流
    private val _globalEventChannel = Channel<GlobalEvent>(Channel.UNLIMITED)
    val globalEvents = _globalEventChannel.receiveAsFlow()

    fun init(context: Context) {

    }

    fun putTaskStatus(taskStatus: TaskStatus) {
        _taskStatus.value = taskStatus
    }

    fun putShizukuState(state: ShizukuPermission.ShizukuPermissionState) {
        _shizukuState.value = state
    }

    fun sendGlobalEvent(event: GlobalEvent) {
        _globalEventChannel.trySend(event)
    }

}