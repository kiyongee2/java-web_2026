package interfaces.scheduler;

import java.io.IOException;

public class SchedulerTest {

	public static void main(String[] args) throws IOException {
		System.out.println("전화 상담 배분 방식 선택: ");
		System.out.println("R: 한 명씩 차례로 배분");
		System.out.println("L: 대기가 가장 적은 상담원에게 배분");
		
		int ch = System.in.read();
		Scheduler scheduler = null;
		
		if(ch == 'R' || ch == 'r') {
			scheduler = new RoundRobin();
		}
		
		scheduler.getNextCall();
		scheduler.sendCallToAgent();
	}

}
