package interfaces.smart_phone;

public class Galaxy implements SmartPhone{

	@Override
	public void call() {
		System.out.println("전화를 겁니다.");
	}

	@Override
	public void takePhone() {
		System.out.println("사진을 찍습니다.");
	}

	@Override
	public void installApp() {
		System.out.println("앱을 설치합니다.");
	}
	
	public static void main(String[] args) {
		SmartPhone phone = new Galaxy();
		phone.call();
		phone.takePhone();
		phone.installApp();
	}

}
