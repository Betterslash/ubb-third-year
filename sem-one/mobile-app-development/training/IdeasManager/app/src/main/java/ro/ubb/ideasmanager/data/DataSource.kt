package ro.ubb.ideasmanager.data

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ro.ubb.ideasmanager.model.IdeaModel

class DataSource(resources: Resources) {
    private val initialIdeaList = DataGenerator().generateObjects()
    private val ideasLiveData = MutableLiveData(initialIdeaList)

    /* Adds flower to liveData and posts value. */
    fun addFlower(ideaModel: IdeaModel) {
        val currentList = ideasLiveData.value
        if (currentList == null) {
            ideasLiveData.postValue(listOf(ideaModel) as MutableList<IdeaModel>?)
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, ideaModel)
            ideasLiveData.postValue(updatedList)
        }
    }

    /* Removes flower from liveData and posts value. */
    fun removeFlower(flower: IdeaModel) {
        val currentList = ideasLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(flower)
            ideasLiveData.postValue(updatedList)
        }
    }

    /* Returns flower given an ID. */
    fun getFlowerForId(id: String): IdeaModel? {
        ideasLiveData.value?.let { flowers ->
            return flowers.firstOrNull{ it.id == id}
        }
        return null
    }

    fun getFlowerList(): LiveData<List<IdeaModel>> {
        return ideasLiveData
    }


    companion object {
        private var INSTANCE: DataSource? = null

        fun getDataSource(resources: Resources): DataSource {
            return synchronized(DataSource::class) {
                val newInstance = INSTANCE ?: DataSource(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}