package com.example.vkr

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.vkr.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityMapBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMapBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        bindingClass.logButton.setOnClickListener {
            val intent = Intent(this, LogActivity::class.java)
            startActivity(intent)
        }

        bindingClass.configButton.setOnClickListener {
            val intent = Intent(this, ConfigActivity::class.java)
            startActivity(intent)

        }

        bindingClass.backButton.setOnClickListener {
            finish()
        }

        bindingClass.incubatorButtonJW1.setOnClickListener {
            val intent = Intent(this, GraphActivity::class.java)
            intent.putExtra("incubNum", "JW1")
            startActivity(intent)
        }
        bindingClass.incubatorButtonJW2.setOnClickListener {
            val intent = Intent(this, GraphActivity::class.java)
            intent.putExtra("incubNum", "JW2")
            startActivity(intent)
        }
        bindingClass.incubatorButtonJW3.setOnClickListener {
            val intent = Intent(this, GraphActivity::class.java)
            intent.putExtra("incubNum", "JW3")
            startActivity(intent)
        }
        bindingClass.incubatorButtonJW4.setOnClickListener {
            val intent = Intent(this, GraphActivity::class.java)
            intent.putExtra("incubNum", "JW4")
            startActivity(intent)
        }
        bindingClass.incubatorButtonJW5.setOnClickListener {
            val intent = Intent(this, GraphActivity::class.java)
            intent.putExtra("incubNum", "JW5")
            startActivity(intent)
        }
        bindingClass.incubatorButtonJW6.setOnClickListener {
            val intent = Intent(this, GraphActivity::class.java)
            intent.putExtra("incubNum", "JW6")
            startActivity(intent)
        }

        bindingClass.incubatorButtonHT1.setOnClickListener {
            val intent = Intent(this, GraphActivity::class.java)
            intent.putExtra("incubNum", "HT1")
            startActivity(intent)
        }
        bindingClass.incubatorButtonHT2.setOnClickListener {
            val intent = Intent(this, GraphActivity::class.java)
            intent.putExtra("incubNum", "HT2")
            startActivity(intent)
        }
        bindingClass.incubatorButtonHT3.setOnClickListener {
            val intent = Intent(this, GraphActivity::class.java)
            intent.putExtra("incubNum", "HT3")
            startActivity(intent)
        }
        bindingClass.incubatorButtonHT4.setOnClickListener {
            val intent = Intent(this, GraphActivity::class.java)
            intent.putExtra("incubNum", "HT4")
            startActivity(intent)
        }
        bindingClass.incubatorButtonHT5.setOnClickListener {
            val intent = Intent(this, GraphActivity::class.java)
            intent.putExtra("incubNum", "HT5")
            startActivity(intent)
        }
        bindingClass.incubatorButtonHT6.setOnClickListener {
            val intent = Intent(this, GraphActivity::class.java)
            intent.putExtra("incubNum", "HT6")
            startActivity(intent)
        }
        bindingClass.incubatorButtonHT7.setOnClickListener {
            val intent = Intent(this, GraphActivity::class.java)
            intent.putExtra("incubNum", "HT7")
            startActivity(intent)
        }
        bindingClass.incubatorButtonHT8.setOnClickListener {
            val intent = Intent(this, GraphActivity::class.java)
            intent.putExtra("incubNum", "HT8")
            startActivity(intent)
        }

        bindingClass.incubatorButtonCM1.setOnClickListener {
            val intent = Intent(this, GraphActivity::class.java)
            intent.putExtra("incubNum", "CM1")
            startActivity(intent)
        }
        bindingClass.incubatorButtonCM2.setOnClickListener {
            bindingClass.incubatorButtonCM1.setOnClickListener {
                val intent = Intent(this, GraphActivity::class.java)
                intent.putExtra("incubNum", "CM2")
                startActivity(intent)
            }
        }
        bindingClass.incubatorButtonCM3.setOnClickListener {
            bindingClass.incubatorButtonCM1.setOnClickListener {
                val intent = Intent(this, GraphActivity::class.java)
                intent.putExtra("incubNum", "CM3")
                startActivity(intent)
            }
        }
        bindingClass.incubatorButtonCM4.setOnClickListener {
            bindingClass.incubatorButtonCM1.setOnClickListener {
                val intent = Intent(this, GraphActivity::class.java)
                intent.putExtra("incubNum", "CM4")
                startActivity(intent)
            }
        }
        bindingClass.incubatorButtonCM5.setOnClickListener {
            bindingClass.incubatorButtonCM1.setOnClickListener {
                val intent = Intent(this, GraphActivity::class.java)
                intent.putExtra("incubNum", "CM5")
                startActivity(intent)
            }
        }
        bindingClass.incubatorButtonCM6.setOnClickListener {
            bindingClass.incubatorButtonCM1.setOnClickListener {
                val intent = Intent(this, GraphActivity::class.java)
                intent.putExtra("incubNum", "CM6")
                startActivity(intent)
            }
        }
        bindingClass.incubatorButtonCM7.setOnClickListener {
            bindingClass.incubatorButtonCM1.setOnClickListener {
                val intent = Intent(this, GraphActivity::class.java)
                intent.putExtra("incubNum", "CM7")
                startActivity(intent)
            }
        }
        bindingClass.incubatorButtonCM8.setOnClickListener {
            bindingClass.incubatorButtonCM1.setOnClickListener {
                val intent = Intent(this, GraphActivity::class.java)
                intent.putExtra("incubNum", "CM8")
                startActivity(intent)
            }
        }
        bindingClass.incubatorButtonCM9.setOnClickListener {
            bindingClass.incubatorButtonCM1.setOnClickListener {
                val intent = Intent(this, GraphActivity::class.java)
                intent.putExtra("incubNum", "CM9")
                startActivity(intent)
            }
        }
        bindingClass.incubatorButtonCM10.setOnClickListener {
            bindingClass.incubatorButtonCM1.setOnClickListener {
                val intent = Intent(this, GraphActivity::class.java)
                intent.putExtra("incubNum", "CM10")
                startActivity(intent)
            }
        }
        bindingClass.incubatorButtonCM11.setOnClickListener {
            bindingClass.incubatorButtonCM1.setOnClickListener {
                val intent = Intent(this, GraphActivity::class.java)
                intent.putExtra("incubNum", "CM11")
                startActivity(intent)
            }
        }
        bindingClass.incubatorButtonCM12.setOnClickListener {
            bindingClass.incubatorButtonCM1.setOnClickListener {
                val intent = Intent(this, GraphActivity::class.java)
                intent.putExtra("incubNum", "CM12")
                startActivity(intent)
            }
        }
        bindingClass.incubatorButtonCM13.setOnClickListener {
            bindingClass.incubatorButtonCM1.setOnClickListener {
                val intent = Intent(this, GraphActivity::class.java)
                intent.putExtra("incubNum", "CM13")
                startActivity(intent)
            }
        }
        bindingClass.incubatorButtonCM14.setOnClickListener {
            bindingClass.incubatorButtonCM1.setOnClickListener {
                val intent = Intent(this, GraphActivity::class.java)
                intent.putExtra("incubNum", "CM14")
                startActivity(intent)
            }
        }
    }
}