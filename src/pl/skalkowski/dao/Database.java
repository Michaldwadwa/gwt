package pl.skalkowski.dao;

import java.util.ArrayList;
import java.util.List;

import pl.skalkowski.model.Mobile;

public class Database {

	private static List<Mobile> mobiles = new ArrayList();

	public Database() {
		mobiles.add(new Mobile("Samgung", 100));
		mobiles.add(new Mobile("Nokia", 300));
		mobiles.add(new Mobile("Sony", 200));

	}

	public static List<Mobile> getMobiles() {
		return mobiles;
	}

}
