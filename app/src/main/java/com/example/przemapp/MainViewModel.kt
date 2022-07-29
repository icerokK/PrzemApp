package com.example.przemapp

import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    var text = ""
    fun setString(s: String){
        text = s
    }
    fun getString(): String{
        return text
    }

    var text2 = "hej"
    fun setString2(s: String){
        text2 = s
    }
    fun getString2(): String{
        return text2
    }
}