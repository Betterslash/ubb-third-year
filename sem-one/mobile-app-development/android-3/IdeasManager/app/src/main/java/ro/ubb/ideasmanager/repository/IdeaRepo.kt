package ro.ubb.ideasmanager.repository

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import ro.ubb.ideasmanager.core.auth.Result
import ro.ubb.ideasmanager.core.service.IdeaService
import ro.ubb.ideasmanager.data.database.IdeaDao
import ro.ubb.ideasmanager.model.IdeaModel
import java.lang.Exception

class IdeaRepo(private val ideaDao: IdeaDao) {
    val ideas = ideaDao.getAll()
    companion object{
        var requests = HashMap<String, String>()
    }
    suspend fun refresh() : Result<Boolean>{
        return try{
            val ideas = IdeaService.service.getAll()
            ideas.forEach{e -> ideaDao.insert(e)}
            Result.Success(true)
        }catch (e : Exception){
            Result.Error(e)
        }
    }

    fun getById(ideaId: String): LiveData<IdeaModel> {
        return ideaDao.getById(ideaId)
    }

    suspend fun save(idea: IdeaModel): Result<IdeaModel> {
        return try {
            val createdItem = IdeaService.service.create(idea)
            ideaDao.insert(createdItem)
            Result.Success(createdItem)
        } catch(e: Exception) {
            addRequest(idea)
            ideaDao.insert(idea)
            Result.Error(e)
        }
    }

    private fun addRequest(idea: IdeaModel){
        requests[Gson().toJson(idea)] = Gson().toJson(idea)
    }

    suspend fun update(idea:  IdeaModel): Result<IdeaModel> {
        return try {
            val updatedItem = IdeaService.service.update(idea.id, idea)
            ideaDao.update(updatedItem)
            Result.Success(updatedItem)
        } catch(e: Exception) {
            addRequest(idea)
            ideaDao.update(idea)
            Result.Error(e)
        }
    }

    suspend fun delete(ideaId : String): Result<String> {
        return try{
            ideaDao.deleteById(ideaId)
            IdeaService.service.delete(ideaId)
            Result.Success(ideaId)
        }catch (e : Exception){
            Result.Error(e)
        }
    }
}