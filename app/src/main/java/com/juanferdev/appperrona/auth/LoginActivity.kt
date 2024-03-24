package com.juanferdev.appperrona.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.juanferdev.appperrona.R
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.databinding.ActivityLoginBinding
import com.juanferdev.appperrona.main.MainActivity
import com.juanferdev.appperrona.models.User

class LoginActivity : AppCompatActivity(), LoginFragment.LoginFragmentActions,
    SignUpFragment.SignUpFragmentActions {

    private val viewModel: AuthViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeStatus()
    }

    private fun observeStatus() {
        viewModel.status.observe(this) { status ->
            when (status) {
                is ApiResponseStatus.Loading -> binding.loadingWheel.visibility = View.VISIBLE
                is ApiResponseStatus.Error -> {
                    binding.loadingWheel.visibility = View.GONE
                    showAlertDialog(status.messageId)
                }

                is ApiResponseStatus.Success -> {
                    binding.loadingWheel.visibility = View.GONE
                    User.setLoggedInUser(this, status.data)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }

        }
    }

    private fun showAlertDialog(messageId: Int) {
        AlertDialog.Builder(this).setTitle(R.string.unknown_error).setMessage(messageId)
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }.create().show()
    }

    override fun onRegisterButtonClick() {

    }

    override fun onLoginButtonClick(email: String, password: String) {
        viewModel.login(email, password)
    }

    override fun onSignUpFieldsValidated(
        email: String, password: String, passwordConfirmation: String
    ) {
        viewModel.signUp(email, password, passwordConfirmation)
    }
}