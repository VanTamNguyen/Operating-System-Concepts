package com.tamco.synchronization;

/**
 * Created by tam-co on 04/04/2017.
 */
public class SharingResource {

	/*
	*
	* This class illustrate sharing resource that is accessed and modified concurrently by many threads
	*
	* */

	private int value;

	public SharingResource() {
		this.value = 0;
	}

	public SharingResource(int resource) {
		this.value = resource;
	}

	public int getValue() {
		return value;
	}

	public void increase() {
		this.value++;
	}

	public void decrease() {
		this.value--;
	}

	@Override
	public String toString() {
		return "SharingResource{" +
				"value=" + value +
				'}';
	}
}
