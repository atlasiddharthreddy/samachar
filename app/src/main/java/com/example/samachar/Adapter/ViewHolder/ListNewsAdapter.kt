package com.example.samachar.Adapter.ViewHolder


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.samachar.Common.ISO8601Parser
import com.example.samachar.Interface.ItemClickListener
import com.example.samachar.Model.Article
import com.example.samachar.NewsDetail
import com.example.samachar.R
import com.squareup.picasso.Picasso
import java.text.ParseException
import java.util.*

class ListNewsAdapter(val articleList:MutableList<Article>, private val context : Context):RecyclerView.Adapter<ListNewsViewHolder>()
{
    //1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNewsViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val itemView = inflater.inflate(R.layout.news_layout,parent,false)
        return  ListNewsViewHolder(itemView)
    }
//2
    override fun getItemCount(): Int {
      return articleList.size
    }
//3
    override fun onBindViewHolder(holder: ListNewsViewHolder, position: Int) {
       //Load Image

        Picasso.with(context)
                .load(articleList[position].urlToImage)
                .into(holder.article_image)

        if(articleList[position].title!!.length > 100)
        {
            holder.article_title.text = articleList[position].title!!.substring(0,100)+"..."
        }
        else
        {
            holder.article_title.text =  articleList[position].title!!
        }
        //2nd
        if (articleList[position].publishedAt != null)
        {
            var date: Date?=null
            try {
                date = ISO8601Parser.parse(articleList[position].publishedAt!!)
            }catch (ex:ParseException){
                ex.printStackTrace()
            }
            holder!!.article_time.setReferenceTime(date!!.time)
        }
      //set event click
        holder!!.setItemClickListener(object : ItemClickListener{
            override fun onClick(view: View, position: Int) {
                    //implement soon
                val detail  = Intent(context, NewsDetail ::class.java)
                detail.putExtra("webURL",articleList[position].url)
                detail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(detail)
            }
        })

    }

}

