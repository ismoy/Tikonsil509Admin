package com.tikonsil.tikonsil509admin.ui.fragment.profile

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.tikonsil.tikonsil509.presentation.profile.ProfileViewModelFactory
import com.tikonsil.tikonsil509admin.R
import com.tikonsil.tikonsil509admin.data.remote.provider.AuthProvider
import com.tikonsil.tikonsil509admin.data.remote.provider.ImagesProvider
import com.tikonsil.tikonsil509admin.data.remote.provider.UpdateUserProvider
import com.tikonsil.tikonsil509admin.domain.model.Users
import com.tikonsil.tikonsil509admin.domain.repository.profile.ProfileRepository
import com.tikonsil.tikonsil509admin.presentation.profile.ProfileViewModel
import com.tikonsil.tikonsil509admin.ui.activity.login.LoginActivity
import com.tikonsil.tikonsil509admin.utils.FileUtil
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File

/** * Created by ISMOY BELIZAIRE on 27/04/2022. */
@Suppress("DEPRECATION")
abstract class ProfileInfo<VB:ViewBinding,VM:ViewModel>:Fragment() {
    protected lateinit var binding: VB
    protected lateinit var viewmodel: ProfileViewModel
    protected lateinit var mAuthProvider: AuthProvider
    protected lateinit var navController: NavController
    protected var logout:LinearLayout?=null
    protected lateinit var imageView: CircleImageView
    private lateinit var imagesProvider: ImagesProvider
    private var mimagefile: File? = null
    private val GALLERY_REQUEST = 1
    private lateinit var updateUserProvider: UpdateUserProvider
    lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getFragmentBinding(inflater, container)
        val repository = ProfileRepository()
        val factory = ProfileViewModelFactory(repository)
        viewmodel = ViewModelProvider(requireActivity(), factory)[ProfileViewModel::class.java]
        mAuthProvider = AuthProvider()
        logout =binding.root.findViewById(R.id.logout)
        imagesProvider = ImagesProvider("admin_images")
        imageView = binding.root.findViewById(R.id.imageViewProfile)
        updateUserProvider = UpdateUserProvider()
        dialog = Dialog(requireContext())
        return binding.root
    }


    fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent , GALLERY_REQUEST)
    }

    override fun onActivityResult(requestCode: Int , resultCode: Int , data: Intent?) {
        super.onActivityResult(requestCode , resultCode , data)
        if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK) {
            try {
                mimagefile = data?.data?.let { FileUtil.from(requireContext() , it) }
                imageView.setImageBitmap(BitmapFactory.decodeFile(mimagefile?.absolutePath))
                updateProfile()
            } catch (e: Exception) {
            }
        }
    }
    private fun updateProfile(){
        dialog.setContentView(R.layout.dialog_loading)
        dialog.setCancelable(false)
        if (dialog.window!=null){
            dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        dialog.show()
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
                            dialog.dismiss()
                        }
                    }
                }else{
                    Toast.makeText(
                        requireContext() ,
                        "Hubo un error al subir la imagen" ,
                        Toast.LENGTH_SHORT
                    ).show()
                    dialog.dismiss()
                }
            }
    }


    fun showDataInProfile(){
        viewmodel.getOnlyUser(mAuthProvider.getId().toString())
        viewmodel.ResposeUsers.observe(viewLifecycleOwner, Observer { response->
            if (response.isSuccessful){
                val role_item =   view?.findViewById<TextView>(R.id.role_item)
                val firstname_item =view?.findViewById<TextView>(R.id.firstname_item)
                val lastname_item = view?.findViewById<TextView>(R.id.lastname_item)
                val number_item = view?.findViewById<TextView>(R.id.number_item)
                val email_item = view?.findViewById<TextView>(R.id.email_item)
                val imageView_item =view!!.findViewById<ImageView>(R.id.imageViewProfile)
                response.body()?.apply {
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

            }else{
                Toast.makeText(requireContext(), response.code(), Toast.LENGTH_SHORT).show()
            }
        })
    }
    fun Logout(){
        logout?.setOnClickListener {
            mAuthProvider.logout()
           startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
    }
    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    abstract fun getViewModel():Class<VM>
}