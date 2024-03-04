package com.juanferdev.appperrona.doglist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.juanferdev.appperrona.api.ApiResponseStatus
import com.juanferdev.appperrona.databinding.ActivityDogListBinding
import com.juanferdev.appperrona.dogdetail.DogDetailActivity
import com.juanferdev.appperrona.dogdetail.DogDetailActivity.Companion.DOG_KEY

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
        dogListViewModel.dogList.observe(this) { listDog ->
            adapter.submitList(listDog)
        }
        dogListViewModel.status.observe(this) { status ->
            when (status) {
                ApiResponseStatus.LOADING -> {
                    binding.loadingWheel.visibility = View.VISIBLE
                }

                ApiResponseStatus.ERROR -> {
                    binding.loadingWheel.visibility = View.GONE
                    Toast.makeText(this, "Error downloading data", Toast.LENGTH_SHORT).show()
                }

                ApiResponseStatus.SUCCESS -> {
                    binding.loadingWheel.visibility = View.GONE
                }

                else -> {
                    binding.loadingWheel.visibility = View.GONE
                    Toast.makeText(
                        this,
                        "Error downloading data, unknown status",
                        Toast.LENGTH_SHORT
                    ).show()
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
        val recycler = binding.dogRecyclerView
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
    }
}