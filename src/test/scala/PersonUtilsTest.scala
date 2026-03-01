package sandbox

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class PersonUtilsTest extends AnyFlatSpec with Matchers:
  "describe" should "identify adults" in {
    val person = Person("Alice", 30)
    val result = PersonUtils.describe(person)
    
    result should be("Alice is adult")
  }

  it should "identify minors" in {
    val person = Person("Bob", 15)
    val result = PersonUtils.describe(person)
    
    result should be("Bob is minor")
  }

  it should "identify 18 year olds as adults" in {
    val person = Person("Charlie", 18)
    val result = PersonUtils.describe(person)
    
    result should be("Charlie is adult")
  }

  "filterAdults" should "filter out minors" in {
    val people = List(
      Person("Alice", 30),
      Person("Bob", 15),
      Person("Charlie", 18)
    )
    val adults = PersonUtils.filterAdults(people)
    
    adults.size should be(2)
    adults.map(_.name) should contain only("Alice", "Charlie")
  }

  it should "handle empty list" in {
    val result = PersonUtils.filterAdults(List())
    result.isEmpty should be(true)
  }

  it should "handle list with only minors" in {
    val people = List(
      Person("Bob", 15),
      Person("Charlie", 12)
    )
    val adults = PersonUtils.filterAdults(people)
    
    adults.isEmpty should be(true)
  }
