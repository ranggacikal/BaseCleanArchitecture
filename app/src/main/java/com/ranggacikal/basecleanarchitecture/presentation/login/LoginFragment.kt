package com.ranggacikal.basecleanarchitecture.presentation.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.ranggacikal.basecleanarchitecture.R
import com.ranggacikal.ui.data.BaseDataDialogGeneral
import com.ranggacikal.basecleanarchitecture.databinding.FragmentLoginBinding
import com.ranggacikal.basecleanarchitecture.presentation.MainActivity
import com.ranggacikal.basecleanarchitecture.presentation.util.HandleComponents
import com.ranggacikal.ui.DialogProgressFragment
import com.ranggacikal.ui.dialog.DialogGeneralErrorFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    lateinit var viewModel: LoginViewModel
    private lateinit var handleComponents: HandleComponents
    private var dialog: DialogProgressFragment? = null
    private var dialogGeneralError: DialogGeneralErrorFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        handleComponents = HandleComponents()
        setupListObserver()
        setupUi()
    }

    private fun setupUi() = with(binding) {
        btnLogin.setOnClickListener {
//            showHideProgress(true)
            validateAuth()
        }
    }

    private fun validateAuth() = with(binding) {
        viewModel.email = edtEmailLogin.text.toString().trim()
        viewModel.password = edtPasswordLogin.text.toString().trim()
        when {
            viewModel.email.isNullOrEmpty() -> {
                edtEmailLogin.error = "email tidak boleh kosong"
            }
            viewModel.password.isNullOrEmpty() -> {
                edtPasswordLogin.error = "Password tidak boleh kosong"
            }
            else -> {
                viewModel.postLogin()
            }
        }
    }

    private fun setupListObserver() {
        viewModel.mState
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }
            .launchIn(lifecycleScope)
    }

    private fun handleStateChange(state: LoginState) {
        when (state) {
            is LoginState.Init -> Unit
            is LoginState.OnLoad -> {
                if (state.onLoad) {
                    showHideProgress(
                        true,
                        getString(R.string.title_progress_login),
                        getString(R.string.message_progress_login)
                    )
                }
            }
            is LoginState.OnLoadSuccess -> {
                if (state.status == 1) {
                    showHideProgress(
                        false,
                        getString(R.string.empty_string),
                        getString(R.string.empty_string)
                    )
                    navigateToHome()
                } else {
                    showHideProgress(
                        false,
                        getString(R.string.empty_string),
                        getString(R.string.empty_string)
                    )
                    showDialogErrorLogin(state.message)
                }
            }
            else -> Log.d("errorState", "handleStateChange: Error")
        }
    }

    private fun navigateToHome() {
        findNavController()
            .navigate(
                R.id.action_login_to_home,
                null,
                NavOptions.Builder()
                    .setPopUpTo(
                        R.id.loginFragment,
                        true
                    ).build()
            )
    }

    private fun showDialogErrorLogin(message: String) {
        val data = BaseDataDialogGeneral(
            title = message,
            message = "Periksa Kembali akun anda",
            icon = R.drawable.icon_error_login,
            textPrimaryButton = "Coba Lagi",
            dismissOnAction = true
        )
        dialogGeneralError = DialogGeneralErrorFragment(data)
        dialogGeneralError?.show(childFragmentManager, MainActivity::class.simpleName)
    }

    private fun showHideProgress(isLoading: Boolean, title: String, message: String) =
        if (isLoading) {
            dialog = DialogProgressFragment(title, message)
            dialog?.show(
                childFragmentManager,
                MainActivity::class.simpleName
            )
        } else {
            dialog?.dismiss()
            dialog = null
        }


}