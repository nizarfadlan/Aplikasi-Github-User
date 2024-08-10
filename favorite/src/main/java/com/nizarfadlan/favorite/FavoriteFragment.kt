package com.nizarfadlan.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nizarfadlan.aplikasigithubuser.ui.base.BaseFragment
import com.nizarfadlan.aplikasigithubuser.ui.component.ListUserAdapter
import com.nizarfadlan.core.data.remote.response.ItemsItem
import com.nizarfadlan.favorite.databinding.FragmentFavoriteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import com.nizarfadlan.aplikasigithubuser.R as appR

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {
    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private val listAdapter by lazy { ListUserAdapter { username -> moveToDetailUser(username) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(favoriteViewModelModule)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        binding.rvFavoriteUsers.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listAdapter
        }

        favoriteViewModel.getAllFavoriteUsers()
        favoriteViewModel.favoriteUser.observe(viewLifecycleOwner) { listFavoriteUser ->
            val listUsers = listFavoriteUser.map {
                ItemsItem(
                    id = it.id,
                    login = it.username,
                    avatarUrl = it.avatarUrl
                )
            }
            listAdapter.listUsers = listUsers
            showNotFound(listFavoriteUser.isEmpty(), "favorite user")
        }
    }

    private fun moveToDetailUser(username: String) {
        findNavController().navigate(
            FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(
                username
            )
        )
    }

    private fun showNotFound(isNotFound: Boolean, text: String = "data") {
        if (isNotFound) {
            setTextNotFound(text)
            binding.tvNoData.visibility = View.VISIBLE
        } else {
            binding.tvNoData.visibility = View.GONE
        }
    }

    private fun setTextNotFound(text: String) {
        binding.apply {
            val textNotFound = resources.getString(R.string.not_found, text)
            tvNoData.text = textNotFound
        }
    }

    private fun initToolbar() {
        binding.apply {
            toolbar.apply {
                title = "Favorite User"
                inflateMenu(appR.menu.main_menu)
                setNavigationOnClickListener {
                    findNavController().popBackStack()
                }
                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        appR.id.menu_settings -> {
                            findNavController().navigate(
                                FavoriteFragmentDirections.actionFavoriteFragmentToSettingFragment()
                            )
                            true
                        }

                        else -> false
                    }
                }
                menu.findItem(appR.id.menu_favorite)?.isVisible = false
            }
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentFavoriteBinding = FragmentFavoriteBinding.inflate(inflater, container, false)
}