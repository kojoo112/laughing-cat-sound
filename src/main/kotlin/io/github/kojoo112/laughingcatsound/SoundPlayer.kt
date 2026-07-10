package io.github.kojoo112.laughingcatsound

import com.intellij.openapi.diagnostic.thisLogger
import io.github.kojoo112.laughingcatsound.settings.LaughingCatSettings
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import javax.sound.sampled.FloatControl
import javax.sound.sampled.LineEvent
import kotlin.math.ln

/**
 * 플러그인 리소스에 내장된 wav 파일을 재생한다.
 * 짧은 시간 안에 여러 번 호출되어도 겹쳐 울리지 않도록 쓰로틀링한다.
 */
object SoundPlayer {

    private const val SOUND_RESOURCE = "/sounds/laughing-cat-sound.wav"

    // 현재 재생 중인 클립. 새 재생을 시작하기 전에 이전 것을 멈추는 데 쓴다.
    private val clipLock = Any()
    private var currentClip: Clip? = null

    /**
     * 에러 이벤트에서 호출한다. 설정된 볼륨으로 재생한다.
     * 쓰로틀링은 [ErrorAlert] 에서 처리한다.
     */
    fun play() {
        playInternal(LaughingCatSettings.getInstance().volume)
    }

    /**
     * 설정 화면의 '소리 테스트' 버튼용. 쓰로틀링을 무시하고 주어진 볼륨으로 즉시 재생한다.
     */
    fun playTest(volume: Int) = playInternal(volume)

    private fun playInternal(volume: Int) {
        if (volume <= 0) return // 0 = 음소거
        try {
            val url = SoundPlayer::class.java.getResource(SOUND_RESOURCE) ?: return
            val stream = AudioSystem.getAudioInputStream(url)
            val clip = AudioSystem.getClip()
            clip.open(stream)
            applyVolume(clip, volume)
            clip.addLineListener { event ->
                if (event.type == LineEvent.Type.STOP) {
                    clip.close()
                    runCatching { stream.close() }
                    synchronized(clipLock) {
                        if (currentClip === clip) currentClip = null
                    }
                }
            }
            // 이전에 재생 중이던 소리를 멈추고 이 클립으로 교체한다.
            synchronized(clipLock) {
                currentClip?.let { old -> runCatching { old.stop() } }
                currentClip = clip
            }
            clip.start()
        } catch (e: Exception) {
            thisLogger().warn("sound play failed", e)
        }
    }

    /**
     * 0..100 볼륨을 데시벨 게인으로 변환해 클립에 적용한다.
     * 100 = 0dB(원음), 값이 낮을수록 감쇠. 컨트롤이 없으면 조용히 무시한다.
     */
    private fun applyVolume(clip: Clip, volume: Int) {
        val control = when {
            clip.isControlSupported(FloatControl.Type.MASTER_GAIN) ->
                clip.getControl(FloatControl.Type.MASTER_GAIN) as FloatControl
            clip.isControlSupported(FloatControl.Type.VOLUME) ->
                clip.getControl(FloatControl.Type.VOLUME) as FloatControl
            else -> return
        }
        val ratio = volume.coerceIn(1, 100) / 100.0
        val gainDb = (20.0 * ln(ratio) / ln(10.0)).toFloat() // 20 * log10(ratio)
        control.value = gainDb.coerceIn(control.minimum, control.maximum)
    }
}
