package io.github.kojoo112.laughingcatsound

import com.intellij.execution.filters.ConsoleFilterProvider
import com.intellij.execution.filters.Filter
import com.intellij.openapi.project.Project

/**
 * 모든 콘솔에 에러 감지 필터를 붙인다.
 * plugin.xml 의 <consoleFilterProvider> 확장으로 등록된다.
 */
class ErrorConsoleFilterProvider : ConsoleFilterProvider {
    override fun getDefaultFilters(project: Project): Array<Filter> =
        arrayOf(ErrorConsoleFilter(project))
}

/**
 * 콘솔에 출력되는 각 줄을 검사해 에러 패턴이 보이면 알림(소리 + 이미지)을 발화한다.
 * 실제 하이라이팅은 하지 않으므로 항상 null 을 반환한다.
 */
class ErrorConsoleFilter(private val project: Project) : Filter {

    // 감지할 키워드. 필요에 맞게 추가/삭제하세요.
    private val patterns = listOf(
        "Exception",
        "ERROR",
        "FATAL",
        "Traceback (most recent call last)",
        "panic:",
        "Segmentation fault"
    )

    override fun applyFilter(line: String, entireLength: Int): Filter.Result? {
        if (patterns.any { line.contains(it) }) {
            ErrorAlert.trigger(project)
        }
        return null
    }
}
