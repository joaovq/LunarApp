package br.com.joaovq.lunarappcompose.data.network.dto

import com.google.gson.annotations.SerializedName

data class Launche(
    @SerializedName("launch_id")
    val launchId: String,
    val provider: String
)