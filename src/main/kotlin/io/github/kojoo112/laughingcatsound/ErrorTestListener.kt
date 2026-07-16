package io.github.kojoo112.laughingcatsound

import com.intellij.execution.testframework.sm.runner.SMTRunnerEventsAdapter
import com.intellij.execution.testframework.sm.runner.SMTestProxy

/**
 * IDE 테스트 러너(JUnit/pytest/Jest 등)에서 테스트가 실패하면 소리를 재생한다.
 * plugin.xml 의 <projectListeners> 로 SMTRunnerEventsListener.TEST_STATUS(@Topic.ProjectLevel) 에 등록된다.
 *
 * 프로세스 종료 코드에 의존하지 않으므로, 테스트가 실패했는데도 프로세스가 exit 0 으로 끝나는
 * 경우(예: Gradle ignoreFailures=true)까지 감지한다. 실패 테스트가 여러 개여도 ErrorAlert 의
 * 쓰로틀 때문에 소리는 한 번만 난다.
 */
class ErrorTestListener : SMTRunnerEventsAdapter() {

    override fun onTestFailed(test: SMTestProxy) {
        // project 를 null 로 넘겨도 이미지 팝업은 보이는 IDE 프레임으로 폴백된다(CatImagePopup 참고).
        ErrorAlert.trigger(null)
    }
}
