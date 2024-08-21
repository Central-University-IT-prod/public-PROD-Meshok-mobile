package com.trifcdr.feedbacker.presentation.fragments.qr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.ScanMode
import com.trifcdr.feedbacker.R
import com.trifcdr.feedbacker.databinding.DialogScannerBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QRFragment : Fragment() {

    private lateinit var dialogScannerBinding: DialogScannerBinding
    private lateinit var codeScanner: CodeScanner


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialogScannerBinding = DialogScannerBinding.inflate(inflater, container, false)
        return dialogScannerBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startScanning()
        dialogScannerBinding.backBtn.setOnClickListener {
            codeScanner.stopPreview()
            codeScanner.releaseResources()
            Navigation.findNavController(dialogScannerBinding.root)
                .navigate(R.id.action_QRFragment_to_scanFragment)
        }
    }


    private fun startScanning() {

        codeScanner = CodeScanner(requireActivity(), dialogScannerBinding.scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.startPreview()
        codeScanner.setDecodeCallback {
            CoroutineScope(Dispatchers.Main).launch {
                codeScanner.stopPreview()
                codeScanner.releaseResources()
                try {
                    val action = QRFragmentDirections.actionQRFragmentToFormFargment(
                        it.text.split("/").last().toInt()
                    )
                    Navigation.findNavController(dialogScannerBinding.root).navigate(action)
                } catch (e: Exception) {
                    codeScanner.startPreview()

                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        if (::codeScanner.isInitialized) {
            codeScanner.startPreview()
        }
    }

    override fun onPause() {
        if (::codeScanner.isInitialized) {
            codeScanner.releaseResources()
        }
        super.onPause()
    }

    override fun onDestroyView() {
        codeScanner.stopPreview()
        codeScanner.releaseResources()
        super.onDestroyView()
    }


}