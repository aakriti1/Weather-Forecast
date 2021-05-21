package aish.android.workmanagerdemo
import android.content.Context
import android.content.Intent

import android.os.Bundle

import androidx.work.CoroutineWorker

import androidx.work.WorkerParameters
import com.aakriti.assignment.model.OpenWeatherResponseModel.LocationResponseModel
import com.aakriti.assignment.utils.CommonMethods
import com.aakriti.assignment.utils.CommonMethods.isNetworkAvailable
import com.aakriti.assignment.utils.Constants
import com.aakriti.assignment.utils.Constants.Companion.MY_BUNDLE
import com.aakriti.assignment.utils.Constants.Companion.MY_OBJECT
import com.aakriti.assignment.utils.Constants.Companion.RECEIVER_DATA
import com.aakriti.assignment.utils.SharedPrefsManager
import com.aakriti.assignment.utils.SharedPrefsManager.get
import com.aakriti.assignment.utils.SharedPrefsManager.set
import com.example.mvvmkotlinexample.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    var context=appContext;

    override suspend fun doWork(): Result {
        return try {
            try {
                doTask(context)
                Result.success()
            } catch (e: Exception) {

                Result.failure()
            }
        } catch (e: Exception) {

            Result.failure()
        }
    }

    private fun doTask(context: Context) {

        var lat = SharedPrefsManager.customPrefs?.get(Constants.LATITUDE, "")
        var lng = SharedPrefsManager.customPrefs?.get(Constants.LONGITUDE, "")


        if (isNetworkAvailable(context)) {
            val call = RetrofitClient.apiInterface.getLocationData(
                lat.toString(),
                lng.toString(),
                Constants.APP_ID,
                "metric"
            )
            call?.enqueue(object : Callback<LocationResponseModel> {
                override fun onFailure(call: Call<LocationResponseModel>, t: Throwable) {
                    // TODO("Not yet implemented")
                    doTask(context)
                }

                override fun onResponse(
                    call: Call<LocationResponseModel>,
                    response: Response<LocationResponseModel>
                ) {
                    // TODO("Not yet implemented")

                    val data = response.body()
                    var strdata = CommonMethods.getStringFromObject(data!!)
                    SharedPrefsManager.customPrefs!!.set(Constants.RESPONSE_DATA, strdata)

                    val intent = Intent(RECEIVER_DATA)
                    val args = Bundle()
                    args.putSerializable(MY_OBJECT, data)
                    intent.putExtra(MY_BUNDLE, args)
                    context.sendBroadcast(intent)
                }
            })


        }

    }

}
