package com.aakriti.assignment.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import com.aakriti.assignment.R
import com.aakriti.assignment.model.OpenWeatherResponseModel.LocationResponseModel
import com.google.gson.Gson
import org.apache.commons.lang3.StringEscapeUtils


object CommonMethods {

    fun showGPSNotEnabledDialog(activity: Activity) {
        AlertDialog.Builder(activity)
            .setTitle(activity.getString(R.string.allow_location_permission))
            .setMessage(activity.getString(R.string.if_permission_not))
            .setCancelable(false)
            .setPositiveButton(activity.getString(R.string.allow)) { _, _ ->

                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package",activity.packageName, null)
                intent.data = uri
                activity.startActivity(intent)

            }
            .setNegativeButton(activity.getString(R.string.not_now)) { _, _ ->
                activity.finish()
            }
            .show()
    }

    fun getStringFromObject(locationResponseModel: LocationResponseModel): String{
        val gson = Gson()
        return gson.toJson(locationResponseModel)
    }

    fun getObjectFromString(strData: String): LocationResponseModel{
        val gson = Gson()
        val json: String = gson.toJson(strData.trim())
        val locationResponseModel: LocationResponseModel = gson.fromJson(removeQuotesAndUnescape(json), LocationResponseModel::class.java)
        return  locationResponseModel
    }

    private fun removeQuotesAndUnescape(uncleanJson: String): String? {
        val noQuotes = uncleanJson.replace("^\"|\"$".toRegex(), "")
        return StringEscapeUtils.unescapeJava(noQuotes)
    }

    fun getConnectionType(context: Context): Int {
        var result = 0 // Returns connection type. 0: none; 1: mobile data; 2: wifi
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (cm != null) {
                val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        result = 2
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        result = 1
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                        result = 3
                    }
                }
            }
        } else {
            if (cm != null) {
                val activeNetwork = cm.activeNetworkInfo
                if (activeNetwork != null) {
                    // connected to the internet
                    if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                        result = 2
                    } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                        result = 1
                    } else if (activeNetwork.type == ConnectivityManager.TYPE_VPN) {
                        result = 3
                    }
                }
            }
        }
        return result
    }

     fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

     fun showNetworkDialog(activity: Activity) {
        AlertDialog.Builder(activity)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle(activity.getString(R.string.connection_lost))
            .setMessage(activity.getString(R.string.please_check_internet))
            .setCancelable(false)
            .setPositiveButton(
                activity.getString(R.string.okay)
            ) { dialog, which -> activity.finish() }

            .show()
    }


}