package com.vamaju.task

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun isMobilePlatform(): Boolean