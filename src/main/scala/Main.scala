import devices.{CoffeeMachine, Light, MusicPlayer, RobotVacuum}
import dsl.IoTDSL.Home
import zio.{ZIO, ZIOAppDefault}

import java.time.{DayOfWeek, LocalTime, ZonedDateTime}

object Main extends ZIOAppDefault {
  def run: ZIO[Any, Throwable, Unit] = {
    val light = Light("light-1", "Living Room")
      .scheduleEvents { device =>
        device.turnOn().daily(LocalTime.of(8,0))
        device.turnOff().daily("23:13")
      }
    
    val coffeeMachine = CoffeeMachine("coffee-machine-1")
    coffeeMachine.scheduleEvents { device =>
      device.makeCoffee().at(ZonedDateTime.now().plusSeconds(15))
    }
    
    val musicPlayer = MusicPlayer("music-player-1").scheduleEvents { device =>
      device.playSong("Bohemian Rhapsody").at(ZonedDateTime.now().plusSeconds(20))
      device.playSong("Leave Me Alone").at(ZonedDateTime.now().plusSeconds(30))
    }
    
    val vacuum = RobotVacuum("vacuum-1")
      .scheduleEvents { device =>
        device.vacuum().weekly(DayOfWeek.WEDNESDAY, "23:12")
        device.stopVacuum().weekly(DayOfWeek.WEDNESDAY, "23:13")
      }
    
    Home(List(light, coffeeMachine, musicPlayer, vacuum)).start()
    
  }
  
}
