package com.anysoftkeyboard.janus.app.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.anysoftkeyboard.janus.app.viewmodels.SimpleViewModel

@Composable
fun SimpleScreen(viewModel: SimpleViewModel) {
  Text(text = viewModel.message)
}
