package common

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import testdata.JoinData

object SparkJoins {
  
  val ss = SparkSession.builder().getOrCreate()
  
  def test_joins(): Unit = {
    val orders = ss.createDataFrame(JoinData.orders)
    val shippings = ss.createDataFrame(JoinData.shippings)
    
    orders.show(false)
    shippings.show(false)
    
    println("inner join  ->")
    orders.join(shippings, Seq("orderNo"), "inner").show(false)
    
    println("outer join  ->")
    orders.join(shippings, Seq("orderNo"), "outer").show(false)
    
    println("left_outer  ->")
    orders.join(shippings, Seq("orderNo"), "left_outer").show(false)
  }
  
  def test_another(): Unit = {
    val left = ss.createDataFrame(JoinData.suburbs_left)
    val right = ss.createDataFrame(JoinData.suburbs_right)
    
    left.show(false)
    right.show(false)
    
    val goodOnes = left.where(col("name").isNotNull)
    val fixedOnes = left.select("pc")
                        .where(col("name").isNull)
                        .join(right, Seq("pc"), "left_outer")
//                        .show(false)
    goodOnes.union(fixedOnes).show(false)
  }
  
  def main(args: Array[String]): Unit = {
    println("======== common.SparkJoins ========")

    test_another()
  }
}