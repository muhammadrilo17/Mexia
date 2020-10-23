package com.gemastik.android.mexia.ui.dashboard.home

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.submission5.sharedpreferences.SharedPreference
import com.gemastik.android.mexia.R
import com.gemastik.android.mexia.ui.dashboard.home.pengenalankata.MembacaActivity
import com.gemastik.android.mexia.ui.dashboard.home.pengenalanhuruf.PengenalanHurufActivity
import com.gemastik.android.mexia.ui.dashboard.home.puzzle.PuzzleActivity
import com.gemastik.android.mexia.ui.dashboard.home.test.TestActivity
import com.gemastik.android.mexia.ui.login.LoginActivity.Companion.ID
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*


class HomeFragment : Fragment(), View.OnClickListener {
    private lateinit var mTTS: TextToSpeech

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mTTS = TextToSpeech(context?.applicationContext) { status ->
            if (status != TextToSpeech.ERROR) {
                mTTS.language = Locale("id", "ID")
            }
        }

        mic_menu1.setOnClickListener(this)
        mic_menu2.setOnClickListener(this)
        mic_menu3.setOnClickListener(this)
        mic_menu4.setOnClickListener(this)
        mic_headHome.setOnClickListener(this)


        home_menu1.setOnClickListener(this)
        home_menu2.setOnClickListener(this)
        home_menu3.setOnClickListener(this)
        home_menu4.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.home_menu1 -> {
                startActivity(Intent(context, PengenalanHurufActivity::class.java))
            }
            R.id.home_menu2 -> {
                startActivity(Intent(context, MembacaActivity::class.java))
            }
            R.id.home_menu3 -> {
                startActivity(Intent(context, PuzzleActivity::class.java))
            }
            R.id.home_menu4 -> {
                startActivity(Intent(context, TestActivity::class.java))
            }
            R.id.mic_menu1 -> {
                val toSpeech = pengenalan_huruf.text.toString()
                mTTS.speak(toSpeech, TextToSpeech.QUEUE_FLUSH, null)
            }
            R.id.mic_menu2 -> {
                val toSpeech = pengenalan_kata.text.toString()
                mTTS.speak(toSpeech, TextToSpeech.QUEUE_FLUSH, null)
            }
            R.id.mic_menu3 -> {
                val toSpeech = puzzle.text.toString()
                mTTS.speak(toSpeech, TextToSpeech.QUEUE_FLUSH, null)
            }
            R.id.mic_menu4 -> {
                val toSpeech = test.text.toString()
                mTTS.speak(toSpeech, TextToSpeech.QUEUE_FLUSH, null)
            }
            R.id.mic_headHome -> {
                val toSpeech = textViewHome.text.toString()
                mTTS.speak(toSpeech, TextToSpeech.QUEUE_FLUSH, null)
            }
        }
    }
}