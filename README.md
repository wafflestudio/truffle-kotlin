# truffle-kotlin
## 와플스튜디오 로깅 라이브러리
- 연동된 와플스튜디오 slack 채널에 로그를 전송하는 kotlin(java) 라이브러리

## 사용법
### slack 채널 생성 및 key 신청
- slack 의 #project-truffle (https://wafflestudio.slack.com/archives/C04KKHS2P1D) 에서 프로젝트 추가 요청
- ex) `안녕하세요! #team-ex 에서 truffle 을 이용하려고 해서, #team-ex-alert 채널에 연결되도록 프로젝트 하나 추가 요청드립니다!`

### spring 연동
#### 방식1) logback 연동 (권장)
- `build.gradle.kts` 혹은 `build.gradle` 파일에 아래와 같이 추가
  - build.gradle.kts
    ```kotlin
    implementation("com.wafflestudio.truffle.sdk:truffle-logback-appender:1.2.0")
    ```
  - build.gradle
    ```groovy
    implementation 'com.wafflestudio.truffle.sdk:truffle-logback-appender:1.2.0'
    ```
- 다음 spring 프로퍼티 설정
  - `truffle.client.api-key: {api-key}`
    - api-key 는 slack 트러플 채널(#project-truffle)에서 발급받은 key
- logback 연동 설정
  - logback-spring.xml 파일 (혹은 spring `logging.config`에 설정한 logback xml 설정 파일)에 truffle-appender.xml 추가
    - `<include resource="com/wafflestudio/truffle/sdk/logback/truffle-appender.xml"/>`
  - logback-spring.xml 예시
    ```xml
    <?xml version="1.0" encoding="UTF-8" ?>
    <configuration>
        <statusListener class="ch.qos.logback.core.status.NopStatusListener" />

        <include resource="org/springframework/boot/logging/logback/defaults.xml" />
        <include resource="org/springframework/boot/logging/logback/file-appender.xml" />
        <include resource="org/springframework/boot/logging/logback/console-appender.xml"/>
        <include resource="com/wafflestudio/truffle/sdk/logback/truffle-appender.xml"/>

        <root level="INFO">
            <appender-ref ref="CONSOLE" />
            <appender-ref ref="TRUFFLE_ERROR_APPENDER"/>
        </root>
    </configuration>
    ```
    - `{api-key}` 는 slack 트러플 채널(#project-truffle)에서 발급받은 key

#### 방식2) truffle-kotlin 연동
- `build.gradle.kts` 혹은 `build.gradle` 파일에 아래와 같이 추가
  - build.gradle.kts
    ```kotlin
    implementation("com.wafflestudio.truffle.sdk:truffle-spring-boot-starter:1.2.0")
    ```
  - build.gradle
    ```groovy
    implementation 'com.wafflestudio.truffle.sdk:truffle-spring-boot-starter:1.2.0'
    ```
- 다음과 같은 spring 프로퍼티 생성
  - `truffle.auto.enabled: true`
  - `truffle.client.api-key: {api-key}`
    - api-key 는 slack 트러플 채널(#project-truffle)에서 발급받은 key
