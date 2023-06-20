package com.example.todolistapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.todolistapi.databinding.ActivityMainBinding
import org.json.JSONObject
import java.lang.Exception

private lateinit var binding: ActivityMainBinding
private lateinit var data:ArrayList<ModelData>
private lateinit var datacek:ArrayList<ModelData>
private lateinit var adapter:RecyclerViewAdapter
private lateinit var recyclerView: RecyclerView
class MainActivity : AppCompatActivity(),RecyclerViewInterface {
    private lateinit var runnable: Runnable
    private var handler = Handler()
    private lateinit var url:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        data = ArrayList<ModelData>()
//        val url = "http://192.168.180.188/api/data123/"
        val api:Api = Api()
      url = resources.getString(R.string.server_api)
      //  startActivity(Intent(this,UpdateActivity::class.java))

        //ambil data

        val Request = JsonObjectRequest(
            Request.Method.GET,url,null, Response.Listener {
                response ->
                val dataarray = response.getJSONArray("data")
              for (i in 0 .. dataarray.length() - 1) {
                  val data1 = dataarray.getJSONObject(i)
                  val ambil = ModelData(

                      data1.getInt("id"),
                      data1.getString("nama"),
                      data1.getString("tanggal"),
                      data1.getString("jam"),
                      data1.getString("keterangan"),
                      data1.getInt("selesai").toByte(),

                           
                  )
                  data.add(ambil)

                adapter = RecyclerViewAdapter(this@MainActivity, data,this)

                  binding.recyclerview.setAdapter(adapter)
                  binding.recyclerview.setLayoutManager(LinearLayoutManager(this))
              }
        },
            Response.ErrorListener {
                    error ->
                Toast.makeText(this,"error",Toast.LENGTH_LONG)

            })
        Volley.newRequestQueue(this).add(Request)


        binding.tambahdata.setOnClickListener {
            startActivity(Intent(this,TambahData::class.java))

        }


        //realtime cek data
//
//        runnable = Runnable{
//            datacek = ArrayList<ModelData>()
//            datacek.clear()
//            var idhps = 0
//            var hpsdiganti = false
//
//            val Request11 = JsonObjectRequest(
//                com.android.volley.Request.Method.GET,url,null, Response.Listener {
//                        response ->
//                    val dataarray = response.getJSONArray("data")
//                    for (i in 0 .. dataarray.length() - 1) {
//                        val data1 = dataarray.getJSONObject(i)
//                        val ambil = ModelData(
//
//                            data1.getInt("id"),
//                            data1.getString("nama"),
//                            data1.getString("tanggal"),
//                            data1.getString("jam"),
//                            data1.getString("keterangan"),
//                            data1.getInt("selesai").toByte(),
//
//
//
//                        )
//
//                        datacek.add(ambil)
//
//                        if (i < data.size) {
//
//                            if (data1.getInt("id") != data.get(i).id) {
//                                if (hpsdiganti == false) {
//                                    idhps = i
//                                    hpsdiganti = true
//                                }
//
//
//                            }
//
//
//                                data.set(i, ModelData(
//                                    data1.getInt("id"),
//                                    data1.getString("nama"),
//                                    data1.getString("tanggal"),
//                                    data1.getString("jam"),
//                                    data1.getString("keterangan"),
//                                   data.get(i).selesai,
//
//                                )
//
//                                )
//                                adapter.notifyItemChanged(i)
//
//                        }
//
//
//
//
//                        //    Toast.makeText(this,"konek",Toast.LENGTH_LONG).show()
//
//                    }
//
//                    if (data.size < datacek.size){
//                        try{
//                        Toast.makeText(this,"data di update",Toast.LENGTH_LONG).show()
//                        data.add(datacek.get(datacek.size-1))
//                        adapter.notifyItemInserted(data.size-1)}
//                        catch (e:Exception){
//
//                            adapter = RecyclerViewAdapter(this@MainActivity, data,this)
//                            Toast.makeText(this,"konek",Toast.LENGTH_LONG)
//                            binding.recyclerview.setAdapter(adapter)
//                            binding.recyclerview.setLayoutManager(LinearLayoutManager(this))
//                        }
//                    }
//                    else if(data.size > datacek.size){
//                          data.removeAt(data.size-1)
//                        adapter.notifyItemRemoved(data.size-1)
//                    }
//                },
//                Response.ErrorListener {
//                        error ->
//                    Toast.makeText(this,"error",Toast.LENGTH_LONG)
//
//                })
//            Volley.newRequestQueue(this).add(Request11)
//
//
//            handler.postDelayed(runnable,5000)
//        }
//        handler.postDelayed(runnable,5000)
    }

    override fun onclick(pos: Int) {
val Intentdetail:Intent = Intent(this,DetailTodo::class.java)
        Intentdetail.putExtra("id", data.get(pos).id.toString())
        startActivity(Intentdetail)

    }

    override fun oneditclick(pos: Int) {
        val intentupdatedata:Intent = Intent(this,UpdateActivity::class.java)
        intentupdatedata.putExtra("id",data.get(pos).id.toString())
        intentupdatedata.putExtra("selesai", data.get(pos).selesai.toString())

        startActivity(intentupdatedata)
    }


    //hapus data


    override fun onhpsclick(pos: Int) {

       
        val request2 = JsonObjectRequest(
            Request.Method.DELETE,url+ data.get(pos).id.toString(),null,
            Response.Listener { 
                response ->
                data.removeAt(pos)
                adapter.notifyItemRemoved(pos)
            },
            Response.ErrorListener { 
                error ->
            }

        )
        Volley.newRequestQueue(this).add(request2)

        

    }

    override fun onchecked(pos: Int,ischeck: Boolean) {
        val selesai:Byte = if (ischeck) 1 else 0
        val json = JSONObject(
            """{
        "nama": "${data.get(pos).nama}",
        "tanggal": "${data.get(pos).tanggal}",
        "jam": "${data.get(pos).jam}",
        "keterangan": "${data.get(pos).keterangan}",
        "selesai": "$selesai"
    }"""
        )
        val request2 = JsonObjectRequest(
            Request.Method.PUT,url+ data.get(pos).id.toString(),json,
            Response.Listener {
                    response ->
            data.set(pos, ModelData(
            data.get(pos).id,
                data.get(pos).nama,
                data.get(pos).tanggal,
                data.get(pos).jam,
                data.get(pos).keterangan,
                selesai
            )
            )
            },
            Response.ErrorListener {
                    error ->
                Toast.makeText(this,"data gagal selesai",Toast.LENGTH_LONG).show()
            }

        )
        Volley.newRequestQueue(this).add(request2)

    }
}