package com.juanferdev.appperrona.doglist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.databinding.ActivityDogListBinding
import com.juanferdev.appperrona.dogdetail.DogDetailActivity
import com.juanferdev.appperrona.dogdetail.DogDetailActivity.Companion.DOG_KEY
import com.juanferdev.appperrona.models.Dog

class DogListActivity : AppCompatActivity() {

    val adapter = DogAdapter()
    private lateinit var binding: ActivityDogListBinding
    private val dogListViewModel: DogListViewModel by viewModels()
    private val numberColumns = 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDogListBinding.inflate(
            layoutInflater
        )

        setContentView(binding.root)
        initRecycler()

        @Suppress("UNCHECKED_CAST")
        dogListViewModel.status.observe(this) { status ->
            when (status) {
                is ApiResponseStatus.Loading -> {
                    binding.loadingWheel.visibility = View.VISIBLE
                }

                is ApiResponseStatus.Error -> {
                    binding.loadingWheel.visibility = View.GONE
                    Toast.makeText(this, status.messageId, Toast.LENGTH_SHORT).show()
                }

                is ApiResponseStatus.Success -> {
                    adapter.submitList(status.data as List<Dog>)
                    binding.loadingWheel.visibility = View.GONE
                }
            }
        }
    }

    private fun initRecycler() {
        adapter.setOnItemClickListener { dog ->
            val intent = Intent(this, DogDetailActivity::class.java)
            intent.putExtra(DOG_KEY, dog)
            startActivity(intent)
        }

        adapter.setOnLongItemClickListener { dog ->
            dogListViewModel.addDogToUser(dog.id)
        }
        val recycler = binding.dogRecyclerView
        recycler.layoutManager = GridLayoutManager(this, numberColumns)
        recycler.adapter = adapter
    }
}