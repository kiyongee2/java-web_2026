package arrays;

public class ArrayTest {

	public static void main(String[] args) {
		int[] arr = {80, 92, 100, 70, 65};
		int sum = 0;
		double avg;
		
		for(int i=0; i<arr.length; i++) {
			sum += arr[i];
		}
		
		avg = (double)sum / arr.length;
		
		System.out.println("합: " + sum);
		System.out.println("평균: " + avg);
		
		//향상 for문
		for(int a : arr) {
			System.out.print(a + " ");
		}
		System.out.println();
		
		char[] ch = {'c', 'l', 'o', 'u', 'd'};
		for(char c : ch)
			System.out.print(c + "");
		
		// 최대값
		int[] a = {30, 10, 50, 20};
		int max = a[0], idx = 0;
		
		for(int i=1; i<a.length; i++) {
			if(a[i] > max) {
				max = a[i];
				idx = i;
			}
		}
		System.out.println("최대값: " + max + ", 위치: " + idx);
		
		//거꾸로 복사
		int[] x = {1, 2, 3, 4};
		int[] y = new int[x.length];
		
		for(int i=0; i<x.length; i++) {
			y[i] = x[(x.length-1)-i];
			System.out.print(y[i] + " ");
		}
		
	}

}
