package at.fhv.itb5c.view.mainview;

import java.io.IOException;

import at.fhv.itb5c.view.login.LoginController;
import at.fhv.itb5c.view.user.UserViewController.UserViewState;
import at.fhv.itb5c.view.user.UserViewFactory;
import at.fhv.itb5c.view.usersearch.SearchUserViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainViewController {

	@FXML
	BorderPane _rootPane;

	@FXML
	Pane _mainPanel;

	@FXML
	public void closeMenueItemActionHandler(ActionEvent event) {
		((Stage) _rootPane.getScene().getWindow()).close();
	}

	@FXML
	public void createUserMenueItemActionHandler(ActionEvent event) throws IOException {	
		new UserViewFactory(UserViewState.newState).create(_mainPanel);
	}
	
	@FXML
	public void searchUserMenueItemActionHandler(ActionEvent event) throws IOException {
		new SearchUserViewFactory().create(_mainPanel);
	}
}
