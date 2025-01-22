import org.scalatest.funsuite.AnyFunSuite

class FunctionalTest extends AnyFunSuite {
  test("Test fonctionnel : Ajouter et exécuter des événements") {
    println("===== Test Fonctionnel : Scheduler =====")

    Scheduler.addEvent("Préparer le café", "08:00")
    Scheduler.addEvent("Allumer les lumières", "08:05")
    Scheduler.addEvent("Lancer la musique", "08:10")

    Scheduler.addRecurringEvent("Lancer Mix Semba", "23:45", "daily")
    Scheduler.addRecurringEvent("Manger au 129", "13:45", "weekly")

    Scheduler.run()

    val event = Scheduler.findEvent("Lancer la musique")
    assert(event.isDefined)
    assert(event.get.action == "Lancer la musique")
    assert(event.get.time == "08:10")

    val sembaEvent = Scheduler.findEvent("Lancer Mix Semba")
    assert(sembaEvent.isDefined)
    assert(sembaEvent.get.action == "Lancer Mix Semba")
    assert(sembaEvent.get.time == "23:45")
    assert(sembaEvent.get.recurrence.contains("daily"))

    val newTime = Scheduler.incrementTime("08:00", "01:30")
    assert(newTime == "09:30")
  }
}
