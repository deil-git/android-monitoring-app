package com.example.vkr.network
import java.util.*

public class LogResponce constructor(){
    var logs = Vector<Log>()

    public class Log constructor(_id: Int = 0, _name: String = "", _data: String = "", _path: String = ""){
        var id = _id
        var name = _name
        var data = _data
        var path = _path
    }
}