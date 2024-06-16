package com.begmyratmammedov.finalmanagement

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.begmyratmammedov.finalmanagement.databinding.FragmentQrCodeBinding
import com.journeyapps.barcodescanner.CaptureManager
import android.Manifest


class QrCodeFragment : Fragment() {

    private lateinit var binding: FragmentQrCodeBinding

    private var captureManager: CaptureManager? = null

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 201
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQrCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (hasCameraPermission()) {
            initializeCamera(savedInstanceState)
        } else {
            requestCameraPermission()
        }
    }

    private fun initializeCamera(savedInstanceState: Bundle?) {
        // Ensure captureManager is only initialized once with the proper context and saved state
        captureManager = CaptureManager(activity, binding.barcodeScanner).apply {
            initializeFromIntent(activity?.intent, savedInstanceState)
            binding.barcodeScanner.decodeContinuous { result ->
                activity?.runOnUiThread {
                    Toast.makeText(context, "Scan result: ${result.text}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        captureManager?.onResume()
    }

    override fun onPause() {
        super.onPause()
        captureManager?.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        captureManager?.onDestroy()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initializeCamera(null)
        } else {
            Toast.makeText(context, "Camera permission is required to use the barcode scanner", Toast.LENGTH_LONG).show()
        }
    }

    private fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
    }
}