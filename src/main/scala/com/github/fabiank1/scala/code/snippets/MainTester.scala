package com.github.fabiank1.scala.code.snippets

object MainTester {
  def main(args: Array[String]): Unit = {

    Test.CALCULATIONTYPE.log(10)
  }

  object Test {
    
    object CALCULATIONTYPE {
      def log(baseValue: Int) = {
        val transformedValue = SCalculationType.LOG.applyTo(baseValue)
        val checkReformedValue = SCalculationType.LOG.revert(transformedValue)
        println(transformedValue)
        println(checkReformedValue)
      }
      def linear(baseValue: Int) = {
        val transformedValue = SCalculationType.LOG.applyTo(baseValue)
        val checkReformedValue = SCalculationType.LOG.revert(transformedValue)
        println(transformedValue)
        println(checkReformedValue)
      }
    }
  }

}
