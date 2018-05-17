package pt.ulisboa.tecnico.cmu.DataObjects;

import java.io.Serializable;

public class Location implements Serializable {

  private String name;

  public Location(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
