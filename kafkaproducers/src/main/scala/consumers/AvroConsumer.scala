package consumers

import java.util.Properties
import java.nio.ByteBuffer
import org.apache.kafka.clients.consumer.{KafkaConsumer,ConsumerRecord}
import org.apache.kafka.common.TopicPartition
import scala.collection.JavaConversions._

object AvroConsumer {
  
  lazy val cfg = Map(
      "bootstrap.servers" -> "172.16.40.54:9092",
      "group.id" -> "gtpu-consgroup",
      "key.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
      "value.deserializer" -> "org.apache.kafka.common.serialization.ByteBufferDeserializer"
    )
    
  lazy val props = {
    val p = new Properties()
    p.putAll(cfg)
    p
  }
  
  lazy val consumer = new KafkaConsumer[String,ByteBuffer](props)
  
  // infinite loop for processing Kafka records !!!
  def process(): Unit = {
    
  } 
 
  def main(args: Array[String]): Unit = {
    
    val topic = common.AllConf.getString("kafkaCluster.topics.gtpu")

//    consumer.subscribe(List(topic))
    val parts = consumer.partitionsFor(topic)
    val tparts = parts.map(p => new TopicPartition(topic, p.partition()))

    consumer.beginningOffsets(tparts).foreach(println)
    consumer.endOffsets(tparts).foreach(println)

    consumer.assign(tparts)
    consumer.assignment().foreach(println)
    
    val metrics = consumer.metrics()
    metrics.keySet().foreach(println)

    tparts.foreach(p => {
      println(s"committed ${p.topic()}: " + consumer.committed(p) + s", next: ${consumer.position(p)}")
    })
  }
  
}