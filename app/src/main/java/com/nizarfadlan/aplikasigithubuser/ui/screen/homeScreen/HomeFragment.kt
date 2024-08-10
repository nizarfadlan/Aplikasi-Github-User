package com.nizarfadlan.aplikasigithubuser.ui.screen.homeScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nizarfadlan.aplikasigithubuser.R
import com.nizarfadlan.aplikasigithubuser.ui.component.ListUserAdapter
import com.nizarfadlan.aplikasigithubuser.databinding.FragmentHomeBinding
import com.nizarfadlan.aplikasigithubuser.ui.base.BaseFragment
import com.nizarfadlan.aplikasigithubuser.utils.Helper.handleError
import com.nizarfadlan.core.domain.common.ResultState
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val homeViewModel: HomeViewModel by viewModel()
    private val listAdapter by lazy { ListUserAdapter { username -> moveToDetailUser(username) } }
    private val usernameDefault = HomeViewModel.GITHUB_USERNAME_DEFAULT

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            rvUsers.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = listAdapter
            }
        }

        homeViewModel.searchUser.observe(viewLifecycleOwner) { result ->
            result?.let {
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true)
                    }

                    is ResultState.Success -> {
                        showLoading(false)
                        showNotFound(false)
                        if (result.data.totalCount == 0 || result.data.incompleteResults) {
                            showNotFound(true, "user")
                        }
                        listAdapter.listUsers = result.data.items
                    }

                    is ResultState.Error -> {
                        showLoading(false)
                        requireActivity().handleError(HomeViewModel.TAG, result.error)
                    }
                }
            }
        }

        setSearchBar()
    }

    private fun setSearchBar() {
        binding.apply {
            searchBar.inflateMenu(R.menu.main_menu)
            searchBar.menu.findItem(R.id.menu_settings).setShowAsAction(0)
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, _, _ ->
                    val searchText = textView.text.toString()
                    searchBar.setText(searchText)

                    val usernameSearch = searchText.ifEmpty { usernameDefault }
                    homeViewModel.getAllUsers(usernameSearch)
                    searchView.hide()

                    false
                }

            searchBar.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_favorite -> {
                        navigateToFavoriteFeature()
                        true
                    }

                    R.id.menu_settings -> {
                        findNavController().navigate(
                            HomeFragmentDirections.actionHomeFragmentToSettingFragment()
                        )
                        true
                    }

                    else -> false
                }
            }
        }
    }

    private fun moveToDetailUser(username: String) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                username
            )
        )
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun showNotFound(isNotFound: Boolean, text: String = "data") {
        binding.apply {
            if (isNotFound) {
                setTextNotFound(text)
                tvNoData.visibility = View.VISIBLE
            } else {
                tvNoData.visibility = View.GONE
            }
        }
    }

    private fun setTextNotFound(text: String) {
        binding.apply {
            val textNotFound = resources.getString(R.string.not_found, text)
            tvNoData.text = textNotFound
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
}