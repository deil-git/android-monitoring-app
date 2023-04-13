package com.example.vkr.network
import java.util.*

public class BoxResponce constructor(){
    var boxes = Vector<Box>()

    public class Box constructor(_id: Int = 0, _name: String = ""){
        var id = _id
        var name = _name
    }
}