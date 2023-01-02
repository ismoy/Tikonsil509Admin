package com.tikonsil.tikonsil509admin.domain.repository.costInnoverit

import com.tikonsil.tikonsil509admin.data.remote.api.RetrofitInstance
import com.tikonsil.tikonsil509admin.domain.model.CostInnoverit
import retrofit2.Response

class PriceCostInnoveritRepository {

    suspend fun registerPriceCost(innoverit: CostInnoverit):Response<CostInnoverit>{
        return RetrofitInstance.tikonsilApi.registerCostTotal(innoverit)
    }
}