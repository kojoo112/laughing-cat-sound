plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.1.20"
    id("org.jetbrains.intellij.platform") version "2.10.2"
}

group = "io.github.kojoo112"
version = "1.3.0"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

// Read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html
dependencies {
    intellijPlatform {
        intellijIdea("2025.2.4")
        testFramework(org.jetbrains.intellij.platform.gradle.TestFrameworkType.Platform)


        // Add plugin dependencies for compilation here, example:
        // bundledPlugin("com.intellij.java")
    }
}

intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild = "252.25557"
        }

        changeNotes = """
            <b>1.3.0</b>
            <ul>
                <li>실행 중 콘솔 텍스트로 에러를 감지하던 방식을 제거했습니다. (통과하는 테스트가 스택 트레이스를 출력하는 등 오탐이 잦았습니다)</li>
                <li>이제 다음 세 경우에만 반응합니다: 테스트 실패(IDE 테스트 러너 이벤트로 감지), 애플리케이션이 종료 코드 1로 끝남(시작 실패·uncaught 예외 등), 컴파일 에러로 실행 중단.</li>
                <li>정상 종료나 사용자가 직접 실행을 멈춘 경우(Stop)에는 소리가 나지 않습니다.</li>
            </ul>
            <b>1.3.0 (English)</b>
            <ul>
                <li>Remove console-text error detection. (It fired too often — e.g. when a passing test prints a stack trace.)</li>
                <li>Now reacts only in three cases: a test fails (detected via the IDE test-runner events), an application exits with code 1 (a failed startup, an uncaught exception, ...), or a run is aborted by a compilation error.</li>
                <li>Stays silent on a normal exit or when you stop the run yourself.</li>
            </ul>
            <b>1.2.0</b>
            <ul>
                <li>에러 발생 시 웃는 고양이 이미지를 잠깐 띄웠다가 사라지게 하는 팝업 추가</li>
                <li>팝업 위치 선택 추가 (우측 하단/좌측 하단/우측 상단/좌측 상단/중앙, 그리고 모두)</li>
                <li>"모두" 선택 시 다섯 위치에 동시에 표시</li>
                <li>테스트 버튼이 소리와 이미지 팝업을 함께 미리보기하도록 변경</li>
                <li>설정창에서 테스트 시 팝업이 사라지지 않던 문제 수정</li>
            </ul>
            <b>1.2.0 (English)</b>
            <ul>
                <li>Add an image popup that briefly shows a laughing cat and fades out on error.</li>
                <li>Add a popup position setting (bottom-right/left, top-right/left, center, and All).</li>
                <li>"All" shows the image at the five positions at once.</li>
                <li>The Test button now previews both the sound and the image popup.</li>
                <li>Fix the popup not disappearing when tested from the settings dialog.</li>
            </ul>
            <b>1.1.0</b>
            <ul>
                <li>설정 페이지 추가: Settings/Preferences | Tools | Laughing Cat Sound</li>
                <li>볼륨 조절 추가 (0-100, 0이면 음소거)</li>
                <li>선택한 볼륨으로 미리 듣는 "소리 테스트" 버튼 추가</li>
                <li>새 소리가 재생되면 이전 소리를 멈춰 겹침 방지</li>
                <li>설정 UI 다국어 지원 (한국어/영어)</li>
            </ul>
            <b>1.1.0 (English)</b>
            <ul>
                <li>Add a settings page at Settings/Preferences | Tools | Laughing Cat Sound.</li>
                <li>Add a volume control (0-100, where 0 mutes the sound).</li>
                <li>Add a "Test sound" button to preview the sound at the chosen volume.</li>
                <li>A newly triggered sound now stops the previous one instead of overlapping.</li>
                <li>Localize the settings UI (Korean and English).</li>
            </ul>
            <b>1.0.0</b>
            <ul>
                <li>Play a laughing cat sound when a run/debug process exits with an error.</li>
                <li>Play the sound when a run is aborted by a compilation error.</li>
                <li>Play the sound when an error pattern is printed to a console.</li>
            </ul>
        """.trimIndent()
    }
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "21"
        targetCompatibility = "21"
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
    }
}
