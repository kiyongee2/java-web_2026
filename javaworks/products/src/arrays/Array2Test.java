package arrays;

public class Array2Test {

	public static void main(String[] args) {
		int[][] arr = {
			{1, 2, 3},
			{4, 5, 6}
		};
		
		for(int i=0; i<arr.length; i++) {
			for(int j=0; j<arr[i].length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
		
		// 배열 요소의 합
		int[][] d = {
			{1, 2},
			{3, 4},
			{5, 6}
		};
		int sum = 0;
		int count = 0;
		double avg;
		
		/*for(int i=0; i<d.length; i++) {
			for(int j=0; j<d[i].length; j++) {
				sum += d[i][j];
				count++;
			}
		}*/
		for(int[] row : d) {
			for(int v : row) {
				sum += v;
				count++;
			}
		}
			
		avg = sum / (double)count;
		
		System.out.println("합: " + sum);
		System.out.println("평균: " + avg);
	}

}
