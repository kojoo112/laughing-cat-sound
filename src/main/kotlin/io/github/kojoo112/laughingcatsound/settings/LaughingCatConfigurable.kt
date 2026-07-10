package io.github.kojoo112.laughingcatsound.settings

import com.intellij.openapi.options.Configurable
import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.FormBuilder
import com.intellij.util.ui.JBUI
import io.github.kojoo112.laughingcatsound.LaughingCatBundle
import io.github.kojoo112.laughingcatsound.SoundPlayer
import java.awt.FlowLayout
import javax.swing.JButton
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

        val testButton = JButton(LaughingCatBundle.message("settings.test.button")).apply {
            addActionListener { SoundPlayer.playTest(slider.value) }
        }

        val volumeRow = JPanel(FlowLayout(FlowLayout.LEFT, JBUI.scale(8), 0)).apply {
            add(slider)
            add(label)
        }

        volumeSlider = slider
        volumeLabel = label

        rootPanel = FormBuilder.createFormBuilder()
            .addLabeledComponent(LaughingCatBundle.message("settings.volume.label"), volumeRow)
            .addComponent(testButton)
            .addComponentFillVertically(JPanel(), 0)
            .panel
        return rootPanel!!
    }

    override fun isModified(): Boolean =
        volumeSlider?.value != LaughingCatSettings.getInstance().volume

    override fun apply() {
        volumeSlider?.let { LaughingCatSettings.getInstance().volume = it.value }
    }

    override fun reset() {
        val v = LaughingCatSettings.getInstance().volume
        volumeSlider?.value = v
        volumeLabel?.text = formatVolume(v)
    }

    override fun disposeUIResources() {
        rootPanel = null
        volumeSlider = null
        volumeLabel = null
    }

    private fun formatVolume(v: Int): String =
        if (v <= 0) LaughingCatBundle.message("settings.volume.muted") else "$v%"
}
