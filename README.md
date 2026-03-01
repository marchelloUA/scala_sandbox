# scala-sandbox

Minimal Scala sandbox for local development on Ubuntu.  
Purpose: quick refresh of Scala syntax and functional patterns, with optional Apache Spark.

## Goals

- run Scala locally
- demonstrate functional style
- show case classes and Options
- basic collections processing
- optional local Spark job
- no external services required

## Prerequisites

- Java 17+
- coursier installed
- scala-cli available

Check:

```bash
java -version
scala-cli version
````

## Project structure

```
scala-sandbox/
├── README.md
├── build.sbt
├── hello.sc
├── wordcount.sc
├── case_class.sc
├── spark_hello.sc
├── dataset_transformation.sc
├── etl_pipeline.sc
├── src/
│   ├── main/scala/
│   │   ├── WordCounter.scala
│   │   └── PersonUtils.scala
│   └── test/scala/
│       ├── WordCounterTest.scala
│       └── PersonUtilsTest.scala
└── project/
```

## Quick start

Run hello world:

```bash
scala-cli hello.sc
```

Run word count example:

```bash
scala-cli wordcount.sc
```

Run case class demo:

```bash
scala-cli case_class.sc
```

Run Spark example (optional, heavier):

```bash
scala-cli spark_hello.sc
```

Run dataset transformation:

```bash
scala-cli dataset_transformation.sc
```

Run ETL pipeline:

```bash
scala-cli etl_pipeline.sc
```

### Build and test with sbt

```bash
sbt compile
sbt test
sbt run
```

---

## Example 1: hello.sc

Demonstrates:

* main entry point
* simple function
* string interpolation

```scala
@main def hello(): Unit =
  val name = "MJ"
  println(s"Hello, $name")
```

---

## Example 2: wordcount.sc

Demonstrates:

* collections
* map and filter
* groupBy
* Option handling

```scala
def wordCount(text: String): Map[String, Int] =
  text
    .toLowerCase
    .split("\\W+")
    .filter(_.nonEmpty)
    .groupBy(identity)
    .view
    .mapValues(_.length)
    .toMap

@main def runWordCount(): Unit =
  val input = "Scala is great and Scala is fast"
  val counts = wordCount(input)

  counts.toSeq.sortBy(_._1).foreach(println)
```

---

## Example 3: case class demo

Create file case_class.sc if you want to extend the sandbox.

Demonstrates:

* case class
* immutability
* pattern matching

```scala
case class Person(name: String, age: Int)

def describe(p: Person): String =
  p match
    case Person(name, age) if age >= 18 => s"$name is adult"
    case Person(name, _) => s"$name is minor"

@main def runCaseClass(): Unit =
  val people = List(
    Person("Alice", 30),
    Person("Bob", 15)
  )

  people.map(describe).foreach(println)
```

---

## Optional: Spark local example

Only needed if practicing Spark.

Create spark_hello.sc:

```scala
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
```

Notes:

* runs fully local
* suitable for laptop testing
* increase RAM if needed

---

## sbt - Scala Build Tool

**What is sbt?**

sbt (Scala Build Tool) is a build and task runner for Scala projects. It manages:
- **Compilation**: Compile Scala code to bytecode
- **Dependencies**: Download and manage libraries (from Maven Central, etc.)
- **Testing**: Run unit tests with frameworks like ScalaTest
- **Packaging**: Create JAR files for distribution

**Key concepts:**

- `build.sbt`: Configuration file defining project name, version, dependencies, Scala version
- `src/main/scala/`: Production code
- `src/test/scala/`: Test code
- `target/`: Compiled output (auto-generated, don't edit)

**Common commands:**

```bash
sbt compile       # Compile code
sbt test          # Run all tests
sbt test-only     # Run specific test
sbt run           # Run main class
sbt clean         # Remove compiled artifacts
sbt package       # Create JAR file
```

**Example build.sbt:**

```scala
ThisBuild / scalaVersion := "3.3.1"
ThisBuild / version := "0.1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .settings(
    name := "scala-sandbox",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.18" % Test,
      "org.apache.spark" %% "spark-sql" % "3.5.1" % Provided
    )
  )
```

---

## Example 4: Unit Tests

Run tests:

```bash
sbt test                              # All tests
sbt testOnly *WordCounterTest         # Specific test
```

Tests in `src/test/scala/` use ScalaTest framework:

```scala
class WordCounterTest extends AnyFlatSpec with Matchers:
  "wordCount" should "count words correctly" in {
    val result = WordCounter.wordCount("Scala is great and Scala is fast")
    result("scala") should be(2)
  }
```

---

## Example 5: Dataset Transformation (Spark)

Demonstrates Spark DataFrame/Dataset operations:

```bash
scala-cli dataset_transformation.sc
```

Features:
* Create datasets from case classes
* Filter and map operations
* GroupBy and aggregations
* Complex transformations with mapGroups

```scala
val employees = Seq(
  Employee(1, "Alice", "Engineering", 120000),
  Employee(2, "Bob", "Sales", 90000)
).toDS()

// Filter
employees.filter(_.department == "Engineering").show()

// GroupBy: Average salary by department
employees
  .groupBy(col("department"))
  .agg(avg(col("salary")))
  .show()
```

---

## Example 6: ETL Pipeline

Extract-Transform-Load demo with Spark:

```bash
scala-cli etl_pipeline.sc
```

Three steps:
1. **Extract** - Load raw log data
2. **Transform** - Clean, validate, aggregate
3. **Load** - Generate summary reports

---

## Useful commands

Format code:

```bash
scalafmt .
```

Start REPL:

```bash
scala-cli repl
```

Compile only:

```bash
scala-cli compile hello.sc
```

---

## What this sandbox demonstrates

* modern Scala 3 syntax
* functional collection processing
* case classes and pattern matching
* Option-safe style
* sbt build system and dependency management
* unit testing with ScalaTest
* local Apache Spark execution
* Dataset transformations
* ETL pipeline patterns
* reproducible local setup

---

## Extensions completed

✅ unit tests (ScalaTest framework)
✅ sbt version with full project structure
✅ Dataset transformations (Spark DataFrames)
✅ Small ETL pipeline example
