package dataload

object ESLoader {
  
  import org.apache.spark.sql.SparkSession
  import org.apache.spark.sql.functions._
  import org.apache.spark.sql.types._
  
  val ss = SparkSession.builder()
            .appName("ESLoader")
            .getOrCreate()
  
  // test with a simple made-up file
  def test(): Unit = {
    val srcFile = "/home/ali/github/DataProcDev/sparkproj/data/csv/staff.csv"
    val scma = StructType( 
        Seq("name", "department", "age", "email", "ParseFailed")
          .map(new StructField(_, StringType, true))  
      )    
      
    val csvReader = ss.read.format("csv")
      .option("head", "false")
      .option("sep", ",")
      .option("quote", "\'")
      .option("comment", "#")
      .option("mode", "PERMISSIVE")
      .option("columnNameOfCorruptRecord", "ParseFailed")
      .schema(scma)

    val ds = ss.read.textFile(srcFile)
    println("loaded lines: " + ds.count())

    val df = csvReader.csv(ds)
    println("parsed records: " + df.count())
    df.show(false)
    
    df.where(
      col("ParseFailed").isNotNull  
    ).select(
      count("*") as "corrupted"
    ).show(false)
  }
  
  def loadGTPC(path: String) = {
    
    val csvReader = ss.read.format("csv")
      .option("head", "false")
      .option("sep", ",")
      .option("quote", "\'")
      .option("comment", "#")
      .option("mode", "PERMISSIVE")
      .option("columnNameOfCorruptRecord", "Corrupted")
      .schema(common.schema.GTPC.schema("Corrupted"))

    val ds = ss.read.textFile(path)
    println(path + " loaded, lines: " + ds.count())

    val parsedDF = csvReader.csv(ds)
    val taggedDf = parsedDF.withColumn(
        "badCDR", when(col("Corrupted").isNotNull,1).otherwise(0)
      ).withColumn(
        "goodCDR", when(col("Corrupted").isNull,1).otherwise(0) 
      )
    
    val SumDF = taggedDf.select(
  		count("*") as "loaded",
  		sum(col("goodCDR")) as "parsed",
      sum(col("badCDR")) as "corrupted"
    )
    
    SumDF.show(false)
  }
  
  def main(args: Array[String]): Unit = {
    
    println("=========== dataload.ESLoader ===========")
//    test()
    
    val gtpcFile = "/home/ali/gitlab/bigdata_analytics_test_data/RA_DU_Data/1001014_GTPC_20170717170000.dat"
    loadGTPC(gtpcFile)      
  }
  
  
}