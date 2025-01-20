import org.scalatest.funsuite.AnyFunSuite

class SchedulerTest extends AnyFunSuite {
  test("Add and sort events") {
    val scheduler = Scheduler
    scheduler.addEvent("Test Event 1", "08:00")
    scheduler.addEvent("Test Event 2", "08:05")

    assert(scheduler.events.size == 2)
    assert(scheduler.events.head.time == "08:00")
  }
}
