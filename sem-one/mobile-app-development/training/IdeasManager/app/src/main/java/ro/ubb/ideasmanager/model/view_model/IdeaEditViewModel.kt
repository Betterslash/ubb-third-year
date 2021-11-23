package ro.ubb.ideasmanager.model.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ro.ubb.ideasmanager.core.log.TAG
import ro.ubb.ideasmanager.model.IdeaModel
import ro.ubb.ideasmanager.repository.IdeaRepository

class IdeaEditViewModel : ViewModel() {
    private val mutableIdea = MutableLiveData<IdeaModel>().apply { value = IdeaModel("", "", 0, 0, 0) }
    private val mutableFetching = MutableLiveData<Boolean>().apply { value = false }
    private val mutableCompleted = MutableLiveData<Boolean>().apply { value = false }
    private val mutableException = MutableLiveData<Exception>().apply { value = null }

    val idea: LiveData<IdeaModel> = mutableIdea
    val fetching: LiveData<Boolean> = mutableFetching
    val fetchingError: LiveData<Exception> = mutableException
    val completed: LiveData<Boolean> = mutableCompleted

    fun loadIdea(ideaId: String) {
        viewModelScope.launch {
            Log.i(TAG, "loadIdea...")
            mutableFetching.value = true
            mutableException.value = null
            try {
                mutableIdea.value = IdeaRepository.load(ideaId)
                Log.i(TAG, "loadIdea succeeded")
                Log.i(TAG, mutableIdea.value.toString())
                mutableFetching.value = false
            } catch (e: Exception) {
                Log.w(TAG, "loadIdea failed", e)
                mutableException.value = e
                mutableFetching.value = false
            }
        }
    }

    fun saveOrUpdateItem(text: String) {
        viewModelScope.launch {
            Log.i(TAG, "saveOrUpdateItem...")
            val idea = mutableIdea.value ?: return@launch
            idea.text = text
            idea.currentBudget = 0
            idea.neededBudget = 1000
            idea.rating = 0
            mutableFetching.value = true
            mutableException.value = null
            try {
                if (idea.id.isNotEmpty()) {
                    mutableIdea.value = IdeaRepository.update(idea)
                } else {
                    mutableIdea.value = IdeaRepository.save(idea)
                }
                Log.i(TAG, "saveOrUpdateItem succeeded");
                mutableCompleted.value = true
                mutableFetching.value = false
            } catch (e: Exception) {
                Log.w(TAG, "saveOrUpdateItem failed", e);
                mutableException.value = e
                mutableFetching.value = false
            }
        }
    }
}