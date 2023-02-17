package com.example.unlimint.utils

import java.util.*

final class FixedQeue <E>(private val limit: Int) : LinkedList<E>() {

    override fun add(o: E): Boolean {
        super.add(o)
        while (size > limit) {
            super.remove()
        }
        return true
    }
}