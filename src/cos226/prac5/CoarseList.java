/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cos226.prac5;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author User
 */
public class CoarseList<T> implements List<T> {

	private Node head;
	private Lock lock = new ReentrantLock();

	private class Node {

		T item;
		int key;
		Node next = null;

		private Node(int key) {
			this.key = key;
		}

		private Node(T item,int key) {
			this.item = item;
			this.key = key;
		}

		@Override
		public String toString() {
			return item.toString();
		}
	}

	public CoarseList() {
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
	}

	public boolean add(T item) {
		Node pred, curr;
		int key = item.hashCode();
		lock.lock();
		try {
			pred = head;
			curr = pred.next;
			while (curr.key < key) {
				pred = curr;
				curr = curr.next;
			}
			if (key == curr.key) {
				return false;
			} else {
				Node node = new Node(item,key);
				node.next = curr;
				pred.next = node;
				return true;
			}
		} finally {
			lock.unlock();
		}

	}

	public boolean remove(T item) {
		Node pred, curr;
		int key = item.hashCode();
		lock.lock();
		try {
			pred = head;
			curr = pred.next;
			while (curr.key < key) {
				pred = curr;
				curr = curr.next;
			}
			if (key == curr.key) {
				pred.next = curr.next;
				return true;
			} else {
				return false;
			}
		} finally {
			lock.unlock();
		}
	}

	public String printList() {
		String out = "";
		lock.lock();
		try {
			Node cur;
			cur = head;
			while (cur != null) {
				if (cur.key != Integer.MAX_VALUE && cur.key != Integer.MIN_VALUE) {
					out += cur.toString() + ",";
				}
				cur = cur.next;
			}
		} finally {
			lock.unlock();
		}

		return out;
	}

}
