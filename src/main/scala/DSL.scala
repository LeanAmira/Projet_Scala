class DSL {

  def event(action: String): EventBuilder = new EventBuilder(action)

  class EventBuilder(action: String){
    def at(time: String): Unit = {
      Scheduler.addEvent(action, time);
    }
  }

}
