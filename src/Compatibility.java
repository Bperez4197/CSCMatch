import java.io.Serializable;
import java.util.LinkedList;

public class Compatibility implements Comparable<Compatibility>, Serializable{

	private Member their;
	private int score;
	
	public Compatibility(Member my, Member their) {
		this.their = their;
		LinkedList<Interest> theirInterests = their.getInterests();
		
		for(Interest interest: theirInterests) {
			if(my.findInterest(interest.getTopic()) != null) {
				score += (interest.getLevel() * my.findInterest(interest.getTopic()).getLevel());
			}else {
				score += (int) Math.floor(interest.getLevel() / 2);
			}
		}
	}
	
	public int getScore() {
		return score;
	}
	
	public Member getTheir() {
		return their;
	}
	
	public String toString() {
		return their.getName() + ", " + their.getYear() + ": " + score;
	}
	
	public int compareTo(Compatibility o) {
		int c = 1;
		if(this.getTheir().getName() == o.getTheir().getName())
			c = 0;
		else if(this.getScore() > o.getScore())
			c = -1;
		
		return c;
	}
}
