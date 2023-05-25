package com.example.vkr.network
import java.util.*

public class AddressResponse constructor(){
    var addresses = Vector<Address>()
    var dev_list = Vector<String>()
    var err = false

    public class Address constructor(_box_active: Int = 0, _alert_active: Int = 0, _box_id: Int = 0, _address: String = "", _box_name: String = ""){
        var box_active = _box_active
        var alert_active = _alert_active
        var box_id = _box_id
        var address = _address
        var box_name = _box_name
    }
}