package com.juanferdev.appperrona.doglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.juanferdev.appperrona.Dog
import com.juanferdev.appperrona.R
import com.juanferdev.appperrona.databinding.ActivityDogListBinding

class DogListActivity : AppCompatActivity() {

    val adapter = DogAdapter()
    private lateinit var binding: ActivityDogListBinding
    private val dogListViewModel: DogListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDogListBinding.inflate(
            layoutInflater
        )

        setContentView(binding.root)
        initRecycler()
        dogListViewModel.dogList.observe(this){ listDog ->
            adapter.submitList(listDog)
        }
    }

    private fun initRecycler() {
        val recycler = binding.dogRecyclerView
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
    }
}