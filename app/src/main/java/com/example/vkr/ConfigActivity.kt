package com.example.vkr

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.vkr.data_structs.ConfigStruct
import com.example.vkr.network.Network

class ConfigActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var abox_id = arrayListOf<Int>()
    var aaddress = arrayListOf<String>()
    var abox_name = arrayListOf<String>()
    var adev_list = arrayListOf<String>()

    @SuppressLint("SetTextI18n", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        Network.getAddress {
            var r: String = ""

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

            Log.d("Result", r)
            runOnUiThread {

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
                    t3v.id = abox_id[i] + 100
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
                    var aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, adev_list)
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    with(sp) {
                        adapter = aa
                        onItemSelectedListener = this@ConfigActivity
                    }
                    sp.id = abox_id[i]
                    stk.addView(tbrow)
                    stk.addView(sp)
                    stk.addView(tbrow1)
                }
            }
        }

        val rba = findViewById<RadioButton>(R.id.radioButtonAdress)
        rba.setOnClickListener {
            val asc = findViewById<ScrollView>(R.id.adressScrollView)
            asc.visibility = VISIBLE
            val isc = findViewById<ScrollView>(R.id.infelicityScrollView)
            isc.visibility = INVISIBLE
        }

        val rbi = findViewById<RadioButton>(R.id.radioButtonInfelicity)
        rbi.setOnClickListener {
            val asc = findViewById<ScrollView>(R.id.adressScrollView)
            asc.visibility = INVISIBLE
            val isc = findViewById<ScrollView>(R.id.infelicityScrollView)
            isc.visibility = VISIBLE
        }

        val saveButtonClick = findViewById<Button>(R.id.saveButton1)
        saveButtonClick.setOnClickListener {
            val data:MutableList<ConfigStruct> = arrayListOf()

            for(i in 0 until abox_id.size) {
                data.add(i, ConfigStruct(abox_id[i], aaddress[i]))
            }

            Network.sendConfig(data){
                Network.getAddress {
                    runOnUiThread{

                        aaddress.clear()

                        for (d in it.addresses) {
                            if(d.address == null){
                                aaddress.add("None")
                            }
                            else{
                                aaddress.add(d.address)
                            }
                        }

                        for (i in 1 until aaddress.size+1) {
                            val adr = findViewById<TextView>(i + 100)
                            if (aaddress[i-1] != null) {
                                adr.text = aaddress[i-1]
                            }
                            else {
                                adr.text = "None"
                            }
                            val sp = findViewById<Spinner>(i)
                            sp.setSelection(0)
                        }
                    }
                }
            }

            Network.getDevices {
                for (d in it.device_list) {
                    Log.d("debug0", d.address + d.box_id + d.correction_t + d.correction_h)
                }
            }

        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        aaddress[parent!!.id-1] = adev_list[position]
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(applicationContext,
            "Ничего не выбрано",
            Toast.LENGTH_LONG).show()
    }

    override fun onStop(){
        super.onStop()

        this.finish()
    }

    override fun onPause(){
        super.onPause()

        this.finish()
    }

    override fun onDestroy(){
        super.onDestroy()

        this.finish()
    }
}