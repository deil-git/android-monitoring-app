package com.example.vkr.network
import java.util.Vector

public final class ServerResponse constructor(_token: String = "", _error: String = ""){
    var token: String = _token
    var error: String = _error
    var data = Vector<Data>()

    public class Data constructor(_hum: Float, _id_box: Int, _name: String, _temp: Float, _time: String){
        var hum = _hum
        var id_box = _id_box
        var name = _name
        var temp = _temp
        var time = _time
    }

}