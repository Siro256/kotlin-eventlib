package dev.siro256.kotlin.eventlib

/**
 * このクラスを継承して[EventManager.registerHandler]でハンドラを登録することで、
 * [Event.call]が呼ばれた際にイベントハンドラが呼び出される
 *
 * @author Siro_256
 * @since 1.0.0
 */

abstract class Event {

    /**
     * この関数を呼び出すことで、[EventManager.handlers]に登録されているハンドラが呼び出される
     *
     * @author Siro_256
     * @since 1.0.0
     */

    open fun call() {
        EventManager.callEvent(this)
    }
}