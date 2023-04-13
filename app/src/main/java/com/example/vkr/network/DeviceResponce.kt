package com.example.vkr.network
import java.util.*

public class DeviceResponce constructor(){
    var device_list = Vector<Device>()

    public class Device constructor(_id: Int = 0, _address: String = "", _correct_t: Float = 0f, _correct_h: Float = 0f){
        var id = _id
        var address = _address
        var correct_t = _correct_t
        var correct_h = _correct_h
    }
}