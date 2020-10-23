package com.gemastik.android.mexia.ui.dashboard.home.pengenalankata.membacakata

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.gemastik.android.mexia.R
import com.gemastik.android.mexia.model.ViewModelFactory
import com.gemastik.android.mexia.repository.remote.entity.Api5WordEntity
import com.gemastik.android.mexia.ui.dashboard.home.pengenalankata.LimaKataViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_membaca_kata.*
import java.util.*
import kotlin.collections.ArrayList

class MembacaKataActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mTTS : TextToSpeech
    private val REQUEST_CODE_SPEECH_INPUT = 100
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var viewModel: LimaKataViewModel
    private lateinit var factory: ViewModelFactory
    private var i = 0
    private lateinit var tempWords : ArrayList<Api5WordEntity>
    private var found = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_membaca_kata)
        tempWords = ArrayList()

        factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[LimaKataViewModel::class.java]

        val dialog = ProgressDialog.show(this, "", "Please wait...", true)
        viewModel.getAllWord().observe(this, androidx.lifecycle.Observer { Words ->
            if (Words != null && Words.size > i) {
                tempWords.addAll(Words)
                Picasso.get().load(Words[i].image).fit().priority(Picasso.Priority.HIGH).into(imageSoal)
                membaca_kata.text = Words[i].name
            }
            dialog.dismiss()
        })

        mTTS = TextToSpeech(this) { status ->
            if (status != TextToSpeech.ERROR){
                mTTS.language = Locale("id", "ID")
            }
        }

        arrow_back.setOnClickListener(this)
        mic_textViewMembacaKata.setOnClickListener(this)
        mic_textViewPengenalanKata.setOnClickListener(this)
        lanjut.setOnClickListener(this)
        rec.setOnClickListener(this)
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.arrow_back -> onBackPressed()
            R.id.mic_textViewMembacaKata -> {
                val toSpeech = instruksi_membaca_kata.text.toString()
                mTTS.speak(toSpeech, TextToSpeech.QUEUE_FLUSH, null)
            }
            R.id.mic_textViewPengenalanKata -> {
                val toSpeech = textViewLatihanMembacaKata.text.toString()
                mTTS.speak(toSpeech, TextToSpeech.QUEUE_FLUSH, null)
            }
            R.id.rec -> speak()
            R.id.lanjut -> {
                if (found){
                    mTTS.speak("", TextToSpeech.QUEUE_FLUSH, null)
                    membaca_kata.text = tempWords[i].name
                    Picasso.get().load(tempWords[i].image).into(imageSoal)
                    result_membaca_kata.text = ""
                    found = false
                }
            }
        }
    }

    private fun speak(){
        val mIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
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
                        result_membaca_kata.text = result[0]
                        if (result[0].toLowerCase(Locale.ROOT) == membaca_kata.text.toString().toLowerCase(Locale.ROOT)){
                            lanjut.setBackgroundResource(R.drawable.bg_lanjut_next)
                            mTTS.speak("Benar", TextToSpeech.QUEUE_FLUSH, null)
                            i++
                            found = true
                        }else{
                            mTTS.speak("Salah", TextToSpeech.QUEUE_FLUSH, null)
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