package com.gemastik.android.mexia.ui.dashboard.home.puzzle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gemastik.android.mexia.R
import com.gemastik.android.mexia.ui.dashboard.home.puzzle.akhir.PuzzleAkhirActivity
import com.gemastik.android.mexia.ui.dashboard.home.puzzle.mudah.PuzzleMudahActivity
import com.gemastik.android.mexia.ui.dashboard.home.puzzle.sedang.PuzzleSedangActivity
import com.gemastik.android.mexia.ui.dashboard.home.puzzle.sulit.PuzzleSulitActivity
import kotlinx.android.synthetic.main.activity_pengenalan_huruf.arrow_back
import kotlinx.android.synthetic.main.activity_puzzle.*

class PuzzleActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_puzzle)

        puzzle_menu1.setOnClickListener(this)
        puzzle_menu2.setOnClickListener(this)
        puzzle_menu3.setOnClickListener(this)
        puzzle_menu4.setOnClickListener(this)
        arrow_back.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.arrow_back -> {
                onBackPressed()
            }
            R.id.puzzle_menu4 -> {
                startActivity(Intent(this, PuzzleAkhirActivity::class.java))
            }
            R.id.puzzle_menu1 -> {
                startActivity(Intent(this, PuzzleMudahActivity::class.java))
            }
            R.id.puzzle_menu3 -> {
                startActivity(Intent(this, PuzzleSulitActivity::class.java))
            }
            R.id.puzzle_menu2 -> {
                startActivity(Intent(this, PuzzleSedangActivity::class.java))
            }
        }
    }
}