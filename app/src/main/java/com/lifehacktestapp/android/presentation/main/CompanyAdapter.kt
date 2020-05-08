package com.lifehacktestapp.android.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lifehacktestapp.android.BuildConfig
import com.lifehacktestapp.android.databinding.ItemCompanyBinding
import com.lifehacktestapp.android.domain.Company
import com.lifehacktestapp.android.util.ImageUtil


class CompanyAdapter(
    val onItemSelectedListener: OnItemSelectedListener
) : RecyclerView.Adapter<CompanyAdapter.ViewHolder>() {

    private var companies: ArrayList<Company> = ArrayList()

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            ItemCompanyBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return companies.size
    }

    fun setCompanies(list: ArrayList<Company>) {
        companies = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(companies[position])
    }

    inner class ViewHolder(private val binding: ItemCompanyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(company: Company) {
            binding.item = company
            ImageUtil.displayImage(binding.root.context, BuildConfig.BASE_URL + company.img, binding.companyImage)
            binding.cardView.setOnClickListener {
                onItemSelectedListener.onItemSelected(binding.companyImage, company)
            }
        }
    }

}