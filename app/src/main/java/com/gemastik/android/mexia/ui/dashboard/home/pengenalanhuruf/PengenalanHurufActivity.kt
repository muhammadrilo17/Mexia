package com.gemastik.android.mexia.ui.dashboard.home.pengenalanhuruf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import com.gemastik.android.mexia.R
import com.gemastik.android.mexia.ui.dashboard.home.pengenalanhuruf.membacahuruf.MembacaHuruf
import com.gemastik.android.mexia.ui.dashboard.home.pengenalanhuruf.menulishuruf.MenulisHuruf
import kotlinx.android.synthetic.main.activity_pengenalan_huruf.*
import kotlinx.android.synthetic.main.activity_pengenalan_huruf.arrow_back
import java.util.*

class PengenalanHurufActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mTTS : TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengenalan_huruf)

        arrow_back.setOnClickListener(this)
        ph_menulis.setOnClickListener(this)
        ph_membaca.setOnClickListener(this)
        mic_menu1.setOnClickListener(this)
        mic_menu2.setOnClickListener(this)
        mic_textViewPengenalanHuruf.setOnClickListener(this)

        mTTS = TextToSpeech(this) { status ->
            if (status != TextToSpeech.ERROR) {
                mTTS.language = Locale("id", "ID")
            }
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.arrow_back -> {
                onBackPressed()
                mTTS.speak("", TextToSpeech.QUEUE_FLUSH, null)
            }
            R.id.ph_menulis -> {
                val intent = Intent(this, MenulisHuruf::class.java)
                mTTS.speak("", TextToSpeech.QUEUE_FLUSH, null)
                startActivity(intent)
            }
            R.id.ph_membaca -> {
                val intent = Intent(this, MembacaHuruf::class.java)
                mTTS.speak("", TextToSpeech.QUEUE_FLUSH, null)
                startActivity(intent)
            }
            R.id.mic_textViewPengenalanHuruf -> {
                val toSpeech = textViewArch.text.toString()
                mTTS.speak(toSpeech, TextToSpeech.QUEUE_FLUSH, null)
            }
            R.id.mic_menu1 -> {
                val toSpeech = menulis_huruf.text.toString()
                mTTS.speak(toSpeech, TextToSpeech.QUEUE_FLUSH, null)
            }
            R.id.mic_menu2 -> {
                val toSpeech = membaca.text.toString()
                mTTS.speak(toSpeech, TextToSpeech.QUEUE_FLUSH, null)
            }
        }
    }
}