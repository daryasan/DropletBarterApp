package com.example.dropletbarterapp.ui.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.dropletbarterapp.databinding.QueryItemBinding
import com.example.dropletbarterapp.mainscreens.profile.dto.UserDataDto
import com.example.dropletbarterapp.models.Advertisement
import com.example.dropletbarterapp.models.Category
import com.example.dropletbarterapp.models.Query
import com.example.dropletbarterapp.ui.images.CircleCrop
import com.example.dropletbarterapp.ui.images.ImageUtils
import com.example.dropletbarterapp.ui.images.SquareCrop
import com.example.dropletbarterapp.ui.models.UICategory
import com.example.dropletbarterapp.utils.Dependencies
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException

class QueryAdapter :
    RecyclerView.Adapter<QueryAdapter.QueryViewHolder>() {

    var queries: MutableList<Query> = mutableListOf()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class QueryViewHolder(val binding: QueryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = QueryItemBinding.inflate(inflater, parent, false)
        return QueryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: QueryViewHolder,
        position: Int
    ) {
        val query = queries[position]
        val context = holder.itemView.context
        var user: UserDataDto
        val ads: Advertisement

        runBlocking {
            ads = findAdvertisement(query.adsId)
            user = findUser(query.userId)
        }


        with(holder.binding) {
            buttonAccept.setOnClickListener {
                permitPurchase(query, ads)
                queries.remove(query)
                notifyDataSetChanged()
            }

            buttonDecline.setOnClickListener {
                declinePurchase(query)
                queries.remove(query)
                notifyDataSetChanged()
            }

            buttonConnect.setOnClickListener {
                context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel: ${user.phone}")))
            }

            ImageUtils.loadImageBitmap(user.photo, context, imageViewSellerPhoto, CircleCrop())
            ImageUtils.loadImageBitmap(ads.photo, context, adsPhoto, SquareCrop())

            textViewSellerName.text = "${user.firstName} ${user.lastName}"


        }

    }

    override fun getItemCount(): Int {
        return queries.size
    }

    private suspend fun findUser(id: Long): UserDataDto {
        return Dependencies.userRepository.findUserById(
            Dependencies.tokenService.getAccessToken().toString(),
            id
        )
    }

    private suspend fun findAdvertisement(id: Long): Advertisement {
        return Dependencies.advertisementRepository.findAdvertisement(
            Dependencies.tokenService.getAccessToken().toString(),
            id
        )
    }

    private fun permitPurchase(query: Query, ads: Advertisement) {
        if (ads.category == UICategory.getPosByCategory(Category.SHARED_USAGE)) {
//            try {
            runBlocking {
                Dependencies.sharedUsageRepository.addToSharedUsage(
                    Dependencies.tokenService.getAccessToken().toString(),
                    userId = query.userId,
                    adsId = ads.id,
                    queryId = query.id
                )
                Dependencies.sharedUsageRepository.addToSharedUsage(
                    Dependencies.tokenService.getAccessToken().toString(),
                    userId = ads.ownerId,
                    adsId = ads.id,
                    queryId = query.id
                )
            }
//            } catch (e: HttpException) {
//                runBlocking {
//                    Dependencies.tokenService.refreshTokens()
//                    Dependencies.sharedUsageRepository.addToSharedUsage(
//                        Dependencies.tokenService.getAccessToken().toString(),
//                        userId = query.userId,
//                        adsId = ads.id,
//                        queryId = query.id
//                    )
//                    Dependencies.sharedUsageRepository.addToSharedUsage(
//                        Dependencies.tokenService.getAccessToken().toString(),
//                        userId = ads.ownerId,
//                        adsId = ads.id,
//                        queryId = query.id
//                    )
//                }
//            }
        }
        try {
            runBlocking {
                Dependencies.purchasesRepository.addToPurchases(
                    Dependencies.tokenService.getAccessToken().toString(),
                    userId = query.userId,
                    adsId = ads.id,
                    queryId = query.id
                )
            }
        } catch (e: HttpException) {
            runBlocking {
                Dependencies.tokenService.refreshTokens()
                Dependencies.purchasesRepository.addToPurchases(
                    Dependencies.tokenService.getAccessToken().toString(),
                    userId = query.userId,
                    adsId = ads.id,
                    queryId = query.id
                )
            }
        }
    }

    private fun declinePurchase(query: Query) {
        try {
            runBlocking {
                Dependencies.queryRepository.editQuery(
                    Dependencies.tokenService.getAccessToken().toString(),
                    query.id,
                    query.adsId,
                    query.userId,
                    1
                )
            }
        } catch (e: HttpException) {
            runBlocking {
                Dependencies.tokenService.refreshTokens()
                Dependencies.queryRepository.editQuery(
                    Dependencies.tokenService.getAccessToken().toString(),
                    query.id,
                    query.adsId,
                    query.userId,
                    1
                )
            }
        }
    }


}