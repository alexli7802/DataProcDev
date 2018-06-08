import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
  lazy val sparkVer = "2.1.0"
  lazy val sparkDeps = Seq(
      "org.apache.spark" %% "spark-core" % sparkVer % "provided",
      "org.apache.spark" %% "spark-sql" % sparkVer % "provided"
    )
    
  lazy val esDeps = "org.elasticsearch" %% "elasticsearch-spark-20" % "6.2.4"
  lazy val avroSupp = "com.databricks" %% "spark-avro" % "4.0.0"
}


