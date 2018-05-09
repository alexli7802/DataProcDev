package example


object MsgInjection {

  import producers._
  
  def main(args: Array[String]): Unit = {
    
    BasicProducer.inject(10)
    
    Thread.sleep(3000)
  }
}