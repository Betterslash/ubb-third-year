package ro.ubb.ideasmanager.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import ro.ubb.ideasmanager.databinding.FragmentTakePhotoBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class TakePhotoFragment : Fragment() {

    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_PICK_IMAGE = 2
    private var _binding : FragmentTakePhotoBinding? = null
    private val binding get() : FragmentTakePhotoBinding = _binding!!
    lateinit var currentPhotoPath: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentTakePhotoBinding.inflate(inflater, container, false)
        binding.btCapturePhoto.setOnClickListener { openCamera() }
        binding.btOpenGallery.setOnClickListener { openGallery() }
        return binding.root
    }


    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            intent.resolveActivity(requireContext().packageManager)?.also {
                val photoFile: File? = try {
                    createCapturedPhoto()
                } catch (ex: IOException) {
                    null
                }
                Log.d("MainActivity", "photofile $photoFile");
                photoFile?.also {
                    val photoURI = FileProvider.getUriForFile(
                        requireContext(),
                        "ro.ubb.ideasmanager.fileprovider",
                        it
                    )
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }


    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                val uri = Uri.parse(currentPhotoPath)
                binding.ivImage.setImageURI(uri)
            }
            else if (requestCode == REQUEST_PICK_IMAGE) {
                val uri = data?.data
                binding.ivImage.setImageURI(uri)
            }
        }
    }

    @Throws(IOException::class)
    private fun createCapturedPhoto(): File {
        var timestamp = ""
        var storageDir: File? = null
        context?.apply {
            timestamp = SimpleDateFormat("yyyyMMdd-HHmmss", Locale.US).format(Date())
            storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        }
        return File.createTempFile("PHOTO_${timestamp}",".jpg", storageDir).apply {
            currentPhotoPath = absolutePath
        }
    }
}