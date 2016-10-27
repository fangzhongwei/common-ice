package com.lawsofnatrue.common.ice

import org.slf4j.LoggerFactory

/**
  * Created by fangzhongwei on 2016/10/27.
  */

trait IcePrxFactory {
  def make[T <: Ice.ObjectPrx](iceInitializeConfig: Array[String], proxyConfig: String, checkedCastFunc: Ice.ObjectPrx => T): T
}

class IcePrxFactoryImpl extends IcePrxFactory {
  val logger = LoggerFactory.getLogger(this.getClass)

  override def make[T <: Ice.ObjectPrx](iceInitializeConfig: Array[String], proxyConfig: String, checkedCastFunc: Ice.ObjectPrx => T): T = {
    // Communicator实例
    val ic: Ice.Communicator = Ice.Util.initialize(iceInitializeConfig)
    // 获取代理
    val base: Ice.ObjectPrx = ic.stringToProxy(proxyConfig)
    // 将上面的代理向下转换成一个Printer接口的代理
    val obj: T = checkedCastFunc(base)
    // 如果转换成功
    if (obj == null) throw new Error("Invalid proxy")
    logger.info("make prx " + proxyConfig + " success!")
    obj
  }
}
