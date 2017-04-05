package com.tamco.synchronization;

import java.util.function.Consumer;

/**
 * Created by tam-co on 04/04/2017.
 */
public class CriticalProcess extends Thread {

	/*
	*
	* What do I mean by CriticalProcess? CriticalProcess is a process with a critical section inside.
	* Critical section is a piece of code that accesses and modifies sharing data.
	* My duty is to synchronize processes.
	*
	* */

	SharingResource sharingResource;

	Runnable entrySection;

	Runnable exitSection;

	Consumer<SharingResource> criticalSection;

	/*
	* @sharingResource
	* @entrySection: piece of code that acquire to enter the critical section
	* @exitSection: piece of code that notify this process has done with critical section so
	*               other process can enter its critical section
	* @criticalSection: a piece of code that accesses and modifies sharing resource
	* */
	public CriticalProcess(SharingResource sharingResource, Runnable entrySection, Runnable exitSection, Consumer<SharingResource> criticalSection) {
		this.sharingResource = sharingResource;
		this.entrySection = entrySection;
		this.exitSection = exitSection;
		this.criticalSection = criticalSection;
	}

	@Override
	public void run() {
		int count = 0;

		do {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// Entry section
			entrySection.run();


			// Critical section
			criticalSection.accept(sharingResource);


			// Exit section
			exitSection.run();

			// Remaining section
			count++;
		} while (count < 5);

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// By printing this statement I expect that the value of sharing resource will be the same value when resource was initialized
		System.out.println("Process has finished: " + sharingResource.getValue());
	}
}
