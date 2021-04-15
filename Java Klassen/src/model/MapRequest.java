package model;

public class MapRequest {

	public Map map;

	public MapRequest(Map map) {
		this.map = map;
	}

	@Override
	public String toString() {
		return "Request: " + map.toString();
	}
}
