package sandbox

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class WordCounterTest extends AnyFlatSpec with Matchers:
  "wordCount" should "count words correctly" in {
    val text = "Scala is great and Scala is fast"
    val result = WordCounter.wordCount(text)
    
    result("scala") should be(2)
    result("is") should be(2)
    result("great") should be(1)
  }

  it should "handle lowercase conversion" in {
    val text = "HELLO hello Hello"
    val result = WordCounter.wordCount(text)
    
    result("hello") should be(3)
  }

  it should "filter empty strings" in {
    val text = "word1  word2   word3"
    val result = WordCounter.wordCount(text)
    
    result.size should be(3)
  }

  it should "return empty map for empty string" in {
    val result = WordCounter.wordCount("")
    result.isEmpty should be(true)
  }

  "topWords" should "return top n words sorted by frequency" in {
    val text = "apple apple banana apple cherry banana"
    val result = WordCounter.topWords(text, 2)
    
    result.size should be(2)
    result(0) should be(("apple", 3))
    result(1) should be(("banana", 2))
  }

  it should "return fewer words if n is larger than word count" in {
    val text = "word1 word2"
    val result = WordCounter.topWords(text, 10)
    
    result.size should be(2)
  }
