import scala.collection.mutable
import java.time.LocalTime
import java.time.format.DateTimeFormatter


object Scheduler {
  private val events: mutable.ListBuffer[Event] = mutable.ListBuffer()

  def addEvent(action: String, time: String, recurrence: Option[String] = None): Unit = {
    events += Event(action, time, recurrence)
  }

  def addRecurringEvent(action: String, time: String, recurrence: String): Unit = {
    addEvent(action, time, Some(recurrence))
  }

  def getEvents: List[Event] = events.toList

  def findEvent(action: String): Option[Event] = {
    events.find(_.action == action)
  }

  def incrementTime(time: String, increment: String): String = {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val initialTime = LocalTime.parse(time, formatter)
    val incrementParts = increment.split(":").map(_.toInt)
    val newTime = initialTime.plusHours(incrementParts(0)).plusMinutes(incrementParts(1))
    newTime.format(formatter)
  }

  def removeEvent(action: String): Boolean = {
    val initialSize = events.size
    events --= events.filter(_.action == action)
    events.size < initialSize
  }

  def run(): Unit = {
    println("Starting scheduled events...")
    events.sortBy(_.time).foreach { event =>
      println(s"Executing: '${event.action}' at ${event.time}")
      event.recurrence.foreach { recurrence =>
        println(s"This event recurs: $recurrence")
      }
    }
  }
}
