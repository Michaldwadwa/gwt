package pl.skalkowski.model;

import java.io.Serializable;

public class Mobile implements Serializable {
	private String name;
	private int weight;

	private static int id = 0;

	public static int getNewID() {
		id++;
		return id;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Mobile() {

	}

	public Mobile(String name, int weight) {
		super();
		this.id = getNewID();
		this.name = name;
		this.weight = weight;
	}

}
