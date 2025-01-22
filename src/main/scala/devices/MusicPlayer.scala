package devices

import dsl.IoTDSL._

class MusicPlayer(deviceId: String) extends Device(deviceId) {
	private case class PlaySong(song: String) extends DeviceEvent {
		def message: String = s"Playing song '$song' on $deviceId"
	}
	
	def playSong(song: String): EventBuilder =
		addEvent(PlaySong(song))
}
