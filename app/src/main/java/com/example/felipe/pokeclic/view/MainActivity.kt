package com.example.felipe.pokeclic.view

import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.ContextThemeWrapper
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.felipe.pokeclic.R
import com.example.felipe.pokeclic.domain.Card
import com.example.felipe.pokeclic.domain.CardDao
import com.example.felipe.pokeclic.rest.config.RetrofitCofig
import com.example.felipe.pokeclic.view.adapter.CardsAdpter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val cards = ArrayList<Card>()
    val barraDeProgresso = BarraDeProgresso.instance
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        acessaServidor()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val idBtn = item?.itemId

        if(idBtn == R.id.btn_sair){

            val builder = AlertDialog.Builder(ContextThemeWrapper(this,R.style.myDialog))

            builder.setMessage("Deseja mesmo sair ?")
                .setTitle(baseContext.resources.getString(R.string.sair))

            builder.setPositiveButton("Sim",  { dialog, whichButton ->
                finish()
            })
            builder.setNegativeButton("Cancelar", { dialog, whichButton ->

            })

            val dialog = builder.create()

            dialog.show()
            dialog.window.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorWhite)))
        }

        return  true
    }
    fun acessaServidor(){
        val call = RetrofitCofig().restInterface().listarCards()
            call.enqueue(object: Callback<CardDao>{
                override fun onResponse(call: Call<CardDao>, response: Response<CardDao>) {
                    barraDeProgresso.showProgress(baseContext,true,pgr_list_card)
                    if(response.isSuccessful){
                        val cards:CardDao? = response.body()

                        val mLayoutManager = LinearLayoutManager(baseContext)
                        rv_pokes.layoutManager = mLayoutManager

                        val adpter = CardsAdpter(baseContext, cards?.cards!!)
                        rv_pokes.adapter = adpter
                        barraDeProgresso.showProgress(baseContext,false,pgr_list_card)
                    }

                }

                override fun onFailure(call: Call<CardDao>, t: Throwable) {
                    Toast.makeText(baseContext, R.string.falha, Toast.LENGTH_LONG).show()
                    barraDeProgresso.showProgress(baseContext,true,pgr_list_card)
                }
            })

    }
}
