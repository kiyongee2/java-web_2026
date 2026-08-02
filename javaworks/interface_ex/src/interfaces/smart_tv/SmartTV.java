package interfaces.smart_tv;

import interfaces.remote.RemoteControl;

public class SmartTV implements RemoteControl, WebSearchable{
	private int volume;
	private boolean isPoweredOn;
	
	@Override
	public void searchWeb(String url) {
		System.out.println("검색 중: " + url);
	}

	@Override
	public void turnOn() {
		if(!isPoweredOn) {
			isPoweredOn = true;
			System.out.println("TV를 켭니다. 현재 상태: ON");
		}
	}

	@Override
	public void turnOff() {
		if(isPoweredOn) {
			isPoweredOn = false;
			System.out.println("TV를 끕니다. 현재 상태: OFF");
		}
	}

	@Override
	public void setVolume(int volume) {
		if(volume > RemoteControl.MAX_VOLUME) {
			this.volume = RemoteControl.MAX_VOLUME;
		}else if(volume < RemoteControl.MIN_VOLUME) {
			this.volume = RemoteControl.MIN_VOLUME;
		}else {
			this.volume = volume;
		}
		
		System.out.println("현재 TV 볼륨: " + this.volume);
	}

}
