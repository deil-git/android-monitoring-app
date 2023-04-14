package com.example.vkr

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vkr.databinding.ActivityMainBinding
import com.example.vkr.network.Network
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import okhttp3.*

typealias MyListener = (String) -> Unit
class MainActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val ll = sharedPreference.getString("login","defaultName")
        val pp = sharedPreference.getString("password","defaultName")
        if(ll != "defaultName") {
            bindingClass.loginText.setText(ll)
            bindingClass.passwordText.setText(pp)
        }

        lateinit var FCMtoken: String;
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("firebase", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            FCMtoken = task.result

            val msg = FCMtoken.toString()
            Log.d("firebase", msg)
        })

        bindingClass.loginButton.setOnClickListener {
            var login = bindingClass.loginText.text.toString()
            var password = bindingClass.passwordText.text.toString()
            val editor = sharedPreference.edit()

            if(bindingClass.checkBox.isChecked) {
                editor.putString("login", login)
                editor.putString("password", password)
                editor.apply()
            }
            else {
                editor.remove("login")
                editor.remove("password")
                editor.apply()
            }

            Network.tokenGet(login, password) {
                runOnUiThread(Runnable {
                    var token = it.token
                    var error = it.error

                    if(token.isNotEmpty()){
                        bindingClass.status.text = token
                        val intent = Intent(this, MapActivity::class.java)
                        Network.sendFCM(FCMtoken) {}
                        startActivity(intent)
                    }
                    else {
                        bindingClass.status.text = error
                        Toast.makeText(applicationContext,
                            "Неверный логин или пароль",
                            Toast.LENGTH_LONG).show()
                    }
                })
            }
        }




        bindingClass.DELETEME.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

        bindingClass.DELETEME2.setOnClickListener {
            bindingClass.loginText.setText("Artemiy")
            bindingClass.passwordText.setText("123")
        }
    }
}