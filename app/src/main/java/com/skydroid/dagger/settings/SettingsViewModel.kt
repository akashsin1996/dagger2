
package com.skydroid.dagger.settings

import com.skydroid.dagger.user.UserDataRepository
import com.skydroid.dagger.user.UserManager
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val userManager: UserManager
) {

    fun refreshNotifications() {
        userDataRepository.refreshUnreadNotifications()
    }

    fun logout() {
        userManager.logout()
    }
}
