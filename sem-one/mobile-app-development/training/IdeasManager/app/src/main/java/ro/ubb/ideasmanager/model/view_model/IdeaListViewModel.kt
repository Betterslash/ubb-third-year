package ro.ubb.ideasmanager.model.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ro.ubb.ideasmanager.core.log.TAG
import ro.ubb.ideasmanager.data.database.IdeaDatabase
import ro.ubb.ideasmanager.model.IdeaModel
import ro.ubb.ideasmanager.repository.IdeaRepo

class IdeaListViewModel(application: Application) : AndroidViewModel(application){
    private val mutableLoading = MutableLiveData<Boolean>().apply { value = false }
    private val mutableException = MutableLiveData<Exception>().apply { value = null }
    private val mutableIdeas = MutableLiveData<List<IdeaModel>>().apply { value = emptyList() }

    private val ideaRepo: IdeaRepo

    val loading: LiveData<Boolean> = mutableLoading
    val loadingError: LiveData<Exception> = mutableException
    var ideas : LiveData<List<IdeaModel>> = mutableIdeas
    init {
        val itemDao = IdeaDatabase.getDatabase(application, viewModelScope).ideaDao()
        ideaRepo = IdeaRepo(itemDao)
        ideas = ideaRepo.ideas
    }

    fun refresh() {
        viewModelScope.launch {
            Log.v(TAG, "refresh...");
            mutableLoading.value = true
            mutableException.value = null
            when (val result = ideaRepo.refresh()) {
                is ro.ubb.ideasmanager.core.auth.Result.Success -> {
                    Log.d(TAG, "refresh succeeded");
                }
                is ro.ubb.ideasmanager.core.auth.Result.Error -> {
                    Log.w(TAG, "refresh failed", result.exception);
                    mutableException.value = result.exception
                }
            }
            mutableLoading.value = false
        }
    }
}

