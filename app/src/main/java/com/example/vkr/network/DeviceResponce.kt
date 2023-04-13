package com.example.vkr.network
import java.util.*

public class DeviceResponce constructor(){
    var device_list = Vector<Device>()

    public class Device constructor(_box_id: Int = 0, _address: String = "", _correction_t: Float = 0f, _correction_h: Float = 0f){
        var box_id = _box_id
        var address = _address
        var correction_t = _correction_t
        var correction_h = _correction_h
    }
}