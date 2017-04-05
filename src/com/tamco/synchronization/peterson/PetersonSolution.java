package com.tamco.synchronization.peterson;

import com.tamco.synchronization.CriticalProcess;
import com.tamco.synchronization.SharingResource;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by tam-co on 04/04/2017.
 */
public class PetersonSolution {

	static boolean increaseFlag = false;
	static boolean decreaseFlag = false;

	static final int increaseTurn = 0, decreaseTurn = 1;
	static int turn;

	static SharingResource resource = new SharingResource(100);

	public static void main(String[] args) {

		Runnable increaseEntrySection = () -> {
			increaseFlag = true;
			turn = decreaseTurn;

			while (decreaseFlag && turn == decreaseTurn) {;}
			System.out.println("INCREASE process ENTER critical section");
		};

		Runnable decreaseEntrySection = () -> {
			decreaseFlag = true;
			turn = increaseTurn;

			while (increaseFlag && turn == increaseTurn) {;}
			System.out.println("DECREASE process ENTER critical section");
		};

		Runnable increaseExitSection = () -> {
			System.out.println("INCREASE process EXIT  critical section\n-------------------");
			increaseFlag = false;
		};

		Runnable decreaseExitSection = () -> {
			System.out.println("DECREASE process EXIT  critical section\n-------------------");
			decreaseFlag = false;
		};

		Consumer<SharingResource> increaseCriticalSection = res -> { res.increase(); };
		Consumer<SharingResource> decreaseCriticalSection = res -> { res.decrease(); };

		CriticalProcess increaseProcess = new CriticalProcess(resource, increaseEntrySection, increaseExitSection, increaseCriticalSection);
		CriticalProcess decreaseProcess = new CriticalProcess(resource, decreaseEntrySection, decreaseExitSection, decreaseCriticalSection);

		increaseProcess.start();
		decreaseProcess.start();
	}
}
