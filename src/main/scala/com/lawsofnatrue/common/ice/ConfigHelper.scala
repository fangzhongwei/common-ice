package com.lawsofnatrue.common.ice

import java.util

import com.typesafe.config.{Config, ConfigFactory}

/**
  * Created by fangzhongwei on 2016/10/27.
  */
object ConfigHelper {
  import scala.collection.JavaConversions._

  def configMap: util.HashMap[String, String] = {
    val config: Config = ConfigFactory.load()
    val map: util.HashMap[String, String] = new java.util.HashMap[String, String]()
    for (entry <- config.entrySet()) {
      val value: String = entry.getValue.render()
      map.put(entry.getKey, value.substring(1, value.length - 1))
    }
    map
  }

}
