package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Klasse, die die Karte als Repräsentation zur Übermittelung an die KIs enthält
 * 
 * @author Tjark Harjes
 */
public class Map {
	
	
    /**
     * Die Namen der möglichen Felder
     */
	public enum Material
    {
        brick,
        wheat,
        rock,
        wood,
        sheep,
        sand,
        ocean
    }

    /**
     * Die Felder
     */
    public List<Tile> tiles;

    /**
     * Die Bauplätze für Siedlungen
     */
    public List<VillageBuildPlace> villageBuildPlaces;
    
    /**
     * Die Bauplätze für Straßen
     */
    public List<RoadBuildPlace> roadBuildPlaces;
    
    /**
     * Die Bauplätze für Städte
     */
    public List<CityBuildPlace> cityBuildPlaces;
    
    /**
     * Die Bauplätze für Städte
     */
    //public List<CityBuildPlace> cityBuildPlaces;

    
    /**
     * Default Konstruktor
     */
    public Map()
    {
        tiles = new ArrayList<Tile>();
        villageBuildPlaces = new ArrayList<VillageBuildPlace>();
        roadBuildPlaces = new ArrayList<RoadBuildPlace>();
    }
    
    @Override
    public String toString() {
        String toString = "Tiles: \n";
        for (Tile tile : tiles) {
            toString += tile.Message();
        }
        toString += "\n Build places Village: ";
        for (VillageBuildPlace place : villageBuildPlaces)
        {
            toString += place.toString();
        }
        toString += "\n Build places Road: ";
        for (RoadBuildPlace place : roadBuildPlaces)
        {
            toString += place.toString();
        }
        return toString;
    }

    /**
     * Prüfung, ob es aktive Bauplätze für Siedlungen gibt
     * @return
     */
	public boolean villagePlacesActive() {
		for (int i = 0; i < villageBuildPlaces.size(); i++) {
			if (villageBuildPlaces.get(i).isActive) {
				return true;
			}
		}
		return false;
	}
	
    /**
     * Prüfung, ob es aktive Bauplätze für Straßen gibt
     * @return
     */
	public boolean roadPlacesActive() {
		for (int i = 0; i < roadBuildPlaces.size(); i++) {
			if (roadBuildPlaces.get(i).isActive) {
				return true;
			}
		}
		return false;
	}
	
    /**
     * Prüfung, ob es aktive Bauplätze für Städte gibt
     * @return
     */
	public boolean cityPlacesActive() {
		for (int i = 0; i < cityBuildPlaces.size(); i++) {
			if (cityBuildPlaces.get(i).isActive) {
				return true;
			}
		}
		return false;
	}
	
    public VillageBuildPlace getVillagePlaceByPosition(int row, int column)
    {
        for (int i = 0; i < villageBuildPlaces.size(); i++) {
        	VillageBuildPlace place = villageBuildPlaces.get(i);
            if (place.row == row && place.column == column)
            {
                return place;
            }
        }
        return null;
    }
    
    public RoadBuildPlace getRoadPlaceByPosition(int row, int column) {
        for (int i = 0; i < roadBuildPlaces.size(); i++) {
        	RoadBuildPlace place = roadBuildPlaces.get(i);
            if (place.row == row && place.column == column)
            {
                return place;
            }
        }
        return null;
    }
    
    public CityBuildPlace getCityPlaceByPosition(int row, int column) {
        for (int i = 0; i < cityBuildPlaces.size(); i++) {
        	CityBuildPlace place = cityBuildPlaces.get(i);
            if (place.row == row && place.column == column)
            {
                return place;
            }
        }
        return null;
    }
    
    

	public VillageBuildPlace getVillagePlaceByPreference(String preference) {
		List<VillageBuildPlace> activeVillageBuildPlaces = new ArrayList<VillageBuildPlace>();
		List<VillageBuildPlace> preferenceVillageBuildPlaces = new ArrayList<VillageBuildPlace>();
		for (int i = 0; i < villageBuildPlaces.size(); i++) {
			if (villageBuildPlaces.get(i).isActive) {
				activeVillageBuildPlaces.add(villageBuildPlaces.get(i));
			}
		}
		for (int i = 0; i < activeVillageBuildPlaces.size(); i++) {
			if (activeVillageBuildPlaces.get(i).resources.contains(preference)) {
				preferenceVillageBuildPlaces.add(activeVillageBuildPlaces.get(i));
			}
		}
		Random rand = new Random(System.currentTimeMillis());
		if (preferenceVillageBuildPlaces.size() == 1) {
			return preferenceVillageBuildPlaces.get(0);
		} else if (preferenceVillageBuildPlaces.size() > 1) {
			return preferenceVillageBuildPlaces.get(rand.nextInt(preferenceVillageBuildPlaces.size()));
		} else if (activeVillageBuildPlaces.size() == 1) {
			return activeVillageBuildPlaces.get(0);
		} else if (activeVillageBuildPlaces.size() > 1) {
			return activeVillageBuildPlaces.get(rand.nextInt(activeVillageBuildPlaces.size()));
		} 
		return null;
	}    
	
	public CityBuildPlace getCityPlaceByPreference(String preference) {
		List<CityBuildPlace> preferenceCityBuildPlaces = new ArrayList<CityBuildPlace>();
		for (int i = 0; i < cityBuildPlaces.size(); i++) {
			if (cityBuildPlaces.get(i).isActive && cityBuildPlaces.get(i).resources.contains(preference)) {
				preferenceCityBuildPlaces.add(cityBuildPlaces.get(i));
			}
		}
		Random rand = new Random(System.currentTimeMillis());
		if (preferenceCityBuildPlaces.size() == 1) {
			return preferenceCityBuildPlaces.get(0);
		} else if (preferenceCityBuildPlaces.size() > 0) 
			return preferenceCityBuildPlaces.get(rand.nextInt(preferenceCityBuildPlaces.size()));
		return null;
	}    


    
	public RoadBuildPlace getRandomRoadPlace() {
		List<RoadBuildPlace> activeRoadBuildPlaces = new ArrayList<RoadBuildPlace>();
		for (int i = 0; i < roadBuildPlaces.size(); i++) {
			if (roadBuildPlaces.get(i).isActive) {
				activeRoadBuildPlaces.add(roadBuildPlaces.get(i));
			}
		}
		Random rand = new Random(System.currentTimeMillis());
		if (activeRoadBuildPlaces.size() > 0) 
			return activeRoadBuildPlaces.get(rand.nextInt(activeRoadBuildPlaces.size()));
		return null;
	}

	public VillageBuildPlace getRandomVillagePlace() {
		List<VillageBuildPlace> activeVillageBuildPlaces = new ArrayList<VillageBuildPlace>();
		for (int i = 0; i < villageBuildPlaces.size(); i++) {
			if (villageBuildPlaces.get(i).isActive) {
				activeVillageBuildPlaces.add(villageBuildPlaces.get(i));
			}
		}
		Random rand = new Random(System.currentTimeMillis());
		if (activeVillageBuildPlaces.size() == 1) {
			return activeVillageBuildPlaces.get(0);
		}
		if (activeVillageBuildPlaces.size() > 0) 
			return activeVillageBuildPlaces.get(rand.nextInt(activeVillageBuildPlaces.size()));
		return null;
	}
	
	public CityBuildPlace getRandomCityPlace() {
		List<CityBuildPlace> activeCityBuildPlaces = new ArrayList<CityBuildPlace>();
		for (int i = 0; i < cityBuildPlaces.size(); i++) {
			if (cityBuildPlaces.get(i).isActive) {
				activeCityBuildPlaces.add(cityBuildPlaces.get(i));
			}
		}
		Random rand = new Random(System.currentTimeMillis());
		if (activeCityBuildPlaces.size() > 0) 
			return activeCityBuildPlaces.get(rand.nextInt(activeCityBuildPlaces.size()));
		return null;
	}

	public boolean cityPlacesExist() {
		if (cityBuildPlaces.size() > 0)
			return true;
		return false;
	}
}

