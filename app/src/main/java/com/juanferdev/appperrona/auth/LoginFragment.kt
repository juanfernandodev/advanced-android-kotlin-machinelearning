package com.juanferdev.appperrona.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.juanferdev.appperrona.R
import com.juanferdev.appperrona.databinding.FragmentLoginBinding
import com.juanferdev.appperrona.utils.isValidEmail


class LoginFragment : Fragment() {

    interface LoginFragmentActions {
        fun onRegisterButtonClick()

        fun onLoginButtonClick(email: String, password: String)
    }

    private lateinit var loginFragmentActions: LoginFragmentActions
    private lateinit var binding: FragmentLoginBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginFragmentActions = try {
            context as LoginFragmentActions
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement LoginFragmentActions")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        initClickListeners()
        return binding.root
    }

    private fun initClickListeners() {
        binding.loginRegisterButton.setOnClickListener {
            loginFragmentActions.onRegisterButtonClick()
        }
        binding.loginButton.setOnClickListener {
            login()
        }
    }

    private fun login() {
        binding.emailInput.error = String()
        binding.passwordInput.error = String()

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
        loginFragmentActions.onLoginButtonClick(email, password)
    }


}