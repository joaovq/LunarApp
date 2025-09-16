package br.com.joaovq.data.network.dto

import com.google.gson.annotations.SerializedName

data class InfoDto(
    val version: String?,
    @SerializedName("news_sites")
    val newsSites: List<String>
)
