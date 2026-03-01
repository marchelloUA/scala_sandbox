//> using dep "org.apache.spark::spark-sql:3.5.1"

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._

case class RawLog(timestamp: String, user_id: String, event: String, value: String)
case class ProcessedEvent(timestamp: String, user_id: String, event: String, value: Double)

@main def etlPipeline(): Unit =
  val spark = SparkSession.builder()
    .appName("ETLPipeline")
    .master("local[*]")
    .getOrCreate()

  import spark.implicits._

  // === EXTRACT: Load raw data ===
  println("=== EXTRACT: Raw Data ===")
  val rawData = Seq(
    RawLog("2024-01-01T10:00:00", "user1", "click", "45.5"),
    RawLog("2024-01-01T10:05:00", "user2", "purchase", "120.99"),
    RawLog("2024-01-01T10:10:00", "user1", "click", "30.0"),
    RawLog("2024-01-01T10:15:00", "user3", "view", "0"),
    RawLog("2024-01-01T10:20:00", "user2", "click", "55.5")
  ).toDS()

  rawData.show()

  // === TRANSFORM: Clean and convert ===
  println("\n=== TRANSFORM: Cleaned Data ===")
  val processedData = rawData
    .filter(col("event").isin("click", "purchase", "view"))  // Filter valid events
    .map { log =>
      ProcessedEvent(
        log.timestamp,
        log.user_id,
        log.event,
        log.value.toDouble
      )
    }
    .toDS()

  processedData.show()

  // === TRANSFORM: Aggregations ===
  println("\n=== TRANSFORM: Total Value by User ===")
  val userMetrics = processedData
    .groupBy(col("user_id"))
    .agg(
      sum(col("value")).as("total_value"),
      count(col("event")).as("event_count"),
      collect_list(col("event")).as("events")
    )

  userMetrics.show()

  // === TRANSFORM: Event frequency ===
  println("\n=== TRANSFORM: Event Distribution ===")
  val eventStats = processedData
    .groupBy(col("event"))
    .agg(
      count(col("*")).as("count"),
      avg(col("value")).as("avg_value")
    )
    .orderBy(col("count").desc)

  eventStats.show()

  // === LOAD: Final output ===
  println("\n=== LOAD: Summary Report ===")
  val summary = userMetrics
    .join(processedData, Seq("user_id"), "left")
    .groupBy(col("user_id"))
    .agg(
      max(col("total_value")).as("total_spent"),
      max(col("event_count")).as("interactions")
    )
    .orderBy(col("total_spent").desc)

  summary.show()

  println("\n=== ETL Pipeline Complete ===")
  spark.stop()
