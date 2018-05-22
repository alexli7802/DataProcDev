import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
  lazy val sparkVer = "2.3.0"
  lazy val sparkDeps = Seq(
      "org.apache.spark" %% "spark-core" % sparkVer % "provided",
      "org.apache.spark" %% "spark-sql" % sparkVer % "provided"
    )
}


