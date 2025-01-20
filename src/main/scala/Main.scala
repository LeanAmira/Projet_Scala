object Main extends App {
  val dsl = new DSL

  dsl.simulate {
    dsl.event("Start coffee machine").at("08:00")
    dsl.event("Turn on the light").at("08:05")
    dsl.event("Play music").at("08:10")
  }

  Scheduler.run()
}
