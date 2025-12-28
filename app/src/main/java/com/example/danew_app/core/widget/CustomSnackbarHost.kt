package com.example.danew_app.core.widget

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.danew_app.core.theme.DanewColors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CustomSnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier
    ) { data ->
        Snackbar(
            snackbarData = data,
            // 요청하신 디자인 적용
            containerColor = DanewColors.darkGrayColor,
            contentColor = DanewColors.whiteColor,         // 흰색 글씨
            shape = RoundedCornerShape(4.dp),   // 각진 네모 (살짝만 둥글게)
            actionColor = DanewColors.blueColor          // (혹시 버튼이 있다면) 노란색 포인트
        )
    }
}

/**
 * 스낵바를 즉시 띄워주는 확장 함수
 * - 기존에 떠 있던 스낵바가 있으면 즉시 닫고(dismiss) 새로운 것을 띄웁니다.
 */
fun SnackbarHostState.showImmediateSnackbar(
    scope: CoroutineScope,
    message: String,
    actionLabel: String? = null,
    duration: SnackbarDuration = SnackbarDuration.Short
) {
    scope.launch {
        // 1. 기존 메시지 닫기 (큐에 쌓이지 않게 함)
        this@showImmediateSnackbar.currentSnackbarData?.dismiss()

        // 2. 새 메시지 보이기
        this@showImmediateSnackbar.showSnackbar(
            message = message,
            actionLabel = actionLabel,
            duration = duration
        )
    }
}