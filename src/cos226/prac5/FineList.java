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
public class FineList<T> implements List<T> {

	private Lock lock = new ReentrantLock();

	private Node head;

	private class Node {

		T item;
		int key;
		Node next = null;
		boolean locked;

		private Lock lock = new ReentrantLock();

		private Node(int key) {
			this.key = key;
		}

		private Node(T item, int key) {
			this.item = item;
			this.key = key;
		}

		@Override
		public String toString() {
			return item.toString();
		}

		private void lock() {
			lock.lock();
		}

		private void unlock() {
			lock.unlock();
		}
	}

	public FineList() {
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
	}

	public boolean add(T item) {
		int key = item.hashCode();
		head.lock();
		Node pred = head;
		try {
			Node curr = pred.next;
			curr.lock();
			try {
				while (curr.key < key) {
					pred.unlock();
					pred = curr;
					curr = curr.next;
					curr.lock();
				}
				if (curr.key == key) {
					return false;
				}
				Node newNode = new Node(item, key);
				newNode.next = curr;
				pred.next = newNode;
				return true;
			} finally {
				curr.unlock();
			}
		} finally {
			pred.unlock();
		}
	}

	public boolean remove(T item) {
		Node pred = null, curr = null;
		int key = item.hashCode();
		head.lock();
		try {
			pred = head;
			curr = pred.next;
			curr.lock();
			try {
				while (curr.key < key) {
					pred.unlock();
					pred = curr;
					curr = curr.next;
					curr.lock();
				}
				if (curr.key == key) {
					pred.next = curr.next;
					return true;
				}
				return false;
			} finally {
				curr.unlock();
			}
		} finally {
			pred.unlock();
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
