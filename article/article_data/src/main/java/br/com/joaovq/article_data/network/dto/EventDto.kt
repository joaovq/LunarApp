package br.com.joaovq.article_data.network.dto

import com.google.gson.annotations.SerializedName

data class EventDto(
    @SerializedName("event_id")
    val eventId: Int,
    val provider: String
)