package com.lawsofnatrue.common.ice


import javax.inject.{Inject, Named}

import org.slf4j.LoggerFactory

/**
  * Created by fangzhongwei on 2016/10/27.
  */
trait IceServerTemplate {
  def startServer
}

class IceServerTemplateImpl @Inject()(@Named("ice.server.init.config") iceInitializeConfig: String,
                                      @Named("ice.server.init.size") iceInitSizeConfig: String,
                                      @Named("ice.server.init.size-max") iceInitSizeMaxConfig: String,
                                      @Named("ice.server.init.size-warn") iceInitSizeWarnConfig: String,
                                      @Named("ice.server.adapter.name") adapterName: String,
                                      @Named("ice.server.adapter.config") adapterConfig: String,
                                      @Named("ice.server.proxy.name") proxyName: String,
                                      iceObj: Ice.ObjectImpl) extends IceServerTemplate {
  val logger = LoggerFactory.getLogger(this.getClass)

  override def startServer = {
    val ic: Ice.Communicator = Ice.Util.initialize(Array[String](
      iceInitializeConfig, iceInitSizeConfig, iceInitSizeMaxConfig, iceInitSizeWarnConfig
    ))
    val adapter: Ice.ObjectAdapter = ic.createObjectAdapterWithEndpoints(adapterName, adapterConfig)
    adapter.add(iceObj, Ice.Util.stringToIdentity(proxyName))
    adapter.activate()
    logger.info(proxyName + " template adapter active , waiting for request...")
    //这个方法挂起发出调用的线程，直到服务器实现终止为止。或我们自己发出一个调用关闭。
    ic.waitForShutdown()
    System.exit(0)
  }
}
