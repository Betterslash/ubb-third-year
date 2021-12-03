package ro.ubb.ideasmanager.model.view_model

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ro.ubb.ideasmanager.core.auth.Result
import ro.ubb.ideasmanager.core.log.TAG
import ro.ubb.ideasmanager.data.database.IdeaDatabase
import ro.ubb.ideasmanager.model.IdeaModel
import ro.ubb.ideasmanager.repository.IdeaRepo

class IdeaEditViewModel(application: Application) : AndroidViewModel(application) {
    private val mutableIdea = MutableLiveData<IdeaModel>().apply { value = IdeaModel("", "", "", 0, 0, 0) }
    private val mutableFetching = MutableLiveData<Boolean>().apply { value = false }
    private val mutableCompleted = MutableLiveData<Boolean>().apply { value = false }
    private val mutableException = MutableLiveData<Exception>().apply { value = null }

    val idea: LiveData<IdeaModel> = mutableIdea
    val fetching: LiveData<Boolean> = mutableFetching
    val fetchingError: LiveData<Exception> = mutableException
    val completed: LiveData<Boolean> = mutableCompleted


    private val ideaRepo: IdeaRepo

    init {
        val ideaDao = IdeaDatabase.getDatabase(application, viewModelScope).ideaDao()
        ideaRepo = IdeaRepo(ideaDao)
    }

    fun loadIdea(ideaId: String): LiveData<IdeaModel> {
        Log.v(TAG, "getIdeaById...")
        return ideaRepo.getById(ideaId)
    }

    fun saveOrUpdateItem(ideaModel : IdeaModel) {
        viewModelScope.launch {
            Log.i(TAG, "saveOrUpdateItem...")

            mutableFetching.value = true
            mutableException.value = null

            val result: Result<IdeaModel> = if (ideaModel.id.isNotEmpty()) {
                Log.i(TAG, "update $ideaModel")
                ideaRepo.update(ideaModel)
            } else {
                Log.i(TAG, "save $ideaModel")
                ideaRepo.save(ideaModel)
            }
            when(result) {
                is Result.Success -> {
                    Log.d(TAG, "saveOrUpdateIdea succeeded");
                }
                is Result.Error -> {
                    Log.w(TAG, "saveOrUpdateIdea failed", result.exception);
                    mutableException.value = result.exception
                }
            }
            mutableCompleted.value = true
            mutableFetching.value = false
        }
    }
}