package ro.ubb.ideasmanager.model.view_model

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ro.ubb.ideasmanager.R
import ro.ubb.ideasmanager.core.auth.AuthRepository
import ro.ubb.ideasmanager.core.auth.Result
import ro.ubb.ideasmanager.core.auth.LoginFormState
import ro.ubb.ideasmanager.core.auth.TokenHolder
import ro.ubb.ideasmanager.core.log.TAG

class LoginViewModel : ViewModel() {
    private val mutableLoginFormState = MutableLiveData<LoginFormState>()
    val loginFormState : LiveData<LoginFormState> = mutableLoginFormState

    private val mutableLoginResult = MutableLiveData<Result<TokenHolder>>()
    val loginResult : LiveData<Result<TokenHolder>> = mutableLoginResult

    fun login(username: String, password: String){
        viewModelScope.launch{
            Log.v(TAG, "login...")
            mutableLoginResult.value = AuthRepository.login(username, password)
            Log.v(TAG, mutableLoginResult.value.toString())
        }
    }

    fun loginDataChanged(username:String, password: String){
        if (!isUserNameValid(username)) {
            //Log.i(TAG, "Username isn't valid")
            mutableLoginFormState.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            //Log.i(TAG, "Password isn't valid")
            mutableLoginFormState.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            Log.i(TAG, "Data is valid")
            mutableLoginFormState.value = LoginFormState(isDataValid = true)
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.isNotEmpty()
    }
}