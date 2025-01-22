package devices

import dsl.IoTDSL._

class RobotVacuum(deviceId: String) extends Device(deviceId) {
	private case class Vacuum() extends DeviceEvent {
		def message: String = s"$deviceId started vacuuming"
	}
	
	private case class StopVacuum() extends DeviceEvent {
		def message: String = s"$deviceId stopped vacuuming"
	}
	
	def vacuum(): EventBuilder =
		addEvent(Vacuum())
		
	def stopVacuum(): EventBuilder =
		addEvent(StopVacuum())
}

