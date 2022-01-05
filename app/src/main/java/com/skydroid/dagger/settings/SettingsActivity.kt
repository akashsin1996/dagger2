
package com.skydroid.dagger.settings

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.skydroid.dagger.MyApplication
import com.skydroid.dagger.R
import com.skydroid.dagger.databinding.ActivityRegistrationBinding
import com.skydroid.dagger.databinding.ActivitySettingsBinding
import com.skydroid.dagger.login.LoginActivity
import javax.inject.Inject

class SettingsActivity : AppCompatActivity() {

    // @Inject annotated fields will be provided by Dagger
    @Inject
    lateinit var settingsViewModel: SettingsViewModel
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        // Gets the userManager from the application graph to obtain the UserComponent
        // and gets this Activity injected
        val userManager = (application as MyApplication).appComponent.userManager()
        userManager.userComponent!!.inject(this)

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_settings)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)
        setupViews()
    }

    private fun setupViews() {
       binding.refresh.setOnClickListener {
            settingsViewModel.refreshNotifications()
        }
        binding.logout.setOnClickListener {
            settingsViewModel.logout()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}
