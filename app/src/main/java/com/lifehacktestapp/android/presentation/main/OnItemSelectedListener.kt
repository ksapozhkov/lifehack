package com.lifehacktestapp.android.presentation.main

import android.view.View
import com.lifehacktestapp.android.domain.Company

interface OnItemSelectedListener {

    fun onItemSelected(view1: View, company : Company)

}