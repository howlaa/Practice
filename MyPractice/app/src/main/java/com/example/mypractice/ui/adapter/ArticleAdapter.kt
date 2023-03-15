package com.example.mypractice.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypractice.databinding.ItemArticleBinding
import com.example.mypractice.model.bean.Article
import com.example.mypractice.model.bean.ArticleItem
import com.example.mypractice.ui.article.ArticleActivity

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.BannerViewHolder>() {
    private var mList : List<ArticleItem> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        return BannerViewHolder(
            ItemArticleBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.binData(mList[position])
        holder.binding.root.setOnClickListener {
            it.context.startActivity(Intent(it.context, ArticleActivity::class.java))
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setList(list: List<ArticleItem>) {
        mList = list
        notifyDataSetChanged()
    }

    class BannerViewHolder(val binding: ItemArticleBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun binData(article: ArticleItem){
            binding.title.text = article.title
        }
    }
}