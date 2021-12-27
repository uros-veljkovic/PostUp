package com.urkeev14.myapplication.feature.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.urkeev14.myapplication.R
import com.urkeev14.myapplication.data.source.local.entity.TypicodePostEntity
import com.urkeev14.myapplication.data.source.remote.dto.TypicodePostId
import com.urkeev14.myapplication.data.source.remote.dto.TypicodeUserId
import com.urkeev14.myapplication.databinding.FragmentPostsBinding
import com.urkeev14.myapplication.utils.extensions.visibleOrGone
import com.urkeev14.myapplication.utils.ui.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class PostsFragment : Fragment(), PostsAdapter.Callback {

    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PostsViewModel by viewModels()
    private val adapter by lazy { PostsAdapter(emptyList(), this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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

            layoutError.buttonTryAgain.setOnClickListener { refreshPosts() }
        }
    }

    private fun refreshPosts() {
        viewModel.fetchPosts()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                when (it) {
                    is UiState.Error -> handleError(it)
                    is UiState.Loading -> handleViewVisibility(it)
                    is UiState.Success -> handleSuccess(it)
                }
            }
        }
    }

    private fun handleViewVisibility(state: UiState<List<TypicodePostEntity>>) = with(binding) {
        progressBar.visibleOrGone(state is UiState.Loading)
        layoutError.root.visibleOrGone(state is UiState.Error)
        recyclerView.visibleOrGone(state is UiState.Success)
    }

    private fun handleError(state: UiState.Error<List<TypicodePostEntity>>) {
        handleViewVisibility(state)

        state.error?.message?.let {
            binding.layoutError.textViewDescription.text = it
        }
    }

    private fun handleSuccess(state: UiState.Success<List<TypicodePostEntity>>) {
        handleViewVisibility(state)
        adapter.setList(state.data ?: emptyList())
    }

    override fun onPostClick(postId: TypicodePostId, userId: TypicodeUserId) {
        val destination = PostsFragmentDirections.actionPostsFragmentToPostFragment(userId, postId)
        findNavController().navigate(destination)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_posts_fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.refresh -> {
                refreshPosts()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}