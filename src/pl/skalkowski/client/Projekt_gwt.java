package pl.skalkowski.client;

import java.util.List;

import pl.skalkowski.model.Mobile;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Projekt_gwt implements EntryPoint {

	private FlexTable t;
	private MobileServiceAsync mobileService = GWT.create(MobileService.class);

	@Override
	public void onModuleLoad() {
		final Button sendButton = new Button("Add new mobile");
		RootPanel.get("sendButtonContainer").add(sendButton);

		t = new FlexTable();
		t.setText(0, 0, "Name");
		t.setText(0, 1, "Weigth");

		populateWithMobs();

		final AddMobButtonHandler addMobButtonHandler = new AddMobButtonHandler();
		sendButton.addClickHandler(addMobButtonHandler);

		RootPanel.get().add(t);

	}

	private void populateWithMobs() {
		mobileService.getAllMobiles(new AsyncCallback() {
			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(Object result) {
				List<Mobile> list = (List<Mobile>) result;
				for (Mobile s : list) {
					int rowCount = t.getRowCount();
					t.setText(rowCount, 0, s.getName());
					t.setText(rowCount, 1, Integer.toString(s.getWeight()));
					t.setWidget(rowCount, 2, new RemoveMobButton(s.getId()));
					t.setWidget(rowCount, 3, new EditMobButton(s.getId()));
				}
			}
		});
	}

	private class RemoveMobButton extends Button {
		private int mobId;

		public RemoveMobButton(int mobId) {
			super.setText("Remove");
			this.mobId = mobId;
			this.addClickHandler(new Handler());
		}

		private class Handler implements ClickHandler {
			@Override
			public void onClick(ClickEvent event) {
				final int row = t.getCellForEvent(event).getRowIndex();
				mobileService.deleteMobile(mobId, new AsyncCallback() {
					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(Object result) {
						t.removeRow(row);
					}

				});
			}
		}
	}

	/*
	 * Edit and add panel group
	 * 
	 */
	
	private class EditMobButton extends Button {
		private int mobId;

		public EditMobButton(int mobId) {
			super.setText("Edit");
			this.mobId = mobId;
			this.addClickHandler(new Handler());
		}

		private class Handler implements ClickHandler {
			@Override
			public void onClick(ClickEvent event) {
				final int row = t.getCellForEvent(event).getRowIndex();
				
				mobileService.getMobile(mobId,
						new AsyncCallback<Mobile>() {

							@Override
							public void onSuccess(Mobile result) {
								final EditMobilePopup editMobilePopup = new EditMobilePopup(row, result.getId(), result);								
								editMobilePopup.show();
							}

							@Override
							public void onFailure(Throwable caught) {
							}
						});
			}
		}
	}
	
	private class EditMobilePopup extends PopupPanel {
		private EditMobilePopup mobilePopup;
		private int row;
		private int mobId;
		private Mobile mobile;
		private HandlerRegistration handler;
		private FlowPanel panel = new FlowPanel();
		private Button editButton = new Button("Edit");
		private Label nameLabel = new Label("Mob name");
		private Label weigthLabel = new Label("Weigth");
		private TextBox nameArea = new TextBox();
		private TextBox weigthArea = new TextBox();
		
		public EditMobilePopup(int row, int mobId, Mobile mobile) {
			super(true);
			mobilePopup = this;
			this.row = row;
			this.mobId = mobId;
			this.mobile = mobile;
			
			panel.add(nameLabel);
			panel.add(nameArea);
			
			panel.add(weigthLabel);
			panel.add(weigthArea);
			panel.add(editButton);
			
			nameArea.setText(t.getText(row, 0));
			weigthArea.setText(t.getText(row, 1));
			
			add(panel);
			editButton.addClickHandler(new EditHandlerClass());
			
			
		}
		public class EditHandlerClass implements ClickHandler{
			private final int id = mobId;
			
			@Override
			public void onClick(ClickEvent event) {
				mobileService.editMobile(id, mobile, new AsyncCallback() {

					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(Object result) {
						t.setText(row, 0, nameArea.getText());
						t.setText(row, 1, weigthArea.getText());
					}
				});
				mobilePopup.hide();
			}
		}
	}
	
	
	private class AddMobilePopup extends PopupPanel {
		private AddMobilePopup mobilePopup;
		private HandlerRegistration handler;
		private FlowPanel addpanel = new FlowPanel();
		private AddClickHandler clickHandler = new AddClickHandler();
		private Button addButton = new Button("Add");
		private Label nameLabel = new Label("Mob name");
		private Label weigthLabel = new Label("Weigth");
		private TextBox nameArea = new TextBox();
		private TextBox weigthArea = new TextBox();
		
		public AddMobilePopup() {
			super(true);
			nameArea.setText("");
			weigthArea.setText("");

			mobilePopup = this;
			
			addpanel.add(nameLabel);
			addpanel.add(nameArea);			
			
			addpanel.add(weigthLabel);
			addpanel.add(weigthArea);
			
			addpanel.add(addButton);
			
			add(addpanel);
			handler = addButton.addClickHandler(clickHandler);
			
		}
		
		class AddClickHandler implements ClickHandler {
			
			@Override
			public void onClick(ClickEvent event) {	
				mobileService.addMobile(new Mobile(nameArea.getText(), Integer.parseInt(weigthArea.getText())),new AsyncCallback() {
					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(Object result) {
						int rowCount = t.getRowCount();
						t.setText(rowCount, 0, nameArea.getText());
						t.setText(rowCount, 1, weigthArea.getText());
						t.setWidget(rowCount, 2, new RemoveMobButton((Integer) result));
						t.setWidget(rowCount, 3, new EditMobButton((Integer) result));
						nameArea.setText("");
						weigthArea.setText("");
					}
				});
				mobilePopup.hide();
			}
		}
		
		
	}
	
	final AddMobilePopup addMobilePopup = new AddMobilePopup();
	
	public class AddMobButtonHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			addMobilePopup.show();
		}
	}

}
