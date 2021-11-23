package ro.ubb.ideasmanager.repository

import android.util.Log
import ro.ubb.ideasmanager.core.log.TAG
import ro.ubb.ideasmanager.model.IdeaModel
import ro.ubb.ideasmanager.core.service.IdeaService

object IdeaRepository {
    private var cachedItems: MutableList<IdeaModel>? = null;

    suspend fun getAll(): List<IdeaModel> {
        Log.i(TAG, "getAll");
        return IdeaService.service.getAll()
    }
    suspend fun load(ideaId: String): IdeaModel {
        Log.i(TAG, "load")
        val item = cachedItems?.find { it.id == ideaId }
        if (item != null) {
            return item
        }
        return IdeaService.service.read(ideaId)
    }

    suspend fun save(idea: IdeaModel): IdeaModel {
        Log.i(TAG, "save")
        val createdItem = IdeaService.service.create(idea)
        cachedItems?.add(createdItem)
        return createdItem
    }

    suspend fun update(idea: IdeaModel): IdeaModel {
        Log.i(TAG, "update")
        val updatedItem = IdeaService.service.update(idea.id, idea)
        val index = cachedItems?.indexOfFirst { it.id == idea.id }
        if (index != null) {
            cachedItems?.set(index, updatedItem)
        }
        return updatedItem
    }
}