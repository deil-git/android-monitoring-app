package com.example.vkr

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.vkr.network.Network


class LogActivity : AppCompatActivity() {
    var lig = arrayListOf<Int>()
    var lname = arrayListOf<String>()
    var ldata = arrayListOf<String>()
    var ldataf = arrayListOf<String>()
    var lpath = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        Network.getLogs {
            var r: String = ""

            for (d in it.logs) {
                lig.add(d.id)
                lname.add(d.name)
                ldata.add(d.data)
                lpath.add(d.path)
                r += "${d.id} ${d.name} ${d.data} ${d.path}\n"

                var s: String = ""
                for(i in 0 until 16){
                    s += d.data[i]
                }
                s += " - "
                for(i in 20 until d.data.length - 3){
                    s += d.data[i]
                }
                ldataf.add(s)
            }

            Log.d("Result", r)

            runOnUiThread {
                val stk = findViewById<View>(R.id.LogTable) as TableLayout

                for (i in 0 until lig.size) {
                    val tbrow = TableRow(this)
                    val space = TextView(this)
                    space.setTextColor(Color.WHITE)
                    stk.addView(space)
                    stk.addView(tbrow)
                    val tbrow1 = TableRow(this)
                    val tbrow2 = TableRow(this)
                    val buttonrow = LinearLayout(this)
                    val t1v = TextView(this)
                    t1v.text = lig[i].toString()
                    t1v.setTextColor(Color.BLACK)
                    t1v.textSize = 20F
                    tbrow1.addView(t1v)
                    val t2v = TextView(this)
                    t2v.text = lname[i]
                    t2v.setTextColor(Color.BLACK)
                    t2v.textSize = 20F
                    tbrow1.addView(t2v)
                    stk.addView(tbrow1)
                    val time = TextView(this)
                    time.text = "Время"
                    time.setTextColor(Color.BLACK)
                    time.textSize = 20F
                    time.setTypeface(null, Typeface.BOLD)
                    tbrow2.addView(time)
                    val data = TextView(this)
                    data.text = ldataf[i]
                    data.setTextColor(Color.BLACK)
                    data.textSize = 18F
                    tbrow2.addView(data)
                    stk.addView(tbrow2)
                    val blank = TextView(this)
                    blank.text = "Время "
                    blank.setTextColor(Color.WHITE)
                    blank.textSize = 20F
                    blank.setTypeface(null, Typeface.BOLD)
                    buttonrow.addView(blank)
                    val b1 = Button(this)
                    b1.text = "Просмотр"
                    b1.setTextColor(Color.WHITE)
                    b1.background.setTint(ContextCompat.getColor(this, R.color.foodrus))
                    buttonrow.addView(b1)
                    val b2 = Button(this)
                    b2.text = " Удалить "
                    b2.setTextColor(Color.WHITE)
                    b2.background.setTint(ContextCompat.getColor(this, R.color.red))
                    buttonrow.addView(b2)
                    stk.addView(buttonrow)
                }
            }
        }



        val createButton = findViewById<Button>(R.id.createButton)
        createButton.setOnClickListener {
            val intent = Intent(this, LogCreateActivity::class.java)
            startActivity(intent)
        }
    }
}