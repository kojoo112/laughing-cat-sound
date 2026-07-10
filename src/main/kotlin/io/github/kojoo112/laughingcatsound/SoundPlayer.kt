package io.github.kojoo112.laughingcatsound

import com.intellij.openapi.diagnostic.thisLogger
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.LineEvent

/**
 * 플러그인 리소스에 내장된 wav 파일을 재생한다.
 * 짧은 시간 안에 여러 번 호출되어도 겹쳐 울리지 않도록 쓰로틀링한다.
 */
object SoundPlayer {

    private const val SOUND_RESOURCE = "/sounds/laughing-cat-sound.wav"

    // 이 시간(ms) 안에 다시 요청되면 무시한다. 콘솔 에러가 폭주할 때 유용.
    private const val THROTTLE_MS = 1500L

    @Volatile
    private var lastPlayed = 0L

    fun play() {
        val now = System.currentTimeMillis()
        synchronized(this) {
            if (now - lastPlayed < THROTTLE_MS) return
            lastPlayed = now
        }
        try {
            val url = SoundPlayer::class.java.getResource(SOUND_RESOURCE) ?: return
            val stream = AudioSystem.getAudioInputStream(url)
            val clip = AudioSystem.getClip()
            clip.open(stream)
            clip.addLineListener { event ->
                if (event.type == LineEvent.Type.STOP) {
                    clip.close()
                    runCatching { stream.close() }
                }
            }
            clip.start()
        } catch (e: Exception) {
            thisLogger().warn("sound play failed", e)
        }
    }
}
