package com.example.mypractice.eventbus

sealed class Event {
    data class ShowInit(val msg: String) : Event()
}
