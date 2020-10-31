package dev.siro256.kotlin.eventlib

/**
 * このクラスをextendsしてEventManagerにハンドラを登録することで、
 * call()が呼ばれた際にイベントハンドラが呼び出される
 */

abstract class Event {

    /**
     * この関数を呼び出すことで、このクラスのハンドラが実行される
     */

    open fun call() {
        EventManager.callEvent(this)
    }
}