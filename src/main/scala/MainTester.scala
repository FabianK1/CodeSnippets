object MainTester {
  def main(args: Array[String]): Unit = {

    logTest()
  }

  protected def logTest() = {
    val logTest = SCalculationType.LOG
    val originalValue = 10
    val transformedValue = logTest.applyTo(originalValue)
    val checkReformedValue = logTest.revert(transformedValue)

    println(transformedValue)
    println(checkReformedValue)
  }
}
