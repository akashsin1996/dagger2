
package com.skydroid.dagger.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.skydroid.dagger.MyApplication
import com.skydroid.dagger.R
import com.skydroid.dagger.databinding.ActivityLoginBinding
import com.skydroid.dagger.databinding.ActivityMainBinding
import com.skydroid.dagger.login.LoginActivity
import com.skydroid.dagger.registration.RegistrationActivity
import com.skydroid.dagger.settings.SettingsActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    // @Inject annotated fields will be provided by Dagger
    @Inject
    lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Grabs instance of UserManager from the application graph
        val userManager = (application as MyApplication).appComponent.userManager()
        if (!userManager.isUserLoggedIn()) {
            if (!userManager.isUserRegistered()) {
                startActivity(Intent(this, RegistrationActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        } else {
//            setContentView(R.layout.activity_main)

            binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

            // If the MainActivity needs to be displayed, we get the UserComponent from the
            // application graph and gets this Activity injected
            userManager.userComponent!!.inject(this)
            setupViews()
        }
    }

    /**
     * Updating unread notifications onResume because they can get updated on SettingsActivity
     */
    override fun onResume() {
        super.onResume()
        binding.notifications.text = mainViewModel.notificationsText
    }

    private fun setupViews() {
       binding.hello.text = mainViewModel.welcomeText
       binding.settings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}
