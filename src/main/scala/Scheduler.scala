import scala.collection.mutable
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object Scheduler {

  private val events: mutable.ListBuffer[Event] = mutable.ListBuffer()

  // Ajouter un événement à la liste des événements
  def addEvent(action: String, time: String): Unit = {
    events += Event(action, time)
  }

  // Exécuter les événements triés par heure
  def run(): Unit = {
    println("Starting scheduled events...")
    events.sortBy(_.time).foreach { event =>
      println(s"Executing: '${event.action}' at ${event.time}")
    }
  }

  // Trouver un événement par son action
  def findEvent(action: String): Option[Event] = {
    events.find(_.action == action)
  }

  // Incrémenter une heure par un intervalle donné (format "HH:mm")
  def incrementTime(time: String, increment: String): String = {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")

    // Convertir les chaînes de caractères en LocalTime
    val initialTime = LocalTime.parse(time, formatter)
    val incrementParts = increment.split(":").map(_.toInt) // Sépare heures et minutes
    val hoursToAdd = incrementParts(0)
    val minutesToAdd = incrementParts(1)

    // Ajouter les heures et minutes
    val newTime = initialTime.plusHours(hoursToAdd).plusMinutes(minutesToAdd)

    // Retourner la nouvelle heure au format "HH:mm"
    newTime.format(formatter)
  }
}
