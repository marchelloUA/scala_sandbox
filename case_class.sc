case class Person(name: String, age: Int)

def describe(p: Person): String =
  p match
    case Person(name, age) if age >= 18 => s"$name is adult"
    case Person(name, _) => s"$name is minor"

@main def runCaseClass(): Unit =
  val people = List(
    Person("Alice", 30),
    Person("Bob", 15),
    Person("Charlie", 18)
  )

  people.map(describe).foreach(println)