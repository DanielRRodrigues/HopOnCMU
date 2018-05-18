package pt.ulisboa.tecnico.cmu.response;

import java.util.List;

public class GetTourDetailsResponse implements Response {

  private static final long serialVersionUID = 5435007773243183042L;

  private boolean error;
  private String tourName;
  private List<String> locationList;
  private String currentLocation;
  private String nextLocation;

  public GetTourDetailsResponse(boolean error) {
    this.error = error;
    this.tourName = null;
    this.locationList = null;
    this.currentLocation = null;
    this.nextLocation = null;
  }

  public GetTourDetailsResponse(String tourName, List<String> locationList,
      String currentLocation, String nextLocation) {
    this.error = false;
    this.tourName = tourName;
    this.locationList = locationList;
    this.currentLocation = currentLocation;
    this.nextLocation = nextLocation;
  }

  @Override
  public void handle(ResponseHandler rh) {
    rh.handle(this);
  }

  public boolean getError() {
    return error;
  }

  public String getTourName() {
    return tourName;
  }

  public List<String> getLocationList() {
    return locationList;
  }

  public String getCurrentLocation() {
    return currentLocation;
  }

  public String getNextLocation() {
    return nextLocation;
  }

}
