package com.gemastik.android.mexia.ui.dashboard.home.pengenalanhuruf.menulishuruf

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.gemastik.android.mexia.R
import com.gemastik.android.mexia.model.ViewModelFactory
import com.gemastik.android.mexia.ui.dashboard.home.pengenalanhuruf.AlphabetEntity
import com.gemastik.android.mexia.ui.dashboard.home.pengenalanhuruf.AlphabetViewModel
import kotlinx.android.synthetic.main.activity_menulis_huruf.*
import kotlinx.android.synthetic.main.activity_pengenalan_huruf.arrow_back
import kotlinx.android.synthetic.main.activity_pengenalan_huruf.mic_textViewPengenalanHuruf
import java.util.*

class MenulisHuruf : AppCompatActivity(), View.OnClickListener {
    private lateinit var mTTS : TextToSpeech
    private val factory: ViewModelFactory = ViewModelFactory.getInstance()
    private lateinit var viewModel : AlphabetViewModel
    private lateinit var alphabet : List<AlphabetEntity>
    private var found = false
    private var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menulis_huruf)

        viewModel = ViewModelProvider(this, factory)[AlphabetViewModel::class.java]

        alphabet = viewModel.getAlphabet()
        instruction_view.text = alphabet[i].alphabet

        mTTS = TextToSpeech(this) { status ->
            if (status != TextToSpeech.ERROR){
                mTTS.language = Locale("id", "ID")
            }
        }

        arrow_back.setOnClickListener(this)
        mic_textViewPengenalanHuruf.setOnClickListener(this)
        mic_huruf.setOnClickListener(this)
        buttonClear.setOnClickListener(this)
        buttonCheck.setOnClickListener(this)
        lanjut.setOnClickListener(this)
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
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.arrow_back -> {
                onBackPressed()
            }
            R.id.mic_huruf -> {
                val toSpeech = instruction_view.text.toString()
                mTTS.speak(toSpeech, TextToSpeech.QUEUE_FLUSH, null)
            }
            R.id.mic_textViewPengenalanHuruf -> {
                val toSpeech = textViewLatihanMenulisHuruf.text.toString()
                mTTS.speak(toSpeech, TextToSpeech.QUEUE_FLUSH, null)
            }
            R.id.buttonClear -> {
                writing_view.clear()
                writing_view.invalidate()
            }
            R.id.buttonCheck -> {
                if (writing_view.textRecognition(this) != null) result_menulis_huruf.text = writing_view.textRecognition(this)
                else result_menulis_huruf.text = "..."
                when {
                    writing_view.textRecognition(this) == alphabet[i].alphabet -> {
                        lanjut.setBackgroundResource(R.drawable.bg_lanjut_next)
                        mTTS.speak("Benar, silahkan klik lanjut", TextToSpeech.QUEUE_FLUSH, null)
                        found = true
                        i++
                    }
                    writing_view.textRecognition(this) == alphabet[i].alphabet.toLowerCase(Locale.ROOT) -> {
                        mTTS.speak("Hampir benar", TextToSpeech.QUEUE_FLUSH, null)
                    }
                    else -> {
                        mTTS.speak("Salah", TextToSpeech.QUEUE_FLUSH, null)
                    }
                }
            }
            R.id.lanjut -> {
                if (found) {
                    instruction_view.text = alphabet[i].alphabet
                    lanjut.setBackgroundResource(R.drawable.bg_lanjut)
                    result_menulis_huruf.text = "..."
                    mTTS.speak("", TextToSpeech.QUEUE_FLUSH, null)
                    writing_view.clear()
                    writing_view.invalidate()
                    found = false
                }
            }
        }
    }
}