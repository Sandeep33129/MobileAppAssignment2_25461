package com.movieapp.sandeep_25461.UI

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.movieapp.sandeep_25461.Models.MovieData
import com.movieapp.sandeep_25461.MovieAdapter
import com.movieapp.sandeep_25461.MovieAdapter.Companion.itemposition
import com.movieapp.sandeep_25461.databinding.ActivityAllMoviesBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AllMovies : AppCompatActivity() {
    lateinit var binding: ActivityAllMoviesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAllMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val jsonString = readJsonFromAssets( "movie.json")
        val movielist = parseJsonToModel(jsonString)

        val adapter = MovieAdapter(movielist,this@AllMovies)
        binding.rvMovies.adapter = adapter



        Log.d("list--> ",movielist.toString()+ "   c ")

    }

    fun readJsonFromAssets( fileName: String): String {
        return assets.open(fileName).bufferedReader().use { it.readText() }
    }
    fun parseJsonToModel(jsonString: String): List<MovieData> {
        val gson = Gson()
        return gson.fromJson(jsonString, object : TypeToken<List<MovieData>>() {}.type)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MovieAdapter.REQUEST_CODE_MOVIE_DETAIL && resultCode == Activity.RESULT_OK) {
            val modifiedRemainingSeats = data?.getStringExtra("modifiedRemainingSeats")
            val modifiedSelectedSeats = data?.getStringExtra("modifiedSelectedSeats")

            Log.d("datacheck: ",modifiedRemainingSeats+" "+modifiedSelectedSeats+" "+MovieAdapter.itemposition)
            // Update the seat data for the corresponding movie item in your RecyclerView
            // You can access the ViewHolder of the clicked item using findViewHolderForAdapterPosition
            val viewHolder = binding.rvMovies.findViewHolderForAdapterPosition(itemposition!!)
            if (viewHolder is MovieAdapter.ViewHolder) {
                viewHolder.binding.reamingseat.text = "Remaining Seats: $modifiedRemainingSeats"
                viewHolder.binding.selectedseat.text = "Selected Seats: $modifiedSelectedSeats"

            }
        }
    }

}