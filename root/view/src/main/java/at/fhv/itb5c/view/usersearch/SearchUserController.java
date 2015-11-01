package at.fhv.itb5c.view.usersearch;

import java.rmi.RemoteException;
import java.util.List;

import at.fhv.itb5c.commons.dto.rmi.IUserRMI;
import at.fhv.itb5c.rmi.client.RMIClient;
import at.fhv.itb5c.rmi.server.UserRMI;
import at.fhv.itb5c.view.user.UserModel;
import at.fhv.itb5c.view.util.AlertUtil;
import at.fhv.itb5c.view.util.PanelClosable;
import at.fhv.itb5c.view.util.PanelCloseHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class SearchUserController implements PanelClosable{
	
	@FXML private TextField _firstNameTextField;	
	@FXML private TextField _lastNameTextField;	
	@FXML private CheckBox _paidCheckBox;
	@FXML private ComboBox _departmentsCombobox;
	
	@FXML private TableView<IUserRMI> _searchResultTableView;
	@FXML private TableColumn<IUserRMI, String> _firstNameTableColumn;
	@FXML private TableColumn<IUserRMI, String>  _lastNameTableColumn;
	@FXML private TableColumn<IUserRMI, String>  _addressTableColumn;
	@FXML private TableColumn<IUserRMI, String>  _dateOfBirthTableColumn;
	@FXML private TableColumn<IUserRMI, String>  _membershipFeeTableColumn;
	
	private PanelCloseHandler _panelCloseHandler;
	private SearchUserModel _searchUserModel;
	
	public SearchUserController() {
		_searchUserModel = new SearchUserModel();
	}
	
	public void initialize() {
		_firstNameTextField.textProperty().bindBidirectional(_searchUserModel.getFirstName());
		_lastNameTextField.textProperty().bindBidirectional(_searchUserModel.getLastName());
		_paidCheckBox.selectedProperty().bindBidirectional(_searchUserModel.getIsPaid());
		_searchResultTableView.setItems(_searchUserModel.getSearchResult());
		
		//TODO(san7985): init _departmentsCombobox when the factory can be accessed	
		
		initializeTable();
	}
	
	public void initializeTable() {
		_firstNameTableColumn.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<IUserRMI,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<IUserRMI, String> param) {
				String value;
				try {
					if(param.getValue().getFirstName() != null) {
						value = param.getValue().getFirstName().toString();
					} else {
						value = "";
					}
				} catch (RemoteException e) {
					AlertUtil.ConnectionAlert();
					value = "ERROR";
				}
				
				return new SimpleStringProperty(value);
			}
		});
		
		_lastNameTableColumn.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<IUserRMI,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<IUserRMI, String> param) {
				String value;
				try {
					if(param.getValue().getLastName() != null) {
						value = param.getValue().getLastName().toString();
					} else {
						value = "";
					}
				} catch (RemoteException e) {
					AlertUtil.ConnectionAlert();
					value = "ERROR";
				}
				
				return new SimpleStringProperty(value);
			}
		});
		
		_addressTableColumn.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<IUserRMI,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<IUserRMI, String> param) {			
				String value;
				try {
					if(param.getValue().getAddress() != null) {
						value = param.getValue().getAddress().toString();
					} else {
						value = "";
					}
				} catch (RemoteException e) {
					AlertUtil.ConnectionAlert();
					value = "ERROR";
				}
				
				return new SimpleStringProperty(value);
			}
		});

		_dateOfBirthTableColumn.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<IUserRMI,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<IUserRMI, String> param) {
				String value;
				try {
					if(param.getValue().getDateOfBirth() != null) {
						value = param.getValue().getDateOfBirth().toString();
					} else {
						value = "";
					}
				} catch (RemoteException e) {
					AlertUtil.ConnectionAlert();
					value = "ERROR";
				}
				
				return new SimpleStringProperty(value);
			}
		});
		
		_membershipFeeTableColumn.setCellValueFactory( new Callback<TableColumn.CellDataFeatures<IUserRMI,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<IUserRMI, String> param) {
				String value;
				try {
					if(param.getValue().getDateOfBirth() != null) {
						value = Boolean.toString(param.getValue().getMembershipFeePaid());
					} else {
						value = "";
					}
				} catch (RemoteException e) {
					AlertUtil.ConnectionAlert();
					value = "ERROR";
				}
				
				return new SimpleStringProperty(value);
			}
		});
	}
	
	@FXML
	public void searchButtonOnReleasedEventHandler(MouseEvent mouseEvent){	
		try {
			List<IUserRMI> users = RMIClient.getRMIClient().getUserFactory().findUsers(_searchUserModel.getFirstName().getValue(), 
																_searchUserModel.getLastName().getValue(), 
																null, //Todo(san7985): add property for department
																_searchUserModel.getIsPaid().getValue());
			
			_searchUserModel.getSearchResult().clear();
			_searchUserModel.getSearchResult().addAll(users);
		} catch (RemoteException e) {
			AlertUtil.ConnectionAlert();
		}
	}
	
	@FXML
	public void cancelButtonOnMouseReleasedEventHandler(MouseEvent mouseEvent){
		_panelCloseHandler.close();
	}

	@Override
	public void setPanelCloseHandler(PanelCloseHandler panelCloseHandler) {
		_panelCloseHandler = panelCloseHandler;
	}
}
