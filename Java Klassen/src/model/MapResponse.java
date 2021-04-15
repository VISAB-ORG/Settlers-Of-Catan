package model;

public class MapResponse {

	public String mapAns;

	public MapResponse(String ans) {
		this.mapAns = ans;
	}

	@Override
	public String toString() {
		return "Response: " + mapAns.toString();
	}
}
