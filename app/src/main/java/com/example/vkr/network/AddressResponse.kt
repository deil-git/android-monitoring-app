package com.example.vkr.network
import java.util.*

public class AddressResponse constructor(){
    var addresses = Vector<Address>()
    var dev_list = Vector<String>()
    var err = false

    public class Address constructor(_box_id: Int = 0, _address: String = "", _box_name: String = ""){
        var box_id = _box_id
        var address = _address
        var box_name = _box_name
    }
}