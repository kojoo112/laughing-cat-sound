package io.github.kojoo112.laughingcatsound

import com.intellij.openapi.project.Project
import io.github.kojoo112.laughingcatsound.settings.LaughingCatSettings

/**
 * 에러 이벤트의 단일 진입점.
 * 짧은 시간 안에 이벤트가 폭주해도 한 번만 반응하도록 여기서 쓰로틀링하고,
 * 소리 재생과 이미지 팝업을 함께 발화시킨다(각각 따로 쓰로틀하면 서로 어긋나므로 통합).
 */
object ErrorAlert {

    // 이 시간(ms) 안에 다시 요청되면 무시한다.
    private const val THROTTLE_MS = 1500L

    @Volatile
    private var lastFired = 0L

    fun trigger(project: Project?) {
        val now = System.currentTimeMillis()
        synchronized(this) {
            if (now - lastFired < THROTTLE_MS) return
            lastFired = now
        }
        SoundPlayer.play()
        if (LaughingCatSettings.getInstance().showImagePopup) {
            CatImagePopup.show(project)
        }
    }
}
