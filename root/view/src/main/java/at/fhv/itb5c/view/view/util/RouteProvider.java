package at.fhv.itb5c.view.view.util;

import java.net.URL;
import java.util.HashMap;

@Deprecated
public class RouteProvider {
	private HashMap<Object, URL> _rootMapping;

	private RouteProvider() {
		_rootMapping = new HashMap<>();
	}

	private static RouteProvider _instance;

	public static RouteProvider getInstance() {
		if (_instance == null) {
			_instance = new RouteProvider();
		}

		return _instance;
	}

	public void add(Object controller, String route) {
		_rootMapping.put(controller, this.getClass().getResource(route));
	}

	public URL get(Object controller) {
		if (!_rootMapping.containsKey(controller)) {
			return null;
		} else {
			return _rootMapping.get(controller);
		}
	}
}
