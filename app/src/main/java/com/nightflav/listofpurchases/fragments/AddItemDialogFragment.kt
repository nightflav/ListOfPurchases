package com.nightflav.listofpurchases.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.nightflav.listofpurchases.R
import com.nightflav.listofpurchases.databinding.FragmentAddItemDialogBinding

class AddItemDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setView(R.layout.fragment_add_item_dialog)
            .create()
    }
}