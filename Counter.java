public class Counter {
	private int value;
	private int numPrimes;
	private long addPrimes;

	public Counter(int i) {
		value = 2;
		numPrimes = 0;
	}



	public synchronized int getAndIncrement(){
		return value++;
	}
	
	public synchronized void incrementNumPrimes(){
		numPrimes++;
	}
	
	public int getNumPrimes() {
		return numPrimes;
	}

	public synchronized void incrementAddPrimes(int i){
		addPrimes += i;
	}
	
	public long getAddPrimes() {
		return addPrimes;
	}
	

		
}
