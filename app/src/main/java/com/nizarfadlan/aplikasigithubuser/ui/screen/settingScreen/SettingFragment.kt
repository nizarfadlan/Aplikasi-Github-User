package com.nizarfadlan.aplikasigithubuser.ui.screen.settingScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nizarfadlan.aplikasigithubuser.databinding.FragmentSettingBinding
import com.nizarfadlan.aplikasigithubuser.ui.base.BaseFragment
import com.nizarfadlan.aplikasigithubuser.ui.component.ThemeDialogFragment
import com.nizarfadlan.aplikasigithubuser.utils.capitalizeWords
import com.nizarfadlan.core.domain.model.ThemeMode
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingFragment : BaseFragment<FragmentSettingBinding>() {
    private val settingViewModel: SettingViewModel by viewModel()
    private var selectedThemeMode: ThemeMode = ThemeMode.SYSTEM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        binding.switchTheme.setOnClickListener {
            showThemeDialog()
        }

        settingViewModel.getThemeSettings().observe(viewLifecycleOwner) { themeMode ->
            binding.tvTheme.text = themeMode.name.capitalizeWords()
        }
    }

    private fun showThemeDialog() {
        val themeDialog = ThemeDialogFragment { selectedTheme ->
            selectedThemeMode = selectedTheme
            settingViewModel.saveThemeSetting(selectedTheme)
        }
        themeDialog.show(parentFragmentManager, "ThemeDialogFragment")
    }

    private fun initToolbar() {
        binding.apply {
            toolbar.apply {
                title = "Settings"
                setNavigationOnClickListener {
                    findNavController().popBackStack()
                }
            }
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSettingBinding = FragmentSettingBinding.inflate(inflater, container, false)
}
