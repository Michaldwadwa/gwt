package pl.skalkowski.client;

import java.util.ArrayList;

import pl.skalkowski.model.Mobile;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MobileServiceAsync {

	 void getMobile(int id, AsyncCallback<Mobile> callback);
	 void deleteMobile(int mobilepId, AsyncCallback callback);
	 void getAllMobiles(AsyncCallback callback);
	 void editMobile(int mobileId, Mobile spacecraft, AsyncCallback callback);
	 void addMobile(Mobile spacecraft, AsyncCallback callback);

}
