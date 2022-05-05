package com.example.fetchinterviewapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.fetchinterviewapplication.DataLayer.Model
import com.example.fetchinterviewapplication.DataLayer.RetrofitService
import com.example.fetchinterviewapplication.DataLayer.Room.AppDatabase
import com.example.fetchinterviewapplication.DataLayer.Room.ListEntity
import com.example.fetchinterviewapplication.View.ListFragment
import com.example.fetchinterviewapplication.View.ListViewModel
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
        setContentView(R.layout.activity_main)
        launchListFragment()
    }

    private fun launchListFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.root_container, ListFragment() )
            .commitAllowingStateLoss()
    }


    private fun intializeDB(entities: ArrayList<ListEntity>) {
        val db = AppDatabase(this)

        GlobalScope.launch {
            db.listDao().insertAll(entities)
        }
    }

    private fun getData() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitService.create().getData()
            response.enqueue(object : Callback<List<Model>?> {
                override fun onResponse(
                    call: Call<List<Model>?>,
                    response: Response<List<Model>?>
                ) {
                    val items = response.body()
                    val entities = items?.map { ListEntity(it.id, it.listID, it.name) }
                    entities?.let { intializeDB(ArrayList(it)) }
                }

                override fun onFailure(call: Call<List<Model>?>, t: Throwable) {
                    Log.e("RETROFIT_ERROR", t.toString())
                }
            })
        }
    }
}