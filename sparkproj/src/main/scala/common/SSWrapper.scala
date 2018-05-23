package common

import org.apache.spark.sql.{SparkSession}
import org.apache.spark.sql.streaming.DataStreamReader
import org.apache.spark.sql.types.StructType

object SSWrapper {
  
  val ss = SparkSession.builder().getOrCreate()
  
  def fileSource() = ss.read
  
  def csvStreaming(scma: StructType): DataStreamReader = {
    ss.readStream.format("csv")
      .schema(scma)                       // by default, file-based streaming needs 'schema'
  }
  
  def socketStreaming(host: String, port: Int): DataStreamReader = 
    ss.readStream.format("socket")
      .option("host", host)
      .option("port", port)

  
}