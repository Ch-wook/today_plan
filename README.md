# 오늘의 할 일 알림 앱 (Daily Todo Alarm)

요일별 할 일과 준비물을 등록하고, 매일 정해진 시간에 전체화면 알람으로 알려주는 Android 앱입니다.

## 주요 기능

- **요일별 할 일 관리** — 월~일 탭으로 전환하며 각 요일의 할 일을 등록/체크/삭제
- **준비물 연동** — 각 할 일에 N개의 준비물을 추가하고 개별 체크 가능
- **요일별 알람 설정** — 각 요일마다 알람 시간을 독립적으로 설정 (on/off 토글)
- **전체화면 알람** — 잠금화면에서도 즉시 전체화면으로 알람이 표시됨
- **알람 화면에서 체크** — 알람이 울릴 때 바로 할 일과 준비물을 확인하고 체크 가능
- **자동 체크 리셋** — 매일 알람이 울릴 때 전날 체크 항목이 자동 초기화
- **부팅 복구** — 재부팅 후 활성화된 알람이 자동으로 재등록됨

## 기술 스택

| 항목 | 기술 |
|------|------|
| 언어 | Kotlin |
| UI | Jetpack Compose + Material3 |
| 아키텍처 | MVVM + Clean Architecture (ui / domain / data) |
| DI | Hilt |
| DB | Room |
| 알람 | AlarmManager (`setExactAndAllowWhileIdle`) |
| 전체화면 | Full-Screen Intent + AlarmActivity |
| 비동기 | Coroutines + Flow |
| 내비게이션 | Navigation Compose |

## 프로젝트 구조

```
app/src/main/java/com/example/dailytodo/
├── DailyTodoApplication.kt
├── core/           # 상수, 유틸리티 (TimeExt)
├── data/           # Entity, DAO, Room DB, Repository 구현체, Mapper
├── domain/         # 도메인 모델, Repository 인터페이스, UseCase
├── alarm/          # AlarmScheduler, AlarmReceiver, BootReceiver, NotificationHelper
├── di/             # Hilt 모듈 (Database, Repository, Alarm)
└── ui/
    ├── home/           # 메인 할 일 목록 화면
    ├── editor/         # 할 일 추가 화면
    ├── alarmsetting/   # 요일별 알람 시간 설정 화면
    ├── alarmscreen/    # 전체화면 알람 화면
    ├── navigation/     # NavHost
    └── theme/          # Compose 테마
```

## 빌드 요구사항

- **minSdk** 26 (Android 8.0 이상)
- **targetSdk** 34 (Android 14)
- Android Studio Hedgehog (2023.1.1) 이상
- JDK 8 이상

## 설치 및 실행

1. 저장소 클론
   ```bash
   git clone https://github.com/Ch-wook/today_plan.git
   ```
2. Android Studio에서 프로젝트 열기
3. Gradle Sync 완료 후 실행

## 필요 권한 안내

앱 첫 실행 또는 알람 설정 화면에서 아래 권한을 허용해야 정상 동작합니다.

| 권한 | 용도 |
|------|------|
| `SCHEDULE_EXACT_ALARM` | 정확한 시각에 알람 예약 |
| `USE_FULL_SCREEN_INTENT` | 잠금화면 전체화면 알람 표시 |
| `POST_NOTIFICATIONS` | 알림 발송 (Android 13+) |
| `RECEIVE_BOOT_COMPLETED` | 재부팅 후 알람 자동 복구 |

## 동작 방식

```
알람 시간 도달
   ↓
AlarmReceiver.onReceive()
   ↓
AlarmNotificationHelper → fullScreenIntent 발사
   ↓
AlarmActivity (잠금화면 위에 표시)
   ↓
AlarmScreen (할 일 체크 + 알람 끄기)
   ↓
AlarmScheduler → 다음 주 같은 요일로 재등록
```

## 라이선스

MIT
