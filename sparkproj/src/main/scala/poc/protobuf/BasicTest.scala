package poc.protobuf


/*	setup:
 * 	1. create 'Employee.pb' according to 'testdata.Employee'
 * 	2. compile 'Employee.pb' to generate java classes
 *  3. convert 'testdata.Employee' into 'protobuf object'
 *  
 *  4a. write 'protobuf object' to disk-file
 *  /
 * 	4b. read disk-file then parse 'protobuf' into 'testdata.Employee'
 *
 *  run:
 *  java -cp path/to/???.jar poc.protobuf.BasicTest
 * 
 *  library dependency:
 *  	- ???
 *  
 *  reference link: 
 *  	- https://github.com/google/protobuf
 * */


object BasicTest {
  
  
  def main(args: Array[String]): Unit = {
    println("============== poc.protobuf.BasicTest ===============")
  }
}