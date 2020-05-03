package com.github.droibit.sample.pass_data

import android.app.Dialog
import android.content.DialogInterface
import android.content.DialogInterface.BUTTON_NEGATIVE
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult

class ConfirmationDialogFragment : DialogFragment(), DialogInterface.OnClickListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Confirmation")
            .setNegativeButton(android.R.string.cancel, this)
            .setPositiveButton(android.R.string.ok, this)
            .create()
    }

    override fun onClick(dialog: DialogInterface, which: Int) {
        setButtonResult(which)
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        setButtonResult(which = BUTTON_NEGATIVE)
    }

    private fun setButtonResult(which: Int) {
        val result = ConfirmationResult(button = which)
        setFragmentResult(REQUEST_KEY_CONFIRMATION, result.toBundle())
    }

    companion object {
        const val REQUEST_KEY_CONFIRMATION = "ConfirmationDialogFragment"
    }
}

data class ConfirmationResult(val button: Int) {

    constructor(data: Bundle) : this(data.getInt(KEY))

    fun toBundle() = bundleOf(KEY to button)

    companion object {
        const val KEY = "button"
    }
}