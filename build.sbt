name := "HDFS"

name := "JSON"

version := "0.1"

scalaVersion := "2.13.6"


libraryDependencies += "org.apache.hadoop" % "hadoop-client" % "3.2.1"


libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.12" % "2.4.7" % Provided,
  "org.apache.spark" % "spark-sql_2.12" % "2.4.7" % "provided"
)
initialCommands := "import bleibinhaus.hdfsscalaexample._"

