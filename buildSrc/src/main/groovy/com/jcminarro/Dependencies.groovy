package com.jcminarro

class Dependencies {

    private static String KOTLIN_VERSION = '1.3.61'
    private static String ANDROID_BUILD_TOOL_VERSION = '3.1.3'
    private static String APP_COMPAT_VERSION = '1.2.0'
    private static String ROBOLECTRIC_VERSION = '3.8'
    private static String JUNIT_VERSION = '4.12'
    private static String ANDROID_TEST_RUNNER_VERSION = '1.0.2'
    private static String ESPRESSO_VERSION = '3.0.2'
    private static String MOKITO_VERSION = '2.18.0'
    private static String KLUENT_VERSION = '1.60'
    private static String MOKITO_KOTLIN_VERSION = '1.5.0'
    private static String VIEW_PUMP_VERSION = '2.0.3'

    static final String kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_VERSION"
    static final String androidBuildToolGradlePlugin = "com.android.tools.build:gradle:$ANDROID_BUILD_TOOL_VERSION"
    static final String kotlinSTDLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$KOTLIN_VERSION"
    static final String appCompat = "androidx.appcompat:appcompat:$APP_COMPAT_VERSION"
    static final String robolectric = "org.robolectric:robolectric:$ROBOLECTRIC_VERSION"
    static final String jUnit = "junit:junit:$JUNIT_VERSION"
    static final String AndroidTestRunner = "com.android.support.test:runner:$ANDROID_TEST_RUNNER_VERSION"
    static final String espresso = "com.android.support.test.espresso:espresso-core:$ESPRESSO_VERSION"
    static final String mockito = "org.mockito:mockito-core:$MOKITO_VERSION"
    static final String kotlinTestJunit = "org.jetbrains.kotlin:kotlin-test-junit:$KOTLIN_VERSION"
    static final String kluent = "org.amshove.kluent:kluent:$KLUENT_VERSION"
    static final String mockitoKotlin = "com.nhaarman:mockito-kotlin:$MOKITO_KOTLIN_VERSION"
    static final String viewPump = "io.github.inflationx:viewpump:$VIEW_PUMP_VERSION"
}