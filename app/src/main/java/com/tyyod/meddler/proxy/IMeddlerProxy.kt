package com.tyyod.meddler.proxy

/**
 * Meddler代理的接口类
 */
interface IMeddlerProxy {
    /**
     * 启动代理服务，默认端口号：8080
     * @param port 端口号
     */
    fun start(port : Int? = 8080)

    /**
     * 返回代理服务是否在运行
     */
    fun isStart() : Boolean

    /**
     * 停止代理服务
     */
    fun stop()
}