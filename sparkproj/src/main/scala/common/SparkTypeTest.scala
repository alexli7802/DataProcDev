package common

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

object SparkTypeTest {
  val ss = SparkSession.builder()
            .appName("SparkTypeTest")
            .getOrCreate()
  
  def main(args: Array[String]): Unit = {
    import testdata.Employees
    val df0 = ss.createDataFrame(Employees.samples)        
    
    val df1 = df0.select(
      col("dob").cast("date"),                       // call to 'sql.functions.to_date(...)'
      col("dob").cast("timestamp") as "dob_ts",      // call to 'sql.functions.to_timestamp(...)'
      col("age").cast("double"),
      col("isMale").cast("boolean"),
      col("children").cast("int")
    )

    df1.show(false)
    df1.printSchema()
    
  }
}