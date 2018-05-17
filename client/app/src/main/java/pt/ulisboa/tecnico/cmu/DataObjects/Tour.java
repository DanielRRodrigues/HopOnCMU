package pt.ulisboa.tecnico.cmu.DataObjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tour implements Serializable {

  private String name;
  private List<Location> locations;
  private List<Score> scores = new ArrayList<Score>();

  public Tour(String name) {
    this.name = name;
    this.locations = new ArrayList<Location>();
  }

  public Tour(String name, List<Location> locations) {
    this.name = name;
    this.locations = locations;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Location getLocationByName(String name) {
    for (Location l : this.locations) {
      if (l.getName().equals(name)) {
        return l;
      }
    }
    return null;
  }

  public List<Location> getLocations() {
    return this.locations;
  }

  public void setLocations(List<Location> locations) {
    this.locations = locations;
  }

  public void addLocation(Location location) {
    if (!this.locations.contains(location)) {
      this.locations.add(location);
    }
  }

  public List<Score> getScores() {
    return this.scores;
  }
  public void addScore(Score score) {
    this.scores.add(score);
  }

  public void updateScores() {
    //TODO
    //Should get the updated scores from server
  }

  public void removeLocation(Location location) {
    this.locations.remove(location);
  }
}
