package ro.ubb.ideasmanager.repository

import android.util.Log
import ro.ubb.ideasmanager.log.TAG
import ro.ubb.ideasmanager.model.IdeaModel
import ro.ubb.ideasmanager.service.IdeaService

object IdeaRepository {
    suspend fun getAll(): List<IdeaModel> {
        Log.i(TAG, "getAll");
        return IdeaService.service.getAll()
    }
}