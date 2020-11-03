package com.yurich.receipts.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DBImage(
    @PrimaryKey val uri: String
)