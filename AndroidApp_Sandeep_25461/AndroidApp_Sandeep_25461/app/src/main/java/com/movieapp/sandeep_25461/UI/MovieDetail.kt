package com.movieapp.sandeep_25461.UI

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.movieapp.sandeep_25461.databinding.ActivityMovieDetailBinding
import java.text.DecimalFormat

class MovieDetail : AppCompatActivity() {
    lateinit var binding:ActivityMovieDetailBinding

    private var moviename:String?=null
    private var postimg:String?=null
    private var desc:String?=null
    private var remaing_seats:String?=null
    private var selectedSeats:String?=null
    private var price:Double?=null
    var mainprice:Double=0.0
    companion object
    {
        var selected_count=0
        var remaing_count=0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        moviename=intent.getStringExtra("moviename")
        postimg=intent.getStringExtra("image")
        desc=intent.getStringExtra("desc")

        price=intent.getDoubleExtra("price",0.0)
        mainprice=intent.getDoubleExtra("price",0.0)


        remaing_seats=intent.getStringExtra("remaingseats")
        selectedSeats=intent.getStringExtra("selectedseats")


        remaing_count = remaing_seats!!.replace(Regex("[^0-9]"), "").toIntOrNull()!!

        binding.plusbtn.setOnClickListener {
            if (remaing_count>0)
            {
                selected_count++
                remaing_count--
               if (selected_count>=1)
               {
                   price=price!!*selected_count
               }

            }

            val decimalFormat = DecimalFormat("#.##")
            val formattedPrice = decimalFormat.format(price)

            binding.selectedseats.text="Selected Seats: $selected_count"
            binding.remaingseats.text="Remaining Seats: $remaing_count"
            binding.tvprice.text="price: €$formattedPrice"
        }
        binding.minusbtn.setOnClickListener {
            if (selected_count>1)
            {

                remaing_count++
                if (selected_count>=1)
                {
                    selected_count--
                    price=price!!/selected_count
                }
                if (selected_count==1)
                {
                    price=mainprice
                }

            }
            else
            {
                Toast.makeText(this@MovieDetail,"Seats Can't be less then one!",Toast.LENGTH_LONG).show()
            }

            val decimalFormat = DecimalFormat("#.##")
            val formattedPrice = decimalFormat.format(price)
            binding.selectedseats.text="Selected Seats: $selected_count"
            binding.remaingseats.text="Remaining Seats: $remaing_count"
            binding.tvprice.text="price: €$formattedPrice"
        }

        binding.selectedseats.text=selectedSeats
        binding.remaingseats.text=remaing_seats
        binding.tvprice.text="price: €$price"


        Glide.with(this@MovieDetail)
            .load(postimg)
            .into(binding.posterimg
            )

        binding.moviedesc.text=desc
        binding.titletv.text=moviename

    }



    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("modifiedRemainingSeats", remaing_count.toString())
        intent.putExtra("modifiedSelectedSeats", selected_count.toString())
        setResult(Activity.RESULT_OK, intent)
        super.onBackPressed()
    }
}