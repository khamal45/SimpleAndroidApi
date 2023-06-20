package com.example.todolistapi

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.todolistapi.databinding.ActivityUpdateBinding
import org.json.JSONObject
import java.util.Calendar

private lateinit var binding1: ActivityUpdateBinding
class UpdateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding1 = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding1.root)

        val c: Calendar = Calendar.getInstance()
        val tahun = c.get(Calendar.YEAR)
        val bulan = c.get(Calendar.MONTH)
        val hari = c.get(Calendar.DAY_OF_MONTH)
        val jam = c.get(Calendar.HOUR)
        val menit = c.get(Calendar.MINUTE)
        binding1.imageButton.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        binding1.tanggal.setOnClickListener {
            val dp = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val i:Int=month+1
                binding1.editTextDate.setText("" + year +"/" + i+ "/" + dayOfMonth)
            },tahun,bulan,hari
            ).show()
        }
        binding1.editTextDate.setOnClickListener{
            val dp = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val i:Int=month+1
                binding1.editTextDate.setText("" + year +"/" + i+ "/" + dayOfMonth)
            },tahun,bulan,hari
            ).show()
        }
        binding1.jam.setOnClickListener {
            val tp = TimePickerDialog(this,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                binding1.editTextTime.setText("" + hourOfDay + ":" + minute)
            },jam,menit,true).show()
        }

        val id:String= getIntent().getStringExtra("id")!!
        val selesai = intent.getStringExtra("selesai")
        val url = resources.getString(R.string.server_api)+id.toString()

        val request1 = JsonObjectRequest(
            Request.Method.GET,url,null, Response.Listener {
                    response ->
                with(binding1){
                    editTextTextPersonName.setText(response.getString("nama"))
                    editTextTime.setText(response.getString("jam"))
                    editTextDate.setText(response.getString("tanggal"))
                    inputEditText.setText(response.getString("keterangan"))
                }
            },
            Response.ErrorListener {
                    error ->
                Toast.makeText(this,"Ada Kesalahan",Toast.LENGTH_LONG).show()
            })

        Volley.newRequestQueue(this).add(request1)


        binding1.tambahdatasimpan.setOnClickListener {


            val nama:String = binding1.editTextTextPersonName.getText().toString()
            val tanggal:String = binding1.editTextDate.getText().toString()
            val jam:String = binding1.editTextTime.getText().toString()
            val keterangan:String = binding1.inputEditText.getText().toString()

            val json = JSONObject(
                """{
        "nama": "$nama",
        "tanggal": "$tanggal",
        "jam": "$jam",
        "keterangan": "$keterangan",
        "selesai": "$selesai"
    }"""
            )
            val request = JsonObjectRequest(
                Request.Method.PUT,url,json, Response.Listener {
                        response ->
                    Toast.makeText(this,"Update Berhasil",Toast.LENGTH_LONG).show()
                    startActivity(
                        Intent(this,MainActivity::class.java)
                    )
                    finish()
                },
                Response.ErrorListener {
                        error ->
                    Toast.makeText(this,"Ada Kesalahan",Toast.LENGTH_LONG).show()
                })

            Volley.newRequestQueue(this).add(request)
        }

    }
}