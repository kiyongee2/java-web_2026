package constant.car;

public abstract class Car {
	
	public void startCar() {
		System.out.println("시동을 켭니다.");
	}
	
	public void turnOff() {
		System.out.println("시동을 끕니다.");
	}
	
	public abstract void drive();
	public abstract void stop();
	
	//템플릿 메서드
	public final void run() {
		startCar();
		drive();
		stop();
		turnOff();
	}
}
