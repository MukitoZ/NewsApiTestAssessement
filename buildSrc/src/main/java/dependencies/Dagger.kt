package dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.dagger() {
    implementation("com.google.dagger:dagger:2.41")
    kapt("com.google.dagger:dagger-compiler:2.41")
    implementation("com.google.dagger:dagger-android:2.41")
    kapt("com.google.dagger:dagger-android-processor:2.41")
}