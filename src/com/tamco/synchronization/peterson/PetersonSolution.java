package com.tamco.synchronization.peterson;

import com.tamco.synchronization.CriticalProcess;
import com.tamco.synchronization.SharingResource;

import java.util.function.Consumer;

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
		};

		Runnable decreaseEntrySection = () -> {
			decreaseFlag = true;
			turn = increaseTurn;

			while (increaseFlag && turn == increaseTurn) {;}
		};

		Runnable increaseExitSection = () -> {
			increaseFlag = false;
		};

		Runnable decreaseExitSection = () -> {
			decreaseFlag = false;
		};

		Consumer<SharingResource> increaseCriticalSection = res -> { res.increase(); };
		Consumer<SharingResource> decreaseCriticalSection = res -> { res.decrease(); };

		CriticalProcess increaseProcess = new CriticalProcess(resource, increaseEntrySection, increaseExitSection, increaseCriticalSection);
		CriticalProcess decreaseProcess = new CriticalProcess(resource, decreaseEntrySection, decreaseExitSection, decreaseCriticalSection);

		increaseProcess.start();
		decreaseProcess.start();

		try {
			increaseProcess.join();
			decreaseProcess.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Sharing resource value after 2 processes finish: " + resource.getValue());
	}
}
