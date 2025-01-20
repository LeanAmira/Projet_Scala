import org.scalatest.funsuite.AnyFunSuite

class FunctionalTest extends AnyFunSuite {
  test("Test fonctionnel : Ajouter et exécuter des événements") {
    println("===== Test Fonctionnel : Scheduler =====")

    // Ajouter des événements
    Scheduler.addEvent("Préparer le café", "08:00")
    Scheduler.addEvent("Allumer les lumières", "08:05")
    Scheduler.addEvent("Lancer la musique", "08:10")

    // Vérifier la liste des événements
    Scheduler.run()

    // Rechercher un événement
    val event = Scheduler.findEvent("Lancer la musique")
    assert(event.isDefined)
    assert(event.get.action == "Lancer la musique")
    assert(event.get.time == "08:10")

    // Tester l'incrémentation du temps
    val newTime = Scheduler.incrementTime("08:00", "01:30")
    assert(newTime == "09:30")
  }
}
