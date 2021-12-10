package ro.ubb.ideasmanager

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.*
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.findNavController
import androidx.work.*
import ro.ubb.ideasmanager.databinding.ActivityMainBinding
import ro.ubb.ideasmanager.core.log.TAG
import ro.ubb.ideasmanager.core.network.ConnectivityLiveData
import ro.ubb.ideasmanager.core.notification.TargetActivity
import ro.ubb.ideasmanager.core.worker.SyncWorker
import ro.ubb.ideasmanager.repository.IdeaRepo

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var connectivityLiveData: ConnectivityLiveData
    val CHANNEL_ID = "CHANNEL_ID"

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = findNavController(R.id.nav_host_fragment)
        connectivityManager = getSystemService(android.net.ConnectivityManager::class.java)
        connectivityLiveData = ConnectivityLiveData(connectivityManager)
        connectivityLiveData.observe(this, {
            Log.d(TAG, "connectivityLiveData $it")
            if(it){
                binding.networkStatusChip.text = "Connected"
                startAndObserveJob(IdeaRepo.requests)

                createNotification("Synced Data", "Offline modified data was synced")
            }else{
                createNotification("Network lost", "Your network connection is down")
                binding.networkStatusChip.text = "Disconnected"
            }
        })
        createNotificationChannel()
        Log.i(TAG, "onCreate")
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "isOnline ${isOnline()}")
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    private fun isOnline(): Boolean {
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
    }

    private val networkCallback = @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            Log.d(TAG, "The default network is now: $network")
        }

        override fun onLost(network: Network) {
            Log.d(
                TAG,
                "The application no longer has a default network. The last default network was $network"
            )
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            Log.d(TAG, "The default network changed capabilities: $networkCapabilities")
        }

        override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
            Log.d(TAG, "The default network changed link properties: $linkProperties")
        }
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy")
        super.onDestroy()
    }

    override fun onRestart() {
        Log.i(TAG, "onRestart")
        super.onRestart()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onStop() {
        super.onStop()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private fun createNotification(title:String, text:String) {
        val intent = Intent(this, TargetActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(this)) {
            notify(1, builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "My channel name"
            val descriptionText = "My channel description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun startAndObserveJob(data : Map<String, String>) {
        val constraints = Constraints.Builder()
            .build()
        val inputData = Data.Builder()
            .putAll(data)
            .build()

          //val myWork = PeriodicWorkRequestBuilder<ExampleWorker>(1, TimeUnit.MINUTES)
        val myWork = OneTimeWorkRequest.Builder(SyncWorker::class.java)
            .setConstraints(constraints)
            .setInputData(inputData)
            .build()
        val workId = myWork.id
        WorkManager.getInstance(this).apply {
            // enqueue Work
            enqueue(myWork)
            // observe work status
            getWorkInfoByIdLiveData(workId)
                .observe(this@MainActivity, { status ->
                    val isFinished = status?.state?.isFinished
                    Log.d(TAG, "Job $workId; finished: $isFinished")
                    if(isFinished == true){
                        IdeaRepo.requests = HashMap()
                    }
                })
        }
        Toast.makeText(this, "Job $workId enqueued", Toast.LENGTH_SHORT).show()
    }

}