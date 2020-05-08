package com.lifehacktestapp.android.presentation.main

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lifehacktestapp.android.R
import com.lifehacktestapp.android.di.ViewModelFactory
import com.lifehacktestapp.android.domain.Company
import com.lifehacktestapp.android.presentation.detail.CompanyDetailActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnItemSelectedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var companyAdapter: CompanyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this, ViewModelFactory()).get(MainViewModel::class.java)
        companyAdapter = CompanyAdapter(this)
        recycler_view.adapter = companyAdapter
        viewModel.isLoading.observe(this, Observer { isLoading ->
            is_loading.visibility = if (isLoading) View.VISIBLE else View.GONE
        })
        viewModel.message.observe(this, Observer { message ->
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        })
        viewModel.companies.observe(this, Observer { items ->
            companyAdapter.setCompanies(items as ArrayList<Company>)
            empty_text.visibility = if (items.isNotEmpty()) View.GONE else View.VISIBLE
        })
    }

    override fun onItemSelected(view1: View, company: Company) {
        val intent = Intent(this, CompanyDetailActivity::class.java)
        intent.putExtra("company_id", company.id);
        intent.putExtra("company_img", company.img);
        intent.putExtra("company_name", company.name);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptions
                .makeSceneTransitionAnimation(this, view1, "image")
            startActivity(intent, options.toBundle())
        } else {
            startActivity(intent)
        }
    }

}