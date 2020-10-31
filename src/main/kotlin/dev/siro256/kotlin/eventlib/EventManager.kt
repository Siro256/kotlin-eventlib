package dev.siro256.kotlin.eventlib

import kotlin.reflect.KCallable
import kotlin.reflect.KType
import kotlin.reflect.full.createType
import kotlin.reflect.full.isSubtypeOf

object EventManager {
    private val handlers = hashMapOf<KCallable<*>, Pair<KType, Any>>()

    fun registerHandler(clazz: Any) {
        val eventHandler = getEventHandler(clazz) ?: return
        handlers.putAll(eventHandler)
    }

    fun unregisterHandler(clazz: Any) {
        val eventHandler = getEventHandler(clazz) ?: return
        eventHandler.forEach { (callable, _) ->
            handlers.remove(callable)
        }
    }

    fun unregisterHandler(kcallable: KCallable<*>) {
        handlers.remove(kcallable)
    }

    fun callEvent(event: Event) {
        val ktype = event::class.createType()
        handlers.forEach { (callable, evt) ->
            if (evt.first != ktype) return@forEach
            callable.call(evt.second, event)
        }
    }

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