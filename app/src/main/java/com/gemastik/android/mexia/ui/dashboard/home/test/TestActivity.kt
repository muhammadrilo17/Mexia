package com.gemastik.android.mexia.ui.dashboard.home.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gemastik.android.mexia.R
import kotlinx.android.synthetic.main.activity_pengenalan_huruf.*

class TestActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        arrow_back.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.arrow_back -> {
                onBackPressed()
            }
        }
    }
}