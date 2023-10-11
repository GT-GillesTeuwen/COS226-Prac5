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
public interface List<T> {
	public boolean add(T item);
	public boolean remove(T item);
	public String printList();
}
