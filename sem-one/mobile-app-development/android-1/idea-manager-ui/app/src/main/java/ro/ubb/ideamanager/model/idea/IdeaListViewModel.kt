package ro.ubb.ideamanager.model.idea

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ro.ubb.ideamanager.model.data.database.IdeaDatabase
import ro.ubb.ideamanager.shared.TAG
import java.lang.Exception
import ro.ubb.ideamanager.shared.Result

class IdeaListViewModel(application: Application) : AndroidViewModel(application) {
    private val mutableLoading = MutableLiveData<Boolean>().apply { value = false }
    private val mutableException = MutableLiveData<Exception>().apply { value = null }

    val ideas : LiveData<List<Idea>>
    val loading : LiveData<Boolean> = mutableLoading
    val loadingError : LiveData<Exception> = mutableException

    private val ideaRepository: IdeaRepository

    init {
        val ideaDao = IdeaDatabase.getDatabase(application, viewModelScope).ideaDao()
        ideaRepository = IdeaRepository(ideaDao)
        ideas = ideaRepository.ideas
    }

    fun refresh() {
        viewModelScope.launch {
            Log.v(TAG, "refresh...");
            mutableLoading.value = true
            mutableException.value = null
            when (val result = ideaRepository.refresh()) {
                is Result.Success -> {
                    Log.d(TAG, "refresh succeeded");
                }
                is Result.Error -> {
                    Log.w(TAG, "refresh failed", result.exception);
                    mutableException.value = result.exception
                }
            }
            mutableLoading.value = false
        }
    }
}