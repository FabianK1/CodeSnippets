package com.github.fabiank1.scala.code.snippets

object SCalculationType {
  val LOG = new SCalculationType {
    override def applyTo(value: Double) = {


      val sign = Math.signum(value)
      if (sign == 0) {
        0
      } else {
        if(Math.log10(Math.abs(value)) == 0){
          sign * Math.log10(1.5)
        }else{
        sign * Math.log10(Math.abs(value))
        }
      }
    }
    override def revert(value: Double) = {
      val sign = Math.signum(value)

      if (sign == 0) {
        0
      } else {
        sign * Math.pow(10, Math.abs(value))
      }
    }
  }
  val LINEAR = new SCalculationType {
    override def applyTo(value: Double) = {
      value
    }
    override def revert(value: Double) = {
      value
    }
  }
}

abstract class SCalculationType protected {

  def applyTo(value: Double): Double
  def revert(value: Double): Double
}

