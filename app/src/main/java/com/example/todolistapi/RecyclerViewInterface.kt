package com.example.todolistapi

interface RecyclerViewInterface {
    fun onclick(pos:Int)
    fun oneditclick(pos: Int)
    fun onhpsclick(pos: Int)
    fun onchecked(pos: Int,ischecked:Boolean)

}