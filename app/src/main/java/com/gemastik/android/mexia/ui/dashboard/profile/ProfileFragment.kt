package com.gemastik.android.mexia.ui.dashboard.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.submission5.sharedpreferences.SharedPreference
import com.gemastik.android.mexia.R
import com.gemastik.android.mexia.model.ViewModelFactory
import com.gemastik.android.mexia.ui.dashboard.profile.ubah.UbahProfileActivity
import com.gemastik.android.mexia.ui.login.LoginActivity
import com.gemastik.android.mexia.ui.login.LoginActivity.Companion.ADDRESS
import com.gemastik.android.mexia.ui.login.LoginActivity.Companion.EMAIL
import com.gemastik.android.mexia.ui.login.LoginActivity.Companion.ID
import com.gemastik.android.mexia.ui.login.LoginActivity.Companion.LOGIN
import com.gemastik.android.mexia.ui.login.LoginActivity.Companion.PASS
import com.gemastik.android.mexia.ui.login.LoginActivity.Companion.PHONE
import com.gemastik.android.mexia.ui.login.LoginActivity.Companion.USERNAME
import com.gemastik.android.mexia.ui.login.LoginViewModel
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment(), View.OnClickListener {
    private lateinit var sharedPreference: SharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sharedPreference = SharedPreference(layoutInflater.context)

        name_profile.text = sharedPreference.getValueString(USERNAME, "")
        email_profile.text = sharedPreference.getValueString(EMAIL, "")
        address_profile.text = sharedPreference.getValueString(ADDRESS, "")
        phone_profile.text = sharedPreference.getValueString(PHONE, "")


        logout_profile.setOnClickListener(this)
        ubah_profile.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.logout_profile -> {
                sharedPreference.setValueBool(LOGIN, false)
                sharedPreference.setValueString(EMAIL, "")
                sharedPreference.setValueString(PASS, "")
                startActivity(Intent(context, LoginActivity::class.java))
            }
            R.id.ubah_profile -> {
                startActivity(Intent(context, UbahProfileActivity::class.java))
            }
        }
    }
}