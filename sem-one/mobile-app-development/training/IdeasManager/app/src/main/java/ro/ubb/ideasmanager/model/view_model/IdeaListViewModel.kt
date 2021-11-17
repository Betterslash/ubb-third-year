package ro.ubb.ideasmanager.model.view_model

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ro.ubb.ideasmanager.data.DataGenerator
import ro.ubb.ideasmanager.log.TAG
import ro.ubb.ideasmanager.model.IdeaModel
import ro.ubb.ideasmanager.repository.IdeaRepository

class IdeaListViewModel : ViewModel(){
    private val mutableLoading = MutableLiveData<Boolean>().apply { value = false }
    private val mutableException = MutableLiveData<Exception>().apply { value = null }
    private val mutableIdeas = MutableLiveData<List<IdeaModel>>().apply {
        value = emptyList()
    }


    val loading: LiveData<Boolean> = mutableLoading
    val loadingError: LiveData<Exception> = mutableException
    val ideas : LiveData<List<IdeaModel>> = mutableIdeas

    private var index = 100

    private fun createIdea() {
        val list = mutableListOf<IdeaModel>()
        list.addAll(mutableIdeas.value!!)
        list.add(DataGenerator().createObject())
        mutableIdeas.value = list
    }

    fun loadItems() {
        viewModelScope.launch {
            Log.v(TAG, "loadItems...");
            mutableLoading.value = true
            mutableException.value = null
            try {
                mutableIdeas.value = IdeaRepository.getAll()
                Log.d(TAG, "loadItems succeeded");
                mutableLoading.value = false
            } catch (e: Exception) {
                Log.w(TAG, "loadItems failed", e);
                mutableException.value = e
                mutableLoading.value = false
            }
        }
    }
}

