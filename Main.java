import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
	static Counter counter = new Counter(1);
	public static int numThreads = 8;
	public static int checkPrimes = 100000000;
	static List<Integer> primes = new ArrayList<>();
	public static void main(String[] args) throws InterruptedException {
		
		final long startTime = System.currentTimeMillis();
		
		//Initializes and run threads
		Thread threads[] = new Thread[numThreads];
		for(int i = 0; i < numThreads; i++) {
			threads[i] = new Thread(new Runnable() {
				public void run() {
					thread();
				}
			});
			threads[i].start();
		}
		
		//Waits for threads to close
		for(int i = 0; i < numThreads; i++) {
			threads[i].join();
		}
		
		//Get final time
		final long endTime = System.currentTimeMillis();
		long time = endTime -startTime;
		output(time);		
	}
	
	
	
	public static void thread() {
		int j = 2;
		while(j<checkPrimes) {
			j = counter.getAndIncrement();
			if(j < checkPrimes)
			isPrime(j);
		}
		
	}
	public static void isPrime(int num){
		if(num == 2) {
			counter.incrementAddPrimes(num);
			addToPrimeList(num);
			counter.incrementNumPrimes();
			return;
		}
		if(num%2 == 0) {
			return;
		}

		for(int i = 3; i * i <= num; i+=2) {
			if(num%i == 0) {
				return;
			}			
		}
		counter.incrementAddPrimes(num);
		addToPrimeList(num);
		counter.incrementNumPrimes();
	}
	
	
	//Adding Primes to list
	public synchronized static void addToPrimeList(int i) {
		primes.add(i);
	}
	
	public static void output(long time) {
		
		//Get top 13 primes because of random offset of threads
		int k = primes.size() - 13;
		List<Integer> topPrimes = new ArrayList<>();
		for(int i = 0; i + k < primes.size(); i++){
			topPrimes.add(primes.get(i+k));
		}
		
		//Sort through them
		Collections.sort(topPrimes);
		
		System.out.println("");
		//Write all to file
		try {
			FileWriter file = new FileWriter("primes.txt");
			file.write(time + " " + counter.getNumPrimes() + " " + counter.getAddPrimes() + "\n");
			for(int i = 0; i < 10; i++) {
				file.write(topPrimes.get(i+3) + " ");
			}
			file.write("\n");
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
 	}
	
		
}


