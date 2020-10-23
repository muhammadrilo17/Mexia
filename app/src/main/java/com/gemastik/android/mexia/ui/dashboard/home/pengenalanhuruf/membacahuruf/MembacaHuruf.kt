package com.gemastik.android.mexia.ui.dashboard.home.pengenalanhuruf.membacahuruf

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.gemastik.android.mexia.R
import com.gemastik.android.mexia.model.ViewModelFactory
import com.gemastik.android.mexia.ui.dashboard.home.pengenalanhuruf.AlphabetEntity
import com.gemastik.android.mexia.ui.dashboard.home.pengenalanhuruf.AlphabetViewModel
import kotlinx.android.synthetic.main.activity_membaca_huruf.*
import kotlinx.android.synthetic.main.activity_membaca_huruf.arrow_back
import kotlinx.android.synthetic.main.activity_membaca_huruf.lanjut
import kotlinx.android.synthetic.main.activity_membaca_huruf.mic_textViewPengenalanHuruf
import java.util.*

class MembacaHuruf : AppCompatActivity(), View.OnClickListener {
    private lateinit var speechRecognizer: SpeechRecognizer
    private val REQUEST_CODE_SPEECH_INPUT = 100
    private lateinit var mTTS : TextToSpeech
    private lateinit var viewModel : AlphabetViewModel
    private val factory: ViewModelFactory = ViewModelFactory.getInstance()
    private var found = false
    private lateinit var alphabet : List<AlphabetEntity>
    private var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_membaca_huruf)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this, factory)[AlphabetViewModel::class.java]

        alphabet = viewModel.getAlphabet()
        membaca_huruf.text = alphabet[i].alphabet

        mTTS = TextToSpeech(this) { status ->
            if (status != TextToSpeech.ERROR){
                mTTS.language = Locale("id", "ID")
            }
        }

        arrow_back.setOnClickListener(this)
        mic_textViewPengenalanHuruf.setOnClickListener(this)
        mic_textViewMembacaHuruf.setOnClickListener(this)
        rec.setOnClickListener(this)
        lanjut.setOnClickListener(this)
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.arrow_back -> {
                onBackPressed()
            }
            R.id.rec -> {
                speak()
            }
            R.id.mic_textViewPengenalanHuruf -> {
                val toSpeech = textViewLatihanMembacaHuruf.text.toString()
                mTTS.speak(toSpeech, TextToSpeech.QUEUE_FLUSH, null)
            }
            R.id.mic_textViewMembacaHuruf -> {
                val toSpeech = instruksi_membaca_huruf.text.toString()
                mTTS.speak(toSpeech, TextToSpeech.QUEUE_FLUSH, null)
            }
            R.id.lanjut -> {
                if (found) {
                    membaca_huruf.text = alphabet[i].alphabet
                    lanjut.setBackgroundResource(R.drawable.bg_lanjut)
                    result_membaca_huruf.text = "..."
                    mTTS.speak("", TextToSpeech.QUEUE_FLUSH, null)
                    found = false
                }
            }
        }
    }
    private fun speak() {
        val mIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"id")
        mIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Silahkan Jawab")

        try {
            startActivityForResult(mIntent, REQUEST_CODE_SPEECH_INPUT)
        }
        catch (e: Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_CODE_SPEECH_INPUT -> {
                if (resultCode == Activity.RESULT_OK && null != data){
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    if (result != null){
                        result_membaca_huruf.text = result[0].toUpperCase()
                        when (result_membaca_huruf.text) {
                            alphabet[i].alphabet -> {
                                lanjut.setBackgroundResource(R.drawable.bg_lanjut_next)
                                mTTS.speak("Benar, silahkan klik lanjut", TextToSpeech.QUEUE_FLUSH, null)
                                found = true
                                i++
                            }
                            else -> {
                                mTTS.speak("Salah", TextToSpeech.QUEUE_FLUSH, null)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mTTS.speak("", TextToSpeech.QUEUE_FLUSH, null)
        if(supportFragmentManager.backStackEntryCount == 0) {
            super.onBackPressed()
            finish()
        }
        else {
            supportFragmentManager.popBackStack()
            finish()
        }
    }
}