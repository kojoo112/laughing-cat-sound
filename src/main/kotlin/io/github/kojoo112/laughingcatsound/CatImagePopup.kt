package io.github.kojoo112.laughingcatsound

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.Balloon
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.wm.WindowManager
import com.intellij.ui.awt.RelativePoint
import io.github.kojoo112.laughingcatsound.settings.LaughingCatSettings
import io.github.kojoo112.laughingcatsound.settings.PopupPosition
import javax.swing.Icon
import javax.swing.ImageIcon
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.Timer

/**
 * 에러 발생 시 IDE 창에 고양이 이미지를 잠깐 띄웠다가 사라지게 한다.
 * 위치가 ALL 이면 5개 위치에 동시에 띄운다.
 */
object CatImagePopup {

    private const val IMAGE_RESOURCE = "/images/laughing-cat.png"

    // 이미지가 화면에 머무는 시간(ms).
    private const val FADEOUT_MS = 2000

    private val icon: Icon? by lazy {
        CatImagePopup::class.java.getResource(IMAGE_RESOURCE)?.let { ImageIcon(it) }
    }

    fun show(project: Project?) =
        show(project, LaughingCatSettings.getInstance().popupPosition)

    fun show(project: Project?, position: PopupPosition) {
        val image = icon ?: return
        ApplicationManager.getApplication().invokeLater {
            try {
                val anchor: JComponent =
                    WindowManager.getInstance().getIdeFrame(project)?.component
                        ?: WindowManager.getInstance().findVisibleFrame()?.rootPane
                        ?: return@invokeLater

                val positions =
                    if (position == PopupPosition.ALL) PopupPosition.CONCRETE else listOf(position)
                positions.forEach { showOne(anchor, image, it) }
            } catch (e: Exception) {
                thisLogger().warn("cat image popup failed", e)
            }
        }
    }

    private fun showOne(anchor: JComponent, image: Icon, position: PopupPosition) {
        val balloon = JBPopupFactory.getInstance()
            .createBalloonBuilder(JLabel(image))
            .setShowCallout(false)
            .setFadeoutTime(FADEOUT_MS.toLong())
            .setHideOnClickOutside(true)
            .setHideOnKeyOutside(true)
            .setHideOnAction(false)
            .createBalloon()

        val (point, side) = anchorPoint(anchor, position)
        balloon.show(point, side)

        // setFadeoutTime 은 모달 다이얼로그(설정창)가 열린 동안 동작하지 않을 수 있으므로,
        // modality 와 무관하게 도는 Swing Timer 로 확실히 숨긴다.
        Timer(FADEOUT_MS) { balloon.hide() }.apply {
            isRepeats = false
            start()
        }
    }

    private fun anchorPoint(anchor: JComponent, position: PopupPosition): Pair<RelativePoint, Balloon.Position> =
        when (position) {
            PopupPosition.BOTTOM_RIGHT -> RelativePoint.getSouthEastOf(anchor) to Balloon.Position.above
            PopupPosition.BOTTOM_LEFT -> RelativePoint.getSouthWestOf(anchor) to Balloon.Position.above
            PopupPosition.TOP_RIGHT -> RelativePoint.getNorthEastOf(anchor) to Balloon.Position.below
            PopupPosition.TOP_LEFT -> RelativePoint.getNorthWestOf(anchor) to Balloon.Position.below
            // CENTER 및 방어적으로 ALL 도 중앙에 매핑(ALL 은 호출 전에 CONCRETE 로 분해됨).
            PopupPosition.CENTER, PopupPosition.ALL -> RelativePoint.getCenterOf(anchor) to Balloon.Position.above
        }
}
