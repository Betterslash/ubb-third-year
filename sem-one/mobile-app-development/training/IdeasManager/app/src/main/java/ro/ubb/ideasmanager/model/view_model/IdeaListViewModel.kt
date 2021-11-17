package ro.ubb.ideasmanager.model.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ro.ubb.ideasmanager.data.DataSource
import ro.ubb.ideasmanager.model.IdeaModel

class IdeaListViewModel(dataSource: DataSource) : ViewModel(){
    val ideasViewData = dataSource.getFlowerList()
}

class IdeaListViewModelFactory(private val context : Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IdeaModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return IdeaListViewModel(dataSource = DataSource.getDataSource(context.resources)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}