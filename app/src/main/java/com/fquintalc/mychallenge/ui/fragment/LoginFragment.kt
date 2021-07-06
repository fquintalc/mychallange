package com.fquintalc.mychallenge.ui.fragment

import android.content.DialogInterface
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.fquintalc.mychallenge.R
import com.fquintalc.mychallenge.databinding.FragmentLoginBinding
import me.aflak.libraries.callback.FingerprintCallback
import me.aflak.libraries.callback.FingerprintDialogCallback
import me.aflak.libraries.dialog.FingerprintDialog
import java.util.concurrent.Executor

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = DataBindingUtil.bind<FragmentLoginBinding>(view)
        binding!!.fingerButton.setOnClickListener{
            openBiometricDialog()
        }
        executor = ContextCompat.getMainExecutor(requireContext())
    }

    private fun openBiometricDialog(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            var dialog = FingerprintDialog.initialize(requireContext())
            dialog.title(R.string.label_identify)
            dialog.message(R.string.label_use_your_fingerprint)

            dialog.callback(object : FingerprintDialogCallback{
                override fun onAuthenticationSucceeded() {

                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                        .navigate(R.id.action_loginFragment_to_graphFragment)
                }

                override fun onAuthenticationCancel() {
                }


            })
            dialog.show()


        }
    }
}