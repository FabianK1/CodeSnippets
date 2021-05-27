
object MainTestLogarithm {

  //testcase to https://bl.ocks.org/d3indepth/30d31098b607b669a7874bf4ab3c9595
  case class SDistanceDouble(val from: Double, val to: Double)
  def main(args: Array[String]): Unit = {

    // correlating to d3js values
    val testSequence = Seq(10, 100, 1000, 10000, 100000)
    val domain = SDistanceDouble(10, 100000)
    val range = SDistanceDouble(0, 700)

    val domainLog = SDistanceDouble(Math.log10(domain.from), Math.log10(domain.to))

    // SScalaBaseMathSupport -> getPositionAtValue method
    //      var relativeDomainValue = domainValue - domain.from
    //      var fraction = relativeDomainValue / (domain.to - domain.from)
    //      var relativeMaxRange = range.to - range.from
    //      var position = fraction * relativeMaxRange + range.from

    for (domainValue <- testSequence) {
      val sign = Math.signum(domainValue)

      //
      val valueChanged = sign * Math.log10(Math.abs(domainValue))

      // getPositionAtValue -> adjusted to incoming log values
      val relativeDomainValue = valueChanged - domainLog.from
      val fraction = relativeDomainValue / (domainLog.to - domainLog.from)
      val relativeMaxRange = range.to - range.from
      val position = fraction * relativeMaxRange + range.from

      println("position")
      println(position)
      println("\nvalue")
      println(domainValue)
      println("\nlog value")
      println(sign * Math.log10(Math.abs(domainValue)))
      println("--------------")
    }
  }
}
