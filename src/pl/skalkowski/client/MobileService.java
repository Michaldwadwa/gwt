package pl.skalkowski.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import pl.skalkowski.model.Mobile;

@RemoteServiceRelativePath("mobile")
public interface MobileService extends RemoteService {
	
	public Mobile getMobile(int id);
	public void deleteMobile(int mobileId);
	public List<Mobile> getAllMobiles();
	public void editMobile(int mobileId, Mobile mobile);
	public int addMobile(Mobile mobile);

}
