package com.example.doktorrandevuapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.doktorrandevuapp.DoctorDetayActivity
import com.example.doktorrandevuapp.data.Doctor
import com.example.doktorrandevuapp.databinding.DoctorListItemBinding

class DoctorsAdapter : RecyclerView.Adapter<DoctorsAdapter.DoctorsViewHolder>() {

    inner class DoctorsViewHolder(val binding: DoctorListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallBack = object : DiffUtil.ItemCallback<Doctor>() {
        override fun areItemsTheSame(oldItem: Doctor, newItem: Doctor): Boolean {
            return oldItem.full_name == newItem.full_name
        }

        override fun areContentsTheSame(oldItem: Doctor, newItem: Doctor): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    var doctors: List<Doctor>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorsViewHolder {
        return DoctorsViewHolder(
            DoctorListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DoctorsViewHolder, position: Int) {

        val context = holder.itemView.context

        holder.binding.apply {
            val doctor = doctors[position]
            textViewDoctorFullName.text = doctor.full_name
            textViewUserStatus.text = doctor.user_status
            textViewGender.text = doctor.gender
            imageViewDoctor.load(doctor.image.url){
                transformations(CircleCropTransformation())
            }

            holder.binding.doctorItemListConstraintLayout.setOnClickListener {

                val doctorName = doctor.full_name
                val doctorStatus = doctor.user_status
                val doctorPicture = doctor.image.url


                //Toast.makeText(context,"DoctorName: $doctorName \n DoctorStatus: $doctorStatus",Toast.LENGTH_SHORT).show()

                val intent = Intent(context,DoctorDetayActivity::class.java)
                intent.putExtra("name",doctorName)
                intent.putExtra("status",doctorStatus)
                intent.putExtra("doctorImage",doctorPicture)
                context.startActivity(intent)
            }




        }
    }

    override fun getItemCount() = doctors.size

}
