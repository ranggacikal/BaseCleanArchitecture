package com.ranggacikal.basecleanarchitecture.presentation.detail_user

import android.os.Bundle
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
import androidx.navigation.fragment.navArgs
import com.ranggacikal.basecleanarchitecture.R
import com.ranggacikal.ui.data.BaseDataDialogGeneral
import com.ranggacikal.basecleanarchitecture.databinding.FragmentDetailUserBinding
import com.ranggacikal.basecleanarchitecture.domain.model.UserByIdModel
import com.ranggacikal.basecleanarchitecture.presentation.MainActivity
import com.ranggacikal.basecleanarchitecture.presentation.util.HandleComponents
import com.ranggacikal.ui.DialogProgressFragment
import com.ranggacikal.ui.dialog.DialogGeneralErrorFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DetailUserFragment : Fragment() {

    private lateinit var binding: FragmentDetailUserBinding
    lateinit var viewModel: DetailUserViewModel
    private lateinit var handleComponents: HandleComponents
    private val args: DetailUserFragmentArgs by navArgs()
    private var dialog: DialogProgressFragment? = null
    private var dialogGeneralError: DialogGeneralErrorFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[DetailUserViewModel::class.java]
        handleComponents = HandleComponents()
        viewModel.idUser = args.argsIdUser
        viewModel.getDetailUser()
        observeDetailUser()
        observeEditUser()
        setupUI()
    }

    private fun setupUI() = with(binding) {
        btnEditUser.setOnClickListener {
            viewModel.nama_lengkap = edtNamaUser.text.toString()
            viewModel.no_handphone = edtNoHandphoneUser.text.toString()
            viewModel.email = edtEmailUser.text.toString()
            viewModel.kode_team = edtKodeTeamUser.text.toString()
            viewModel.editUser()
        }

    }

    private fun observeEditUser() {
        viewModel.mStateEditUser
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { editUserState -> handleStateEditUserChange(editUserState) }
            .launchIn(lifecycleScope)
    }

    private fun handleStateEditUserChange(editUserState: EditUserState) {
        when (editUserState) {
            is EditUserState.Init -> Unit
            is EditUserState.OnLoad -> {
                if (editUserState.onLoad) showHideProgress(
                    true,
                    getString(R.string.title_progress_edit_user),
                    getString(R.string.message_progress_edit_user)
                )
            }
            is EditUserState.SuccessPost -> {
                if (editUserState.status == 1) {
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
                    showDialogErrorLogin(editUserState.message)
                }
            }
        }
    }

    private fun navigateToHome() {
        findNavController()
            .navigate(
                R.id.action_detail_to_home,
                null,
                NavOptions.Builder()
                    .setPopUpTo(
                        R.id.detailUserFragment,
                        true
                    ).build()
            )
    }

    private fun observeDetailUser() {
        viewModel.mState
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }
            .launchIn(lifecycleScope)
    }

    private fun handleStateChange(state: DetailUserState) {
        when (state) {
            is DetailUserState.Init -> Unit
            is DetailUserState.OnLoad -> {
                if (state.onLoad) showHideProgress(
                    true,
                    getString(R.string.title_progress_user_details),
                    getString(R.string.message_progress_user_details)
                )
            }
            is DetailUserState.SuccessLoad -> {
                showHideProgress(
                    false,
                    getString(R.string.empty_string),
                    getString(R.string.empty_string)
                )
                setupToDetail(state.listUser)
            }
        }
    }

    private fun setupToDetail(listUser: List<UserByIdModel>) {
        listUser.let {
            binding.edtNamaUser.setText(it[0].nama_lengkap)
            binding.edtEmailUser.setText(it[0].email)
            binding.edtNoHandphoneUser.setText(it[0].no_handphone)
            binding.edtKodeTeamUser.setText(it[0].kode_team)
            viewModel.password = it[0].password
        }
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

    private fun showDialogErrorLogin(message: String) {
        val data = BaseDataDialogGeneral(
            title = message,
            message = getString(R.string.title_dialog_error_edit_user),
            icon = R.drawable.icon_error_login,
            textPrimaryButton = getString(R.string.message_error_try_again),
            dismissOnAction = true
        )
        dialogGeneralError = DialogGeneralErrorFragment(data)
        dialogGeneralError?.show(childFragmentManager, MainActivity::class.simpleName)
    }
}