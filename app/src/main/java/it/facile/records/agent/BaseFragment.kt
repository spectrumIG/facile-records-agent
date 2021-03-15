package it.facile.records.agent

import android.Manifest
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.eazypermissions.common.model.PermissionResult
import com.eazypermissions.dsl.extension.requestPermissions

/**
 * Actually this is not needed because the Internet permission is not dangerous but I keep this class as a blueprint for my project
 * */
open class BaseFragment(layout: Int) : Fragment(layout) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions(
            Manifest.permission.INTERNET
        ) {
            requestCode = 10
            resultCallback = {
                when (this) {
                    is PermissionResult.PermissionGranted -> {
                    }
                    is PermissionResult.PermissionDenied -> {
                        AlertDialog.Builder(requireContext()).setMessage("Spiacenti, l'app necessita dei permessi per poter essere eseguita.")
                            .setPositiveButton(
                                R.string
                                    .ok
                            ) { dialog, _ ->
                                dialog.dismiss()
                                requireActivity().finish()
                            }
                    }
                    is PermissionResult.PermissionDeniedPermanently -> {
                    }
                    is PermissionResult.ShowRational -> {
                    }
                }
            }
        }
    }
}
