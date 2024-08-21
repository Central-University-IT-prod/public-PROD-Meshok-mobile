package com.trifcdr.feedbacker.presentation.fragments.scan

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnKeyListener
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.budiyev.android.codescanner.CodeScanner
import com.trifcdr.feedbacker.R
import com.trifcdr.feedbacker.databinding.FragmentScanBinding
import com.trifcdr.feedbacker.presentation.fragments.form.FormViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScanFragment : Fragment() {

    private lateinit var binding: FragmentScanBinding
    private val vm: FormViewModel by viewModels()

    private lateinit var codeScanner: CodeScanner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.scanQrBtn.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CAMERA),
                    123
                )
            } else {
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_scanFragment_to_QRFragment)
            }
        }
        binding.idEt.setOnTouchListener(object : OnTouchListener {
            @SuppressLint("ClickableViewAccessibility")
            override fun onTouch(p0: View?, event: MotionEvent): Boolean {
                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= (binding.idEt.getRight() - binding.idEt.getCompoundDrawables()[2].getBounds()
                            .width())
                        && binding.idEt.text.toString() != ""
                    ) {
                        val action = ScanFragmentDirections.actionScanFragmentToFormFargment(
                            binding.idEt.text.toString().toInt()
                        )
                        Navigation.findNavController(binding.root).navigate(action)
                        return true
                    }
                }
                return false
            }

        })

        binding.idEt.setOnKeyListener(object : OnKeyListener {
            override fun onKey(p0: View?, keyCode: Int, event: KeyEvent): Boolean {
                if ((event.action == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)
                    && binding.idEt.text.toString() != ""
                ) {
                    val action = ScanFragmentDirections.actionScanFragmentToFormFargment(
                        binding.idEt.text.toString().toInt()
                    )
                    Navigation.findNavController(binding.root).navigate(action)
                    return true
                }
                return false
            }

        })

    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Camera permission granted", Toast.LENGTH_LONG).show()
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_scanFragment_to_QRFragment)
            } else {
                Toast.makeText(context, "Camera permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }


}