package inheritance.car;

public class CarTest {

	public static void main(String[] args) {
		Car car = new Car("현대", "소나타");
		car.accelerate(30);
		car.brake(10);
		car.showInfo();
		
		// 자식 객체 생성
		EV ev = new EV("기아", "EV6", 80);
		ev.accelerate(60);
		ev.charge(30);
		ev.showInfo();
		
		// 다형성으로 객체 생성
		Car c = new EV("현대", "ionic6", 50);
		c.accelerate(50);
		//charge() 사용
		
		if(c instanceof EV) {
			EV ionic = (EV)c;
			ionic.charge(30);
		}
	}

}
