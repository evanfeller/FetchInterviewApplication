package com.example.fetchinterviewapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchinterviewapplication.DataLayer.Model
import com.example.fetchinterviewapplication.DataLayer.RetrofitService
import com.example.fetchinterviewapplication.DataLayer.Room.AppDatabase
import com.example.fetchinterviewapplication.DataLayer.Room.ListEntity
import com.example.fetchinterviewapplication.View.ListFragment
import com.example.fetchinterviewapplication.View.ListViewModel
import com.example.fetchinterviewapplication.databinding.ActivityMainBinding
import com.example.fetchinterviewapplication.databinding.ListFragmentBinding
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel : ViewModel() {

    var db: AppDatabase? = null

    fun setUpRoom(context: Context) {
        db = AppDatabase(context)
    }

    private fun intializeDB(entities: ArrayList<ListEntity>) {
        GlobalScope.launch {
            db?.listDao()?.insertAll(entities)
        }
    }

    fun getData() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitService.create().getData()
            response.enqueue(object : Callback<List<Model>?> {
                override fun onResponse(
                    call: Call<List<Model>?>,
                    response: Response<List<Model>?>
                ) {
                    val items = response.body()
                    val entities = items?.mapNotNull {
                        if (!it.name.isNullOrEmpty()) {
                            ListEntity(it.id, it.listId, it.name)
                        } else {
                            null
                        }
                    }
                    entities?.let { intializeDB(ArrayList(it)) }
                }

                override fun onFailure(call: Call<List<Model>?>, t: Throwable) {
                    Log.e("RETROFIT_ERROR", t.toString())
                }
            })
        }
    }
}


class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel = MainActivityViewModel()

    var tabLayout: TabLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setUpRoom(this)
        viewModel.getData()
        setContentView(R.layout.activity_main)
        tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> launchListFragment(1)
                    1 -> launchListFragment(2)
                    2 -> launchListFragment(3)
                    3 -> launchListFragment(4)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        launchListFragment(1)
    }

    private fun launchListFragment(listId: Int) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.root_container, ListFragment(listId))
            .commitAllowingStateLoss()
    }

}