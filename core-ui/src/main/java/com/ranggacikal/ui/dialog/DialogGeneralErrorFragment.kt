package com.ranggacikal.ui.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.ranggacikal.myapplication.R
import com.ranggacikal.myapplication.databinding.FragmentDialogGeneralErrorBinding
import com.ranggacikal.ui.data.BaseDataDialogGeneral

class DialogGeneralErrorFragment(
    private val data: BaseDataDialogGeneral
) : DialogFragment() {

    lateinit var binding: FragmentDialogGeneralErrorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogGeneralErrorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupListener()
    }

    private fun setupListener() = with(binding) {
        btnDialogGeneralError.setOnClickListener {
            dismiss()
        }
    }

    private fun setupView() = with(binding) {
        tvTitleDialogGeneralError.text = data.title
        tvMessageDialogGeneralError.text = data.message
        btnDialogGeneralError.text = data.textPrimaryButton
        data.icon?.let { imgDialogGeneralError.setImageResource(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dismiss()
    }
}