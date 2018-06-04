package dataload

/*		setup env.
 * 			- elasticsearch
 * 				1. elasticsearch.yml ---->  network.host: 0.0.0.0
 * 				2. limit on open-file-handles -----> sudo su; ulimit -n 65536; su
 * 				3. increase mmap counts -----> sysctl -w vm.max_map_count=262144
 * 
 * 		spark.app
 * 			- sbt assembly
 * 			- bin/spark-submit --master=local --class=dataload.ESLoader target/.../xxxx.jar
 * 
 * 		For more configuration
 * 			- https://www.elastic.co/guide/en/elasticsearch/hadoop/current/configuration.html
 * */

object ESLoader {
  
  import org.apache.spark.sql.SparkSession
  import org.apache.spark.sql.functions._
  import org.apache.spark.sql.types._
  import org.elasticsearch.spark.sql._
  
  val ss = SparkSession.builder()
            .appName("ESLoader")
            .config("es.index.auto.create", "true")
            .config("es.nodes", "172.16.40.139")
            .config("es.port", "9200")
            .config("es.net.http.auth.user", "elastic")
            .config("es.net.http.auth.pass", "elastic")
            .config("es.nodes.wan.only", "false")
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

    val df = csvReader.csv(srcFile)
    
    df.show(false)
    
    df.saveToEs("spark/docs")
  }

  // ------------- write ctrl reports into ES -------------------- 
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

    val parsedDF = csvReader.csv(path)
    
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

    parsedDF.saveToEs("cplan/docs")
  }
  
  // -------------- read from ES ----------------
  def readGTPC(): Unit = {
    val df = ss.read.format("es").load("cplan/docs")
    df.printSchema()
  }
  
  def main(args: Array[String]): Unit = {
    
    println("=========== dataload.ESLoader ===========")
//    test()

    //write to ES
//    val gtpcFile = "/home/ali/gitlab/bigdata_analytics_test_data/RA_DU_Data/1001014_GTPC_20170717170000.dat"
//    loadGTPC(gtpcFile)
    
    //read from ES
    readGTPC()
  }
  
  
}