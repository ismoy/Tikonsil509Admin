package com.tikonsil.tikonsil509admin.ui.fragment.profile

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.data.remote.provider.AuthProvider
import com.tikonsil.tikonsil509admin.data.remote.provider.ImagesProvider
import com.tikonsil.tikonsil509admin.data.remote.provider.UpdateUserProvider
import com.tikonsil.tikonsil509admin.databinding.FragmentProfileBinding
import com.tikonsil.tikonsil509admin.domain.model.Users
import com.tikonsil.tikonsil509admin.presentation.home.UserViewModel
import com.tikonsil.tikonsil509admin.ui.activity.login.LoginActivity
import com.tikonsil.tikonsil509admin.ui.fragment.base.BaseFragment
import com.tikonsil.tikonsil509admin.utils.FileUtil
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File
import javax.inject.Inject


@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, UserViewModel>() {

    private val viewModel:UserViewModel by viewModels()

    private lateinit var imageView: CircleImageView
    private lateinit var imagesProvider: ImagesProvider
    private lateinit var mprogressBarProfile:ProgressBar
    private var mimagefile: File? = null
    val pickMedia = registerForActivityResult(PickVisualMedia()){uri->
        if (uri!=null){
            mimagefile = uri.let { FileUtil.from(requireContext() , it) }
            imageView.setImageURI(uri)
            updateProfile()
        }
    }
    @Inject
    lateinit var mAuthProvider: AuthProvider
    lateinit var updateUserProvider: UpdateUserProvider
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomNavigationView =
            activity!!.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.visibility = View.GONE
        mAuthProvider = AuthProvider()
        imagesProvider = ImagesProvider("image/*")
        updateUserProvider = UpdateUserProvider()
        mprogressBarProfile = binding.root.findViewById(R.id.progressBarProfile)
        viewModelObserver()
      val btnLogout = binding.root.findViewById<LinearLayout>(R.id.logout)
         imageView = binding.root.findViewById(R.id.imageViewProfile)
        btnLogout.setOnClickListener(logout())
        imageView.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }
    }



    private fun updateProfile(){
        mprogressBarProfile.isGone = false
        imagesProvider.saveImage(requireContext() , mimagefile!! , mAuthProvider.getId().toString())
            .addOnCompleteListener {
                if (it.isSuccessful){
                    imagesProvider.storage.downloadUrl.addOnSuccessListener { uri->
                        val imageUri:String = uri.toString()
                        val users = Users()
                        users.image = imageUri
                        users.id =mAuthProvider.getId().toString()
                        updateUserProvider.updateImage(users)?.addOnSuccessListener {
                            Toast.makeText(
                                requireContext() ,
                                "Su informacion se actualizo correctamente" ,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        mprogressBarProfile.isGone = true
                    }
                }else{
                    Toast.makeText(
                        requireContext() ,
                        "Hubo un error al subir la imagen" ,
                        Toast.LENGTH_SHORT
                    ).show()
                    mprogressBarProfile.isGone =true
                }
            }
    }

    private fun logout(): View.OnClickListener {
        return View.OnClickListener {
            mAuthProvider.logout()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }

    }

    private fun viewModelObserver() {
      viewModel.responseUser.observe(viewLifecycleOwner){
          if (it.isSuccessful){
              with(binding.root){
                  val role_item =   findViewById<TextView>(R.id.role_item)
                  val firstname_item =findViewById<TextView>(R.id.firstname_item)
                  val lastname_item = findViewById<TextView>(R.id.lastname_item)
                  val number_item = findViewById<TextView>(R.id.number_item)
                  val email_item = findViewById<TextView>(R.id.email_item)
                  val imageView_item =findViewById<ImageView>(R.id.imageViewProfile)
                  it.body()?.apply {
                      if (role==3){
                          role_item?.text =getString(R.string.admin)
                      }
                      firstname_item?.text = firstname
                      lastname_item?.text = lastname
                      number_item?.text =phone
                      email_item?.text =email
                      if (image!=null){
                          Glide.with(requireActivity()).load(image).into(imageView_item)
                      }
                  }
              }
          }else{
              Toast.makeText(requireContext(), it.code(), Toast.LENGTH_SHORT).show()
          }
      }
    }


    override fun onResume() {
        super.onResume()
        viewModel.getOnlyUser(mAuthProvider.getId().toString())

    }
    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentProfileBinding.inflate(inflater,container,false)
    override fun getViewModel() = UserViewModel::class.java


}