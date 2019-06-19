package com.example.pokemon

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.Exception


class Pokemon : AppCompatActivity(), OnMapReadyCallback {


    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        checkLocation()
    }


    val accesslocation=123
    fun checkLocation(){






        if (Build.VERSION.SDK_INT>=23){

            if (ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){

                requestPermissions(arrayOf( android.Manifest.permission.ACCESS_FINE_LOCATION),accesslocation)

                return
            } }

        getuserLocation()

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {



        when (requestCode){
            accesslocation->{
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    getuserLocation()
                } else{
                    Toast.makeText(this,"permission denied",Toast.LENGTH_LONG).show()
                }
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

     fun getuserLocation() {


        var location=MyLocation()
        var locationManger=getSystemService(Context.LOCATION_SERVICE) as LocationManager

         locationManger.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1,3f,location)

        var myThread=MyThread()
        myThread.start()


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


         }



  var Mylocation:Location?=null
  inner class MyLocation:LocationListener{

      constructor(){
          Mylocation= Location("me")

      }


       override fun onLocationChanged(location: Location?) {

           Mylocation=location
           Log.e("Location2",Mylocation?.latitude.toString())

           Toast.makeText(applicationContext,"Location changed",Toast.LENGTH_LONG).show()
       }

       override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
       }

       override fun onProviderEnabled(p0: String?) {
       }

       override fun onProviderDisabled(p0: String?) {
       }

   }

   inner class  MyThread :Thread{

        constructor():super(){

        }

        override fun run() {

            while (true){

                try{
                    runOnUiThread {
                        mMap!!.clear()
                        val CurrentLocation = LatLng(Mylocation!!.latitude, Mylocation!!.longitude)
                        Log.e("Location",Mylocation!!.latitude.toString())
                        mMap!!.addMarker(
                            MarkerOptions()
                                .position(CurrentLocation)
                                .title("Marker in Egypt")
                        )
                        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(CurrentLocation, 5f))
                    }


                    Thread.sleep(1000)
                }catch (e:Exception){}

            }

        }

    }
}
