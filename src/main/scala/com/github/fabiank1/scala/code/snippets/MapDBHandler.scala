package datax.util

import org.mapdb.DBMaker
import org.mapdb.Serializer
import scala.collection.JavaConversions._
import org.mapdb.DB
import org.mapdb.HTreeMap

object MapDBHandler {

  protected case class DBConfiguration(db: DB, mapDBMap: HTreeMap[Integer, String], var index: Int)
  protected var dbConfiguration = getDefaultInit()

  def initCustomFile(filePath: String) = {
    dbConfiguration.db.close()
    val db = DBMaker.fileDB(filePath).make()
    val mapDBMap = db.hashMap("mapDBMap").keySerializer(Serializer.INTEGER).valueSerializer(Serializer.STRING).createOrOpen()
    var index = getLatestIndex(mapDBMap).getOrElse(new Integer(-1))
    db.commit()
    dbConfiguration = DBConfiguration(db, mapDBMap, index)
  }
  def getAllEntries() = {
    val entries = dbConfiguration.mapDBMap.getEntries
    dbConfiguration.db.commit()
    entries
  }

  def push(valueToPush: String) {
    dbConfiguration.index = dbConfiguration.index + 1
    dbConfiguration.mapDBMap.put(dbConfiguration.index, valueToPush)
    dbConfiguration.db.commit()
  }

  def pop() = {
    if (dbConfiguration.index < 0) {
      throw new IllegalAccessException("No Entries to Pop - index <0")
    }
    val poppedValue = dbConfiguration.mapDBMap.remove(dbConfiguration.index)
    dbConfiguration.db.commit()
    dbConfiguration.index = dbConfiguration.index - 1
    poppedValue
  }

  protected def getDefaultInit() = {
    val db = DBMaker.fileDB("mapDBFile.db").make()
    val mapDBMap = db.hashMap("mapDBMap").keySerializer(Serializer.INTEGER).valueSerializer(Serializer.STRING).createOrOpen()
    var index = getLatestIndex(mapDBMap).getOrElse(new Integer(-1))
    db.commit()
    DBConfiguration(db, mapDBMap, index)
  }
  protected def getLatestIndex(mapDBMap: HTreeMap[Integer, String]): Option[Integer] = {
    try {
      mapDBMap.getEntries.map(entry => if (entry.getKey > -1) { Some(entry.getKey) } else { None }).max
    } catch {
      case e: Exception => None
    }
  }
}