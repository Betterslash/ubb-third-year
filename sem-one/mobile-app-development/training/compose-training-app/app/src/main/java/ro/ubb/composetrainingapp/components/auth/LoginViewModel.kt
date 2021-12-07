package ro.ubb.composetrainingapp.components.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ro.ubb.composetrainingapp.auth.AuthRepository
import ro.ubb.composetrainingapp.auth.TokenHolder
import ro.ubb.composetrainingapp.core.TAG

class LoginViewModel : ViewModel() {
    private val mutableUser = MutableLiveData<Result<TokenHolder>>()
    var loginResult : LiveData<Result<TokenHolder>> = mutableUser
    fun login(username : String, password: String){
        viewModelScope.launch {
            Log.v(TAG, "login...")
            mutableUser.value = AuthRepository.login(username, password)
        }
    }
}