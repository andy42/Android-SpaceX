package com.jaehl.spacex.ui.extensions

import android.app.AlertDialog
import android.content.Context
import com.jaehl.spacex.R

fun Context.showDialog(title : String? = null,
                       message : String? = null,
                       positive: String? = null,
                       negative : String? = null,
                       positiveClick: () -> Unit = {},
                       negativeClick: () -> Unit = {},
                       cancelable: Boolean = true){
    AlertDialog.Builder(this).apply {
        setCancelable(cancelable)
        if(title != null) setTitle(title)
        if(message != null) setMessage(message)
        if(positive != null) setPositiveButton(positive) { dialog, _ ->
            dialog.dismiss()
            positiveClick()
        }
        if(negative != null) setNegativeButton(negative) { dialog, _ ->
            dialog.dismiss()
            negativeClick()
        }
        show()
    }
}

fun Context.showErrorDialog(message : String,
                            title : String? = getString(R.string.error_dialog_title),
                            positive: String? = getString(R.string.ok_btn),
                            positiveClick: () -> Unit ={}) = showDialog(
    title = title,
    message = message,
    positive = positive,
    positiveClick = positiveClick)


fun Context.showErrorDialog(message : Int, positiveClick: () -> Unit ={}) = showErrorDialog(message = getString(message), positiveClick = positiveClick)