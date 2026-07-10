plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.1.20"
    id("org.jetbrains.intellij.platform") version "2.10.2"
}

group = "io.github.kojoo112"
version = "1.1.0"

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
