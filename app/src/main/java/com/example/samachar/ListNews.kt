package com.example.samachar


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samachar.Adapter.ViewHolder.ListNewsAdapter
import com.example.samachar.Common.Common
import com.example.samachar.Interface.NewService
import com.example.samachar.Model.News
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_list_news.*
import retrofit2.Call
import retrofit2.Response



class ListNews : AppCompatActivity() {

    var source=""
    var webHotUrl:String?=""

    lateinit var dialog: AlertDialog
    lateinit var mService: NewService
    lateinit var adapter: ListNewsAdapter
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_news)
        //Init view
        mService =Common.newsService

        dialog = SpotsDialog(this)

        swipe_to_refresh.setOnRefreshListener {
            loadNews(source,true)
        }
        diagonalLayout.setOnClickListener{
                //implement soon
            val detail  = Intent(baseContext,NewsDetail::class.java)
            detail.putExtra("webURL",webHotUrl)
            startActivity(detail)
        }
        list_news.setHasFixedSize(true)
        list_news.layoutManager= LinearLayoutManager(this)

        if(intent!=null){
                source = intent.getStringExtra("source")
                    if (!source.isEmpty())
                    {
                        loadNews(source,false)
                    }

            }
    }

    private fun loadNews(source: String?, isRefreshed : Boolean) {
        if (isRefreshed)
        {
            dialog.show()
            mService.getnewsFromSource(Common.getNewsAPI(source!!))
                    .enqueue(object : retrofit2.Callback<News>{
                        override fun onFailure(call: Call<News>, t: Throwable) {
                            TODO("Not yet implemented")
                        }

                        override fun onResponse(call: Call<News>?, response: Response<News>?) {
                            dialog.dismiss()
                            //Get first article from hot news
                            Picasso.with(baseContext)
                                    .load(response!!.body()!!.articles!![0].urlToImage)
                                    .into(top_image)
                            top_title.text = response!!.body()!!.articles!![0].title
                            top_author.text = response!!.body()!!.articles!![0].author

                            webHotUrl = response!!.body()!!.articles!![0].url
                            //Load all remain articles
                            val removeFirstItem = response!!.body()!!.articles
                            //Because you get first Item to hot news, so we need to remove it
                            removeFirstItem!!.removeAt(0)

                            adapter = ListNewsAdapter(removeFirstItem!!, baseContext)
                            adapter.notifyDataSetChanged()
                            list_news.adapter = adapter
                        }

                    })


        }

        else
        {
            swipe_to_refresh.isRefreshing = true
            mService.getnewsFromSource(Common.getNewsAPI(source!!))
                    .enqueue(object : retrofit2.Callback<News>{
                        override fun onFailure(call: Call<News>?, t: Throwable?) {
                            TODO("Not yet implemented")
                        }

                        override fun onResponse(call: Call<News>?, response: Response<News>?) {
                           swipe_to_refresh.isRefreshing = false
                            //Get first article from hot news
                            Picasso.with(baseContext)
                                    .load(response!!.body()!!.articles!![0].urlToImage)
                                    .into(top_image)
                            top_title.text = response!!.body()!!.articles!![0].title
                            top_author.text = response!!.body()!!.articles!![0].author

                            webHotUrl = response!!.body()!!.articles!![0].url
                            //Load all remain articles
                            val removeFirstItem = response!!.body()!!.articles
                            //Because you get first Item to hot news, so we need to remove it
                            removeFirstItem!!.removeAt(0)

                            adapter = ListNewsAdapter(removeFirstItem!!, baseContext)
                            adapter.notifyDataSetChanged()
                            list_news.adapter = adapter
                        }

                    })
        }
    }

}
