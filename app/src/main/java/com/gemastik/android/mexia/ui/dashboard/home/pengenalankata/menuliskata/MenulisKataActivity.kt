package com.gemastik.android.mexia.ui.dashboard.home.pengenalankata.menuliskata

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.gemastik.android.mexia.R
import com.gemastik.android.mexia.model.ViewModelFactory
import com.gemastik.android.mexia.repository.remote.entity.Api5WordEntity
import com.gemastik.android.mexia.ui.dashboard.home.pengenalankata.LimaKataViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_menulis_kata.*
import java.util.*
import kotlin.collections.ArrayList

class MenulisKataActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mTTS : TextToSpeech
    private lateinit var viewModel: LimaKataViewModel
    private lateinit var factory: ViewModelFactory
    private var i = 0
    private lateinit var tempWords : ArrayList<Api5WordEntity>
    private var found = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menulis_kata)
        tempWords = ArrayList()

        factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[LimaKataViewModel::class.java]

        val dialog = ProgressDialog.show(this, "", "Please wait...", true)
        viewModel.getAllWord().observe(this, androidx.lifecycle.Observer { Words ->
            if (Words != null && Words.size > i) {
                tempWords.addAll(Words)
                Picasso.get().load(Words[i].image).fit().priority(Picasso.Priority.HIGH).into(imageSoal)
                menulis_kata.text = Words[i].name
            }
            dialog.dismiss()
        })

        mTTS = TextToSpeech(this) { status ->
            if (status != TextToSpeech.ERROR){
                mTTS.language = Locale("id", "ID")
            }
        }

        arrow_back.setOnClickListener(this)
        mic_textViewPengenalanKata.setOnClickListener(this)
        buttonCheck.setOnClickListener(this)
        buttonClear.setOnClickListener(this)
        lanjut.setOnClickListener(this)
        mic_kata.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.arrow_back -> onBackPressed()
            R.id.mic_textViewPengenalanKata -> {
                val toSpeech = textViewLatihanMenulisKata.text.toString()
                mTTS.speak(toSpeech, TextToSpeech.QUEUE_FLUSH, null)
            }
            R.id.mic_kata -> {
                val toSpeech = menulis_kata.text.toString()
                mTTS.speak(toSpeech, TextToSpeech.QUEUE_FLUSH, null)
            }
            R.id.buttonCheck -> {
                val result = writing_view.textRecognition(this)
                result_menulis_kata.text = result
                if (result.toLowerCase(Locale.ROOT) == tempWords[i].name.toLowerCase(Locale.ROOT)){
                    lanjut.setBackgroundResource(R.drawable.bg_lanjut_next)
                    val toSpeech = "Benar"
                    mTTS.speak(toSpeech, TextToSpeech.QUEUE_FLUSH, null)
                    i++
                    found = true
                }else{
                    mTTS.speak("Salah", TextToSpeech.QUEUE_FLUSH, null)
                }
            }
            R.id.lanjut -> {
                if (found){
                    writing_view.clear()
                    writing_view.invalidate()
                    Picasso.get().load(tempWords[i].image).into(imageSoal)
                    menulis_kata.text = tempWords[i].name
                    lanjut.setBackgroundResource(R.drawable.bg_lanjut)
                    result_menulis_kata.text = "..."
                    found = false
                }
            }
            R.id.buttonClear -> {
                writing_view.clear()
                writing_view.invalidate()
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