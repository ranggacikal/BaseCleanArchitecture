package com.ranggacikal.ui

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.DialogFragment
import com.ranggacikal.myapplication.R
import com.ranggacikal.myapplication.databinding.FragmentDialogProgressBinding


class DialogProgressFragment(private val title: String, private val message: String) :
    DialogFragment() {

    lateinit var binding: FragmentDialogProgressBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val lp = dialog.window?.attributes
        lp?.gravity = Gravity.CENTER

        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.attributes = lp
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)

        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() = with(binding) {
        tvTitleDialogProgress.text = title
        tvMessageProgress.text = message
    }
}