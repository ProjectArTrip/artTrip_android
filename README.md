<h1>
  <img src="https://github.com/user-attachments/assets/d4706a8f-49b0-4c96-82c3-4a82616957cb" alt="arttrip logo" width="36">
  &nbsp;아트트립 - ArtTrip (Android)
</h1>

**아트트립과 함께 국내·해외 전시·공연을 한 번에 모아보고, 일상과 여행 속 문화 경험을 더 가볍게 시작해 보세요.**  
> 여행 중 “지금 이 도시에서 볼 만한 전시”부터, 관심사에 맞는 전시까지 한 흐름으로 이어집니다.

<img width="1200" alt="10" src="https://github.com/user-attachments/assets/4ed844d1-7682-46e3-b3ab-91645fed0261" />

---

## Key Features
* **일정에 맞는 전시 찾기**: 원하는 날짜/기간을 선택하면, 그때 볼 수 있는 전시만 모아 보여줘요.
* **취향에 맞는 추천**: 관심 장르/스타일을 바탕으로 당신에게 어울리는 전시를 추천해요.
* **전시 상세 정보 한눈에**: 장소·기간·가격·소개는 물론, **진행 중/오픈 예정/마감 임박** 상태까지 바로 확인할 수 있어요.
* **공식 사이트로 바로 이동**: 더 자세한 정보나 예매가 필요하면, 공식 예매처/전시 사이트로 즉시 이동할 수 있어요.
* **보관함 & 최근 본 전시**: 저장해 둔 전시와 최근에 본 전시를 다시 쉽게 확인할 수 있어요.

---
## Tech Stack
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack%20Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Material%203](https://img.shields.io/badge/Material%203-757575?style=for-the-badge&logo=materialdesign&logoColor=white)
![Coroutines](https://img.shields.io/badge/Coroutines-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Flow](https://img.shields.io/badge/Flow-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Hilt](https://img.shields.io/badge/Hilt-0A0A0A?style=for-the-badge&logo=dagger&logoColor=white)
![Retrofit](https://img.shields.io/badge/Retrofit-000000?style=for-the-badge&logo=github&logoColor=white)
![Coil](https://img.shields.io/badge/Coil-2D9CDB?style=for-the-badge&logo=coil&logoColor=white)
![Paging%203](https://img.shields.io/badge/Paging%203-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)

## Architecture
### MVI (Presentation)
* `Intent` → `ViewModel` → `State`
* 네비게이션/토스트 등 1회성 이벤트는 `Effect`로 분리합니다.

### Clean Architecture (Layered)
* **Presentation**: Screen / ViewModel / Contract(State·Intent·Effect)
* **Domain**: Model / UseCase / Repository Interface
* **Data**: Repository Implementation / Remote·Local DataSource

### Navigation
* feature 단위 라우팅을 기준으로 화면을 구성합니다.
* route contract를 명시해 파라미터/진입 조건을 관리합니다.

### Project Structure
```text
core/
  di/
  navigation/
  ui/        # component / theme / util
data/
  local/
  remote/
  repository/
domain/
  model/
  repository/
  usecase/
presentation/
  home/
  map/
  stamp/
  bookmark/
  mypage/
  exhibition_detail/
````

---

## Design System

UI 일관성과 개발 속도를 위해 **Jetpack Compose 기반 디자인 시스템**을 구축했습니다.

* 공통 컴포넌트: Button/Chip/Tab/BottomSheet/TopBar 등
* 테마 토큰: color / typography / shape 기준 정리

---

## Getting Started

### Requirements

* Android Studio (최신 안정 버전 권장)
* JDK 17+
* Android SDK

### Run

```bash
git clone <repo-url>
```

Android Studio에서 프로젝트를 열고 실행(Run)하세요.

---

## Git Workflow

* 작업은 기능 단위 브랜치에서 진행하고, PR 리뷰 후 `develop`에 병합합니다.
* 개발 중에는 변경 단위를 PR 기준으로 관리하기 위해 **Squash Merge**를 사용합니다.
* 배포 시점에는 `develop → main`을 **Merge Commit**으로 반영해 릴리즈 단위 이력을 추적합니다.
* 브랜치 네이밍 예: `feat|fix|chore/<scope>/<task>`

---

## Versioning

이 프로젝트는 **Semantic Versioning(SemVer)** 을 따릅니다.

* 형식: `MAJOR.MINOR.PATCH`
* 예시:

  * `1.2.0` 기능 추가(하위 호환 유지)
  * `1.2.1` 버그 수정
  * `2.0.0` 호환성 깨지는 변경
