package br.com.joaovq.lunarappcompose.data.articles.network.dto

import com.google.gson.annotations.SerializedName

data class Event(
    @SerializedName("event_id")
    val eventId: Int,
    val provider: String
)