package aish.android.workmanagerdemo

import android.content.Context
import android.util.Log
import androidx.work.*
import com.aakriti.assignment.utils.Constants.Companion.WORK_MANAGER_WEATHER
import java.util.*
import java.util.concurrent.TimeUnit

object WorkManagerScheduler {

    fun refreshPeriodicWork(context: Context) {

        val myConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()


        val refreshCpnWork = PeriodicWorkRequest.Builder(MyWorker::class.java,
            2, TimeUnit.HOURS)
            .setConstraints(myConstraints)
            .addTag(WORK_MANAGER_WEATHER)
            .build()


        WorkManager.getInstance(context).enqueueUniquePeriodicWork("myWorkManager",
            ExistingPeriodicWorkPolicy.KEEP, refreshCpnWork)

    }
}