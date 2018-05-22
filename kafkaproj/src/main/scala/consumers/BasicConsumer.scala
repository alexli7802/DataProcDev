package consumers

import java.util.Properties
import scala.collection.JavaConversions._
import org.apache.kafka.clients.consumer.{KafkaConsumer,ConsumerRecord}
object BasicConsumer {
  
  // config
  lazy val cfg = Map(
      "bootstrap.servers" -> "172.16.40.54:9092",
      "group.id" -> "test-grp",
      "key.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
      "value.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer"
    )
  lazy val prop = {
    val p = new Properties()
    p.putAll(cfg)
    p
  }
  
  // wrap a KafkaConsumer
  lazy val consumer = new KafkaConsumer[String,String](prop)
  
  // assign topics
  def subscribe(topics: String*): Unit = consumer.subscribe(topics)

  private def record2String(cdr: ConsumerRecord[String,String]): String = 
    "[" + cdr.topic() + ":" + cdr.partition() + "] " +
    "key=" + cdr.key() + ", value=" + cdr.value()
    
    
  // infinite loop for processing Kafka records !!!
  def process(): Unit = {
    var cnt = 0
    while (true) {
      val cdrs = consumer.poll(5000)     // timeout = 100ms
      cnt += 1
      println(s"--> poll: ($cnt)")
      cdrs.foreach(cdr => {
        cdr.topic()
        println(record2String(cdr))
      })
    }
  }
  
  
  def main(args: Array[String]): Unit = {
    
    val allTopics = consumer.listTopics().keySet()
    println("all available topics: " + allTopics.mkString("[", ",", "]"))
    
    subscribe(allTopics.head)
    
    process()
  }
}