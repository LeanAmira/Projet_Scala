class DSL {
  def simulate(actions: => Unit): Unit = {
    actions
  }

  def event(action: String): EventBuilder = {
    new EventBuilder(action)
  }
}

class EventBuilder(action: String) {
  private var time: String = _

  def at(time: String): Unit = {
    this.time = time
    Scheduler.addEvent(action, time)
  }
}
