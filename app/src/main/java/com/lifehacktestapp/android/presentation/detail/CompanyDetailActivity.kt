package com.lifehacktestapp.android.presentation.detail

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lifehacktestapp.android.BuildConfig
import com.lifehacktestapp.android.R
import com.lifehacktestapp.android.di.ViewModelFactory
import com.lifehacktestapp.android.domain.Company
import com.lifehacktestapp.android.util.ImageUtil
import kotlinx.android.synthetic.main.activity_company_detail.*

class CompanyDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CompanyDetailViewModel
    private val callRequestCode = 101
    private var mCompany: Company? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_detail)
        setSupportActionBar(toolbar);
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }
        val extras = intent.extras
        if (extras != null) {
            supportActionBar!!.title = extras.getString("company_name")
            ImageUtil.displayImage(
                this,
                BuildConfig.BASE_URL + extras.getString("company_img"),
                company_image
            )
            viewModel =
                ViewModelProvider(this, ViewModelFactory()).get(CompanyDetailViewModel::class.java)
            viewModel.isLoading.observe(this, Observer { isLoading ->
                is_loading.visibility = if (isLoading) View.VISIBLE else View.GONE
            })
            viewModel.message.observe(this, Observer { message ->
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            })
            viewModel.company.observe(this, Observer { item ->
                if (item != null) {
                    updateUI(item)
                }
            })
            viewModel.getCompanyById(extras.getString("company_id")!!)
        }
    }

    private fun updateUI(item: Company) {
        mCompany = item
        description.text = item.description
        if (item.phone != null && item.phone.isNotEmpty()) {
            fab.visibility = View.VISIBLE
            fab.setOnClickListener {
                checkAndCall(item.phone)
            }
        }
        if (item.lat == 0.0f || item.lon == 0.0f) {
            map_image.visibility = View.GONE
        } else {
            ImageUtil.displayImage(
                this,
                String.format(getString(R.string.map_url), item.lat, item.lon),
                map_image
            )
        }
        if (item.www != null && item.www.isNotEmpty()) {
            www.text = item.www
            layout_www.visibility = View.VISIBLE
            layout_www.setOnClickListener {
                var url = item.www
                if (!url.startsWith("https://") && !url.startsWith("http://")) {
                    url = "http://$url";
                }
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            callRequestCode -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkAndCall(mCompany!!.phone!!)
                }
            }
        }
    }

    private fun checkAndCall(phone: String) {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CALL_PHONE),
                callRequestCode
            )
            return
        }
        startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:$phone")))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}

