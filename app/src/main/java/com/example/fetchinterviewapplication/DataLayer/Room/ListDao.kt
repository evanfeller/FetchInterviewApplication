package com.example.fetchinterviewapplication.DataLayer.Room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ListDao {
    @Query("SELECT * FROM listentity")
    fun getAll(): LiveData<List<ListEntity>>

    @Query("SELECT * FROM listentity ORDER BY listId, name")
    fun getOrderedList(): LiveData<List<ListEntity>>

    @Query("SELECT * FROM listentity WHERE listId LIKE :listId ORDER BY name")
    fun getByListID(listId: Int): LiveData<List<ListEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( list: List<ListEntity>)

    @Delete
    fun delete(list: ListEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateList( list: ListEntity)
}