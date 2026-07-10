package io.github.kojoo112.laughingcatsound

import com.intellij.DynamicBundle
import org.jetbrains.annotations.Nls
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.PropertyKey

@NonNls
private const val BUNDLE = "messages.LaughingCatBundle"

/**
 * 플러그인 UI 문자열 번들.
 * DynamicBundle 을 쓰므로 IDE 언어 설정/언어팩에 맞춰 messages/LaughingCatBundle_<locale>.properties 가 선택된다.
 */
object LaughingCatBundle : DynamicBundle(BUNDLE) {

    @Nls
    fun message(
        @PropertyKey(resourceBundle = BUNDLE) key: String,
        vararg params: Any
    ): String = getMessage(key, *params)
}
