package io.github.kojoo112.laughingcatsound.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.service

/**
 * 플러그인 설정을 IDE 전역에 영구 저장한다.
 * @Service 로 자동 등록되므로 plugin.xml 에 별도 등록이 필요 없다.
 */
@Service(Service.Level.APP)
@State(
    name = "LaughingCatSoundSettings",
    storages = [Storage("laughingCatSound.xml")]
)
class LaughingCatSettings : PersistentStateComponent<LaughingCatSettings.State> {

    /** 직렬화되는 상태. 필드를 추가하면 그대로 저장/복원된다. */
    class State {
        /** 재생 볼륨(0..100). 0 이면 음소거. */
        var volume: Int = 80
    }

    private var state = State()

    override fun getState(): State = state

    override fun loadState(state: State) {
        this.state = state
    }

    var volume: Int
        get() = state.volume
        set(value) {
            state.volume = value.coerceIn(0, 100)
        }

    companion object {
        fun getInstance(): LaughingCatSettings = service()
    }
}
