package testdata

case class Employee(name: String, dob: String, age: String, isMale: String, children: String)

object Employees {
  def samples: Seq[Employee] = Seq(
      Employee("Lisa", "1981-09-15 16:43:25.234", "36.8", "false", "2"),
      Employee("Ben", "1978-02-25 09:32:09.011", "40.2", "true", "3")
    )
}


object JoinData {
  case class Order(orderNo: Int, customer: String)
  case class Shipping(orderNo: Int, address: String)
  
  def orders: Seq[Order] = Seq(
        Order(23, "Alex"),
//        Order(23, "Alex2"),
        Order(24, "Bob"),
        Order(25, "Simon")
      )
      
  def shippings: Seq[Shipping] = Seq(
        Shipping(23, "Greensborough, VIC 3088"),
//        Shipping(23, "St Helena, VIC 3088"),
        Shipping(24, "Sichuan, China"),
//        Shipping(24, "Chongqing, China"),
        Shipping(26, "Chicago, USA")
      )
      
  case class Suburb(pc: String, name: String)
  
  def suburbs_left: Seq[Suburb] = Seq(
      Suburb("3088", "Greensborough"),
      Suburb("3049", "Mt Waverley"),
      Suburb("3068", null)
    )
    
  def suburbs_right: Seq[Suburb] = Seq(
      Suburb("3068", "Oakleigh East"),
      Suburb("3000", "Melbourne")
    )
}