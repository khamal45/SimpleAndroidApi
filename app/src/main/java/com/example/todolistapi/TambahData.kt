package com.example.todolistapi

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.todolistapi.databinding.ActivityTambahDataBinding
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

private lateinit var binding: ActivityTambahDataBinding
class TambahData : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val c: Calendar = Calendar.getInstance()
        val tahun = c.get(Calendar.YEAR)
        val bulan = c.get(Calendar.MONTH)
        val hari = c.get(Calendar.DAY_OF_MONTH)
        val jam = c.get(Calendar.HOUR)
        val menit = c.get(Calendar.MINUTE)

        binding.tanggal.setOnClickListener {
            val dp = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val i:Int=month+1
    binding.editTextDate.setText("" + year +"/" + i+ "/" + dayOfMonth)
            },tahun,bulan,hari
            ).show()
        }
        binding.editTextDate.setOnClickListener{
            val dp = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val i:Int=month+1
                binding.editTextDate.setText("" + year +"/" + i+ "/" + dayOfMonth)
            },tahun,bulan,hari
            ).show()
        }
        binding.jam.setOnClickListener {
            val tp = TimePickerDialog(this,TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                binding.editTextTime.setText("" + hourOfDay + ":" + minute)
            },jam,menit,true).show()
        }

        binding.tambahdatasimpan.setOnClickListener {
            val url = resources.getString(R.string.server_api)
            val nama:String = binding.editTextTextPersonName.getText().toString()
            val tanggal:String = binding.editTextDate.getText().toString()
            val jam:String = binding.editTextTime.getText().toString()
            val keterangan:String = binding.inputEditText.getText().toString()
            val json = JSONObject(
                """{
        "nama": "$nama",
        "tanggal": "$tanggal",
        "jam": "$jam",
        "keterangan": "$keterangan",
        "selesai": "0"
    }"""
            )

            val request = JsonObjectRequest(
                Request.Method.POST,
                url,
                json,
                Response.Listener { response ->
                    Toast.makeText(this, "Tambah Data Berhasil", Toast.LENGTH_LONG).show()
                    startActivity(
                        Intent(this,MainActivity::class.java))
                    finish()

                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, "error", Toast.LENGTH_LONG).show()
                }
            )
        binding.editTextDate.text = null
            binding.editTextTime.text = null
            binding.editTextTextPersonName.text = null
            binding.inputEditText.text = null
            Volley.newRequestQueue(this).add(request)
        }

    }
}