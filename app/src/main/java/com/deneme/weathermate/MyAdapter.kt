package com.deneme.weathermate

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.io.File


class MyAdapter(private val newList: ArrayList<FavCity>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.favcity, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = newList[position]
        holder.cityname.text = currentItem.cityname
    }

    override fun getItemCount(): Int {
        return newList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val cityname: TextView = itemView.findViewById(R.id.cityname)
        val unFavBtn: Button = itemView.findViewById(R.id.unFavBtn)

        init {
            cardView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val intent = Intent(itemView.context, MainActivity::class.java)
                    intent.putExtra("cityname", cityname.text)
                    itemView.context.startActivity(intent)
                }
            }

            unFavBtn.setOnClickListener {
                val cityToRemove = cityname.text.toString()
                val externalFile = File(itemView.context.getExternalFilesDir(null), "fav_cities2.txt")

                try {

                    val lines = externalFile.readLines()


                    val updatedLines = lines.filter { it.trim() != cityToRemove }


                    externalFile.writeText(updatedLines.joinToString("\n"))


                    updateRecyclerView(updatedLines)

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        private fun updateRecyclerView(updatedLines: List<String>) {


            val newList = arrayListOf<FavCity>()
            for (line in updatedLines) {
                newList.add(FavCity(line))
            }

            setNewData(newList)
            notifyDataSetChanged()
        }

        private fun setNewData(newList: ArrayList<FavCity>) {
            this@MyAdapter.newList.clear()
            this@MyAdapter.newList.addAll(newList)
        }
    }
}
