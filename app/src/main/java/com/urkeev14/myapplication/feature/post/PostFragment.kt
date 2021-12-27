package com.urkeev14.myapplication.feature.post

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
import androidx.navigation.fragment.navArgs
import com.urkeev14.myapplication.R
import com.urkeev14.myapplication.data.source.local.entity.TypicodePostDetailsEntity
import com.urkeev14.myapplication.databinding.FragmentPostBinding
import com.urkeev14.myapplication.utils.extensions.visibleOrGone
import com.urkeev14.myapplication.utils.ui.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class PostFragment : Fragment() {

    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PostViewModel by viewModels()
    private val args: PostFragmentArgs by navArgs()
    private val userId by lazy { args.userId }
    private val postId by lazy { args.postId }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPostBinding.inflate(inflater)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        binding.layoutError.buttonTryAgain.setOnClickListener {
            fetchDetails()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fetchDetails()
        observeData()
    }

    private fun fetchDetails() {
        viewModel.fetchDetails(postId, userId)
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


    private fun handleViewVisibility(state: UiState<TypicodePostDetailsEntity>) = with(binding) {
        progressBar.visibleOrGone(state is UiState.Loading)
        layoutError.root.visibleOrGone(state is UiState.Error)

        imageView.visibleOrGone(state is UiState.Success)
        textViewTitle.visibleOrGone(state is UiState.Success)
        textViewContent.visibleOrGone(state is UiState.Success)
        textViewCreatorPlaceholder.visibleOrGone(state is UiState.Success)
        textViewCreator.visibleOrGone(state is UiState.Success)
    }

    private fun handleError(state: UiState.Error<TypicodePostDetailsEntity>) {
        handleViewVisibility(state)

        state.error?.message?.let {
            binding.layoutError.textViewDescription.text = it
        }
    }

    private fun handleSuccess(state: UiState.Success<TypicodePostDetailsEntity>) {
        handleViewVisibility(state)

        with(binding) {
            state.data?.let {
                textViewTitle.text = it.post.title
                textViewContent.text = it.post.content
                textViewCreator.text = "${it.user.fullName}(${it.user.email})"
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_posts_fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete -> {
                viewModel.deletePost()
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