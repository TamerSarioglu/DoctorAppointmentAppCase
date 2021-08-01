package com.example.doktorrandevuapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import coil.load
import coil.transform.CircleCropTransformation
import com.example.doktorrandevuapp.databinding.ActivityDoctorDetayBinding
import com.example.doktorrandevuapp.network.OdemeEkrani

class DoctorDetayActivity : AppCompatActivity() {

    private lateinit var bindingDoctorDetay: ActivityDoctorDetayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingDoctorDetay = ActivityDoctorDetayBinding.inflate(layoutInflater)
        setContentView(bindingDoctorDetay.root)

        val doctorName = intent.getStringExtra("name")
        val doctorStatus = intent.getStringExtra("status")
        val doctorPicture = intent.getStringExtra("doctorImage")

        bindingDoctorDetay.textViewDoctorName.text = doctorName
        bindingDoctorDetay.textViewDoctorStatus.text = doctorStatus

        bindingDoctorDetay.imageViewDoctorDetailPicture.load(doctorPicture) {
            transformations(CircleCropTransformation())
        }

        if (doctorStatus.equals("free")) {
            bindingDoctorDetay.textViewDoctorStatus.isInvisible = true
            bindingDoctorDetay.textViewRandevuAl.text = "Premium Paket Al"
        }

        bindingDoctorDetay.textViewRandevuAl.setOnClickListener {
            if (bindingDoctorDetay.textViewDoctorStatus.isVisible){
                val intent = Intent(this,RandevuEkrani::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this,OdemeEkrani::class.java)
                startActivity(intent)
            }
        }
    }
}