package com.example.bikeapitest.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.example.bikeapitest.R
import com.example.bikeapitest.databinding.DialogMapsDetailBinding

class MapsDetailDialog : DialogFragment() {

    private lateinit var binding: DialogMapsDetailBinding

    private val args: MapsDetailDialogArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogMapsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog?.window?.setGravity(Gravity.BOTTOM)

        binding.placesTextView.text = args.feature.properties.label
        binding.distanceTextView.text = "600 - Bikes Station"
        binding.availableBikeTextView.text = args.feature.properties.bikes
        binding.availablePlaceTextView.text = args.feature.properties.bikeRacks
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.apply {
            val params = attributes
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            params.height = ViewGroup.LayoutParams.MATCH_PARENT
            attributes = params
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    companion object {

        fun newInstance() =
            MapsDetailDialog().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
