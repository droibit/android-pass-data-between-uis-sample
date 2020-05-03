package com.github.droibit.sample.pass_data

import android.Manifest
import android.content.DialogInterface.BUTTON_NEGATIVE
import android.content.DialogInterface.BUTTON_POSITIVE
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.invoke
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.github.droibit.sample.pass_data.ConfirmationDialogFragment.Companion.REQUEST_KEY_CONFIRMATION
import com.github.droibit.sample.pass_data.DetailFragment.Companion.REQUEST_KEY_DETAIL
import com.github.droibit.sample.pass_data.MainFragmentDirections.Companion.toConfirmation
import com.github.droibit.sample.pass_data.MainFragmentDirections.Companion.toDetail
import com.github.droibit.sample.pass_data.OtherActivity.StartOtherForResult
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private val startOtherForResult = registerForActivityResult(StartOtherForResult()) { result ->
        val message = result?.message ?: "Canceled"
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private val requestPermission = registerForActivityResult(RequestPermission()) { granted ->
        val message = if (granted) {
            "Camera permission granted"
        } else {
            "Camera permission denied"
        }
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(REQUEST_KEY_DETAIL) { _, data ->
            val result = DetailResult(data)
            Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
        }

        setFragmentResultListener(REQUEST_KEY_CONFIRMATION) { _, data ->
            val result = ConfirmationResult(data)
            val selectedButton = when (result.button) {
                BUTTON_POSITIVE -> getString(android.R.string.ok)
                BUTTON_NEGATIVE -> getString(android.R.string.cancel)
                else -> "Unknown(${result.button})"
            }
            Toast.makeText(requireContext(), "Selected: $selectedButton", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showDetailButton.setOnClickListener {
            findNavController().navigate(toDetail())
        }

        showDialogButton.setOnClickListener {
            findNavController().navigate(toConfirmation())
        }

        showOtherButton.setOnClickListener {
            startOtherForResult()
        }

        requestPermissionButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) != PERMISSION_GRANTED
            ) {
                requestPermission(Manifest.permission.CAMERA)
            } else {
                Toast.makeText(requireContext(), "Camera permission granted", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}