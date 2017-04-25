package com.tamco.synchronization.hardware;

import com.tamco.synchronization.CriticalProcess;
import com.tamco.synchronization.SharingResource;

import java.util.function.Consumer;

/**
 * Created by tam-co on 25/04/2017.
 */
public class HardwareSolution {

	static Boolean target = Boolean.FALSE;

	static SharingResource resource = new SharingResource(100);

	public static void main(String... args) {
		Runnable increaseEntrySection = () -> {
			while (testAndSet()) {;}
			System.out.println("INCREASE process ENTER critical section");
		};

		Runnable decreaseEntrySection = () -> {
			while (testAndSet()) {;}
			System.out.println("DECREASE process ENTER critical section");
		};

		Runnable increaseExitSection = () -> {
			System.out.println("INCREASE process EXIT  critical section\n-------------------");
			release();
		};

		Runnable decreaseExitSection = () -> {
			System.out.println("DECREASE process EXIT  critical section\n-------------------");
			release();
		};

		Consumer<SharingResource> increaseCriticalSection = res -> { res.increase(); };
		Consumer<SharingResource> decreaseCriticalSection = res -> { res.decrease(); };

		CriticalProcess increaseProcess = new CriticalProcess(resource, increaseEntrySection, increaseExitSection, increaseCriticalSection);
		CriticalProcess decreaseProcess = new CriticalProcess(resource, decreaseEntrySection, decreaseExitSection, decreaseCriticalSection);

		increaseProcess.start();
		decreaseProcess.start();
	}

	/*
	* The dinosaur book says that the software based solutions such as Peterson solution
	* are not guaranteed to work on modern computer architectures.
	*
	* Many modern computer systems therefore provide special hardware
	* instructions that allow us either to test and modify the content of a word or atomically
	* testAndSet is a hardware instruction like that
	*
	* Here I use Java keyword synchronized to illustrate that instruction
	* */
	public synchronized static boolean testAndSet() {
		boolean rv = target.booleanValue();
		target = Boolean.TRUE;
		return rv;
	}

	public synchronized static void release() {
		target = Boolean.FALSE;
	}
}
