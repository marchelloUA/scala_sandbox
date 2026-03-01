package sandbox

case class Person(name: String, age: Int)

object PersonUtils:
  def describe(p: Person): String =
    p match
      case Person(name, age) if age >= 18 => s"$name is adult"
      case Person(name, _) => s"$name is minor"

  def filterAdults(people: List[Person]): List[Person] =
    people.filter(_.age >= 18)
