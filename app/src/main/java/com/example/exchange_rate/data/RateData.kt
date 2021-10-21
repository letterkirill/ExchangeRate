package com.example.exchange_rate.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.ColumnInfo.INTEGER
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "rate_table")
class RateData(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "date", typeAffinity = INTEGER)
    val date: Date,
    val valueRate: Float)