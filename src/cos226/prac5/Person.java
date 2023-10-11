/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cos226.prac5;

/**
 *
 * @author User
 */
public class Person {

	public int number;
	public int time;

	public Person(int number) {
		this.number = number;
		this.time = (int) ((Math.random() * (1000 - 100 + 1)) + 100);
	}

	public String toString() {
		return "(P-" + number + ", " + time + "ms)";
	}
}
