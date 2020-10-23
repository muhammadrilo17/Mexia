package com.gemastik.android.mexia.ui.login

import android.Manifest
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.submission5.sharedpreferences.SharedPreference
import com.gemastik.android.mexia.MainActivity
import com.gemastik.android.mexia.R
import com.gemastik.android.mexia.model.ViewModelFactory
import com.gemastik.android.mexia.ui.login.forgetkey.ForgetPasswordActivity
import com.gemastik.android.mexia.ui.login.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    companion object{
        const val LOGIN = "login"
        const val ID = "id"
        const val PASS = "pass"
        const val USERNAME = "username"
        const val EMAIL = "email"
        const val PHONE = "phone"
        const val ADDRESS = "address"
        const val TIME_LEARNING = "time"
        const val XP = "xp"
        const val PHOTO = "photo"
    }

    private lateinit var viewModel: LoginViewModel
    private val factory = ViewModelFactory.getInstance()
    private lateinit var sharedPreference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]
        sharedPreference = SharedPreference(this)
        if (sharedPreference.getValueBool(LOGIN, false)){
            val email = sharedPreference.getValueString(EMAIL, "")
            val pass = sharedPreference.getValueString(PASS, "")
            val dialog = ProgressDialog.show(this, "", "Please wait...", true)
            viewModel.getUserByEmail(email).observe(this, Observer { user ->
                    if (user != null && user.password == pass) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    dialog.dismiss()
                })
        }
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1001
        )
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            1002
        )

        btnLogin.setOnClickListener(this)
        btnRegister.setOnClickListener(this)
        forgetPassword.setOnClickListener(this)
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Apakah anda ingin keluar?")

        builder.setPositiveButton(android.R.string.yes) { _, _ ->
            this.finishAffinity()
        }

        builder.setNegativeButton(android.R.string.no) { _, _ ->

        }
        builder.show()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnLogin -> {
                if (email_input.text.isEmpty()) {
                    email_input.error = "Please input"
                }
                if (password_input.text.isEmpty()) {
                    password_input.error = "Please input"
                }
                if (email_input.text.isNotEmpty() && password_input.text.isNotEmpty()) {
                    val dialog = ProgressDialog.show(this, "", "Please wait...", true)
                    viewModel.getUserByEmail(email_input.text.toString())
                        .observe(this, Observer { user ->
                            if (user != null && user.password == password_input.text.toString()) {
                                val intent = Intent(this, MainActivity::class.java)
                                sharedPreference.setValueBool(LOGIN, true)
                                sharedPreference.setValueString(ID, user.id)
                                sharedPreference.setValueString(USERNAME, user.username)
                                sharedPreference.setValueString(PASS, user.password)
                                sharedPreference.setValueString(EMAIL, user.email)
                                sharedPreference.setValueString(PHONE, user.phoneNumber)
                                sharedPreference.setValueString(ADDRESS, user.address)
                                sharedPreference.setValueString(PHOTO, user.pathImage)
                                sharedPreference.setValueString(XP, user.totalXp)
                                sharedPreference.setValueString(TIME_LEARNING, user.timeLearning)
                                startActivity(intent)
                            } else if (user.email != email_input.text.toString()) {
                                Toast.makeText(this, "Email tidak terdaftar", Toast.LENGTH_SHORT)
                                    .show()
                            }else {
                                Toast.makeText(this, "Password Salah", Toast.LENGTH_SHORT).show()
                            }
                            dialog.dismiss()
                        })
                }

            }
            R.id.btnRegister -> startActivity(Intent(this, RegisterActivity::class.java))
            R.id.forgetPassword -> startActivity(Intent(this, ForgetPasswordActivity::class.java))
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1001 -> {
                Log.i(ContentValues.TAG, "Received response for SDCARD permission request.")

                if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(
                        ContentValues.TAG,
                        "SDCARD permission has now been granted. Showing preview."
                    )
                } else {
                    Log.i(ContentValues.TAG, "SDCARD permission was NOT granted.")
                }
            }
            1002 -> {
                Log.i(ContentValues.TAG, "Received response for SDCARD permission request.")
                if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(
                        ContentValues.TAG,
                        "SDCARD permission has now been granted. Showing preview."
                    )
                } else {
                    Log.i(ContentValues.TAG, "SDCARD permission was NOT granted.")
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}