package com.example.fetchinterviewapplication.View

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fetchinterviewapplication.DataLayer.Room.AppDatabase
import com.example.fetchinterviewapplication.DataLayer.Room.ListEntity
import com.example.fetchinterviewapplication.databinding.ListFragmentBinding

class ListViewModel : ViewModel() {

    fun getSortedItems(context: Context, listId: Int): LiveData<List<ListEntity>> {
        val db = AppDatabase(context)
        return db.listDao().getByListID(listId)
    }
}

class ListFragment(var listId: Int) : Fragment() {

    private var _binding: ListFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListViewModel = ListViewModel()

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
            viewModel.getSortedItems(it, listId).observe(viewLifecycleOwner, Observer { newData ->
                recyclerAdapter.updateItemList(newData)
            })
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapter
        }
    }
}