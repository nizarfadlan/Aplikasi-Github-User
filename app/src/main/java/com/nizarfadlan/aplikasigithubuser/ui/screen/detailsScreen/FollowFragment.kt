package com.nizarfadlan.aplikasigithubuser.ui.screen.detailsScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.nizarfadlan.aplikasigithubuser.R
import com.nizarfadlan.aplikasigithubuser.ui.component.ListUserAdapter
import com.nizarfadlan.aplikasigithubuser.databinding.FragmentFollowBinding
import com.nizarfadlan.aplikasigithubuser.ui.base.BaseFragment
import com.nizarfadlan.aplikasigithubuser.utils.Helper.handleError
import com.nizarfadlan.core.data.remote.response.ItemsItem
import com.nizarfadlan.core.domain.common.ResultState
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowFragment : BaseFragment<FragmentFollowBinding>() {
    private val detailViewModel: DetailViewModel by viewModel()
    private val listAdapter by lazy { ListUserAdapter(isClickable = false) }

    private var username: String? = null
    private var position: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        setAdapter()

        if (position == 1) {
            setFollowersData(savedInstanceState)
        } else {
            setFollowingData(savedInstanceState)
        }
    }

    private fun setAdapter() {
        binding.apply {
            rvFollow.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = listAdapter
            }
        }
    }

    private fun setFollowersData(savedInstanceState: Bundle?) {
        username.takeIf { savedInstanceState === null }?.let { detailViewModel.getFollowers(it) }
        detailViewModel.followersUser.observe(viewLifecycleOwner) { result ->
            if (result !== null) {
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true)
                    }

                    is ResultState.Success -> {
                        showLoading(false)
                        setListFollowAdapter(result.data)
                    }

                    is ResultState.Error -> {
                        showLoading(false)
                        requireContext().handleError(DetailViewModel.TAG, result.error)
                    }
                }
            }
        }
    }

    private fun setFollowingData(savedInstanceState: Bundle?) {
        username.takeIf { savedInstanceState === null }?.let { detailViewModel.getFollowing(it) }
        detailViewModel.followingUser.observe(viewLifecycleOwner) { result ->
            if (result !== null) {
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true)
                    }

                    is ResultState.Success -> {
                        showLoading(false)
                        setListFollowAdapter(result.data)
                    }

                    is ResultState.Error -> {
                        showLoading(false)
                        requireContext().handleError(DetailViewModel.TAG, result.error)
                    }
                }
            }
        }
    }

    private fun setListFollowAdapter(listFollow: List<ItemsItem>) {
        listAdapter.listUsers = listFollow
        showEmptyView(listFollow.isEmpty())
    }

    private fun showEmptyView(isEmpty: Boolean) {
        binding.apply {
            tvEmpty.visibility = if (isEmpty) View.VISIBLE else View.GONE
            tvEmpty.text = resources.getString(R.string.not_found, "user")
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentFollowBinding = FragmentFollowBinding.inflate(inflater, container, false)

    companion object {
        const val ARG_POSITION = "arg_position"
        const val ARG_USERNAME = "arg_username"
    }
}