object Sdk {
    const val MIN_SDK_VERSION = 22
    const val TARGET_SDK_VERSION = 30
    const val COMPILE_SDK_VERSION = 30
}

object Versions {
    const val ANDROIDX_TEST_EXT = "1.1.1"
    const val ANDROIDX_TEST = "1.2.0"
    const val APPCOMPAT = "1.1.0"
    const val CONSTRAINT_LAYOUT = "2.0.4"
    const val CORE_KTX = "1.3.2"
    const val ESPRESSO_CORE = "3.2.0"
    const val JUNIT = "4.13.1"
    const val KTLINT = "0.40.0"
    const val RETROFIT = "2.9.0"
    const val RETROFIT_SERIAL_ADAPTER = "0.8.0"
    const val HILT = "2.32-alpha"
    const val HILT_VIEWMODEL = "1.0.0-alpha03"
    const val HILT_NAVIGATION = "1.0.0-alpha03"
    const val OKHTTP = "4.9.0"
    const val COIL = "1.1.0"
    const val LIVEDATAKTX = "2.2.0"
    const val VIEWMODELKTX = "2.2.0"
    const val NAVIGATION = "2.3.0"
    const val ROOM = "2.2.5"
    const val TIMBER = "4.7.1"
    const val MATERIAL = "1.3.0-alpha04"
    const val RECYCLER = "1.1.0"
    const val RECYCLER_SELECTION = "1.1.0-rc01"
    const val PERMISSIONS_VER = "2.0.1"
    const val NAV_ARGS = "2.3.0"
    const val FLOWBINDING_VERSION = "1.0.0-alpha02"
    const val JSON_SERIALIZATION = "1.0.1"
    const val SWIPE_REFRESH_LAYOUT = "1.1.0"
    const val FRAGMENT_KTX: String = "1.2.5"
    const val PAGIN_VERSION: String = "3.0.0-alpha10"
    const val MOCKITO_VERSION: String = "1.10.19"
    const val COROUTINE_TEST: String = "1.4.2"
    const val MOCKITO_KOTLIN_VERSION = "2.2.0"
    const val MOCKK_VERSION = "1.10.6"
    const val TRUTH = "1.1.2"
}

object BuildPluginsVersion {
    const val AGP = "4.1.2"
    const val DETEKT = "1.9.1"
    const val KOTLIN = "1.4.31"
    const val KTLINT = "9.2.1"
    const val VERSIONS_PLUGIN = "0.28.0"
    const val SERIALIZATION = KOTLIN

}

object CoreLibs {
    const val ANDROIDX_APPCOMPAT = "androidx.appcompat:appcompat:${Versions.APPCOMPAT}"
    const val ANDROIDX_CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"
    const val ANDROIDX_CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
    const val ANDROIDX_NAV_RUNTIME_KTX = "androidx.navigation:navigation-runtime-ktx:${Versions.NAVIGATION}"
    const val ANDROIDX_NAV_FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION}"
    const val ANDROIDX_NAV_UI_KTX = "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION}"
    const val ANDROID_SWIPE_REFRESH = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.SWIPE_REFRESH_LAYOUT}"

    const val TIMBER_LIB = "com.jakewharton.timber:timber:${Versions.TIMBER}"
    const val MATERIAL_UI_LIB = "com.google.android.material:material:${Versions.MATERIAL}"
    const val RECYCLER_VIEW = "androidx.recyclerview:recyclerview:${Versions.RECYCLER}"
    const val RECYCLER_VIEW_SELECTION = "androidx.recyclerview:recyclerview-selection:${Versions.RECYCLER_SELECTION}"
    const val EAZY_PERMISSIONS = "com.sagar:dslpermission:${Versions.PERMISSIONS_VER}"
    const val PAGING_LIB = "androidx.paging:paging-runtime:${Versions.PAGIN_VERSION}}"
    const val KOTLIN_LIB = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${BuildPluginsVersion.KOTLIN}"


}

object FlowBindingUI {
    const val FLOWBINDING_CORE = "io.github.reactivecircus.flowbinding:flowbinding-android:${Versions.FLOWBINDING_VERSION}"
    const val FLOWBINDING_BASE = "io.github.reactivecircus.flowbinding:flowbinding-core:${Versions.FLOWBINDING_VERSION}"
    const val FLOWBINDING_RECYCLER = "io.github.reactivecircus.flowbinding:flowbinding-recyclerview:${Versions.FLOWBINDING_VERSION}"
    const val FLOWBINDING_VIEWPAGER2 = "io.github.reactivecircus.flowbinding:flowbinding-viewpager2:${Versions.FLOWBINDING_VERSION}"

}


object JetPackKTX {
    const val VIEWMODELSCOPE = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.VIEWMODELKTX}"
    const val LIFECYCLESCOPE = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIVEDATAKTX}"
    const val LIVEDATA = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIVEDATAKTX}"
    const val LIFECYCLE_EXTENSION = "androidx.lifecycle:lifecycle-extensions:${Versions.LIVEDATAKTX}"
    const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${Versions.FRAGMENT_KTX}"
}

object Navigation {
    const val NAVIGATION_COMP = "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION}"
    const val NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION}"
    const val NAVIGATION_DYNAMIC_FEATURE = "androidx.navigation:navigation-dynamic-features-fragment:${Versions.NAVIGATION}"

}

object DILibs {
    const val HILT_DI = "com.google.dagger:hilt-android:${Versions.HILT}"
    const val HILT_VIEWMODEL = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.HILT_VIEWMODEL}"
    const val HILT_DI_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
    const val HILT_DI_COMPILER_KAPT = "androidx.hilt:hilt-compiler:${Versions.HILT_VIEWMODEL}"
    const val HILT_NAVIGATION = "androidx.hilt:hilt-compiler:${Versions.HILT_NAVIGATION}"
}

object NetLibs {
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val OKHTTP = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"
    const val OKHTTP_LOGGIN_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}"
    const val KOTLIN_SERIALIZATION = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.JSON_SERIALIZATION}"

    const val SERIALIZATION_ADAPTER = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.RETROFIT_SERIAL_ADAPTER}"

}


object CoilImageLib {
    const val COIL = "io.coil-kt:coil:${Versions.COIL}"
}

object RoomLib {
    const val ROOM = "androidx.room:room-runtime:${Versions.ROOM}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"
}

object TestingLib {
    const val JUNIT = "junit:junit:${Versions.JUNIT}"

    //    const val ROBOELECTRIC = "'androidx.test:core:${Versions.ROBOLECTRIC}"
    const val MOCKITO = "org.mockito:mockito-core:${Versions.MOCKITO_VERSION}"
    const val MOCKITO_KOTLIN = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.MOCKITO_KOTLIN_VERSION}"

    const val COROUTINE_HELPER = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINE_TEST}"
    const val ROOM_TESTING = "androidx.room:room-testing:${Versions.ROOM}"
    const val RETTROFIT_MOCK = "com.squareup.retrofit2:retrofit-mock:${Versions.RETROFIT}"
    const val MOCKK_TEST = "io.mockk:mockk:${Versions.MOCKK_VERSION}"
    const val TEST_TRUTH = "com.google.truth:truth:${Versions.TRUTH}"
//    const val MOCKK_COROUTINE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.MOCKK_VERSION}"
}
object AndroidTestingLib {

    const val ANDROIDX_TEST_RULES = "androidx.test:rules:${Versions.ANDROIDX_TEST}"
    const val ANDROIDX_TEST_RUNNER = "androidx.test:runner:${Versions.ANDROIDX_TEST}"
    const val ANDROIDX_TEST_EXT_JUNIT = "androidx.test.ext:junit:${Versions.ANDROIDX_TEST_EXT}"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
    const val NAVIGATION_TEST = "androidx.navigation:navigation-testing:${Versions.NAVIGATION}"
    const val MOCKK_INST_TEST ="io.mockk:mockk-android:${Versions.MOCKK_VERSION}"
}
