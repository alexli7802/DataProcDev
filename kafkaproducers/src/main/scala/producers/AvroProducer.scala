package producers

import java.util.Properties
import java.nio.ByteBuffer
import scala.collection.JavaConversions._
import org.apache.kafka.clients.producer.{KafkaProducer,ProducerRecord}

object AvroProducer {
  
  lazy val cfg = Map(
      "bootstrap.servers" -> "172.16.40.54:9092",
      "key.serializer" -> "org.apache.kafka.common.serialization.StringSerializer",
      "value.serializer" -> "org.apache.kafka.common.serialization.ByteBufferSerializer"
    )
  lazy val props = {
    val p = new Properties()
    p.putAll(cfg)
    p
  }  
  
  lazy val producer = new KafkaProducer[String,ByteBuffer](props)
    
  // produce
  def produce(topic: String, fields: Array[String]): Unit = {
      
    import datasources.SchemaRepo.gtpu
    val msg = new cellos.messages.avro.gtpu_report()
    msg.setImsi(fields(gtpu.indexOf("imsi")))
    msg.setBearerStart(fields(gtpu.indexOf("bearer_creation_time")))
    msg.setReason(fields(gtpu.indexOf("report_reason")))
    msg.setFstPktTs(fields(gtpu.indexOf("user_plane_data_collection_start_time")))
    msg.setLstPktTs(fields(gtpu.indexOf("user_plane_data_collection_end_time")))
    msg.setUlBytes(fields(gtpu.indexOf("delta_bytes_uplink")).toLong)
    msg.setDwBytes(fields(gtpu.indexOf("delta_bytes_downlink")).toLong)
      
    val kfkRecord = new ProducerRecord[String,ByteBuffer](
          topic, msg.toByteBuffer()
        )    
        
    producer.send(kfkRecord)
  }
  
  def main(args: Array[String]): Unit = {
    val gtpu_msg_file = common.AllConf.getString("appConf.datasrc.file.gtpu")
    val gtpu_msg_topic = common.AllConf.getString("kafkaCluster.topics.gtpu")
    
    println(s"gtpu_msg_file=$gtpu_msg_file")
    println(s"gtpu_msg_topic=$gtpu_msg_topic")
    val lines = scala.io.Source.fromFile(gtpu_msg_file).getLines()
    
    var sent = 0
    var avoided = 0
    val messages = lines.map(_.replaceAll("\'", ""))
    messages.foreach( msg => {
      val cols = msg.split(",")
      if (cols.length == 150) {
    	  produce(gtpu_msg_topic, cols)
    	  sent += 1;
      }
      else
        avoided += 1
    })
    
    println(s"sent/avoided: $sent / $avoided")
  }
  
  
}