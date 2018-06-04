import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "ali.experiments",
      scalaVersion := "2.11.8",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "DataDev_Spark",
    libraryDependencies += scalaTest % Test,
    libraryDependencies ++= sparkDeps,
    libraryDependencies += esDeps
  )
