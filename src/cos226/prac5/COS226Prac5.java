/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cos226.prac5;

import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class COS226Prac5 {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		Gallery mainGallery = new Gallery(Integer.parseInt(JOptionPane.showInputDialog("Select a type of synchronisation:\n\n0:Coarse\n1:Fine\n2:Optemistic")));
		
                int numPeoplePerPoint = 10;

		SecurityPersonnel[] allAccessPoints = new SecurityPersonnel[5];

		for (int i = 0; i < allAccessPoints.length; i++) {
			allAccessPoints[i] = new SecurityPersonnel(mainGallery);
		}

		for (int i = 0; i < allAccessPoints.length * numPeoplePerPoint; i++) {
			allAccessPoints[i % allAccessPoints.length].arrive(new Person(i + 1));
		}

		for (int i = 0; i < allAccessPoints.length; i++) {
			allAccessPoints[i].start();
		}
	}

}
