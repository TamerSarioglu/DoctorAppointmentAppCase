package com.example.doktorrandevuapp

import android.content.ContentValues.TAG
import android.icu.text.Transliterator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.core.view.isNotEmpty
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.example.doktorrandevuapp.adapter.DoctorsAdapter
import com.example.doktorrandevuapp.data.Doctor
import com.example.doktorrandevuapp.databinding.ActivityMainBinding
import com.example.doktorrandevuapp.network.RetrofitInstance
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var doctorAdapter: DoctorsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()

        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.api.getDoctors()
            } catch (e: IOException) {
                Log.e(TAG, "IOException, you might not have internet connection")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException, unexpected response")
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null) {
                doctorAdapter.doctors = response.body()!!.doctors
            } else {
                Log.e(TAG, "Response not successful")
            }

            /*Response Boş ve Null Dönerse RecyclerView görünmez olacak ve uyarı fotosu
            ve uyarı yazısı görünür olacak.*/
            if (doctorAdapter.doctors.isNullOrEmpty()) {
                binding.doctorsRecyclerView.isVisible = false
                binding.imageViewKullaniciBulunamadi.isVisible = true
                binding.textViewKullaniciBulunamadi.isVisible = true

                binding.imageViewKullaniciBulunamadi.load(R.drawable.ic_baseline_person_24) {
                    transformations(CircleCropTransformation())
                }
            }
        }
    }

    private fun setUpRecyclerView() = binding.doctorsRecyclerView.apply {
        doctorAdapter = DoctorsAdapter()
        adapter = doctorAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
        addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }
}