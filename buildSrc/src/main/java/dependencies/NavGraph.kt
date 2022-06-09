package dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler



fun DependencyHandler.navGraph(){
    val navVersion = "2.4.2"

    implementation ("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation ("androidx.navigation:navigation-ui-ktx:$navVersion")
}