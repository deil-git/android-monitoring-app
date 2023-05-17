package com.example.vkr

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.Window
import android.widget.*
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.vkr.data_structs.ConfigStruct
import com.example.vkr.data_structs.CorrectStruct
import com.example.vkr.network.Network

class ConfigActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var abox_id = arrayListOf<Int>()
    var aaddress = arrayListOf<String>()
    var abox_name = arrayListOf<String>()
    var adev_list = arrayListOf<String>()
    var did = arrayListOf<Int>()
    var daddress = arrayListOf<String>()
    var dcorrect_t = arrayListOf<Float>()
    var dcorrect_h = arrayListOf<Float>()

    fun showToast(toast: String?) {
        runOnUiThread {
            Toast.makeText(this@ConfigActivity, toast, Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SetTextI18n", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        Network.getAddress {
            if (it.err) {
                MainActivity.AppPreferences.login?.let {
                    it1 -> MainActivity.AppPreferences.password?.let {
                        it2 -> Network.tokenGet(it1, it2){ it3 ->
                            MainActivity.AppPreferences.token = it3.token
                        }
                    }
                }
            }
        }

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
                    val ttbrow = TableRow(this)
                    val tbrow1 = TableRow(this)
                    val spp = Space(this)
                    val chk1 = CheckBox(this)
                    ttbrow.addView(Space(this))
                    tbrow.addView(chk1)
                    ttbrow.addView(Space(this))
                    tbrow.addView(spp)
                    val t1v = TextView(this)
                    t1v.text = abox_id[i].toString()
                    t1v.setTextColor(Color.BLACK)
                    t1v.textSize = 20F
                    ttbrow.addView(Space(this))
                    tbrow.addView(t1v)
                    val t2v = TextView(this)
                    t2v.text = abox_name[i]
                    t2v.setTextColor(Color.BLACK)
                    t2v.textSize = 20F
                    ttbrow.addView(Space(this))
                    tbrow.addView(t2v)
                    val t3v = TextView(this)
                    t3v.id = abox_id[i] + 100
                    if (aaddress[i] != null) {
                        t3v.text = aaddress[i]
                    } else {
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
                    ttbrow.addView(sp)
                    stk.addView(tbrow)
                    stk.addView(ttbrow)
                    stk.addView(tbrow1)
                }
            }
        }


        Network.getDevices {
            var r: String = ""

            for (d in it.device_list) {
                did.add(d.id)
                daddress.add(d.address)
                dcorrect_t.add(d.correct_t)
                dcorrect_h.add(d.correct_h)
                r += "${d.id} ${d.address} ${d.correct_t} ${d.correct_h}\n"
            }

            Log.d("Result", r)

            runOnUiThread {

                val stk = findViewById<View>(R.id.SensorTable) as TableLayout

                for (i in 0 until did.size) {
                    val tbrow = TableRow(this)
                    val space = TextView(this)
                    space.setTextColor(Color.WHITE)
                    stk.addView(space)
                    stk.addView(tbrow)
                    val tbrow1 = TableRow(this)
                    val tbrow2 = TableRow(this)
                    val tbrow3 = TableRow(this)
                    val t1v = TextView(this)
                    t1v.text = did[i].toString()
                    t1v.setTextColor(Color.BLACK)
                    t1v.textSize = 20F
                    tbrow1.addView(t1v)
                    val t2v = TextView(this)
                    t2v.text = daddress[i]
                    t2v.setTextColor(Color.BLACK)
                    t2v.textSize = 20F
                    tbrow1.addView(t2v)
                    stk.addView(tbrow1)
                    val ct = TextView(this)
                    ct.text = "Коррекция, °C"
                    ct.setTextColor(Color.BLACK)
                    ct.textSize = 20F
                    ct.setTypeface(null, Typeface.BOLD)
                    tbrow2.addView(ct)
                    val et1 = EditText(this)
                    et1.hint = dcorrect_t[i].toString()
                    et1.id = did[i] + 1000
                    et1.setRawInputType(InputType.TYPE_CLASS_NUMBER)
                    et1.setText(dcorrect_t[i].toString())
                    et1.background.setTint(ContextCompat.getColor(this, R.color.foodrus))
                    tbrow2.addView(et1)
                    stk.addView(tbrow2)
                    val ht = TextView(this)
                    ht.text = "Коррекция, %"
                    ht.setTextColor(Color.BLACK)
                    ht.textSize = 20F
                    ht.setTypeface(null, Typeface.BOLD)
                    tbrow3.addView(ht)
                    val et2 = EditText(this)
                    et2.hint = dcorrect_h[i].toString()
                    et2.id = did[i] + 10000
                    et2.setRawInputType(InputType.TYPE_CLASS_NUMBER)
                    et2.setText(dcorrect_h[i].toString())
                    et2.background.setTint(ContextCompat.getColor(this, R.color.foodrus))
                    tbrow3.addView(et2)
                    stk.addView(tbrow3)
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
            val data_config:MutableList<ConfigStruct> = arrayListOf()
            val data_correct:MutableList<CorrectStruct> = arrayListOf()

            Network.getAddress {
                if (it.err) {
                    MainActivity.AppPreferences.login?.let {
                            it1 -> MainActivity.AppPreferences.password?.let {
                            it2 -> Network.tokenGet(it1, it2){ it3 ->
                                MainActivity.AppPreferences.token = it3.token
                            }
                        }
                    }
                }
            }

            for(i in 0 until abox_id.size) {
                data_config.add(i, ConfigStruct(abox_id[i], aaddress[i]))
            }

            Network.sendConfig(data_config){
                if (!it.contains("OK")) {
                    showToast("Ошибка: данные не отправлены")
                }
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

                        for(i in 1 until did.size + 1) {
                            val et1 = findViewById<EditText>(i + 1000)
                            val et2 = findViewById<EditText>(i + 10000)
                            if (et1.text.isNotEmpty()) {
                                et1.setText(et1.text.toString().replace(",", "."))
                                if (et1.text.count { it == '.' } == 0) {
                                    et1.setText(et1.text.toString() + ".0")
                                }
                                if (et1.text.count { it == '.' } > 1) {
                                    Toast.makeText(applicationContext,
                                        "Некорректные данные температуры",
                                        Toast.LENGTH_LONG).show()
                                }
                                else {
                                    dcorrect_t[i-1] = et1.text.toString().toFloat()
                                }
                            }
                            if (et2.text.isNotEmpty()) {
                                et2.setText(et2.text.toString().replace(",", "."))
                                if (et2.text.count { it == '.' } == 0) {
                                    et2.setText(et2.text.toString() + ".0")
                                }
                                if (et2.text.count { it == '.' } > 1) {
                                    Toast.makeText(applicationContext,
                                        "Некорректные данные влажности",
                                        Toast.LENGTH_LONG).show()
                                }
                                else {
                                    dcorrect_h[i-1] = et2.text.toString().toFloat()
                                }
                            }
                        }

                        for(i in 0 until did.size) {
                            data_correct.add(i, CorrectStruct(did[i], dcorrect_t[i], dcorrect_h[i]))
                        }

                        Network.sendCorect(data_correct){ it2 ->
                            if (!it2.contains("OK")) {
                                showToast("Ошибка: данные не отправлены")
                            }
                            Network.getDevices {
                                dcorrect_t.clear()
                                dcorrect_h.clear()

                                for (d in it.device_list) {
                                    dcorrect_t.add(d.correct_t)
                                    dcorrect_h.add(d.correct_h)
                                }

                                for(i in 1 until did.size + 1) {
                                    val et1 = findViewById<EditText>(i + 1000)
                                    val et2 = findViewById<EditText>(i + 10000)
                                    et1.setText(dcorrect_t[i-1].toString())
                                    et1.hint = dcorrect_t[i-1].toString()
                                    et2.setText(dcorrect_h[i-1].toString())
                                    et2.hint = dcorrect_h[i-1].toString()
                                }
                            }
                        }
                    }
                }
            }
        }

        val notif = findViewById<ImageView>(R.id.notif)
        notif.setOnClickListener {
            Toast.makeText(applicationContext,"Получение уведомлений", LENGTH_LONG).show()
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