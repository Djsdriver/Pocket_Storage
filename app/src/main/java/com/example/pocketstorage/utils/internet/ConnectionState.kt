package com.example.pocketstorage.utils.internet

sealed class ConnectionState {
  data object Available : ConnectionState()
  data object Unavailable : ConnectionState()
}