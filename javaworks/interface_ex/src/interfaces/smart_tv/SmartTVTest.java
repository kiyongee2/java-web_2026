package interfaces.smart_tv;

import interfaces.remote.RemoteControl;

public class SmartTVTest {

	public static void main(String[] args) {
		RemoteControl remocon = new SmartTV();
		WebSearchable searcher = (WebSearchable)remocon;
		
		remocon.turnOn();
		remocon.setVolume(7);
		remocon.setVolume(-1);
		remocon.setMute(true);
		remocon.setMute(false);
		remocon.turnOff();
		
		searcher.searchWeb("www.naver.com");
		
		RemoteControl.replaceBattery();
	}

}
