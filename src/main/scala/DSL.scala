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
  private var recurrence: Option[String] = None

  def at(time: String): EventBuilder = {
    this.time = time
    this
  }

  def recurring(every: String): Unit = {
    this.recurrence = Some(every)
    Scheduler.addRecurringEvent(action, time, every)
  }

}
