package datax.test

import scala.util.Random

import org.junit.Test
import org.mapdb.DBMaker
import org.mapdb.HTreeMap
import org.mapdb.Serializer

import datax.util.MapDBHandler

class MapDBTest {

  protected val stringToPush = "I'm a text"
  protected val customFilePath = "pathToFile.db"

  @Test
  def test() {

    // Auskommentiert da default file benutzt werden würde
    //    testPush(stringToPush).andPop()
    testOpenCustomFile(customFilePath).andPush(stringToPush).andCompareEntriesTo(getTreeWithValue(stringToPush)).andPopAndCompareEntries()

  }

  protected def getTreeWithValue(stringToPush: String) = {
    val newTree = getNewTreeMap
    newTree.put(0, stringToPush)
    newTree
  }
  protected def getNewTreeMap = DBMaker.memoryDB().make().hashMap(getRandomName).keySerializer(Serializer.INTEGER).valueSerializer(Serializer.STRING).create()
  protected def getRandomName = Random.alphanumeric.take(9).mkString

  protected def testOpenCustomFile(customFilePath: String) = new {
    MapDBHandler.initCustomFile(customFilePath)
    def andPush(pushString: String) = new {
      MapDBHandler.push(pushString)
      def andCompareEntriesTo(treeMap: HTreeMap[Integer, String]) = new {
        protected val allEntries = MapDBHandler.getAllEntries()
        assume(allEntries.equals(treeMap.getEntries))
        def andPopAndCompareEntries() = {
          val poppedString = MapDBHandler.pop()
          assume(pushString == poppedString)
          val allCurrentEntries = MapDBHandler.getAllEntries()
          treeMap.remove(0)
          assume(allCurrentEntries.equals(treeMap.getEntries))
        }
      }
    }
  }

}