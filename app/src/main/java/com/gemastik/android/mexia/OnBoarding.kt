package com.gemastik.android.mexia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.submission5.sharedpreferences.SharedPreference
import com.gemastik.android.mexia.adapter.OnBoardingViewPagerAdapter
import com.gemastik.android.mexia.model.OnBoardingData
import com.gemastik.android.mexia.ui.login.LoginActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import java.lang.StringBuilder

class OnBoarding : AppCompatActivity() {
    companion object{
        private const val SLIDE_INTRO_SAVE = "save"
    }
    var onBoardingViewPagerAdapter: OnBoardingViewPagerAdapter? = null
    var tabLayout: TabLayout? = null
    var onBoardingViewPager: ViewPager? = null
    var buttonNext: Button? = null
    var position = 0
    private lateinit var sharedPreferences: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        sharedPreferences = SharedPreference(this)

        if (sharedPreferences.getValueBool(SLIDE_INTRO_SAVE, false)){
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }

        tabLayout = findViewById(R.id.tab_indicator)
        buttonNext = findViewById(R.id.btnNext)

        val onBoardingData: MutableList<OnBoardingData> = ArrayList()
        onBoardingData.add(
            OnBoardingData(
                "Mexia",
                "Pengalaman belajar yang lebih baik dengan berbagai macam fitur pendukung",
                R.drawable.slide1
            )
        )
        onBoardingData.add(OnBoardingData("Mexia", "h1h1h11h1h1111", R.drawable.slide2))
        onBoardingData.add(OnBoardingData("Mexia", "h1h1h11h1h1111", R.drawable.slide3))

        setOnBoardingViewPagerAdapter(onBoardingData)

        position = onBoardingViewPager!!.currentItem

        buttonNext?.setOnClickListener {
            if (position < onBoardingData.size){
                position++
                onBoardingViewPager!!.currentItem = position
            }
            if (position == onBoardingData.size){
                sharedPreferences.setValueBool(SLIDE_INTRO_SAVE, true)
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        tabLayout?.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                position = tab!!.position
                if (tab.position == onBoardingData.size - 1) {
                    buttonNext!!.text = StringBuilder("Mulai")
                } else {
                    buttonNext!!.text = StringBuilder("Selanjutnya ->")
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

    private fun setOnBoardingViewPagerAdapter(onBoardingData: List<OnBoardingData>){
        onBoardingViewPager = findViewById(R.id.screenPager)
        onBoardingViewPagerAdapter = OnBoardingViewPagerAdapter(this, onBoardingData)
        onBoardingViewPager?.adapter = onBoardingViewPagerAdapter
        tabLayout?.setupWithViewPager(onBoardingViewPager)
    }
}