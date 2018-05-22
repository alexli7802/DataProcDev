import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
  lazy val kafka = "org.apache.kafka" % "kafka-clients" % "1.1.0"
  lazy val avro = "org.apache.avro" % "avro" % "1.8.2"
  lazy val typesafeCfg = "com.typesafe" % "config" % "1.3.2"
}
