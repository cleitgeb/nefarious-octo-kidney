package at.fhv.itb5c.view.util.factories;

import java.io.IOException;

import javafx.scene.layout.Pane;

public interface IPanelAndViewFactory {
	public Pane create() throws IOException;
}
