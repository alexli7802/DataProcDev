package dataload

import org.apache.spark.sql.SparkSession
import com.databricks.spark.avro._

object SparkAvro {
  
  val ss = SparkSession.builder().appName("Demo.SparkAvro").getOrCreate()
            
  def writeToAvro(): Unit = {
    
    val testDF = ss.createDataFrame(testdata.Employees.samples)
    testDF.show(false)

    testDF.coalesce(1).write
      .format("com.databricks.spark.avro")
      .mode("overwrite")
      .save("tmp/Employees.avro")
  } 
  
  def readFromAvro(): Unit = {
    val df = ss.read.format("com.databricks.spark.avro")
              .load("tmp/Employees.avro")
    
    println("============ read in avro file: =============")
    df.show(false)
  }
  
  // =============== Main Entry Point ================
  def main(args: Array[String]): Unit = {
    
    writeToAvro()
    readFromAvro()
  }
}