package io.github.kojoo112.laughingcatsound.settings

/**
 * 이미지 팝업이 표시될 IDE 창 내 위치.
 * labelKey 는 설정 UI에 표시할 다국어 번들 키다.
 */
enum class PopupPosition(val labelKey: String) {
    BOTTOM_RIGHT("settings.position.bottomRight"),
    BOTTOM_LEFT("settings.position.bottomLeft"),
    TOP_RIGHT("settings.position.topRight"),
    TOP_LEFT("settings.position.topLeft"),
    CENTER("settings.position.center"),

    /** 위 5개 위치 모두에 동시에 표시한다. */
    ALL("settings.position.all");

    companion object {
        /** ALL 을 제외한 실제 단일 위치들. */
        val CONCRETE: List<PopupPosition> = listOf(BOTTOM_RIGHT, BOTTOM_LEFT, TOP_RIGHT, TOP_LEFT, CENTER)
    }
}
