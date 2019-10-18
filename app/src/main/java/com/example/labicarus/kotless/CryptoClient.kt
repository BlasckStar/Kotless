package com.example.labicarus.kotless

import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Base64


class CryptoClient {

    //region //----- BASE 64 FUNCTION -----\\
    @RequiresApi(Build.VERSION_CODES.FROYO)
    fun encode(thing: String): String{
        val encoded = Base64.encode(thing.toByteArray(), Base64.NO_WRAP)
        return String(encoded)
    }

    fun decoded(thing: String): String{
        val encoded = Base64.decode(thing.toByteArray(), Base64.NO_WRAP)
        return String(encoded)
    }
    //endregion

}