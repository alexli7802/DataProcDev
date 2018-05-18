package consumers

import java.util.Properties
import java.nio.ByteBuffer
import org.apache.kafka.clients.consumer.{KafkaConsumer,ConsumerRecord}
import org.apache.kafka.common.TopicPartition
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

case class PartitionStatus(part: TopicPartition, sOffset: Long, eOffset: Long, lstCommitted: Long) {
  override def toString = {
    val len = eOffset - sOffset
    s"${part.topic()}:${part.partition()}: len=$len, [$sOffset - $eOffset], committed-at: $lstCommitted"
  }
}

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

    val allTopics = consumer.listTopics().keySet().filterNot(_.startsWith("__"))
    
    allTopics.foreach { t => 
      
      val tparts = consumer.partitionsFor(t)
                    .map(p => new TopicPartition(p.topic(), p.partition()) )
                    .toList
      if (t == topic) consumer.assign(tparts)
      
      val stats = {
        val b_offsets = consumer.beginningOffsets(tparts)
        val e_offsets = consumer.endOffsets(tparts)
        
        tparts.map(p => {
          val cmted = consumer.committed(p)
          val cmtOffset = if (cmted == null) 0 else cmted.offset() 
          PartitionStatus(p, b_offsets(p), e_offsets(p), cmtOffset)
        })
      }

      val totalCnt = stats.map(s => (s.eOffset - s.sOffset)).sum
      
      println(s"[$t] overall: $totalCnt / ${stats.length}")
      stats.foreach(s => println("\t" + s.toString))
    }
  }
  
}