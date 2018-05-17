package pt.ulisboa.tecnico.cmu.server;

import java.io.Serializable;
import java.util.UUID;

public class Account implements Serializable {

	private static final long serialVersionUID = -819438701814131856L;

	private String username;
	private String code;
	private String sessionId;
	private Tour tour;
	private Score score;
	private Location lastLocation;
	private Location currentLocation;

	public Account(String username, String code, Tour tour) {
		this.username = username;
		this.code = code;
		this.sessionId = null;
		this.tour = tour;
		this.score = new Score(tour, this);
		this.lastLocation = null;
		this.currentLocation = null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Account))
			return false;
		Account other = (Account) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	public Location getLastLocation() {
		return lastLocation;
	}

	public void setLastLocation(Location lastLocation) {
		this.lastLocation = lastLocation;
	}

	public Location getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Location currentLocation) {
		this.currentLocation = currentLocation;
	}

	public boolean hasActiveSession() {
		return this.sessionId != null;
	}

	public void generateSessionId() {
		setSessionId(UUID.randomUUID().toString());
	}

}
