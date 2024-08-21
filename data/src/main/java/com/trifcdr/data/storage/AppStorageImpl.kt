package com.trifcdr.data.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.trifcdr.data.network.models.user.User
import com.trifcdr.domain.models.UserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

private val Context.dataStore by preferencesDataStore("user_preferences")

class AppStorageImpl(context: Context) : AppStorage {

    private val dataStore = context.dataStore

    override suspend fun saveToken(key: String): Boolean {
        val dataStoreKey = stringPreferencesKey(OAUTH_TOKEN)
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.edit { settings ->
                settings[dataStoreKey] = key
            }
        }.join()
        return true
    }

    override suspend fun getToken(): String {
        var res: String? = null
        CoroutineScope(Dispatchers.IO).launch {
            val dataStoreKey = stringPreferencesKey(OAUTH_TOKEN)
            val prefs = dataStore.data.first()
            res = prefs[dataStoreKey] ?: return@launch
        }.join()

        return res ?: DEFAULT_TOKEN_VALUE
    }

    override suspend fun getUserData(): User {
        var res: User? = null
        CoroutineScope(Dispatchers.IO).launch {
            val dataStoreEmail = stringPreferencesKey(EMAIL)
            val dataStoreGender = stringPreferencesKey(GENDER)
            val dataStoreAge = intPreferencesKey(AGE)
            val prefs = dataStore.data.first()
            res = User(
                email = prefs[dataStoreEmail]!!,
                age = prefs[dataStoreAge]!!,
                gender = prefs[dataStoreGender]!!
            )
        }.join()

        return res!!
    }

    override suspend fun saveUserData(user: UserData): Boolean {
        val dataStoreEmail = stringPreferencesKey(EMAIL)
        val dataStoreGender = stringPreferencesKey(GENDER)
        val dataStoreAge = intPreferencesKey(AGE)
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.edit { settings ->
                settings[dataStoreEmail] = user.email
                settings[dataStoreGender] = user.gender
                settings[dataStoreAge] = user.age
            }
        }.join()
        return true
    }


    companion object {
        private const val OAUTH_TOKEN = "access_token"
        private const val EMAIL = "email"
        private const val GENDER = "gender"
        private const val AGE = "age"
        private const val DEFAULT_TOKEN_VALUE = "empty"
    }
}