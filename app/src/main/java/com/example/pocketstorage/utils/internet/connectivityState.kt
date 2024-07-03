package com.example.pocketstorage.utils.internet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext

@Composable
fun connectivityState(): State<ConnectionState> {
  val context = LocalContext.current
  return produceState(context.currentConnectActivityState) {
    context.observeConnectivityAsFlow().collect { value = it }
  }
}

