package testdata

case class Employee(name: String, dob: String, age: String, isMale: String, children: String)

object Employees {
  def samples: Seq[Employee] = Seq(
      Employee("Lisa", "1981-09-15 16:43:25.234", "36.8", "false", "2"),
      Employee("Ben", "1978-02-25 09:32:09.011", "40.2", "true", "3")
    )
}
