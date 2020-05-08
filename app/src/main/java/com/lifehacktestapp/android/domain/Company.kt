package com.lifehacktestapp.android.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Company(
    @field:PrimaryKey
    val id: String,
    val name: String,
    val img: String,
    val description: String? = null,
    val phone: String? = null,
    val www: String? = null,
    val lat: Float? = null,
    val lon: Float? = null
)