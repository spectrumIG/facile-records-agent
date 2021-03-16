plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization") version BuildPluginsVersion.SERIALIZATION
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion(Sdk.COMPILE_SDK_VERSION)

    defaultConfig {
        minSdkVersion(Sdk.MIN_SDK_VERSION)
        targetSdkVersion(Sdk.TARGET_SDK_VERSION)

        applicationId = AppCoordinates.APP_ID
        versionCode = AppCoordinates.APP_VERSION_CODE
        versionName = AppCoordinates.APP_VERSION_NAME
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }

    lintOptions {
        isWarningsAsErrors = true
        isAbortOnError = true
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    implementation(project(":library-android"))
    implementation(project(":library-kotlin"))

    implementation(CoreLibs.KOTLIN_LIB)
    implementation(CoreLibs.ANDROIDX_APPCOMPAT)
    implementation(CoreLibs.ANDROIDX_CONSTRAINT_LAYOUT)
    implementation(CoreLibs.ANDROIDX_CORE_KTX)
    implementation(CoreLibs.ANDROIDX_NAV_FRAGMENT_KTX)
    implementation(CoreLibs.ANDROIDX_NAV_RUNTIME_KTX)
    implementation(CoreLibs.ANDROIDX_NAV_UI_KTX)
    implementation(CoreLibs.MATERIAL_UI_LIB)
    implementation(CoreLibs.RECYCLER_VIEW)
    implementation(CoreLibs.RECYCLER_VIEW_SELECTION)
    implementation(CoreLibs.ANDROID_SWIPE_REFRESH)

    implementation(DILibs.HILT_DI)
    implementation(DILibs.HILT_VIEWMODEL)
    implementation(DILibs.HILT_NAVIGATION)

    implementation(NetLibs.OKHTTP)
    implementation(NetLibs.RETROFIT)
    implementation(NetLibs.OKHTTP_LOGGIN_INTERCEPTOR)
    implementation(NetLibs.SERIALIZATION_ADAPTER)
    implementation(NetLibs.KOTLIN_SERIALIZATION)
    implementation(NetLibs.HTTP_MOCKER_LIB)
    implementation(NetLibs.HTTP_MOCKER_ADAPTER)


    implementation(JetPackKTX.LIVEDATA)
    implementation(JetPackKTX.LIFECYCLESCOPE)
    implementation(JetPackKTX.VIEWMODELSCOPE)
    implementation(JetPackKTX.LIFECYCLE_EXTENSION)
    implementation(JetPackKTX.FRAGMENT_KTX)

    implementation(Navigation.NAVIGATION_COMP)
    implementation(Navigation.NAVIGATION_DYNAMIC_FEATURE)
    implementation(Navigation.NAVIGATION_UI)
    implementation(Navigation.NAVIGATION_DYNAMIC_FEATURE)

    implementation(CoreLibs.TIMBER_LIB)
    implementation(CoreLibs.EAZY_PERMISSIONS)

    implementation(RoomLib.ROOM)
    implementation(RoomLib.ROOM_KTX)

    implementation(FlowBindingUI.FLOWBINDING_CORE)
    implementation(FlowBindingUI.FLOWBINDING_BASE)
    implementation(FlowBindingUI.FLOWBINDING_RECYCLER)
    implementation(FlowBindingUI.FLOWBINDING_VIEWPAGER2)

    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    kapt(DILibs.HILT_DI_COMPILER)
    kapt(DILibs.HILT_DI_COMPILER_KAPT)
    kapt(RoomLib.ROOM_COMPILER)

    testImplementation(TestingLib.JUNIT)
    testImplementation(TestingLib.ROOM_TESTING)
    testImplementation(TestingLib.MOCKITO)
    testImplementation(TestingLib.MOCKITO_KOTLIN)
    testImplementation(TestingLib.COROUTINE_HELPER)

    testImplementation(TestingLib.RETROFIT_MOCK)
    testImplementation(TestingLib.MOCKK_TEST)
    testImplementation(TestingLib.TEST_TRUTH)
//    testImplementation(TestingLib.MOCKK_COROUTINE)

    androidTestImplementation(AndroidTestingLib.ANDROIDX_TEST_EXT_JUNIT)
    androidTestImplementation(AndroidTestingLib.ANDROIDX_TEST_RULES)
    androidTestImplementation(AndroidTestingLib.ESPRESSO_CORE)
    androidTestImplementation(AndroidTestingLib.NAVIGATION_TEST)
    androidTestImplementation(AndroidTestingLib.MOCKK_INST_TEST)
}
