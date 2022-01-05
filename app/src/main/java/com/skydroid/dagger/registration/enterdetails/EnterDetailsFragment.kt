
package com.skydroid.dagger.registration.enterdetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.skydroid.dagger.R
import com.skydroid.dagger.databinding.FragmentEnterDetailsBinding
import com.skydroid.dagger.registration.RegistrationActivity
import com.skydroid.dagger.registration.RegistrationViewModel
import javax.inject.Inject

class EnterDetailsFragment : Fragment() {


    @Inject
    lateinit var registrationViewModel: RegistrationViewModel

    @Inject
    lateinit var enterDetailsViewModel: EnterDetailsViewModel

//    private lateinit var errorTextView: TextView
//    private lateinit var usernameEditText: EditText
//    private lateinit var passwordEditText: EditText
    private var binding: FragmentEnterDetailsBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Grabs the registrationComponent from the Activity and injects this Fragment
        (activity as RegistrationActivity).registrationComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //val view = inflater.inflate(R.layout.fragment_enter_details, container, false)
        binding = FragmentEnterDetailsBinding.inflate(inflater, container, false)
        enterDetailsViewModel.enterDetailsState.observe(
            viewLifecycleOwner,
            { state ->
                when (state) {
                    is EnterDetailsSuccess -> {

                        val username = binding!!.username.text.toString()
                        val password = binding!!.password.text.toString()
                        registrationViewModel.updateUserData(username, password)

                        (activity as RegistrationActivity).onDetailsEntered()
                    }
                    is EnterDetailsError -> {
                        binding!!.error.text = state.error
                        binding!!.error.visibility = View.VISIBLE
                    }
                }
            }
        )

        setupViews(binding!!.root)
        return binding!!.root
    }

    private fun setupViews(view: View) {
      /*  errorTextView = view.findViewById(R.id.error)

        usernameEditText = view.findViewById(R.id.username)*/
        binding!!.username.doOnTextChanged { _, _, _, _ -> binding!!.error.visibility = View.INVISIBLE }

//        binding!!.password = view.findViewById(R.id.password)
        binding!!.password.doOnTextChanged { _, _, _, _ -> binding!!.error.visibility = View.INVISIBLE }

        view.findViewById<Button>(R.id.next).setOnClickListener {
            val username =  binding!!.username.text.toString()
            val password = binding!!.password.text.toString()
            enterDetailsViewModel.validateInput(username, password)
        }
    }
}

sealed class EnterDetailsViewState
object EnterDetailsSuccess : EnterDetailsViewState()
data class EnterDetailsError(val error: String) : EnterDetailsViewState()
