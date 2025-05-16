package br.com.joaovq.article_data.network.dto

import com.google.gson.annotations.SerializedName

data class LaunchDto(
    @SerializedName("launch_id")
    val launchId: String,
    val provider: String
)