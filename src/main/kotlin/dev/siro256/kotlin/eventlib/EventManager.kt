package dev.siro256.kotlin.eventlib

import kotlin.reflect.KCallable
import kotlin.reflect.KType
import kotlin.reflect.full.createType
import kotlin.reflect.full.isSubtypeOf

/**
 * ハンドラの登録/解除やイベントの発火を担当するクラス
 */

object EventManager {
    /**
     * イベントハンドラの保持用変数
     */

    private val handlers = hashMapOf<KCallable<*>, Pair<KType, Any>>()

    /**
     * イベントハンドラを登録する
     * @param clazz [EventHandler]アノテーションを付けた関数があるクラス
     */

    fun registerHandler(clazz: Any) {
        val eventHandler = getEventHandler(clazz) ?: return
        handlers.putAll(eventHandler)
    }

    /**
     * イベントハンドラの登録を解除する
     * @param clazz [EventHandler]アノテーションをつけた関数があるクラス
     */

    fun unregisterHandler(clazz: Any) {
        val eventHandler = getEventHandler(clazz) ?: return
        eventHandler.forEach { (callable, _) ->
            handlers.remove(callable)
        }
    }

    /**
     * イベントハンドラの登録を解除する
     * @param kcallable [EventHandler]アノテーションをつけた関数
     */

    fun unregisterHandler(kcallable: KCallable<*>) {
        handlers.remove(kcallable)
    }

    /**
     * イベントを発火する
     * @param event [Event]クラスを継承したクラス
     */

    fun callEvent(event: Event) {
        val ktype = event::class.createType()
        handlers.forEach { (callable, evt) ->
            if (evt.first != ktype) return@forEach
            callable.call(evt.second, event)
        }
    }

    /**
     * 引数のクラスの中を探索して、
     * [EventHandler]アノテーションがついている関数を返却する
     * @param clazz 探索対象のクラス
     * @return Keyが関数、ValueがKTypeと引数のクラスになっているMap
     */

    private fun getEventHandler(clazz: Any): Map<KCallable<*>, Pair<KType, Any>>? {
        val map = mutableMapOf<KCallable<*>, Pair<KType, Any>>()
        clazz::class.members.forEach { member ->
            member.annotations.forEach {annotation ->
                if(annotation.annotationClass == EventHandler::class) {
                    member.parameters.forEach { parameter ->
                        if (parameter.type.isSubtypeOf(Event::class.createType()))
                            map[member] = parameter.type to clazz
                    }
                }
            }
        }
        return if (map.isNotEmpty()) map else null
    }
}