package io.github.kojoo112.laughingcatsound.settings

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.SimpleListCellRenderer
import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.FormBuilder
import com.intellij.util.ui.JBUI
import io.github.kojoo112.laughingcatsound.CatImagePopup
import io.github.kojoo112.laughingcatsound.LaughingCatBundle
import io.github.kojoo112.laughingcatsound.SoundPlayer
import java.awt.FlowLayout
import javax.swing.JButton
import javax.swing.JCheckBox
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JSlider

/**
 * Settings → Tools → Laughing Cat Sound 페이지.
 * 볼륨 슬라이더와 '소리 테스트' 버튼을 제공한다.
 */
class LaughingCatConfigurable : Configurable {

    private var rootPanel: JPanel? = null
    private var volumeSlider: JSlider? = null
    private var volumeLabel: JBLabel? = null
    private var imagePopupCheckBox: JCheckBox? = null
    private var positionComboBox: ComboBox<PopupPosition>? = null

    override fun getDisplayName(): String = LaughingCatBundle.message("settings.displayName")

    override fun createComponent(): JComponent {
        val slider = JSlider(0, 100, LaughingCatSettings.getInstance().volume).apply {
            majorTickSpacing = 25
            minorTickSpacing = 5
            paintTicks = true
            paintLabels = true
        }
        val label = JBLabel(formatVolume(slider.value))
        slider.addChangeListener { label.text = formatVolume(slider.value) }

        val positionCombo = ComboBox(PopupPosition.values()).apply {
            selectedItem = LaughingCatSettings.getInstance().popupPosition
            renderer = SimpleListCellRenderer.create("") { LaughingCatBundle.message(it.labelKey) }
        }

        val testButton = JButton(LaughingCatBundle.message("settings.test.button")).apply {
            addActionListener {
                // 현재 설정값(적용 전 UI 값)으로 소리와 이미지 팝업을 함께 미리보기한다.
                SoundPlayer.playTest(slider.value)
                CatImagePopup.show(null, positionCombo.selectedItem as PopupPosition)
            }
        }

        val volumeRow = JPanel(FlowLayout(FlowLayout.LEFT, JBUI.scale(8), 0)).apply {
            add(slider)
            add(label)
        }

        val imagePopup = JCheckBox(
            LaughingCatBundle.message("settings.image.checkbox"),
            LaughingCatSettings.getInstance().showImagePopup
        )

        volumeSlider = slider
        volumeLabel = label
        imagePopupCheckBox = imagePopup
        positionComboBox = positionCombo

        rootPanel = FormBuilder.createFormBuilder()
            .addLabeledComponent(LaughingCatBundle.message("settings.volume.label"), volumeRow)
            .addComponent(imagePopup)
            .addLabeledComponent(LaughingCatBundle.message("settings.position.label"), positionCombo)
            .addComponent(testButton)
            .addComponentFillVertically(JPanel(), 0)
            .panel
        return rootPanel!!
    }

    override fun isModified(): Boolean {
        val settings = LaughingCatSettings.getInstance()
        return volumeSlider?.value != settings.volume ||
            imagePopupCheckBox?.isSelected != settings.showImagePopup ||
            positionComboBox?.selectedItem != settings.popupPosition
    }

    override fun apply() {
        val settings = LaughingCatSettings.getInstance()
        volumeSlider?.let { settings.volume = it.value }
        imagePopupCheckBox?.let { settings.showImagePopup = it.isSelected }
        (positionComboBox?.selectedItem as? PopupPosition)?.let { settings.popupPosition = it }
    }

    override fun reset() {
        val settings = LaughingCatSettings.getInstance()
        volumeSlider?.value = settings.volume
        volumeLabel?.text = formatVolume(settings.volume)
        imagePopupCheckBox?.isSelected = settings.showImagePopup
        positionComboBox?.selectedItem = settings.popupPosition
    }

    override fun disposeUIResources() {
        rootPanel = null
        volumeSlider = null
        volumeLabel = null
        imagePopupCheckBox = null
        positionComboBox = null
    }

    private fun formatVolume(v: Int): String =
        if (v <= 0) LaughingCatBundle.message("settings.volume.muted") else "$v%"
}
