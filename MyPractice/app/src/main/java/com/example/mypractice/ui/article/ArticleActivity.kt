package com.example.mypractice.ui.article

import com.example.mypractice.base.BaseActivity
import com.example.mypractice.databinding.ActivityArticleBinding
import com.example.mypractice.eventbus.Event
import com.example.mypractice.eventbus.FlowEventBus

class ArticleActivity : BaseActivity<ActivityArticleBinding>(){
    override fun getViewBinding(): ActivityArticleBinding =
        ActivityArticleBinding.inflate(layoutInflater)

    override fun initViews() {
        super.initViews()
        binding.reset.setOnClickListener {
            FlowEventBus.post(Event.ShowInit("article iit"))
            finish()
        }
    }
}