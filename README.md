# Laughing Cat Sound (한국어)

IntelliJ IDE에서 에러가 발생하는 순간 웃는 고양이 소리를 재생해, 실패한 실행을 놓치지 않고
조금은 유쾌하게 알아챌 수 있는 플러그인입니다.

## 기능

- 다음과 같은 경우에 소리를 재생합니다:
  - 실행/디버그 프로세스가 에러로 종료될 때 (0이 아닌 종료 코드),
  - 컴파일 에러로 실행이 중단될 때,
  - 콘솔에 에러 패턴(`Exception`, `ERROR`, 스택 트레이스, `panic` 등)이 출력될 때.
- 새 소리가 재생되면 이전 소리를 멈춰 겹치지 않습니다.
- **Settings/Preferences | Tools | Laughing Cat Sound** 설정 페이지 제공:
  - **볼륨** 조절 (0–100, 0이면 음소거).
  - 선택한 볼륨으로 미리 듣는 **소리 테스트** 버튼.
- IDE 언어에 맞춰 전환되는 다국어 UI (영어 / 한국어).

## 설치

- **마켓플레이스에서 설치 (권장):** **Settings/Preferences | Plugins | Marketplace** 를 열고
  **Laughing Cat Sound** 를 검색해 **Install** 후, 안내에 따라 IDE를 재시작합니다.
  (JetBrains 마켓플레이스에 게시된 이후부터 검색됩니다.)
- **디스크에서 설치:** 플러그인 ZIP(마켓플레이스 페이지나 릴리스에서 내려받은 것)을 준비한 뒤,
  **Settings/Preferences | Plugins | ⚙ | Install Plugin from Disk...** 에서 그 ZIP을 선택합니다.

### 소스에서 빌드 (개발용)

- `./gradlew buildPlugin` — 설치용 ZIP을 `build/distributions/` 에 생성합니다
  (Windows PowerShell에서는 `.\gradlew.bat buildPlugin`).
- `./gradlew runIde` — 플러그인이 설치된 샌드박스 IDE를 띄워 테스트합니다.

## 요구 사항

- IntelliJ IDEA 2025.2 이상 (플러그인은 IDE 번들 런타임에서 동작하므로 별도 JDK가 필요 없으며,
  프로젝트가 어떤 JDK를 쓰든 동작합니다).

## 설정

**Settings/Preferences | Tools | Laughing Cat Sound** 에서 볼륨 조절·음소거가 가능하고,
**소리 테스트** 버튼으로 미리 들어볼 수 있습니다.

---

# Laughing Cat Sound (English)

Plays a laughing cat sound the moment an error appears in your IntelliJ IDE, so a failed
run never slips by unnoticed — with a bit of humor to soften the blow.

## Features

- Plays a sound when:
  - a run or debug process exits with an error (non-zero exit code),
  - a run is aborted by a compilation error, or
  - an error pattern (`Exception`, `ERROR`, stack trace, `panic`, ...) is printed to a console.
- A newly triggered sound stops the previous one instead of overlapping.
- Settings page at **Settings/Preferences | Tools | Laughing Cat Sound**:
  - **Volume** control (0–100, where 0 mutes the sound).
  - **Test sound** button to preview the sound at the chosen volume.
- Localized UI (English / Korean), following the IDE language.

## Installation

- **From the Marketplace (recommended):** open **Settings/Preferences | Plugins | Marketplace**,
  search for **Laughing Cat Sound**, click **Install**, and restart the IDE when prompted.
  (Available once the plugin is published to the JetBrains Marketplace.)
- **From disk:** download the plugin ZIP (from the Marketplace page or a release), then go to
  **Settings/Preferences | Plugins | ⚙ | Install Plugin from Disk...** and select the ZIP.

### Build from source (for development)

- `./gradlew buildPlugin` builds the installable ZIP into `build/distributions/`
  (on Windows PowerShell, use `.\gradlew.bat buildPlugin`).
- `./gradlew runIde` launches a sandbox IDE with the plugin for testing.

## Requirements

- IntelliJ IDEA 2025.2 or newer (the plugin runs on the IDE's bundled runtime; no separate JDK
  is required, and it works regardless of the JDK your project uses).

## Configuration

Open **Settings/Preferences | Tools | Laughing Cat Sound** to adjust the volume, mute the sound,
or preview it with the **Test sound** button.
