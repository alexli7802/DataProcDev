package common

import com.typesafe.config.ConfigFactory

object AllConf {

  lazy val cfg = ConfigFactory.load
  
  def getString(p: String) = cfg.getString(p)
  
  def main(args: Array[String]): Unit = {
    
    println("========== config ============")
    println(cfg.root().render())
  }
}