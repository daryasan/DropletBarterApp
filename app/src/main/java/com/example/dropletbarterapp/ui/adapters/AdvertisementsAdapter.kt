package com.example.dropletbarterapp.ui.adapters

import android.graphics.drawable.Drawable
import android.media.Image
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.databinding.AdvertisementItemBinding
import com.example.dropletbarterapp.models.Advertisement


class AdvertisementsAdapter :
    RecyclerView.Adapter<AdvertisementsAdapter.AdvertisementViewHolder>() {

    private var onAdvertisementClickListener: OnAdvertisementClickListener? = null
    var advertisements: List<Advertisement> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class AdvertisementViewHolder(val binding: AdvertisementItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertisementViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdvertisementItemBinding.inflate(inflater, parent, false)
        return AdvertisementViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return advertisements.size
    }

    override fun onBindViewHolder(holder: AdvertisementViewHolder, position: Int) {
        val ads = advertisements[position]
        val context = holder.itemView.context

        with(holder.binding) {

            buttonFavourites.bringToFront()

            // TODO set currentuser.favourites.contsins(ads)
//            buttonFavourites.setOnClickListener {
//                if (ads.) {
//                    buttonFavourites.setImageResource(R.drawable.favourites_true)
//                } else {
//                    buttonFavourites.setImageResource(R.drawable.favourites_false)
//                }
//
//            }

            // TODO set favourite
            if (ads.photo != null) {
                Glide.with(context).load(ads.photo)
                    .centerInside()
                    .error(R.drawable.empty_profile_image)
                    .placeholder(R.drawable.empty_profile_image).into(imageViewAds)
            }

            textViewAdsName.text = advertisements[position].name

            holder.itemView.setOnClickListener {
                onAdvertisementClickListener?.onAdvertisementClick(advertisements[position])
            }
        }
    }

    fun setOnAdvertisementClickListener(listener: OnAdvertisementClickListener) {
        onAdvertisementClickListener = listener
    }

    interface OnAdvertisementClickListener {
        fun onAdvertisementClick(advertisement: Advertisement)
    }

}