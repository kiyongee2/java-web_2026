package interfaces.remote;

public class Television implements RemoteControl{
	private int volume;
	private boolean isPoweredOn;

	@Override
	public void turnOn() {
		if(!isPoweredOn) {
			isPoweredOn = true;
			System.out.println("전원을 켭니다. 현재 상태: ON");
		}
	}

	@Override
	public void turnOff() {
		if(isPoweredOn) {
			isPoweredOn = false;
			System.out.println("전원을 끕니다. 현재 상태: OFF");
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
