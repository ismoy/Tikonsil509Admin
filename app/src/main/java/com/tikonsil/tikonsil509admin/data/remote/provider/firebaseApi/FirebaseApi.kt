package com.tikonsil.tikonsil509admin.data.remote.provider.firebaseApi

import com.tikonsil.tikonsil509admin.utils.FirebaseUtil


object FirebaseApi {
    var apis: Apis? = null

    suspend fun getFSApis() : Apis {
        if (apis == null || apis == Apis()){
            apis = FirebaseUtil().getApis()

            if (apis == null) {
                return Apis()
            }

            return apis as Apis
        }

        return apis as Apis
    }
}