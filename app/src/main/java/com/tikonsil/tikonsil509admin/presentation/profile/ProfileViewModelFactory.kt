package com.tikonsil.tikonsil509.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tikonsil.tikonsil509admin.domain.repository.profile.ProfileRepository
import com.tikonsil.tikonsil509admin.presentation.profile.ProfileViewModel

/** * Created by ISMOY BELIZAIRE on 27/04/2022. */
class ProfileViewModelFactory(private val repository: ProfileRepository):ViewModelProvider.Factory {
 override fun <T : ViewModel> create(modelClass: Class<T>): T {
  return ProfileViewModel(repository) as T
 }

}