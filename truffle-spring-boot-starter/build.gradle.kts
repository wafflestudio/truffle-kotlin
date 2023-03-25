apply {
    plugin("kotlin-allopen")
    plugin("kotlin-spring")
}

dependencies {
    compileOnly("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.springframework.boot:spring-boot-starter-webflux")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation(project(":truffle-core"))
    compileOnly(project(":truffle-logback"))
}
