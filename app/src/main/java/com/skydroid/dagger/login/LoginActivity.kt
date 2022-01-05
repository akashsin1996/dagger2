
package com.skydroid.dagger.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.skydroid.dagger.MyApplication
import com.skydroid.dagger.R
import com.skydroid.dagger.databinding.ActivityLoginBinding
import com.skydroid.dagger.main.MainActivity
import com.skydroid.dagger.registration.RegistrationActivity
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    // @Inject annotated fields will be provided by Dagger
    @Inject
    lateinit var loginViewModel: LoginViewModel

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        // Creates an instance of Login component by grabbing the factory from the app graph
        // and injects this activity to that Component
        (application as MyApplication).appComponent.loginComponent().create().inject(this)

        super.onCreate(savedInstanceState)
     //   setContentView(R.layout.activity_login)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        loginViewModel.loginState.observe(this, Observer<LoginViewState> { state ->
            when (state) {
                is LoginSuccess -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                is LoginError -> binding.error.visibility = View.VISIBLE
            }
        })

        setupViews()
    }

    private fun setupViews() {

        binding.username.isEnabled = false
        binding.username.setText(loginViewModel.getUsername())


        binding.password.doOnTextChanged { _, _, _, _ -> binding.error.visibility = View.INVISIBLE }

        binding.login.setOnClickListener {
            loginViewModel.login(binding.username.text.toString(), binding.password.text.toString())
        }
        binding.unregister.setOnClickListener {
            loginViewModel.unregister()
            val intent = Intent(this, RegistrationActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}

sealed class LoginViewState
object LoginSuccess : LoginViewState()
object LoginError : LoginViewState()
