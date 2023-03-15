package com.example.mypractice.ui.main.fragment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypractice.base.BaseFragment
import com.example.mypractice.base.LoadUiIntent
import com.example.mypractice.databinding.FragmentHomeBinding
import com.example.mypractice.eventbus.Event
import com.example.mypractice.eventbus.FlowEventBus
import com.example.mypractice.ui.adapter.ArticleAdapter
import com.example.mypractice.ui.adapter.BannerAdapter
import com.example.mypractice.ui.main.BannerUiState
import com.example.mypractice.ui.main.DetailUiState
import com.example.mypractice.ui.main.MainIntent
import com.example.mypractice.ui.main.MainViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 */
class HomeFragment : BaseFragment <FragmentHomeBinding> (){
    companion object {
        private const val TAG = "HomeFragment"
    }

    private lateinit var bannerAdapter: BannerAdapter
    private lateinit var articleAdapter: ArticleAdapter
    //koin
    private val mViewModel by viewModel<MainViewModel>()
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater)
    }

    override fun initViews() {
        bannerAdapter = BannerAdapter()
        binding.viewPager.adapter = bannerAdapter

        articleAdapter = ArticleAdapter()
        binding.recyclerView.adapter = bannerAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                context, DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun initDatas() {
        binding.button.setOnClickListener {
            mViewModel.sendUiIntent(MainIntent.GetBanner)
            mViewModel.sendUiIntent(MainIntent.GetDetail(0))
        }

        lifecycleScope.launchWhenStarted {
            mViewModel.loadUiIntentFlow.collect { state ->
                Log.d(TAG, "loadUiStateFlow: $state")
                when (state) {
                    is LoadUiIntent.Error -> Log.d(TAG, state.msg)
                    is LoadUiIntent.ShowMainView -> {
                        binding.viewPager.isVisible = true
                        binding.recyclerView.isVisible = true
                        binding.button.isVisible = false
                    }
                    is LoadUiIntent.Loading -> Log.d(TAG, "show loading")
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            mViewModel.uiStateFlow.map { it.bannerUiState }
                .collect { bannerUiState ->
                    Log.d(TAG,"bannerUiState: $bannerUiState")
                    when (bannerUiState) {
                        is BannerUiState.INIT -> {}
                        is BannerUiState.SUCCESS -> {
                            binding.viewPager.isVisible = true
                            binding.button.isVisible = false
                            val imgs = mutableListOf<String>()
                            for (model in bannerUiState.models) {
                                imgs.add(model.imagePath)
                            }
                            bannerAdapter.setList(imgs)
                        }
                    }
            }
        }

        lifecycleScope.launchWhenStarted {
            mViewModel.uiStateFlow.map { it.detailUiState }
                .collect { detailUiState ->
                    Log.d(TAG, "detailUiState: $detailUiState")
                    when (detailUiState) {
                        is DetailUiState.INIT -> {}
                        is DetailUiState.SUCCESS -> {
                            binding.recyclerView.isVisible = true
                            val list = detailUiState.articles.datas
                            articleAdapter.setList(list)
                        }
                    }
                }
        }

        FlowEventBus.observe<Event.ShowInit>(this, Lifecycle.State.CREATED) {
            it.msg
            binding.viewPager.isVisible = false
            binding.recyclerView.isVisible = false
            binding.button.isVisible = true
        }


    }
}