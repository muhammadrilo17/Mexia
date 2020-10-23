package com.gemastik.android.mexia.ui.dashboard.home.pengenalankata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import com.gemastik.android.mexia.R
import com.gemastik.android.mexia.ui.dashboard.home.pengenalankata.membacakata.MembacaKataActivity
import com.gemastik.android.mexia.ui.dashboard.home.pengenalankata.menuliskata.MenulisKataActivity
import kotlinx.android.synthetic.main.activity_membaca.*
import java.util.*

class MembacaActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mTTS : TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_membaca)

        mTTS = TextToSpeech(this.applicationContext) { status ->
            if (status != TextToSpeech.ERROR) {
                mTTS.language = Locale("id", "ID")
            }
        }

        m_membacakata.setOnClickListener(this)
        m_menuliskata.setOnClickListener(this)
        arrow_back.setOnClickListener(this)
        mic_membaca_head.setOnClickListener(this)
        mic_menu1.setOnClickListener(this)
        mic_menu2.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.arrow_back -> {
                onBackPressed()
            }
            R.id.m_membacakata -> {
                val intent = Intent(this, MembacaKataActivity::class.java)
                startActivity(intent)
            }
            R.id.m_menuliskata -> {
                val intent = Intent(this, MenulisKataActivity::class.java)
                startActivity(intent)
            }
            R.id.mic_membaca_head -> {
                val toSpeech = textViewArch.text.toString()
                mTTS.speak(toSpeech, TextToSpeech.QUEUE_FLUSH, null)
            }
            R.id.mic_menu2 -> {
                val toSpeech = menulis_kata.text.toString()
                mTTS.speak(toSpeech, TextToSpeech.QUEUE_FLUSH, null)
            }
            R.id.mic_menu1 -> {
                val toSpeech = membaca_kata.text.toString()
                mTTS.speak(toSpeech, TextToSpeech.QUEUE_FLUSH, null)
            }
        }
    }
}