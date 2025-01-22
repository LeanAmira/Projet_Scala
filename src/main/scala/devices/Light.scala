package devices

import dsl.IoTDSL._

class Light(deviceId: String, room: String) extends Device(deviceId) {
	private case class TurnOnLight() extends DeviceEvent {
		def message: String = s"Turning on lights in '$room' on $deviceId"
	}
	
	private case class TurnOffLight() extends DeviceEvent {
		def message: String = s"Turning off lights in '$room' on $deviceId"
	}
	
	def turnOn(): EventBuilder =
		addEvent(TurnOnLight())
	
	def turnOff(): EventBuilder =
		addEvent(TurnOffLight())
}
