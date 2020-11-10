package com.tyyod.meddler.proxy.impl

import com.tyyod.meddler.proxy.IMeddlerProxy
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.http.HttpRequest
import org.littleshoot.proxy.HttpFilters
import org.littleshoot.proxy.HttpFiltersSource
import org.littleshoot.proxy.HttpProxyServer
import org.littleshoot.proxy.impl.DefaultHttpProxyServer
import java.lang.IllegalStateException
import java.net.InetSocketAddress
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Meddle代理服务的实现
 */
class IMeddleProxyImpl : IMeddlerProxy {

    companion object {
        @JvmStatic
        private val VIA_HEADER_ALIAS: String = "MeddleProxyImpl"
    }

    /**
     * 仅当代理服务成功启动后才会是True
     */
    private val isStarted: AtomicBoolean = AtomicBoolean(false)

    /**
     * 当代理服务启动后，然后再停止/终止才会是True
     */
    private val isStopped: AtomicBoolean = AtomicBoolean(false)

    private var proxyServer: HttpProxyServer? = null

    override fun start(port: Int?) {
        val notStarted: Boolean = isStarted.compareAndSet(false, true)
        if (!notStarted) {
            throw IllegalStateException("Meddle Proxy server is already started.")
        }
        val clientBindSocket = InetSocketAddress(port?: 8080)

        val bootstrap = DefaultHttpProxyServer.bootstrap()
                .withFiltersSource(object: HttpFiltersSource {
                    override fun filterRequest(originalRequest: HttpRequest?, ctx: ChannelHandlerContext?): HttpFilters {
                        TODO("Not yet implemented")
                    }

                    override fun getMaximumRequestBufferSizeInBytes(): Int {
                        TODO("Not yet implemented")
                    }

                    override fun getMaximumResponseBufferSizeInBytes(): Int {
                        TODO("Not yet implemented")
                    }

                }).withAddress(clientBindSocket)
                .withProxyAlias(VIA_HEADER_ALIAS)
        proxyServer = bootstrap.start()
    }

    override fun isStart(): Boolean {
        return isStarted.get()
    }

    override fun stop() {
        if (isStart()) {
            if (isStopped.compareAndSet(false, true)) {
                proxyServer?.stop()
            }
        }
    }
}