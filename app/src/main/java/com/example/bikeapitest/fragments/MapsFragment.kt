package com.example.bikeapitest.fragments

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bikeapitest.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {
    private val args: MapsFragmentArgs by navArgs()
    private val callback = OnMapReadyCallback { googleMap ->

        val sydney = LatLng(args.feature.geometry.coordinates[1], args.feature.geometry.coordinates[0])
        googleMap.addMarker(
            MarkerOptions().position(sydney).icon(
                createStoreMarker("${args.feature.properties.bikes} bikes")?.let { BitmapDescriptorFactory.fromBitmap(it) }
            )
        )

        val cameraPosition = CameraPosition.Builder()
            .target(sydney) // Sets the center of the map to Mountain View
            .zoom(18f) // Sets the zoom
            .build() // Creates a CameraPosition from the builder
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        googleMap.setOnMarkerClickListener {
            findNavController().navigate(MapsFragmentDirections.actionMapsFragmentToMapsDetailDialog(args.feature))
            true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun createStoreMarker(title: String): Bitmap? {
        val markerLayout: View = layoutInflater.inflate(R.layout.marker_layout, null)
        val markerImage: ImageView = markerLayout.findViewById<View>(R.id.marker_image) as ImageView
        val markerRating = markerLayout.findViewById<View>(R.id.marker_text) as TextView
        markerImage.setImageResource(R.drawable.ic_bike_png)
        markerRating.text = title
        markerLayout.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        markerLayout.layout(0, 0, markerLayout.measuredWidth, markerLayout.measuredHeight)
        val bitmap = Bitmap.createBitmap(
            markerLayout.measuredWidth,
            markerLayout.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        markerLayout.draw(canvas)
        return bitmap
    }
}
