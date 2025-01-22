package dsl

import zio.*
import zio.stream.*

import java.time.format.DateTimeFormatter
import java.time.{DayOfWeek, LocalTime, ZonedDateTime}
import scala.collection.mutable

object IoTDSL {
	
	val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
	
	trait DeviceEvent {
		def message: String
		
		def trigger(): Unit =
			val currentTime = ZonedDateTime.now()
			println(s"${currentTime.format(formatter)} : $message")
	}
	
	abstract class Device(val deviceId: String) {

		private val events = mutable.ListBuffer[UIO[Unit]]()
		
		def getEvents: List[UIO[Unit]] = events.toList
		
		def addEvent(event: DeviceEvent): EventBuilder = {
			new EventBuilder(event)
		}
		
		def scheduleEvents(eventsBlock: this.type => Unit): Device = {
			events.clear()
			eventsBlock(this)
			this
		}
		
		class EventBuilder(event: DeviceEvent) {
			private def scheduleRepeatedEvent(schedule: Schedule[Any, Any, Any], delay: Duration = zio.Duration.Zero): UIO[Unit] = {
				val effect = ZStream
				  .repeatWithSchedule(event, schedule)
				  .foreach { e => ZIO.succeed(e.trigger()) }
				  .delay(delay)
				  .fork
				  .flatMap(_.join)

				events += effect
				effect
			}
			
			private def scheduleOneTimeEvent(delay: Duration = zio.Duration.Zero): UIO[Unit] = {
				val effect = ZStream
				  .succeed(event)
				  .schedule(Schedule.once.addDelay( _ => delay)) // Add the delay before triggering
				  .runForeach(e => ZIO.succeed(e.trigger())) // Trigger the event once
				  .fork
				  .flatMap(_.join)
				
				events += effect
				effect
			}
			
			def at(timestamp: Long): EventBuilder = {
				val delay = zio.Duration.fromMillis(timestamp - ZonedDateTime.now().toInstant.toEpochMilli)
				scheduleOneTimeEvent(delay)
				this
			}
			
			def at(dateTime: ZonedDateTime): EventBuilder = {
				val timestamp = dateTime.toInstant.toEpochMilli
				at(timestamp)
			}
			
			def every(interval: Duration, delay: zio.Duration = zio.Duration.Zero): UIO[Unit] = scheduleRepeatedEvent(Schedule.fixed(interval), delay)
			
			def daily(time: LocalTime): UIO[Unit] = {
				val now = ZonedDateTime.now()
				val firstOccurrence = now
				  .withHour(time.getHour)
				  .withMinute(time.getMinute)
				  .withSecond(0)
				  .withNano(0)
				  .plusDays(if (now.toLocalTime.isAfter(time)) 1 else 0)
				val firstMilli = firstOccurrence.toInstant.toEpochMilli
				val delay = Duration.fromMillis(firstOccurrence.toInstant.toEpochMilli - now.toInstant.toEpochMilli)
				every(zio.Duration.fromJava(java.time.Duration.ofHours(24)), delay)
			}
			
			def daily(time: String): UIO[Unit] = {
				val parsedTime = LocalTime.parse(time)
				daily(parsedTime)
			}
			
			def weekly(dayOfWeek: DayOfWeek, time: LocalTime): UIO[Unit] = {
				val now = ZonedDateTime.now()
				val firstOccurrence = now
				  .`with`(time)
				  .`with`(dayOfWeek)
				  .plusWeeks(if (now.getDayOfWeek.getValue > dayOfWeek.getValue || (now.getDayOfWeek == dayOfWeek && now.toLocalTime.isAfter(time))) 1 else 0)
				val delay = zio.Duration.fromMillis(firstOccurrence.toInstant.toEpochMilli - now.toInstant.toEpochMilli)
				every(zio.Duration.fromJava(java.time.Duration.ofDays(7)), delay)
			}
			
			def weekly(dayOfWeek: DayOfWeek, time: String): UIO[Unit] = {
				val parsedTime = LocalTime.parse(time)
				weekly(dayOfWeek, parsedTime)
			}
			
		}
		
		
	}
	
	class Home(val devices: List[Device]) {
		def start(): UIO[Unit] = {
			ZIO.collectAllPar(devices.flatMap(device => device.getEvents)).unit
		}
		
	}
	
}
