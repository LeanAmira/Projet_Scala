package devices

import dsl.IoTDSL._

class CoffeeMachine(deviceId: String) extends Device(deviceId) {
	private case class MakeCoffee() extends DeviceEvent {
		def message: String = s"Making coffee on $deviceId"
	}
	
	def makeCoffee(): EventBuilder =
		addEvent(MakeCoffee())
}
