plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.navigationSafeArgs) apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
}