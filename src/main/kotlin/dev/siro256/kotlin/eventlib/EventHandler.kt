package dev.siro256.kotlin.eventlib

/**
 * 関数の引数にEventを取り、このアノテーションを付けて
 * [EventManager.registerHandler]を呼び出すことで、
 * [Event.call]または[EventManager.callEvent]が呼び出された際に
 * 関数が実行される
 */

@Target(AnnotationTarget.FUNCTION)
annotation class EventHandler