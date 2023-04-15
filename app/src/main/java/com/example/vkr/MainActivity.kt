package com.example.vkr

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.vkr.databinding.ActivityMainBinding
import com.example.vkr.network.Network
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import okhttp3.*

typealias MyListener = (String) -> Unit
class MainActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityMainBinding

    object AppPreferences {
        private var sharedPreferences: SharedPreferences? = null

        fun setup(context: Context) {
            sharedPreferences = context.getSharedPreferences("vkr.sharedprefs", MODE_PRIVATE)
        }

        var token: String?
            get() = Key.TOKEN.getString()
            set(value) = Key.TOKEN.setString(value)

        var login: String?
            get() = Key.LOGIN.getString()
            set(value) = Key.LOGIN.setString(value)

        var password: String?
            get() = Key.PASSWORD.getString()
            set(value) = Key.PASSWORD.setString(value)

        private enum class Key {
            TOKEN, LOGIN, PASSWORD;

            fun getString(): String? = if (sharedPreferences!!.contains(name)) sharedPreferences!!.getString(name, "") else null

            fun setString(value: String?) = value?.let { sharedPreferences!!.edit { putString(name, value) } } ?: remove()

            fun remove() = sharedPreferences!!.edit { remove(name) }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        AppPreferences.setup(applicationContext)
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
            AppPreferences.login = login
            AppPreferences.password = password
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
                    AppPreferences.token = token

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