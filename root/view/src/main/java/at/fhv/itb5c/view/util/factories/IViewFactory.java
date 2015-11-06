package at.fhv.itb5c.view.util.factories;

import java.io.IOException;
import javafx.scene.layout.Pane;

public interface IViewFactory {
	void create(Pane paneToPlaceIn) throws IOException;
}
