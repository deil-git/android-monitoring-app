package com.example.vkr

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.vkr.databinding.ActivityMainBinding
import com.example.vkr.network.MyApplication
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

        lateinit var FCMtoken: String;
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("firebase", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            FCMtoken = task.result

            // Log and toast
            val msg = FCMtoken.toString()
            Log.d("firebase", msg)
            // Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })

        val mApp: MyApplication = this.application as MyApplication

        bindingClass.loginButton.setOnClickListener {
            var login = bindingClass.loginText.text.toString()
            mApp.login = login
            var password = bindingClass.passwordText.text.toString()
            mApp.password = password

            Network.tokenGet(login, password) {

                runOnUiThread(Runnable {
                    var token = it.token
                    var error = it.error

                    if(token.isNotEmpty()){
                        bindingClass.status.text = token
                        val intent = Intent(this, MapActivity::class.java)
                        Network.sendFCM(FCMtoken) {
                            //Nothing to do
                        }
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