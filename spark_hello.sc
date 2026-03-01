//> using dep "org.apache.spark::spark-sql:3.5.1"

import org.apache.spark.sql.SparkSession

@main def sparkHello(): Unit =
  val spark = SparkSession.builder()
    .appName("LocalHello")
    .master("local[*]")
    .getOrCreate()

  import spark.implicits._

  val ds = Seq(1, 2, 3, 4).toDS()
  ds.show()

  spark.stop()