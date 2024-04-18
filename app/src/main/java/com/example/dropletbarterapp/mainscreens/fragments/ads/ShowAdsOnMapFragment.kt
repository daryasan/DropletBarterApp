package com.example.dropletbarterapp.mainscreens.fragments.ads

import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.databinding.FragmentShowAdsOnMapBinding
import com.example.dropletbarterapp.mainscreens.profile.screens.fragments.maps.MapsFragment
import com.example.dropletbarterapp.ui.images.ImageLoader
import com.example.dropletbarterapp.ui.images.ImageUtils
import com.example.dropletbarterapp.utils.Dependencies
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.runtime.image.ImageProvider

class ShowAdsOnMapFragment : Fragment() {

    private lateinit var binding: FragmentShowAdsOnMapBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mapObjects: MapObjectCollection
    private lateinit var address: String

    companion object {
        fun newInstance() = ShowAdsOnMapFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        binding.map.map.isRotateGesturesEnabled = true
        mapObjects = binding.map.map.mapObjects

        address = requireArguments().getString("address").toString()

        showAtPoint()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        MapKitFactory.initialize(requireContext())
        binding = FragmentShowAdsOnMapBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun showAtPoint() {
        val addressLoc = Dependencies.geocoder.getFromLocationName(address, 1)?.get(0)
        val loc = Location("address")
        loc.latitude = addressLoc!!.latitude
        loc.longitude = addressLoc.longitude
        binding.map.map.move(
            CameraPosition(
                Point(
                    loc.latitude,
                    loc.longitude
                ), 15.0f, 0.0f, 0.0f
            )
        )
        val placemarkMapObject = mapObjects.addPlacemark(
            Point(loc.latitude, loc.longitude),
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


}