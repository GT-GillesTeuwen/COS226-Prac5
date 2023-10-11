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
public class OptimisticList<T> implements List<T> {

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

	public OptimisticList() {
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
	}

	public boolean add(T item) {
		int key = item.hashCode();
		while (true) {
			Node pred = head;
			Node curr = pred.next;
			while (curr.key < key) {
				pred = curr;
				curr = curr.next;
			}
			pred.lock();
			curr.lock();
			try {
				if (validate(pred, curr)) {
					if (curr.key == key) {
						return false;
					} else {
						Node node = new Node(item, key);
						node.next = curr;
						pred.next = node;
						return true;
					}
				}
			} finally {
				pred.unlock();
				curr.unlock();
			}
		}
	}

	public boolean remove(T item) {
		int key = item.hashCode();
		while (true) {
			Node pred = head;
			Node curr = pred.next;
			while (curr.key < key) {
				pred = curr;
				curr = curr.next;
			}
			pred.lock();
			curr.lock();
			try {
				if (validate(pred, curr)) {
					if (curr.key == key) {
						pred.next = curr.next;
						return true;
					} else {
						return false;
					}
				}
			} finally {
				pred.unlock();
				curr.unlock();
			}
		}
	}

	private boolean validate(Node pred, Node curr) {
		Node node = head;
		while (node.key <= pred.key) {
			if (node == pred) {
				return pred.next == curr;
			}
			node = node.next;
		}
		return false;
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
