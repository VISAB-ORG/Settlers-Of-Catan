package model.plan;

import model.CityBuildPlace;

/**
 * Klasse, die die Aktion des Bauens von Städten darstellt
 * 
 * @author tjark
 */
public class BuildCity extends Action {
	
	private int row, column;
	
	public BuildCity() {}

	public BuildCity(CityBuildPlace randomCityPlace) {
		this.row = randomCityPlace.row;
		this.column = randomCityPlace.column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

}
