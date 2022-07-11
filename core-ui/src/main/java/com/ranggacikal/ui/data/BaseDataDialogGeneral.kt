package com.ranggacikal.ui.data

import androidx.annotation.DrawableRes

data class BaseDataDialogGeneral(
    val title: String?="",
    val message: String?="",
    @DrawableRes val icon : Int? =null,
    val textPrimaryButton: String?,
    val dismissOnAction: Boolean = false
)
