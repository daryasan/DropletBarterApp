package com.example.dropletbarterapp.ui.models

import org.aspectj.weaver.Position

object StatusConverter {

    fun gerPositionByEnum(status: StatusEnum): Int {
        return when (status) {
            StatusEnum.PENDING -> 0
            StatusEnum.DECLINED -> 1
            StatusEnum.ACCEPTED -> 2
        }
    }

    fun getStatusByPosition(position: Int): StatusEnum {
        return when (position) {
            0 -> StatusEnum.PENDING
            1 -> StatusEnum.DECLINED
            2 -> StatusEnum.ACCEPTED
            else -> {
                throw java.lang.IllegalArgumentException("No such status!")
            }
        }
    }

}