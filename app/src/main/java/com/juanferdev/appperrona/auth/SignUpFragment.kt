package com.juanferdev.appperrona.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.juanferdev.appperrona.R
import com.juanferdev.appperrona.databinding.FragmentSignUpBinding
import com.juanferdev.appperrona.utils.isValidEmail

class SignUpFragment : Fragment() {

    fun interface SignUpFragmentActions {
        fun onSignUpFieldsValidated(email: String, password: String, passwordConfirmation: String)

    }

    private lateinit var signUpFragmentActions: SignUpFragmentActions
    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater)
        setupSignUpButton()
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        signUpFragmentActions = try {
            context as SignUpFragmentActions
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement SignUpFragmentActions")
        }
    }

    private fun setupSignUpButton() {
        binding.signUpButton.setOnClickListener {
            validateFields()
        }
    }

    private fun validateFields() {
        binding.emailInput.error = String()
        binding.passwordInput.error = String()
        binding.confirmPasswordInput.error = String()

        val email = binding.emailEdit.text.toString()
        if (email.isValidEmail().not()) {
            binding.emailInput.error = getString(R.string.email_is_not_valid)
            return
        }
        val password = binding.passwordEdit.text.toString()
        if (password.isBlank()) {
            binding.passwordInput.error = getString(R.string.password_must_not_be_empty)
            return
        }

        val passwordConfirmation = binding.confirmPasswordEdit.text.toString()
        if (passwordConfirmation.isBlank()) {
            binding.confirmPasswordInput.error = getString(R.string.password_must_not_be_empty)
            return
        }

        if (password != passwordConfirmation) {
            binding.confirmPasswordInput.error = getString(R.string.password_do_not_match)
            return
        }
        signUpFragmentActions.onSignUpFieldsValidated(email, password, passwordConfirmation)
    }



}