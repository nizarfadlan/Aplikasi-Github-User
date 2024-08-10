package com.nizarfadlan.aplikasigithubuser.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.dynamicfeatures.DynamicExtras
import androidx.navigation.dynamicfeatures.DynamicInstallMonitor
import androidx.navigation.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallSessionState
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import com.nizarfadlan.aplikasigithubuser.R

abstract class BaseFragment<viewBinding: ViewBinding> : Fragment() {
    private var _binding: viewBinding? = null
    val binding get() = _binding!!

    protected lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = requireActivity().findNavController(R.id.nav_host_fragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): viewBinding

    protected fun navigateToFavoriteFeature() {
        val destinationId = R.id.favoriteFragment
        val installMonitor = DynamicInstallMonitor()

        navController.navigate(
            destinationId,
            null,
            null,
            DynamicExtras(installMonitor)
        )

        if (installMonitor.isInstallRequired) {
            installMonitor.status.observe(viewLifecycleOwner, object : Observer<SplitInstallSessionState> {
                override fun onChanged(value: SplitInstallSessionState) {
                    when (value.status()) {
                        SplitInstallSessionStatus.INSTALLED -> {
                            navController.navigate(destinationId, null, null, null)
                        }
                        SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                            SplitInstallManagerFactory.create(requireContext())
                                .startConfirmationDialogForResult(value, requireActivity(), REQUEST_CODE)
                        }
                        SplitInstallSessionStatus.FAILED -> {
                            Toast.makeText(requireContext(), "Gagal mengunduh fitur", Toast.LENGTH_SHORT).show()
                        }
                        SplitInstallSessionStatus.CANCELED -> {
                            Toast.makeText(requireContext(), "Unduhan dibatalkan", Toast.LENGTH_SHORT).show()
                        }
                    }

                    if (value.hasTerminalStatus()) {
                        installMonitor.status.removeObserver(this)
                    }
                }
            })
        }
    }

    companion object {
        private const val REQUEST_CODE = 101
    }
}