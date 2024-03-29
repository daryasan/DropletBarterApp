package com.example.dropletbarterapp.ui.adapters

import android.graphics.drawable.Drawable
import android.media.Image
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.auth0.android.jwt.JWT
import com.bumptech.glide.Glide
import com.example.dropletbarterapp.R
import com.example.dropletbarterapp.databinding.AdvertisementItemBinding
import com.example.dropletbarterapp.models.Advertisement
import com.example.dropletbarterapp.ui.images.ImageUtils
import com.example.dropletbarterapp.ui.images.SquareCrop
import com.example.dropletbarterapp.utils.Dependencies
import kotlinx.coroutines.runBlocking


class AdvertisementsAdapter :
    RecyclerView.Adapter<AdvertisementsAdapter.AdvertisementViewHolder>() {

    private var onAdvertisementClickListener: OnAdvertisementClickListener? = null
    private lateinit var favourites: List<Advertisement>
    var advertisements: List<Advertisement> = mutableListOf()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class AdvertisementViewHolder(val binding: AdvertisementItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvertisementViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdvertisementItemBinding.inflate(inflater, parent, false)
        runBlocking {
            favourites = getFavourites()
        }
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
            if (favourites.any { it.id == ads.id }) {
                buttonFavourites.setImageResource(R.drawable.favourites_true)
            } else {
                buttonFavourites.setImageResource(R.drawable.favourites_false)
            }


            buttonFavourites.setOnClickListener {
                if (favourites.any { it.id == ads.id }) {
                    buttonFavourites.setImageResource(R.drawable.favourites_false)
                    runBlocking {
                        removeFromFavourites(ads)
                    }

                } else {
                    buttonFavourites.setImageResource(R.drawable.favourites_true)
                    runBlocking {
                        addToFavourites(ads)
                    }

                }
            }

            ImageUtils.loadImageBitmap(ads.photo, context, imageViewAds, SquareCrop())

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

    private suspend fun getFavourites(): List<Advertisement> {
        return Dependencies.favouritesRepository.findForUser(
            Dependencies.tokenService.getAccessToken().toString(),
            getUserId()
        )
    }

    private suspend fun addToFavourites(ads: Advertisement) {
        if (!favourites.any { it.id == ads.id }) {
            Dependencies.favouritesRepository.addToFavourites(
                Dependencies.tokenService.getAccessToken().toString(),
                getUserId(),
                ads.id
            )
            favourites = getFavourites()
            notifyDataSetChanged()
        }
    }

    private suspend fun removeFromFavourites(ads: Advertisement) {
        if (favourites.any { it.id == ads.id }) {
            Dependencies.favouritesRepository.deleteFromFavourites(
                Dependencies.tokenService.getAccessToken().toString(),
                getUserId(),
                ads.id
            )
            favourites = getFavourites()
            notifyDataSetChanged()
        }
    }

    private fun getUserId(): Long {
        val jwt = JWT(Dependencies.tokenService.getAccessToken().toString())
        return jwt.getClaim("id").asString()!!.toLong()
    }

}