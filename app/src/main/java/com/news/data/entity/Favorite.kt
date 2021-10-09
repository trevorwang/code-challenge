package com.news.data.entity

import androidx.room.*

@Entity(indices = [Index("news_id", unique = true)])
data class Favorite(@PrimaryKey(autoGenerate = true) val id: Int = 0, @Embedded(prefix = "news_") val news: News)