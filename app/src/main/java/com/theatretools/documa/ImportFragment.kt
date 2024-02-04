package com.theatretools.documa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.theatretools.documa.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ImportFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View?{
        return ComposeView(requireContext()).apply{
            setContent {
                Button(onClick = { docActivityResult.launch(null)}) {
                    Text("Import aus individuellen Preset-Xmls")
                }
                Text(resultText?:"null")
            }
        }
    }


}


