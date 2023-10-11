/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cos226.prac5;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class SecurityPersonnel extends Thread {
	
	

	private Gallery gallery;
	private Queue<Person> order = new LinkedList<Person>();

	public SecurityPersonnel(Gallery g) {
		this.gallery = g;

	}

	public void arrive(Person p) {
		order.add(p);
	}

	public void run() {
		while (!order.isEmpty()) {
			long startTime = System.currentTimeMillis();
			Person next = order.remove();
			gallery.enter(next);
			while (System.currentTimeMillis() - startTime <= 200) {

				try {
					this.sleep(1);
				} catch (InterruptedException ex) {
					Logger.getLogger(SecurityPersonnel.class.getName()).log(Level.SEVERE, null, ex);
				}

			}
		}

	}

}
