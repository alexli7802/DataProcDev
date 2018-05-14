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
  lazy val props = {
    val p = new Properties()
		p.putAll(cfg)
		p
  }
  
  lazy val producer = new KafkaProducer[String,String](props)
 
  // produce
  def produce(topic: String, n: Int): Unit = {
    
    for (i <- 0 to n) {
      val msg = new ProducerRecord[String,String](
            topic, i.toString, i.toString
          )
      producer.send(msg)
    }
    
    producer.close()
  }
  
  
  def main(args: Array[String]): Unit = {

    val topic = args.headOption.getOrElse("test") 
    val numOfRecords = if (args.length >= 2) args(1).toInt else 20
    
    produce(topic, numOfRecords)
  }
}