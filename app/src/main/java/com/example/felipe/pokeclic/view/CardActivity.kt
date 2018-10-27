package com.example.felipe.pokeclic.view

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.Toast
import com.example.felipe.pokeclic.R
import com.example.felipe.pokeclic.domain.Card
import com.example.felipe.pokeclic.domain.CardDao
import com.example.felipe.pokeclic.rest.config.RetrofitCofig
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_card.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class CardActivity(): AppCompatActivity() {


    val barraDeProgresso = BarraDeProgresso.instance
    var id :String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)

        mostrarViews(false)

        val bundle = intent.extras
        val cardId = bundle.getString("CardId")

        if (!cardId.isEmpty()){
          id = cardId
          acessaServidor()
        }
        voltar()

    }

    fun acessaServidor(){
        val call = RetrofitCofig().restInterface().getCard(id)
        call.enqueue(object: Callback<CardDao>{
            override fun onResponse(call: Call<CardDao>, response: Response<CardDao>) {
                barraDeProgresso.showProgress(baseContext,true,pgr_card)

                if(response.isSuccessful){
                    val cards = response.body()

                    try{
                        inicianizar(cards?.card!!)
                        mostrarViews(true)

                    }catch (e:Exception){
                        Toast.makeText(applicationContext, R.string.falha, Toast.LENGTH_LONG).show()
                    }

                }
            }

            override fun onFailure(call: Call<CardDao>, t: Throwable) {
                Toast.makeText(applicationContext, R.string.falhaImagem, Toast.LENGTH_LONG).show()
                mostrarViews(false)
                barraDeProgresso.showProgress(applicationContext,false,pgr_card)
            }
        })
    }

    @Throws(Exception::class)
    fun  inicianizar(card:Card){
        try {
            Picasso.with(applicationContext).load(card.imageUrlHiRes).into(img_card)
        }catch (e: Exception) {
            try{
                Picasso.with(applicationContext).load(card.imageUrl).into(img_card)
            }catch (e:Exception){
                Toast.makeText(applicationContext, R.string.falhaImagem, Toast.LENGTH_LONG).show()
            }
        }

        moreInfo(card)
        barraDeProgresso.showProgress(baseContext,false,pgr_card)
    }

    fun mostrarViews(mostra:Boolean){
        if(!mostra) {
            img_card.visibility = View.INVISIBLE
            btn_more_info.visibility = View.INVISIBLE
            tlb_card_name.visibility = View.INVISIBLE

        }else{
            img_card.visibility = View.VISIBLE
            btn_more_info.visibility = View.VISIBLE
            tlb_card_name.visibility = View.VISIBLE
            //barraDeProgresso.showProgress(baseContext,false,pgr_card)
        }
    }

   private fun voltar(){
        img_voltar.setOnClickListener({
            finish()
        })
    }

    fun moreInfo(card: Card){

        btn_more_info.setOnClickListener(View.OnClickListener {

            val msg: String
            if (card.supertype.equals("Pokémon")) {
                msg = ("Esse Pokémon possui " + card.hp + " de HP e seu número da pokédex é "
                        + card.nationalPokedexNumber + ".")
            } else if (card.supertype.equals("Trainer")) {
                msg = card.text.get(0)
            } else {
                msg = card.name
            }

            val builder = AlertDialog.Builder(ContextThemeWrapper(this,R.style.myDialog))

            builder.setMessage(msg)
                .setTitle(card.name.replace("-EX","").replace("ex",""));

            // Cria botão ok
            builder.setPositiveButton("ok", null);
            val dialog = builder.create();

            // Mostra o Dialog
            dialog.show();
            dialog.getWindow().setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorWhite)));
        })
    }

}