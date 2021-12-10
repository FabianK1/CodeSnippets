package com.github.fabiank1.scala.code.snippets

object CSVCreator {
  protected case class TestData(id:String,timestamp:String)
  
  def main(args: Array[String]): Unit = {
    getcsv()
  }
  
  def getcsv() = {
    import java.io._
    val currentDirectory = new java.io.File(".").getCanonicalPath
    val fileObj = new File(currentDirectory+ "csvTestFile.csv")
    val writer = new FileWriter(fileObj, false)
    val separator = sys.props("line.separator")
    val csvBegin = "ID,TIMESTAMP" + separator
    writer.write(csvBegin)
    data.map(entry => {
      writer.write(entry.id + "," + entry.timestamp  + separator)
    })
    writer.close()
  }

  protected val data = {
    Seq(
    TestData("1","1439152106"),
    TestData("2","1539152106"),
    TestData("3","1609152106"),
    TestData("123","1639152106"),
    )
  }
}