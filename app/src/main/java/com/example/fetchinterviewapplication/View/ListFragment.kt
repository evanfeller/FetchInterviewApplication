package com.example.fetchinterviewapplication.View

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchinterviewapplication.DataLayer.Room.AppDatabase
import com.example.fetchinterviewapplication.DataLayer.Room.ListEntity
import com.example.fetchinterviewapplication.R
import com.example.fetchinterviewapplication.databinding.ListFragmentBinding

class ListViewModel : ViewModel() {
    val liveData = MutableLiveData<List<ListEntity>>()
    var myLiveData : LiveData<List<ListEntity>> = liveData

    fun updateInfo(newData: List<ListEntity>){
        liveData.value = newData
    }

    fun getAllItems(context: Context): LiveData<List<ListEntity>> {
        val db = AppDatabase(context)
        return db.listDao().getAll()
    }
}

class ListFragment : Fragment() {

    private var _binding: ListFragmentBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: ListViewModel = ListViewModel()

    private var layoutManager: RecyclerView.LayoutManager? = null
//    private var adapter: RecyclerView.Adapter<RecyclerAdapter.View>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerAdapter = RecyclerAdapter()
        context?.let {
            viewModel.getAllItems(it).observe(viewLifecycleOwner, Observer { newData ->
                recyclerAdapter.updateItemList(newData)
            })
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = recyclerAdapter
        }
    }
}