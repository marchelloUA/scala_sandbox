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
├── hello.sc
├── wordcount.sc
└── spark_hello.sc
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

Run Spark example (optional, heavier):

```bash
scala-cli spark_hello.sc
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
* case classes
* Option-safe style
* local Apache Spark execution
* reproducible local setup

---

## Next extensions

Possible improvements:

* add unit tests
* add sbt version
* add Dataset transformations
* add small ETL pipeline
