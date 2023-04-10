package com.example.vkr

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.vkr.network.Network


class ConfigActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var abox_id = arrayListOf<Int>()
    var aaddress = arrayListOf<String>()
    var abox_name = arrayListOf<String>()
    var adev_list = arrayListOf<String>()



    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)


        Network.getDevices {
            runOnUiThread {
                var r: String = ""
                var NEW_SPINNER_ID = 0

                for (d in it.addresses) {
                    abox_id.add(d.box_id)
                    aaddress.add(d.address)
                    abox_name.add(d.box_name)
                    r += "${d.box_id} ${d.address} ${d.box_name} \n"
                }

                for (d in it.dev_list) {
                    adev_list.add(d)
                    r += "${d} \n"
                }

                Log.d("debug0", r)


                val stk = findViewById<View>(R.id.ConfigTable) as TableLayout

                for (i in 0 until abox_id.size) {
                    val tbrow2 = TableRow(this)
                    val space = TextView(this)
                    space.setTextColor(Color.WHITE)
                    stk.addView(space)
                    stk.addView(tbrow2)
                    val tbrow = TableRow(this)
                    val tbrow1 = TableRow(this)
                    val t1v = TextView(this)
                    t1v.text = abox_id[i].toString()
                    t1v.setTextColor(Color.BLACK)
                    t1v.textSize = 20F
                    tbrow.addView(t1v)
                    val t2v = TextView(this)
                    t2v.text = abox_name[i]
                    t2v.setTextColor(Color.BLACK)
                    t2v.textSize = 20F
                    tbrow.addView(t2v)
                    val t3v = TextView(this)
                    if (aaddress[i] != null) {
                        t3v.text = aaddress[i]
                    }
                    else {
                        t3v.text = "None"
                    }
                    t3v.setTextColor(Color.BLACK)
                    t3v.textSize = 20F
                    tbrow.addView(t3v)
                    val sp = Spinner(this)
                    NEW_SPINNER_ID += 1
                    var aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, adev_list)
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    with(sp) {
                        adapter = aa
                        onItemSelectedListener = this@ConfigActivity
                    }
                    sp.id = NEW_SPINNER_ID
                    tbrow1.tag = NEW_SPINNER_ID
                    stk.addView(tbrow)
                    stk.addView(sp)
                    stk.addView(tbrow1)
                }
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Toast.makeText(applicationContext,
            "Выбран :${parent!!.id} и ${adev_list[position]}",
            Toast.LENGTH_LONG).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(applicationContext,
            "Ничего не выбрано",
            Toast.LENGTH_LONG).show()
    }
}