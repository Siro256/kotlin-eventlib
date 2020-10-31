package dev.siro256.kotlin.eventlib

abstract class Event {
    open fun call() {
        EventManager.callEvent(this)
    }
}