package com.vrp.kotlinmasterapp.utils

import android.content.Context
import android.graphics.Color
import cn.pedant.SweetAlert.SweetAlertDialog

class SweetAlertDialog {
    var pDialog: SweetAlertDialog? = null

    fun showProgressDialog(context: Context?) {
        pDialog = SweetAlertDialog(
            context,
            SweetAlertDialog.PROGRESS_TYPE
        )
        pDialog!!.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog!!.titleText = "Please wait..."
        pDialog!!.setCancelable(false)
        pDialog!!.show()
    }

    fun dismissProgressDialog() {
        pDialog!!.dismiss()
    }

    fun showSuccessDialog(context: Context?) {

    }

    fun showErrorDialog(context: Context?) {

    }

    fun showWarningDialog(context: Context?) {

    }
}