package dev.siro256.kotlin.eventlib

/**
 * このクラスを継承して[EventManager.registerHandler]でハンドラを登録することで、
 * [Event.call]が呼ばれた際にイベントハンドラが呼び出される
 */

abstract class Event {

    /**
     * この関数を呼び出すことで、[EventManager.handlers]に登録されているハンドラが呼び出される
     */

    open fun call() {
        EventManager.callEvent(this)
    }
}