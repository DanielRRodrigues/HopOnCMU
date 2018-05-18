package pt.ulisboa.tecnico.cmu.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tour implements Serializable {

	private static final long serialVersionUID = -3481821139235668465L;

	private String id;
	private String name;
	private List<Location> locations;
	private List<Account> accounts;
	private List<String> availableCodes;
	private List<String> usedCodes;

	public Tour(String id, String name) {
		this.id = id;
		this.name = name;
		this.locations = new ArrayList<Location>();
		this.accounts = new ArrayList<Account>();
		this.availableCodes = new ArrayList<String>();
		this.usedCodes = new ArrayList<String>();
	}

	public Tour(String id, String name, List<Location> locations,
			List<Account> accounts, List<String> availableCodes) {
		this.id = id;
		this.name = name;
		this.locations = new ArrayList<Location>(locations);
		this.accounts = new ArrayList<Account>(accounts);
		this.availableCodes = new ArrayList<String>(availableCodes);
		this.usedCodes = new ArrayList<String>();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Tour))
			return false;
		Tour other = (Tour) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getLocationByName(String name) {
		for (Location l : this.locations) {
			if (l.getName().equals(name))
				return l;
		}
		return null;
	}

	public List<Location> getLocations() {
		return Collections.unmodifiableList(this.locations);
	}

	public void setLocations(List<Location> locations) {
		this.locations = new ArrayList<Location>(locations);
	}

	public boolean addLocation(Location location) {
		return this.locations.add(location);
	}

	public boolean removeLocation(Location location) {
		return this.locations.remove(location);
	}

	public List<Account> getAcounts() {
		return Collections.unmodifiableList(this.accounts);
	}

	public Account getAcountByUsername(String username) {
		for (Account a : this.accounts) {
			if (a.getUsername().equals(username))
				return a;
		}
		return null;
	}

	public void setAcounts(List<Account> accounts) {
		this.accounts = new ArrayList<Account>(accounts);
	}

	public boolean addAccount(Account account) {
		return this.accounts.add(account);
	}

	public boolean removeAccount(Account account) {
		return this.accounts.remove(account);
	}

	public List<String> getAvailableCodes() {
		return Collections.unmodifiableList(this.availableCodes);
	}

	public void setAvailableCodes(List<String> availableCodes) {
		this.availableCodes = new ArrayList<String>(availableCodes);
	}

	public boolean addAvailableCode(String availableCodes) {
		if (availableCodes.startsWith(id))
			return this.availableCodes.add(availableCodes);
		else
			return this.availableCodes.add(id + availableCodes);
	}

	public boolean removeAvailableCode(String availableCodes) {
		return this.availableCodes.remove(availableCodes);
	}

	public List<String> getUsedCodes() {
		return Collections.unmodifiableList(this.usedCodes);
	}

	public void setUsedCodes(List<String> usedCodes) {
		this.usedCodes = new ArrayList<String>(usedCodes);
	}

	public boolean addUsedCode(String usedCode) {
		return this.usedCodes.add(usedCode);
	}

	public boolean removeUsedCode(String usedCode) {
		return this.usedCodes.remove(usedCode);
	}

	public List<Score> getAllScores(boolean ordered) {
		List<Score> allScores = new ArrayList<Score>();
		for (Account a : this.accounts) {
			allScores.add(a.getScore());
		}
		if (ordered)
			Collections.sort(allScores);
		return allScores;
	}

	public List<Quiz> getAllQuizzes() {
		List<Quiz> allQuizzes = new ArrayList<Quiz>();
		for (Location l : this.locations) {
			allQuizzes.addAll(l.getQuizzes());
		}
		return allQuizzes;
	}

	public boolean useCode(String code) {
		if (this.availableCodes.contains(code)) {
			this.availableCodes.remove(code);
			this.usedCodes.add(code);
			return true;
		}
		return false;
	}

	public Location getNextLocation(Location location) {
		if (this.locations.isEmpty())
			return null;
		if (location == null)
			return this.locations.get(0);
		if (!this.locations.contains(location))
			return null;
		int position = this.locations.indexOf(location);
		if (position == this.locations.size() - 1)
			return null;
		return this.locations.get(position + 1);
	}

	public Location getEndLocation() {
		if (this.locations.isEmpty())
			return null;
		return this.locations.get(this.locations.size() - 1);
	}
}
