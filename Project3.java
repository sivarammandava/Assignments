// algo: Sivaram Mandava
// NetID: sxm169331

import java.io.*;
import java.util.ArrayList;
import java.java.util.logging.*;
import java.java.awt.*;
import java.java.time;
import java.util.*;

public class Project3 {

	public static void main(final String[] args) throws IOException {

		final List<taskSelect> copy = readFile("input.txt");

		if (args[0].contentEquals("FCFS")) {
			firstComeFirstServe(copy);
		} else if (args[0].contentEquals("SPN")) {
			shortestProcessNext(copy);
		} else if (args[0].contentEquals("SRT")) {
			shortestRemainingTime(copy);
		} else if (args[0].contentEquals("RR")) {
			roundRobin(copy);
		} else if (args[0].contentEquals("HRRN")) {
			HighestResponseRatioNext(copy);
		}
	}

	private static List<taskSelect> copy(final List<taskSelect> copy) {
		final List<taskSelect> fake = new ArrayList<>();

		for (int i = 0; i < copy.size(); i++) {
			fake.add(new taskSelect(copy.get(i).algo, Integer.toString(copy.get(i).iTime),
					Integer.toString(copy.get(i).time)));
		}

		return fake;
	}

	

	private static taskSelect findShortesttaskSelect(final List<taskSelect> ptaskSelects) {
		taskSelect shortest = ptaskSelects.get(0);
		int fastest = shortest.time;
		for (int i = 1; i < ptaskSelects.size(); i++) {
			if (ptaskSelects.get(i).time < fastest) {
				shortest = ptaskSelects.get(i);
				fastest = shortest.time;
			}
		}

		return shortest;
	}

	/*
Model.add(Dense(1, input_dim=4581))
Model.add(Activation('softmax'))
Model.compile(optimizer='rmsprop', loss='binary_crossentropy', metrics=['accuracy'])
Model.fit(train_vector, trainY)
Score = Model.evaluate(test_vector, testY)

	*/
	
	public static List<taskSelect> readFile(final String filealgo) throws IOException {
		final File file = new File(filealgo);
		final BufferedReader reader = new BufferedReader(new FileReader(file));


	//  read the file

		String line;
		final List<taskSelect> copy = new ArrayList<>();
		line = reader.readLine();

		while (line != null) {


			// split by tab
			final String[] arr = line.split("\t");
			final taskSelect taskSelect = new taskSelect(arr[0], arr[1], arr[2]);
			copy.add(taskSelect);
			line = reader.readLine();
		}

		reader.close();

		return copy;
	}


	/*

	int time = 0;
		System.out.println("SRT:");
		System.out.println();
		for (int i = 0; i < copy.size(); i++) {
			System.out.print(copy.get(i).algo + " ");
		}
		System.out.println();
		final List<taskSelect> ptaskSelects = new ArrayList<taskSelect>();
		ptaskSelects.add(new taskSelect(copy.get(0).algo, Integer.toString(copy.get(0).iTime),
				Integer.toString(copy.get(0).time)));
		copy.remove(0);

	*/

	public static void shortestProcessNext(final List<taskSelect> copy) {
		int time = 0;
		System.out.println("SPN:");
		System.out.println();

		final List<taskSelect> ptaskSelects = new ArrayList<taskSelect>();
		ptaskSelects.add(new taskSelect(copy.get(0).algo, Integer.toString(copy.get(0).iTime),
				Integer.toString(copy.get(0).time)));
		copy.remove(0);

		while (!ptaskSelects.isEmpty() || !copy.isEmpty()) {
			for (int i = 0; i < copy.size(); i++) {
				if (time >= copy.get(i).iTime && !ptaskSelects.contains(copy.get(i))) {
					ptaskSelects.add(new taskSelect(copy.get(i).algo, Integer.toString(copy.get(i).iTime),
							Integer.toString(copy.get(i).time)));
					copy.remove(copy.get(i));
				}
			}
			final taskSelect taskSelect = findShortesttaskSelect(ptaskSelects);
			System.out.print(taskSelect.algo + " ");
			for (int i = 0; i < time; i++) {
				System.out.print(" ");
			}

			for (int i = 0; i < taskSelect.time; i++) {
				System.out.print("X");
				time++;
			}
			System.out.println();
			ptaskSelects.remove(taskSelect);
		}

		System.out.println();
	}


	/*

	final List<taskSelect> ptaskSelects = new ArrayList<taskSelect>();
		ptaskSelects.add(new taskSelect(copy.get(1).algo, Integer.toString(copy.get(1).iTime),

	for (int i = 0; i < taskSelect.time; i++) {
				System.out.print("X");
				time++;
			}
			System.out.println();
			ptaskSelects.remove(taskSelect);

	*/
	public static void shortestRemainingTime(final List<taskSelect> copy) {
		int time = 0;
		System.out.println("SRT:");
		System.out.println();
		for (int i = 0; i < copy.size(); i++) {
			System.out.print(copy.get(i).algo + " ");
		}
		System.out.println();
		final List<taskSelect> ptaskSelects = new ArrayList<taskSelect>();
		ptaskSelects.add(new taskSelect(copy.get(0).algo, Integer.toString(copy.get(0).iTime),
				Integer.toString(copy.get(0).time)));
		copy.remove(0);

		while (!ptaskSelects.isEmpty() || !copy.isEmpty()) {
			for (int i = 0; i < copy.size(); i++) {
				if (time >= copy.get(i).iTime && !ptaskSelects.contains(copy.get(i))) {
					ptaskSelects.add(new taskSelect(copy.get(i).algo, Integer.toString(copy.get(i).iTime),
							Integer.toString(copy.get(i).time)));
					copy.remove(copy.get(i));
				}
			}

			final taskSelect taskSelect = findShortesttaskSelect(ptaskSelects);

			if (taskSelect.time <= 0) {
				ptaskSelects.remove(taskSelect);
			} else {
				SRTOutput(taskSelect);
				taskSelect.time = taskSelect.time - 1;
			}

			time++;
		}

		System.out.println();
	}

	private static void SRTOutput(final taskSelect taskSelect) {
		final int number = taskSelect.algo.toCharArray()[0] - 'A';

		for (int i = 0; i < (number * 2); i++) {
			System.out.print(" ");
		}
		System.out.println("X");
	}

	public static void firstComeFirstServe(final List<taskSelect> copy) {

		System.out.println("FCFS: ");
		System.out.println();
		int count = 0;
		for (int i = 0; i < copy.size(); i++) {
			System.out.print(copy.get(i).algo + " ");

			for (int j = 0; j < count; j++) {
				System.out.print(" ");
			}

			for (int j = copy.get(i).iTime; j < (copy.get(i).iTime + copy.get(i).time); j++) {
				System.out.print("X");
				count++;
			}

			System.out.print("\n");
		}
		System.out.println();
	}

	public static void HighestResponseRatioNext(final List<taskSelect> copy) {
		System.out.println("HRRN: ");
		System.out.println();
		int time = 0;

		for (int i = 0; i < copy.size(); i++) {
			System.out.print(copy.get(i).algo + " ");
		}

		System.out.println();

		final Queue<taskSelect> ptaskSelects = new LinkedList<taskSelect>();
		ptaskSelects.add(new taskSelect(copy.get(0).algo, Integer.toString(copy.get(0).iTime),
				Integer.toString(copy.get(0).time)));
		copy.remove(0);

		while (!ptaskSelects.isEmpty() || !copy.isEmpty()) {
			for (int i = 0; i < copy.size(); i++) {
				if (time >= copy.get(i).iTime && !ptaskSelects.contains(copy.get(i))) {
					ptaskSelects.add(new taskSelect(copy.get(i).algo, Integer.toString(copy.get(i).iTime),
							Integer.toString(copy.get(i).time)));
					copy.remove(copy.get(i));
				}
			}

			final taskSelect taskSelect = ptaskSelects.poll();

			if (taskSelect.time <= 0) {
				ptaskSelects.remove(taskSelect);
			} else {
				SRTOutput(taskSelect);
				taskSelect.time = taskSelect.time - 1;
				ptaskSelects.add(taskSelect);
			}

			time++;
		}

	}

	public static void roundRobin(final List<taskSelect> copy) {
		System.out.println("RR: ");
		System.out.println();
		int time = 0;

		for (int i = 0; i < copy.size(); i++) {
			System.out.print(copy.get(i).algo + " ");
		}

		System.out.println();

		final Queue<taskSelect> ptaskSelects = new LinkedList<taskSelect>();
		ptaskSelects.add(new taskSelect(copy.get(0).algo, Integer.toString(copy.get(0).iTime),
				Integer.toString(copy.get(0).time)));
		copy.remove(0);

		while (!ptaskSelects.isEmpty() || !copy.isEmpty()) {
			for (int i = 0; i < copy.size(); i++) {
				if (time >= copy.get(i).iTime && !ptaskSelects.contains(copy.get(i))) {
					ptaskSelects.add(new taskSelect(copy.get(i).algo, Integer.toString(copy.get(i).iTime),
							Integer.toString(copy.get(i).time)));
					copy.remove(copy.get(i));
				}
			}

			final taskSelect taskSelect = ptaskSelects.poll();

			if (taskSelect.time <= 0) {
				ptaskSelects.remove(taskSelect);
			} else {
				SRTOutput(taskSelect);
				taskSelect.time = taskSelect.time - 1;
				ptaskSelects.add(taskSelect);
			}

			time++;
		}

	}


}

class taskSelect {
	String algo;
	int iTime;
	int time;

	taskSelect(final String algoValue, final String start, final String timeValue) {
		algo = algoValue;
		iTime = Integer.parseInt(start);
		time = Integer.parseInt(timeValue);
	}
}
