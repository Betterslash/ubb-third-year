package ro.ubb.ideamanager.model.idea

import androidx.lifecycle.LiveData
import ro.ubb.ideamanager.model.data.database.IdeaDao
import ro.ubb.ideamanager.model.data.web.IdeaApi
import ro.ubb.ideamanager.shared.Result
import java.lang.Exception

class IdeaRepository(private val ideaDao: IdeaDao) {
    val ideas = ideaDao.getAll()

    suspend fun refresh() : Result<Boolean>{
        return try{
            val ideas = IdeaApi.service.getAll()
            for (idea in ideas){
                ideaDao.insert(idea)
            }
            Result.Success(true)
        }catch (e: Exception){
            Result.Error(e)
        }
    }

    fun getById(ideaId : String) : LiveData<Idea>{
        return ideaDao.getById(ideaId)
    }

}