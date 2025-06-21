package br.com.joaovq.common.di.annotations

import javax.inject.Qualifier

@Qualifier
annotation class LunarDispatcher(val dispatcher: MyDispatchers)

enum class MyDispatchers {
    Default,
    IO,
}
