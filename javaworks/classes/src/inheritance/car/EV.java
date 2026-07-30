package inheritance.car;

public class EV extends Car{
	private int battery;

	public EV(String brand, String model, int battery) {
		super(brand, model);
		this.battery = battery;
	}
	
	public void charge(int amount) {
		battery += amount;
		if(battery > 100) battery = 100;
		System.out.println(model + " 충전됨 - 배터리: " + battery + "%");
	}

	@Override
	public void showInfo() {
		super.showInfo();
		System.out.println("배터리 잔량: " + battery + "%");
	}

}
