package producers

import java.util.Properties
import scala.collection.JavaConversions._
import org.apache.kafka.clients.producer.{KafkaProducer,ProducerRecord}
object BasicProducer {
  

  lazy val cfg = Map(
      "bootstrap.servers" -> "172.16.40.54:9092",
      "key.serializer" -> "org.apache.kafka.common.serialization.StringSerializer",
      "value.serializer" -> "org.apache.kafka.common.serialization.StringSerializer"
    )  
  lazy val props = new Properties()
  props.putAll(cfg)
  
  
  def inject(n: Int): Unit = {
    val producer = new KafkaProducer[String,String](props)
    
    for (i <- 0 to n) {
      val msg = new ProducerRecord[String,String](
            "test", i.toString, i.toString
          )
      producer.send(msg)
    }
    
    producer.close()
  }
}