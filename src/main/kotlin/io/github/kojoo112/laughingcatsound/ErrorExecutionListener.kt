package io.github.kojoo112.laughingcatsound

import com.intellij.execution.ExecutionListener
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.runners.ExecutionEnvironment

/**
 * Run/Debug 프로세스가 "실패"로 끝나면 소리를 재생한다.
 * plugin.xml 의 <projectListeners> 로 등록된다.
 *
 * 발동하는 경우:
 * - 프로세스가 종료 코드 1 로 끝난 경우 (일반적인 실패: 애플리케이션 에러, 시작 실패, JVM 의 uncaught 예외 등)
 * - before-run 빌드(컴파일) 실패로 프로세스가 아예 시작되지 못한 경우
 *
 * 발동하지 않는 경우:
 * - 정상 종료(exit 0)
 * - 사용자 Stop/인터럽트(SIGINT→130, SIGTERM→143 등) 및 그 밖의 종료 코드
 *
 * 정책 — "확실한 실패일 때만 운다". exit 1 은 대부분의 런타임(JVM/Gradle/pytest/Jest 등)에서
 * "일반적인 실패"를 뜻하는 코드다. Stop(130/143)과 겹치지 않으므로 exit 1 만 보면 사용자 중단이
 * 자연스럽게 배제된다. 테스트 실패는 종료 코드에 의존하지 않고 ErrorTestListener 가 별도로 감지한다.
 * (트레이드오프: 세그폴트(139)·OOM킬(137) 같은 시그널 크래시나 1이 아닌 에러 코드는 감지하지 않는다.)
 */
class ErrorExecutionListener : ExecutionListener {

    override fun processTerminated(
        executorId: String,
        env: ExecutionEnvironment,
        handler: ProcessHandler,
        exitCode: Int
    ) {
        if (exitCode == FAILURE_EXIT_CODE) {
            ErrorAlert.trigger(env.project)
        }
    }

    override fun processNotStarted(executorId: String, env: ExecutionEnvironment) {
        // 컴파일 에러 등으로 실행 전 단계에서 중단되어 프로세스가 시작되지 못한 경우
        ErrorAlert.trigger(env.project)
    }

    companion object {
        // 대부분의 런타임에서 "일반적인 실패"를 의미하는 종료 코드.
        private const val FAILURE_EXIT_CODE = 1
    }
}
