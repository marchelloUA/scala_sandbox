package sandbox

object WordCounter:
  def wordCount(text: String): Map[String, Int] =
    text
      .toLowerCase
      .split("\\W+")
      .filter(_.nonEmpty)
      .groupBy(identity)
      .view
      .mapValues(_.length)
      .toMap

  def topWords(text: String, n: Int): List[(String, Int)] =
    wordCount(text)
      .toSeq
      .sortBy(_._2)(Ordering[Int].reverse)
      .take(n)
      .toList
