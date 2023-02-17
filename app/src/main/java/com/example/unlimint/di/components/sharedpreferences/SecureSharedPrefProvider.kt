package com.example.unlimint.di.components.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

object SecureSharedPrefProvider {

    /**
     * Security Crypto library requires minimum SDK 23 so for SDK 21 and 22 we use normal shared preference
     * and for 23+ we use encrypted shared preference
     */
    fun getSharedPreference(context: Context, preferenceName: String) : SharedPreferences {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
            return context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
        }
        return EncryptedSharedPreferences.create(
            preferenceName,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

}