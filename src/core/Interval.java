package core;

/**
 * Representa um intervalo fechado em 'min' e aberto em 'max'.
 * 
 * Matematicamente descrito por: [min, max)
 * 
 * @author daniel
 */
public class Interval {
	int min, max;
	
	public Interval(int min, int max) {
		this.min = min < max ? min : max;
		this.max = max > min ? max : min;
	}
	
	// Verifica se um determinado valor está no intervalo
	public boolean inRange(int x) {
		return this.min <= x && x < this.max;
	}
	
	public int min() {
		return min;
	}
	public int max() {
		return max;
	}
	
	@Override
	public String toString() {
		return "[" + min + "," + max + ")";
	}
}
