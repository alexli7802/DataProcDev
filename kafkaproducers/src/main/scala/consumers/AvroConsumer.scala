package consumers

import java.util.Properties
import java.nio.ByteBuffer
import org.apache.kafka.clients.consumer.{KafkaConsumer,ConsumerRecord}
import org.apache.kafka.common.TopicPartition
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import cellos.messages.avro.gtpu_report

case class PartitionStatus(part: TopicPartition, sOffset: Long, eOffset: Long, lstCommitted: Long) {
  override def toString = {
    val len = eOffset - sOffset
    s"${part.topic()}:${part.partition()}: len=$len, [$sOffset - $eOffset], committed-at: $lstCommitted"
  }
  
  def messageCount = eOffset - sOffset
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
 
 
  def partitionStatus(parts: List[TopicPartition]): List[PartitionStatus] = {
    val b_offsets = consumer.beginningOffsets(parts)
    val e_offsets = consumer.endOffsets(parts)
    parts.map(p => {
      val lstCommitted = consumer.committed(p)
      val cmtOffset = if (lstCommitted == null) 0 else lstCommitted.offset()
      PartitionStatus(p, b_offsets(p), e_offsets(p), cmtOffset)
    })
  }
    
  def singlePoll(): Unit = {

    val poll_ms = common.AllConf.getInteger("kafkaCluster.consumer.timeouts.poll_ms")
    val cdrs = consumer.poll(poll_ms)     // timeout for poll = 100ms
		println(s"consumer polls ${cdrs.count()} messages --->")
      
    // batch-processing
		val (fst, lst) = {
      val l = cdrs.iterator().toList
      (l.head, l.last)
    }
    
    println(s"\t${fst.topic()}-${fst.partition()} [offset=${fst.offset()}]: " + gtpu_report.fromByteBuffer(fst.value()))              
    println("\t... ...")
    println(s"\t${lst.topic()}-${lst.partition()} [offset=${lst.offset()}]: " + gtpu_report.fromByteBuffer(lst.value()))              

    cdrs.foreach(cdr => {
      val msg = gtpu_report.fromByteBuffer(cdr.value())
//      println(s"${cdr.topic()}-${cdr.partition()} [offset=${cdr.offset()}]: " + msg)              
    })
   
    consumer.commitSync()
  }
  
  // application entries here ----->
  def main(args: Array[String]): Unit = {
    
    val topic = common.AllConf.getString("kafkaCluster.topics.gtpu")
    val tparts = consumer.partitionsFor(topic).map(p => new TopicPartition(p.topic(), p.partition())).toList
    consumer.assign(tparts)
    
    val fromBegin = common.AllConf.getBoolean("kafkaCluster.consumer.startover")
    if (fromBegin) consumer.seekToBeginning(tparts)
    
    val partStats = partitionStatus(tparts)
    println(s"[$topic] overall: ${partStats.map(_.messageCount).sum} / ${partStats.length}")
    partStats.foreach(s => println("\t" + s.toString))
    
    //consuming
    singlePoll()
  }
  
}