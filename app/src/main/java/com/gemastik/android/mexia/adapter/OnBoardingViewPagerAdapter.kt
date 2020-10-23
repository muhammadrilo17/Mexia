package com.gemastik.android.mexia.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.gemastik.android.mexia.R
import com.gemastik.android.mexia.model.OnBoardingData

class OnBoardingViewPagerAdapter(private var context: Context, private var onBoardingDataList: List<OnBoardingData>) : PagerAdapter() {
    override fun getCount(): Int = onBoardingDataList.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view==`object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    @SuppressLint("InflateParams")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.onboarding_screen_layout, null)
        val imageView: ImageView
        val title: TextView
        val desc: TextView

        onBoardingDataList[0].desc = "Pengalaman belajar yang lebih baik dengan berbagai macam fitur pendukung"
        onBoardingDataList[1].desc = "Mexia memiliki fitur yang dapat meningkatkan Motivasi belajar anak"
        onBoardingDataList[2].desc = "Game puzzle yang dapat menghilangkan kejenuhan disaat belajar sekaligus meningkatkan kreatifitas anak"

        imageView = view.findViewById(R.id.imageView)
        title = view.findViewById(R.id.title)
        desc = view.findViewById(R.id.descriptions)

        imageView.setImageResource(onBoardingDataList[position].imageUrl)
        title.text = onBoardingDataList[position].title
        desc.text = onBoardingDataList[position].desc

        container.addView(view)
        return view
    }
}