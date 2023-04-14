package com.example.vkr.network
import java.util.Vector

public final class ServerResponse constructor(_token: String = "", _error: String = ""){
    var token: String = _token
    var error: String = _error
    var data = Vector<Data>()

    public class Data constructor(_hum: Float = 0f, _id_box: Int = 0, _name: String = "", _temp: Float = 0f, _time: String = ""){
        var hum = _hum
        var id_box = _id_box
        var name = _name
        var temp = _temp
        var time = _time
    }
}
