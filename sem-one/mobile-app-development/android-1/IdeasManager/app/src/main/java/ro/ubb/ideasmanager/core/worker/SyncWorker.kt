package ro.ubb.ideasmanager.core.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ro.ubb.ideasmanager.core.log.TAG
import ro.ubb.ideasmanager.core.service.IdeaService
import ro.ubb.ideasmanager.model.IdeaModel
import java.util.concurrent.TimeUnit.SECONDS

class SyncWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val requestBody = inputData.keyValueMap.map{ e: Map.Entry<String, Any> ->
            Gson().fromJson(e.value.toString(), IdeaModel::class.java)
        }.toList()
        CoroutineScope(Dispatchers.IO).launch {
            IdeaService.service.sync(requestBody)
        }
        return Result.success()
    }
}