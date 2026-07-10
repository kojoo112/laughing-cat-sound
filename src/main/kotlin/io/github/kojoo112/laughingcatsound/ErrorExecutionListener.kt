package io.github.kojoo112.laughingcatsound

import com.intellij.execution.ExecutionListener
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.runners.ExecutionEnvironment

/**
 * Run/Debug 실행이 에러로 이어지면 소리를 재생한다.
 * plugin.xml 의 <projectListeners> 로 등록된다.
 *
 * - processTerminated: 프로세스가 0이 아닌 종료 코드로 끝난 경우
 * - processNotStarted: before-run 빌드(컴파일) 실패 등으로 프로세스가 아예 시작되지 못한 경우
 */
class ErrorExecutionListener : ExecutionListener {
    override fun processTerminated(
        executorId: String,
        env: ExecutionEnvironment,
        handler: ProcessHandler,
        exitCode: Int
    ) {
        if (exitCode != 0) {
            SoundPlayer.play()
        }
    }

    override fun processNotStarted(executorId: String, env: ExecutionEnvironment) {
        // 컴파일 에러 등으로 실행 전 단계에서 중단되어 프로세스가 시작되지 못한 경우
        SoundPlayer.play()
    }
}
