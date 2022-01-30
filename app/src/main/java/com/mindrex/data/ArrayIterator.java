package com.mindrex.data;

import java.util.ListIterator;

public final class ArrayIterator<E> implements ListIterator<E> {
	private final Array<E> arr;
	private int index = 0;
	private int lastRet = -1;

	ArrayIterator(Array<E> a) {
		this.arr = a;
	}

	ArrayIterator(Array<E> a, int b) {
		this.arr = a;
		this.index = b;
	}

	@Override
	public final boolean hasNext() {
		return index < arr.size();
	}

	@Override
	public final E next() {
		lastRet = index;
		return arr.get(index++);
	}

	@Override
	public final boolean hasPrevious() {
		return index > 0;
	}

	@Override
	public final E previous() {
		lastRet = index;
		return arr.get(--index);
	}

	@Override
	public final int nextIndex() {
		return index;
	}

	@Override
	public final int previousIndex() {
		return index - 1;
	}

	@Override
	public final void remove() {
		arr.remove(lastRet);
		if (lastRet < index)
			index--;
		lastRet = -1;
	}

	@Override
	public final void set(E e) {
		arr.set(lastRet, e);
	}

	@Override
	public final void add(E e) {
		arr.add(index++, e);
		lastRet = -1;
	}
}