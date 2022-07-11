package com.ranggacikal.basecleanarchitecture.presentation.data_user

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ranggacikal.basecleanarchitecture.R
import com.ranggacikal.basecleanarchitecture.databinding.ActivityMainBinding
import com.ranggacikal.basecleanarchitecture.databinding.FragmentHomeBinding
import com.ranggacikal.basecleanarchitecture.databinding.FragmentLoginBinding
import com.ranggacikal.basecleanarchitecture.domain.model.UserModel
import com.ranggacikal.basecleanarchitecture.presentation.MainActivity
import com.ranggacikal.basecleanarchitecture.presentation.data_user.adapter.UserAdapter
import com.ranggacikal.basecleanarchitecture.presentation.login.LoginViewModel
import com.ranggacikal.basecleanarchitecture.presentation.util.HandleComponents
import com.ranggacikal.ui.DialogProgressFragment
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: UserViewModel
    private lateinit var userAdapter: UserAdapter
    private var dialog: DialogProgressFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        viewModel.getUsers()
        setupListObserver()
    }

    private fun setupListObserver() {
        viewModel.mState
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }
            .launchIn(lifecycleScope)
    }

    private fun handleStateChange(state: UserState) {
        when (state) {
            is UserState.Init -> Unit
            is UserState.OnLoad -> {
                if (state.onLoad) showHideProgress(
                    true,
                    getString(R.string.title_progress_home),
                    getString(R.string.message_progress_home)
                )
            }
            is UserState.SuccessLoad -> {
                showHideProgress(
                    false,
                    getString(R.string.empty_string),
                    getString(R.string.empty_string)
                )
                setupAdapter(state.listUser)
            }
        }
    }

    private fun setupAdapter(listUser: List<UserModel>) {
        userAdapter = UserAdapter(listUser, requireContext())
        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userAdapter
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
}