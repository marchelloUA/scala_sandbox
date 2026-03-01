//> using dep "org.apache.spark::spark-sql:3.5.1"

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

case class Employee(id: Int, name: String, department: String, salary: Double)

@main def datasetTransformation(): Unit =
  val spark = SparkSession.builder()
    .appName("DatasetTransformation")
    .master("local[*]")
    .getOrCreate()

  import spark.implicits._

  // Create dataset
  val employees = Seq(
    Employee(1, "Alice", "Engineering", 120000),
    Employee(2, "Bob", "Sales", 90000),
    Employee(3, "Charlie", "Engineering", 125000),
    Employee(4, "Diana", "HR", 85000),
    Employee(5, "Eve", "Sales", 95000)
  ).toDS()

  println("=== Original Dataset ===")
  employees.show()

  // Filter: Engineering department employees
  println("\n=== Engineering Department ===")
  employees
    .filter(_.department == "Engineering")
    .show()

  // Map: Increase salary by 10%
  println("\n=== Salary Increase (10%) ===")
  employees
    .map(e => (e.name, e.salary * 1.1))
    .toDF("name", "new_salary")
    .show()

  // GroupBy: Average salary by department
  println("\n=== Average Salary by Department ===")
  employees
    .groupBy(col("department"))
    .agg(avg(col("salary")).as("avg_salary"))
    .show()

  // Complex transformation: Get top earners per department
  println("\n=== Top Earner per Department ===")
  employees
    .repartition(col("department"))
    .sortBy(_.salary)(Ordering[Double].reverse)
    .groupByKey(_.department)
    .mapGroups { case (dept, group) =>
      val topEarner = group.maxBy(_.salary)
      (dept, topEarner.name, topEarner.salary)
    }
    .toDF("department", "top_earner", "salary")
    .show()

  spark.stop()
