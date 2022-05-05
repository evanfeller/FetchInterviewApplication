package com.example.fetchinterviewapplication.DataLayer.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ListEntity(
    @PrimaryKey var id: Int,
    @ColumnInfo(name = "listID") var listID: Int,
    @ColumnInfo(name = "name") var name: String?
)