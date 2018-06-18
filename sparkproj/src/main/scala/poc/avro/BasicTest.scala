package poc.avro

/*	setup:
 * 	1. create '???' according to 'testdata.Employee'
 * 	2. compile '???' to generate java classes
 *  3. convert 'testdata.Employee' into 'avro object'
 *  
 *  4a. write 'avro object' to disk-file
 *  /
 * 	4b. read disk-file then parse 'avro' into 'testdata.Employee'
 * 
 * 
 *  run:
 *  java -cp path/to/???.jar poc.avro.BasicTest
 *  
 *  library dependency:
 *  	- "org.apache.avro" % "avro" % "1.8.2"
 * */


object BasicTest {
  
  
  def main(args: Array[String]): Unit = {
    println("============== poc.avro.BasicTest ===============")
  }
}