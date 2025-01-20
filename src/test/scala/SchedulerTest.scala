import org.scalatest.funsuite.AnyFunSuite

class SchedulerTest extends AnyFunSuite {
  test("Add and sort events") {
    val scheduler = Scheduler
    scheduler.addEvent("Test Event 1", "08:00")
    scheduler.addEvent("Test Event 2", "08:05")

    // Vérification de la taille et de l'ordre des événements
    assert(scheduler.getEvents.size == 2)
    assert(scheduler.getEvents.head.time == "08:00")
  }
}
