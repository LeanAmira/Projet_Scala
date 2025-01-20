import scala.collection.mutable
import java.time.LocalTime
import java.time.format.DateTimeFormatter


object Scheduler {
  private val events: mutable.ListBuffer[Event] = mutable.ListBuffer()

  // Ajouter un événement
  def addEvent(action: String, time: String): Unit = {
    events += Event(action, time)
  }

  // Exposer les événements (lecture seule)
  def getEvents: List[Event] = events.toList

  // Exécuter les événements triés par heure
  def run(): Unit = {
    println("Starting scheduled events...")
    events.sortBy(_.time).foreach { event =>
      println(s"Executing: '${event.action}' at ${event.time}")
    }
  }

  // Trouver un événement
  def findEvent(action: String): Option[Event] = {
    events.find(_.action == action)
  }

  // Incrémenter une heure
  def incrementTime(time: String, increment: String): String = {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val initialTime = LocalTime.parse(time, formatter)
    val incrementParts = increment.split(":").map(_.toInt)
    val newTime = initialTime.plusHours(incrementParts(0)).plusMinutes(incrementParts(1))
    newTime.format(formatter)
  }
}
