
package com.skydroid.dagger.di

import android.content.Context
import com.skydroid.dagger.login.LoginComponent
import com.skydroid.dagger.registration.RegistrationComponent
import com.skydroid.dagger.user.UserManager
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
// Definition of a Dagger component that adds info from the different modules to the graph
@Component(modules = [StorageModule::class, AppSubcomponents::class])
interface AppComponent {

    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    // Types that can be retrieved from the graph
    fun registrationComponent(): RegistrationComponent.Factory
    fun loginComponent(): LoginComponent.Factory
    fun userManager(): UserManager
}
