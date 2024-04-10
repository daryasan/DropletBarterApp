package com.example.dropletbarterapp.mainscreens.profile.screens.fragments.maps

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.VectorDrawable
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.auth0.android.jwt.JWT
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.databinding.FragmentMapsBinding
import com.example.dropletbarterapp.mainscreens.profile.screens.ProfileActivity
import com.example.dropletbarterapp.ui.images.ImageUtils
import com.example.dropletbarterapp.utils.Dependencies
import com.example.dropletbarterapp.validators.Toaster
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.GeoObjectTapListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.runtime.image.ImageProvider
import kotlinx.coroutines.runBlocking
import java.util.*


class MapsFragment : Fragment() {

    private lateinit var binding: FragmentMapsBinding
    private val toaster = Toaster()
    private var address: String? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mapObjects: MapObjectCollection


    private val requestPermissionLocationLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            binding.map.map.move(CameraPosition(Point(55.753215, 37.622504), 10.0f, 0.0f, 0.0f))
        } else {
            showAtPoint()
        }
    }

    private val tapListener = GeoObjectTapListener { geoObjectTapEvent ->
        setMarkerLocation(geoObjectTapEvent.geoObject.geometry[0].point!!)
        getAddressForLocation(geoObjectTapEvent.geoObject.geometry[0].point!!)
        false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        binding.map.map.isRotateGesturesEnabled = true
        mapObjects = binding.map.map.mapObjects

        showAtPoint()

        binding.buttonSaveAddress.setOnClickListener {
            if (address != null) {
                runBlocking {
                    saveEditedData(address)
                }
                requireActivity().onBackPressed()
                requireActivity().onBackPressed()
                (requireActivity() as ProfileActivity).enableAndShowElements()
            } else {
                toaster.getToast(requireContext(), "Выберите адрес чтобы сохранить!")
            }
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().onBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        MapKitFactory.initialize(requireContext())
        binding = FragmentMapsBinding.inflate(layoutInflater)


        binding.map.map.addTapListener(tapListener)

        return binding.root
    }

    private fun setMarkerLocation(location: Point) {
        val mapObjects = binding.map.map.mapObjects
        mapObjects.clear()
        val placemarkMapObject = mapObjects.addPlacemark(
            Point(location.latitude, location.longitude),
            ImageProvider.fromBitmap(
                createBitmapFromVector(R.drawable.location_mark)
            )
        )
        placemarkMapObject.opacity = 0.7f
    }

    private fun createBitmapFromVector(art: Int): Bitmap? {
        val drawable = ContextCompat.getDrawable(requireContext(), art) ?: return null
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        ) ?: return null
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }


    suspend fun saveEditedData(address: String?) {
        val jwt = JWT(Dependencies.tokenService.getAccessToken().toString())
        val userData = Dependencies.userRepository.findUserById(
            Dependencies.tokenService.getAccessToken().toString(),
            jwt.getClaim("id").asString()!!.toLong()
        )
        Dependencies.userRepository.editPersonalData(
            Dependencies.tokenService.getAccessToken().toString(),
            jwt.getClaim("id").asString()!!.toLong(),
            userData.firstName.toString(),
            userData.lastName.toString(),
            address = address,
            photo = userData.photo?.let { ImageUtils.encodeImageFromString(it) }
        )
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.map.onStart()
    }

    override fun onStop() {
        binding.map.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    private fun getAddressForLocation(point: Point) {
        val geocoder = Geocoder(requireContext(), Locale("ru", "RU"))
        val addressGeo = geocoder.getFromLocation(point.latitude, point.longitude, 1)
        address = addressGeo?.get(0)?.getAddressLine(0)
        Log.d("Address", address.toString())
        binding.textViewAddress.setText(address.toString())
    }

    private fun showAtPoint() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val locationManager: LocationManager =
                requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        val userLatLng = LatLng(location.latitude, location.longitude)
                        binding.map.map.move(
                            CameraPosition(
                                Point(
                                    userLatLng.latitude,
                                    userLatLng.longitude
                                ), 15.0f, 0.0f, 0.0f
                            )
                        )
                    }
                }
            } else {
                binding.map.map.move(
                    CameraPosition(
                        Point(55.753215, 37.622504), 10.0f, 0.0f, 0.0f
                    )
                )
            }

        } else {
            requestPermissionLocationLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

    }


    companion object {
        fun newInstance() = MapsFragment()
    }

}