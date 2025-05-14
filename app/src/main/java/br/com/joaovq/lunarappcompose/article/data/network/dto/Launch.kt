package br.com.joaovq.lunarappcompose.article.data.network.dto

import com.google.gson.annotations.SerializedName

data class Launch(
    @SerializedName("launch_id")
    val launchId: String,
    val provider: String
)