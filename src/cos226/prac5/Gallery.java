/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cos226.prac5;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class Gallery {

	/////////For formatting/////////
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	////////////////////////////////

	private List<Person> peopleInside;

	public Gallery(int listType) {
		System.out.println("Gallery created");
		switch (listType) {
			case 0:
				this.peopleInside = new CoarseList<Person>();
                                System.out.println("Course");
				break;
			case 1:
				this.peopleInside = new FineList<Person>();
                                System.out.println("Fine");
				break;
			case 2:
				this.peopleInside = new OptimisticList<Person>();
                                System.out.println("Optimistic");
				break;
			default:
				this.peopleInside = new CoarseList<Person>();
				break;
		}
	}

	public void enter(Person p) {
		System.out.println(ANSI_GREEN + Thread.currentThread().getName() + ": added " + p.toString() + ANSI_RESET);
		peopleInside.add(p);
		try {
			while (p.time > 0) {
				Thread.sleep(1);
				p.time -= 1;
			}
			System.out.println(ANSI_YELLOW + Thread.currentThread().getName() + " " + peopleInside.printList() + ANSI_RESET);
			peopleInside.remove(p);
                } catch (InterruptedException ex) {
			Logger.getLogger(Gallery.class.getName()).log(Level.SEVERE, null, ex);
		}

	}
}
