package com.ranggacikal.basecleanarchitecture.presentation.util

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast

class HandleComponents {

    fun handleLoading(loading: Boolean, progressLoading: ProgressBar) {
        if (loading) {
            progressLoading.visibility = View.VISIBLE
        } else {
            progressLoading.visibility = View.GONE
        }
    }

    fun showToast(message: String, context: Context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}