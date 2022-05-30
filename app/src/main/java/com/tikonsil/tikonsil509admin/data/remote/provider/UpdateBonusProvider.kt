package com.tikonsil.tikonsil509admin.data.remote.provider

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/** * Created by ISMOY BELIZAIRE on 22/05/2022. */
class UpdateBonusProvider {
    var mDatabase: DatabaseReference?= FirebaseDatabase.getInstance().reference.child("BonusUser")

    fun updateBonusTopUpHaiti(bonustopuphaiti:Float):Task<Void?>?{
        val map:MutableMap<String?,Any> = HashMap()
        map["bonustopuphaiti"] =bonustopuphaiti
            return mDatabase?.updateChildren(map)
    }
    fun updateBonusMoncashHaiti(bonusmoncashhaiti:Float):Task<Void?>?{
        val map:MutableMap<String?,Any> = HashMap()
        map["bonusmoncashhaiti"] =bonusmoncashhaiti
        return mDatabase?.updateChildren(map)
    }
    fun updateBonusNatCashHaiti(bonusnatcashhaiti:Float):Task<Void?>?{
        val map:MutableMap<String?,Any> = HashMap()
        map["bonusnatcashhaiti"] =bonusnatcashhaiti
        return mDatabase?.updateChildren(map)
    }
    fun updateBonusLapoulaHaiti(bonuslapoulahaiti:Float):Task<Void?>?{
        val map:MutableMap<String?,Any> = HashMap()
        map["bonuslapoulahaiti"] =bonuslapoulahaiti
        return mDatabase?.updateChildren(map)
    }


    fun updateBonusTopUpChile(bonustopupchile:Float):Task<Void?>?{
        val map:MutableMap<String?,Any> = HashMap()
        map["bonustopupchile"] =bonustopupchile
        return mDatabase?.updateChildren(map)
    }
    fun updateBonusMoncashChile(bonusmoncashchile:Float):Task<Void?>?{
        val map:MutableMap<String?,Any> = HashMap()
        map["bonusmoncashchile"] =bonusmoncashchile
        return mDatabase?.updateChildren(map)
    }
    fun updateBonusNatCashChile(bonusnatcashchile:Float):Task<Void?>?{
        val map:MutableMap<String?,Any> = HashMap()
        map["bonusnatcashchile"] =bonusnatcashchile
        return mDatabase?.updateChildren(map)
    }
    fun updateBonusLapoulaChile(bonuslapoulachile:Float):Task<Void?>?{
        val map:MutableMap<String?,Any> = HashMap()
        map["bonuslapoulachile"] =bonuslapoulachile
        return mDatabase?.updateChildren(map)
    }
    fun updateBonusTopUpPanama(bonustopuppanama:Float):Task<Void?>?{
        val map:MutableMap<String?,Any> = HashMap()
        map["bonustopuppanama"] =bonustopuppanama
        return mDatabase?.updateChildren(map)
    }
    fun updateBonusMoncashPanama(bonusmoncashpanama:Float):Task<Void?>?{
        val map:MutableMap<String?,Any> = HashMap()
        map["bonusmoncashpanama"] =bonusmoncashpanama
        return mDatabase?.updateChildren(map)
    }
    fun updateBonusNatCashPanama(bonusnatcashpanama:Float):Task<Void?>?{
        val map:MutableMap<String?,Any> = HashMap()
        map["bonusnatcashpanama"] =bonusnatcashpanama
        return mDatabase?.updateChildren(map)
    }
    fun updateBonusLapoulaPanama(bonuslapoulapanama:Float):Task<Void?>?{
        val map:MutableMap<String?,Any> = HashMap()
        map["bonuslapoulapanama"] =bonuslapoulapanama
        return mDatabase?.updateChildren(map)
    }
    fun updateBonusTopUpCuba(bonustopupcuba:Float):Task<Void?>?{
        val map:MutableMap<String?,Any> = HashMap()
        map["bonustopupcuba"] =bonustopupcuba
        return mDatabase?.updateChildren(map)
    }
    fun updateBonusMoncashCuba(bonusmoncashcuba:Float):Task<Void?>?{
        val map:MutableMap<String?,Any> = HashMap()
        map["bonusmoncashcuba"] =bonusmoncashcuba
        return mDatabase?.updateChildren(map)
    }
    fun updateBonusNatCashCuba(bonusnatcashcuba:Float):Task<Void?>?{
        val map:MutableMap<String?,Any> = HashMap()
        map["bonusnatcashcuba"] =bonusnatcashcuba
        return mDatabase?.updateChildren(map)
    }
    fun updateBonusLapoulaCuba(bonuslapoulacuba:Float):Task<Void?>?{
        val map:MutableMap<String?,Any> = HashMap()
        map["bonuslapoulacuba"] =bonuslapoulacuba
        return mDatabase?.updateChildren(map)
    }
    fun updateBonusTopUpRd(bonustopuprd:Float):Task<Void?>?{
        val map:MutableMap<String?,Any> = HashMap()
        map["bonustopuprd"] =bonustopuprd
        return mDatabase?.updateChildren(map)
    }
    fun updateBonusMoncashRd(bonosmoncashrd:Float):Task<Void?>?{
        val map:MutableMap<String?,Any> = HashMap()
        map["bonosmoncashrd"] =bonosmoncashrd
        return mDatabase?.updateChildren(map)
    }
    fun updateBonusNatCashRd(bonusnatcashrd:Float):Task<Void?>?{
        val map:MutableMap<String?,Any> = HashMap()
        map["bonusnatcashrd"] =bonusnatcashrd
        return mDatabase?.updateChildren(map)
    }
    fun updateBonusLapoulaRd(bonuslapoulard:Float):Task<Void?>?{
        val map:MutableMap<String?,Any> = HashMap()
        map["bonuslapoulard"] =bonuslapoulard
        return mDatabase?.updateChildren(map)
    }
    fun updateBonusTopUpBrazil(bonustopupbrazil:Float):Task<Void?>?{
        val map:MutableMap<String?,Any> = HashMap()
        map["bonustopupbrazil"] =bonustopupbrazil
        return mDatabase?.updateChildren(map)
    }
    fun updateBonusMoncashBrazil(bonusmoncashbrazil:Float):Task<Void?>?{
        val map:MutableMap<String?,Any> = HashMap()
        map["bonusmoncashbrazil"] =bonusmoncashbrazil
        return mDatabase?.updateChildren(map)
    }
    fun updateBonusNatCashBrazil(bonusnatcashbrazil:Float):Task<Void?>?{
        val map:MutableMap<String?,Any> = HashMap()
        map["bonusnatcashbrazil"] =bonusnatcashbrazil
        return mDatabase?.updateChildren(map)
    }
    fun updateBonusLapoulaBrazil(bonuslapoulabrazil:Float):Task<Void?>?{
        val map:MutableMap<String?,Any> = HashMap()
        map["bonuslapoulabrazil"] =bonuslapoulabrazil
        return mDatabase?.updateChildren(map)
    }
    fun updateBonusTopUpMexico(bonustopupmexico:Float):Task<Void?>?{
        val map:MutableMap<String?,Any> = HashMap()
        map["bonustopupmexico"] =bonustopupmexico
        return mDatabase?.updateChildren(map)
    }
    fun updateBonusMoncashMexico(bonusmoncashmexico:Float):Task<Void?>?{
        val map:MutableMap<String?,Any> = HashMap()
        map["bonusmoncashmexico"] =bonusmoncashmexico
        return mDatabase?.updateChildren(map)
    }
    fun updateBonusNatCashMexico(bonusnatcashmexico:Float):Task<Void?>?{
        val map:MutableMap<String?,Any> = HashMap()
        map["bonusnatcashmexico"] =bonusnatcashmexico
        return mDatabase?.updateChildren(map)
    }
    fun updateBonusLapoulaMexico(bonuslapoulamexico:Float):Task<Void?>?{
        val map:MutableMap<String?,Any> = HashMap()
        map["bonuslapoulamexico"] =bonuslapoulamexico
        return mDatabase?.updateChildren(map)
    }
    fun updateBonusTopUpOtherCountry(bonustopupothercountry:Float):Task<Void?>?{
        val map:MutableMap<String?,Any> = HashMap()
        map["bonustopupothercountry"] =bonustopupothercountry
        return mDatabase?.updateChildren(map)
    }
    fun updateBonusMoncashOtherCountry(bonusmoncashothercountry:Float):Task<Void?>?{
        val map:MutableMap<String?,Any> = HashMap()
        map["bonusmoncashothercountry"] =bonusmoncashothercountry
        return mDatabase?.updateChildren(map)
    }
    fun updateBonusNatCashOtherCountry(bonusnatcashothercountry:Float):Task<Void?>?{
        val map:MutableMap<String?,Any> = HashMap()
        map["bonusnatcashothercountry"] =bonusnatcashothercountry
        return mDatabase?.updateChildren(map)
    }
    fun updateBonusLapoulaOtherCountry(bonuslapoulaothercountry:Float):Task<Void?>?{
        val map:MutableMap<String?,Any> = HashMap()
        map["bonuslapoulaothercountry"] =bonuslapoulaothercountry
        return mDatabase?.updateChildren(map)
    }


}