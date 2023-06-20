package com.example.todolistapi

import android.app.DownloadManager.Request
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.todolistapi.databinding.ActivityDetailTodoBinding

private lateinit var binding: ActivityDetailTodoBinding
class DetailTodo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getStringExtra("id")
        val url = resources.getString(R.string.server_api) + id

with(binding){
        val json = JsonObjectRequest(
            com.android.volley.Request.Method.GET,url,null,Response.Listener {
                response ->
                editTextTextPersonName.text = response.getString("nama")
                editTextDate.text = response.getString("tanggal")
                editTextTime.text = response.getString("jam")
                inputEditText.text = response.getString("keterangan")
            },Response.ErrorListener {
                error ->
                Toast.makeText(this@DetailTodo, "error",Toast.LENGTH_SHORT).show()
            }
        )
    Volley.newRequestQueue(this@DetailTodo).add(json)

}
    }
}