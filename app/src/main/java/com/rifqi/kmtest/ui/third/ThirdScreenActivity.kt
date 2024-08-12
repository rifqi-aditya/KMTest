package com.rifqi.kmtest.ui.third

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rifqi.kmtest.databinding.ActivityThirdScreenBinding
import com.rifqi.kmtest.ui.ViewModelFactory
import com.rifqi.kmtest.ui.adapter.LoadingStateAdapter
import com.rifqi.kmtest.ui.adapter.UserAdapter
import kotlinx.coroutines.launch

class ThirdScreenActivity : AppCompatActivity() {
    private val viewModel by viewModels<ThirdViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val binding by lazy {
        ActivityThirdScreenBinding.inflate(layoutInflater)
    }

    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupRecyclerView()
        setupSwipeRefreshLayout()
        observeViewModel()
        setupBackButton()
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter()
        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(this@ThirdScreenActivity)
            adapter = userAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    userAdapter.retry()
                }
            )
            addItemDecoration(DividerItemDecoration(this@ThirdScreenActivity, DividerItemDecoration.VERTICAL))
        }
    }

    private fun setupSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            userAdapter.refresh()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.users.collect { pagingData ->
                userAdapter.submitData(pagingData)
            }
        }
        userAdapter.addLoadStateListener { loadState ->
            binding.tvEmptyState.visibility = if (loadState.refresh is LoadState.NotLoading && userAdapter.itemCount == 0) {
                android.view.View.VISIBLE
            } else {
                android.view.View.GONE
            }
            binding.swipeRefreshLayout.isRefreshing = loadState.refresh is LoadState.Loading
        }
    }

    private fun setupBackButton() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}