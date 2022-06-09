package dependencies

import dependencies.android.*
import dependencies.kotlin.*
import dependencies.retrofit_okhttp.*
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.appLibraries() {
    androidCore()
    materialDesign()
    testUnit()
    androidX()
    vmLifeCycle()
    coroutine()
    gson()
    okHttp()
    retrofit()
    glide()
    gander()
//    dagger()
    androidPaging()
    daggerHilt()
    navGraph()
//    googleFirebase()
}