package com.urkeev14.myapplication.feature.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.urkeev14.myapplication.R
import com.urkeev14.myapplication.data.source.local.entity.TypicodePostEntity
import com.urkeev14.myapplication.databinding.FragmentPostsBinding
import com.urkeev14.myapplication.utils.state.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class PostsFragment : Fragment(), PostsAdapter.Callback {

    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PostsViewModel by viewModels()
    private val adapter by lazy { PostsAdapter(emptyList(), this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        val itemMargin = requireContext().resources.getDimension(R.dimen.margin_small).toInt()
        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = adapter
            recyclerView.addItemDecoration(PostsItemDecorator(itemMargin))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                when (it) {
                    is UiState.Error -> {}
                    is UiState.Loading -> {}
                    is UiState.Success -> handleSuccess(it.data!!)
                }
            }
        }
    }

    private fun handleSuccess(list: List<TypicodePostEntity>) {
        adapter.setList(list)
    }

    override fun onPostClick(id: Int) {
        Snackbar.make(requireView(), "Post ID: $id", Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}