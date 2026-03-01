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