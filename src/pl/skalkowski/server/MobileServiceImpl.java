package pl.skalkowski.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import pl.skalkowski.client.MobileService;
import pl.skalkowski.dao.Database;
import pl.skalkowski.model.Mobile;

public class MobileServiceImpl extends RemoteServiceServlet implements MobileService {

	@Override
	public Mobile getMobile(int id) {
		Iterator it = Database.getMobiles().iterator();
		while(it.hasNext()){
			Mobile s = (Mobile) it.next();
			if(s.getId() == id){
				return s;
			}
		}
		return null;
	}
	
	public void deleteMobile(int shipId){
		Iterator it = Database.getMobiles().iterator();
		while(it.hasNext()){
			Mobile s = (Mobile) it.next();
			if(s.getId() == shipId){
				it.remove();
			}
		}
	}

	public List<Mobile> getAllMobiles(){
		return Database.getMobiles();
	}

	public void editMobile(int shipId, Mobile mobile){
		Iterator it = Database.getMobiles().iterator();
		while(it.hasNext()){
			Mobile s = (Mobile) it.next();
			if(s.getId() == shipId){
				s.setName(mobile.getName());
				s.setWeight(mobile.getWeight());
			}
		}
	}
	public int addMobile(Mobile mobile){
		Database.getMobiles().add(mobile);
		return mobile.getId();
	}
	
}
