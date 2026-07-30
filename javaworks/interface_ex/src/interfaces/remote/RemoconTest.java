package interfaces.remote;

public class RemoconTest {

	public static void main(String[] args) {
		RemoteControl remocon = new Television();
		
		remocon.turnOn();
		remocon.setVolume(7);
		remocon.setVolume(-1);
		remocon.setMute(true);
		remocon.turnOff();
	}

}
