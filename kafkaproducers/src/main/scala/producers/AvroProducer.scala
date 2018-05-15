package producers

object AvroProducer {
  
  lazy val cfg = Map(
      "bootstrap.servers" -> "172.16.40.54:9092",
      "key.serializer" -> "org.apache.kafka.common.serialization.StringSerializer",
      "value.serializer" -> "org.apache.kafka.common.serialization.ByteBufferSerializer"
    )
    
    
  def produce(topic: String, srcFile: String): Unit = {
      
    val lines = scala.io.Source.fromFile(srcFile).getLines()
                .map(_.replaceAll("\'",""))
                .map(_.split(","))
                .filter(_.length == 150)

    import datasources.SchemaRepo.gtpu
    lines.foreach { l =>
      
      val msg = new cellos.messages.avro.gtpu_report()
      msg.setImsi(l(gtpu.indexOf("imsi")))
      msg.setBearerStart(l(gtpu.indexOf("bearer_creation_time")))
      msg.setReason(l(gtpu.indexOf("report_reason")))
      msg.setFstPktTs(l(gtpu.indexOf("user_plane_data_collection_start_time")))
      msg.setLstPktTs(l(gtpu.indexOf("user_plane_data_collection_end_time")))
      msg.setUlBytes(l(gtpu.indexOf("delta_bytes_uplink")).toLong)
      msg.setDwBytes(l(gtpu.indexOf("delta_bytes_downlink")).toLong)
      
//      msg.toByteBuffer().
    }
    
  }
  
  
}