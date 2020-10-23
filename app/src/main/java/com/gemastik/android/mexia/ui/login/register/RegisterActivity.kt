package com.gemastik.android.mexia.ui.login.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.gemastik.android.mexia.MainActivity
import com.gemastik.android.mexia.R
import com.gemastik.android.mexia.model.ViewModelFactory
import com.gemastik.android.mexia.repository.remote.entity.ApiUserEntity
import com.gemastik.android.mexia.ui.login.LoginActivity
import com.gemastik.android.mexia.ui.login.LoginViewModel
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var viewModel: LoginViewModel
    private val factory = ViewModelFactory.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]


        btnLogin.setOnClickListener(this)
        btnRegister.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnLogin -> {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            R.id.btnRegister -> {
                if (email_input.text.isEmpty()){
                    email_input.error = "Please input"
                }
                if (password_input.text.isEmpty()){
                    password_input.error = "Please input"
                }
                if (password_reinput.text.isEmpty()){
                    Toast.makeText(this, email_input.text.toString(), Toast.LENGTH_SHORT).show()
                    password_reinput.error = "Please input"
                }
                if (password_input.text.toString() == password_reinput.text.toString()) {
                    viewModel.getUserByEmail(email_input.text.toString()).observe(this, Observer { user ->
                            val temp = ApiUserEntity("","", email_input.text.toString(), password_input.text.toString())
                            if (user!= null && user.email == email_input.text.toString()) {
                                Toast.makeText(this, "Email telah terdaftar, ${user.email} && ${password_reinput.text}", Toast.LENGTH_SHORT).show()
                            }
                            else {
                                viewModel.registerUser(temp)
                                startActivity(Intent(this, MainActivity::class.java))
                            }
                        })
                }else{
                    Toast.makeText(this, "Password tidak sama", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}