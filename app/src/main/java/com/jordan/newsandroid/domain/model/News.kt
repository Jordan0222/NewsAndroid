package com.jordan.newsandroid.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = "articles"
)
@Parcelize
data class News(
    val description: String?,
    val title: String?,
    val url: String?,
    val urlToImage: String?,
    @PrimaryKey val id: Int? = null
): Parcelable