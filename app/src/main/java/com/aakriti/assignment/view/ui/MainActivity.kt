package com.aakriti.assignment.view.ui

import aish.android.workmanagerdemo.WorkManagerScheduler
import android.Manifest
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import com.aakriti.assignment.R
import com.aakriti.assignment.model.OpenWeatherResponseModel.LocationResponseModel
import com.aakriti.assignment.utils.Constants.Companion.GPS_REQUEST
import com.aakriti.assignment.utils.Constants.Companion.IMAGE_URL
import com.aakriti.assignment.utils.Constants.Companion.REQUEST_CODE_PERMISSIONS
import com.aakriti.assignment.utils.GpsUtils
import com.aakriti.assignment.utils.CommonMethods
import com.aakriti.assignment.utils.Constants
import com.aakriti.assignment.utils.Constants.Companion.MY_BUNDLE
import com.aakriti.assignment.utils.Constants.Companion.MY_OBJECT
import com.aakriti.assignment.utils.Constants.Companion.RECEIVER_DATA
import com.aakriti.assignment.utils.Constants.Companion.WORK_MANAGER_WEATHER
import com.aakriti.assignment.utils.SharedPrefsManager
import com.aakriti.assignment.utils.SharedPrefsManager.get
import com.aakriti.assignment.utils.SharedPrefsManager.set
import com.aakriti.assignment.viewmodel.LocationCordinatesViewModel
import com.aakriti.assignment.viewmodel.OpenWeatherViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(){

    private var isGPSEnabled = false
    private lateinit var locationCordinatesViewModel: LocationCordinatesViewModel
    private lateinit var openWeatherViewModel: OpenWeatherViewModel
    private lateinit var myReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        actionBar?.hide()

        myReceiver = object: BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {

                val bundle = intent!!.getBundleExtra(MY_BUNDLE)
                val theObject: LocationResponseModel = bundle!!.getSerializable(MY_OBJECT) as LocationResponseModel

                getWeatherData(theObject)

            }

        }

        var mIntentFilter= IntentFilter();
        mIntentFilter.addAction(RECEIVER_DATA)
        registerReceiver(myReceiver,mIntentFilter)


        locationCordinatesViewModel = ViewModelProvider(this).get(LocationCordinatesViewModel::class.java)
        openWeatherViewModel = ViewModelProvider(this).get(OpenWeatherViewModel::class.java)

        GpsUtils(this).turnGPSOn(object : GpsUtils.OnGpsListener {

            override fun gpsStatus(isGPSEnable: Boolean) {
                isGPSEnabled = isGPSEnable
            }
        })


    }

    override fun onStart() {
        super.onStart()
        requestLocationPermission()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GPS_REQUEST) {
                isGPSEnabled = true
                requestLocationPermission()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {

            for (i in permissions.indices) {
                if (permissions[i].equals(Manifest.permission.ACCESS_COARSE_LOCATION, ignoreCase = true)) {
                    //foreground permission allowed
                    if (grantResults[i] >= 0) {

                        requestBackgroundPermission()
                        continue
                    } else {

                        CommonMethods.showGPSNotEnabledDialog(this)
                        break
                    }
                }

            }
        }
    }

    private fun requestBackgroundPermission(){
        val background = ActivityCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (background) {
                handleLocationUpdates()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
                    REQUEST_CODE_PERMISSIONS
                )
            }
        }

        else{
            handleLocationUpdates()
        }
    }

    private fun requestLocationPermission() {

        if(isGPSEnabled){
            val foreground = ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            if (foreground) {
                handleLocationUpdates()

            } else {

                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ), REQUEST_CODE_PERMISSIONS
                )
            }

        }
        else{
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }

    private fun handleLocationUpdates() {
        locationCordinatesViewModel.getLocationData().observe(this, Observer {

            SharedPrefsManager.customPrefs!!.set(Constants.LATITUDE, it.latitude.toString())
            SharedPrefsManager.customPrefs!!.set(Constants.LONGITUDE, it.longitude.toString())

            lnrRefresh.setOnClickListener {

                if(CommonMethods.isNetworkAvailable(this)){
                    WorkManager.getInstance(this).cancelAllWorkByTag(WORK_MANAGER_WEATHER);
                    WorkManagerScheduler.refreshPeriodicWork(this)
                }
                else{
                    CommonMethods.showNetworkDialog(this)
                }



            }

            var res=   SharedPrefsManager.customPrefs?.get(Constants.RESPONSE_DATA, "")
            if(!res!!.equals("")){
              getWeatherData(CommonMethods.getObjectFromString(res))
             }
            else{

                if(CommonMethods.isNetworkAvailable(this)){
                    WorkManagerScheduler.refreshPeriodicWork(this)
                }
                else{
                    CommonMethods.showNetworkDialog(this)
                }


            }


        })
    }

        private fun getWeatherData(it: LocationResponseModel){

            val data = it

            val updatedAt:Long = data.getDt().toLong();
            val updatedAtText =SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.ENGLISH).format(Date(updatedAt*1000))
            val sunriseTime:Long = data.getSys()!!.getSunrise().toLong()
            val sunsettIME:Long = data.getSys()!!.getSunset().toLong()
            var image=   IMAGE_URL+data.getWeather()!!.get(0)!!.getIcon()+".png"


            address.text = data.getName()+", " +data.getSys()?.getCountry()
            updated_at.text = updatedAtText
            status.text = data.getWeather()?.get(0)?.getMain()
            statusdesc.text = data.getWeather()?.get(0)?.getDescription()
            temp.text =data.getMain()!!.getTemp().toString()+"°C"
            temp_min.text = getString(R.string.min_temp)+data.getMain()!!.getTempMin().toString()+"°C"
            temp_max.text = getString(R.string.max_temp)+data.getMain()!!.getTempMax().toString()+"°C"
            sunrise.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunriseTime*1000))
            sunset.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunsettIME*1000))
            wind.text = data.getWind()?.getSpeed().toString()+" kph"
            pressure.text = data.getMain()?.getPressure().toString()+" hPa"
            humidity.text = data.getMain()?.getHumidity().toString() +"%"

            val picasso = Picasso.Builder(this).listener(
                object : Picasso.Listener{
                    override fun onImageLoadFailed(picasso: Picasso?, uri: Uri?, exception: Exception?) {
                        exception?.printStackTrace()
                        println("Picasso loading failed : ${exception?.message}")
                    }

                }
            ).build()
            picasso.load(image.trim()).into(imgLoc)



    }



    override fun onStop() {
        super.onStop()
        unregisterReceiver(myReceiver)
    }

}