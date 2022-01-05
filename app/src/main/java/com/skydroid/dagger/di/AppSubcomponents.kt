
package com.skydroid.dagger.di

import com.skydroid.dagger.login.LoginComponent
import com.skydroid.dagger.registration.RegistrationComponent
import com.skydroid.dagger.user.UserComponent
import dagger.Module

// This module tells a Component which are its subcomponents
@Module(
    subcomponents = [
        RegistrationComponent::class,
        LoginComponent::class,
        UserComponent::class
    ]
)
class AppSubcomponents
