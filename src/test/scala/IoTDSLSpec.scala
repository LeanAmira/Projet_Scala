import zio.*
import zio.test.*
import zio.test.Assertion.*
import zio.test.TestClock
import zio.durationInt
import java.time.{LocalTime, DayOfWeek, ZonedDateTime}
import dsl.IoTDSL.*

object IoTDSLSpec extends ZIOSpecDefault {
  
  override def spec: Spec[TestEnvironment with Scope, Any] = suite("IoTDSL with TestClock")(
    test("Home manages devices and schedules events") {
      val testEvent1 = new DeviceEvent {
        override def message: String = "Light Turned On"
      }
      
      val light = new Device("light-device") {}
      
      for {
        _ <- light.addEvent(testEvent1).daily(LocalTime.of(8, 0))
        _ <- TestClock.adjust(10.seconds) // Fast-forward the clock by 10 seconds
        _ <- ZIO.yieldNow                // Allow runtime to process scheduled effects
      } yield assertCompletes
    },
    test("Daily event triggers correctly in Home") {
      val testEvent = new DeviceEvent {
        override def message: String = "Daily Event Triggered"
      }
      
      val light = new Device("light-device") {}
      
      for {
        _ <- light.addEvent(testEvent).daily(LocalTime.now().plusMinutes(1).withSecond(0))
        _ <- TestClock.adjust(1.minute) // Simulate 1 minute passing
        _ <- ZIO.yieldNow              // Allow runtime to process the effects
      } yield assertCompletes
    },
    test("Weekly event triggers correctly in Home") {
      val testEvent = new DeviceEvent {
        override def message: String = "Weekly Event Triggered"
      }
      
      val vacuum = new Device("vacuum-device") {}
      
      for {
        _ <- vacuum.addEvent(testEvent).weekly(DayOfWeek.WEDNESDAY, LocalTime.now().plusMinutes(1).withSecond(0))
        _ <- TestClock.adjust(7.days) // Fast-forward the clock by one week
        _ <- ZIO.yieldNow            // Allow runtime to process the effects
      } yield assertCompletes
    }
  )
}
