import scala.collection.mutable

object Scheduler {

  private val events: mutable.ListBuffer[Event] = mutable.ListBuffer()

  def addEvent(action: String, time: String): Unit = {
    events += Event(action, time);
  }

  def run(): Unit = {
    events.sortBy(_.time).foreach { event =>
      println(s"Executing: '${event.action}' at ${event.time}")
    }
  }
}
