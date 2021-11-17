import java.util.LinkedList;
import java.io.Serializable;
import java.util.Iterator;

public class Member implements Iterable<Interest>, Serializable {

	private String name;
	private int year;
	private LinkedList<Interest> interests;
	private LinkedList<Compatibility> userMatches;
	
	public Member(String name, int year) throws InvalidMemberException {
		setName(name);
		setYear(year);
		interests = new LinkedList<Interest>();
		userMatches = new LinkedList<Compatibility>();
	}
	
	public void addInterest(Interest interest) throws InvalidInterestException {
		String topic = interest.getTopic();
		int level = interest.getLevel();
		Interest tempInterest = this.findInterest(topic);
		if(tempInterest == null) {
			interests.add(interest);
		}else {
			tempInterest.setLevel(level);
		}
		interests.sort(null);
	}
	
	public void addMatch(Compatibility m) {
		userMatches.add(m);
	}
	
	public void removeMatch(Member m) {
		boolean removed = false;
		Iterator<Compatibility> itr = userMatches.iterator();
		while(itr.hasNext() && !removed) {
			Compatibility n = itr.next();
			if(n.getTheir().getName().equals(m.getName())) {
				itr.remove();
				removed = true;
			}
		}
		
	}
	
	public Interest findInterest(String topic) {
		for(Interest interest: interests) {
			if(interest.getTopic().equals(topic)) {
				return interest;
			}
		}
		return null;
	}
	
	public void setName(String name) throws InvalidMemberException {
		if(name.equals("")) {
			throw new InvalidMemberException("Name may not be blank");
		}
		this.name = name;
	}
	
	public void setYear(int year) throws InvalidMemberException {
		if(year < 1 || year > 5) {
			throw new InvalidMemberException("Year " + year + " is invalid; please specify between 1-5");
		}
		this.year = year;
	}
	
	public String getName() {
		return name;
	}
	
	public int getYear() {
		return year;
	}
	
	public LinkedList<Interest> getInterests(){
		return interests;
	}
	
	public Iterator<Interest> iterator(){
		return interests.iterator();
	}
	
	public String toString() {
		String result = "Name: " + name + " Year: " + year + " Interests: "; 
		 for (Interest interest: interests) { 
		  result += interest.getTopic() + " - " + interest.getLevel() + ", "; 
		 } 
		 result += userMatches.size() > 0 ? "\n-- Top Matches --\n" : "\n-- No Matches, add other members --\n";
			
			for(int i = 0; i < (userMatches.size() < 5 ? userMatches.size() : 5); i++) {
				result += userMatches.get(i).toString() + " \n";
			}
			
			return result;
		}
	}
