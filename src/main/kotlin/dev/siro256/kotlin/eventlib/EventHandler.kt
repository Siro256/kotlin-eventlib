package dev.siro256.kotlin.eventlib

/**
 * 関数の引数にEventを取り、このアノテーションを付けて
 * [EventManager.registerHandler]を呼び出すことで、
 * 引数のEventが発生した際にメソッドが呼び出される
 */

@Target(AnnotationTarget.FUNCTION)
annotation class EventHandler