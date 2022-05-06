package com.example.fetchinterviewapplication.DataLayer.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ListEntity(
    @PrimaryKey var id: Int,
    @ColumnInfo(name = "listId") var listId: Int,
    @ColumnInfo(name = "name") var name: String?
)