import java.io.Serializable;

public class Interest implements Comparable<Interest>, Serializable{

	private String topic;
	private int level;
	
	public Interest(String topic, int level) throws InvalidInterestException {
		setTopic(topic);
		setLevel(level);
	}
	
	public int compareTo(Interest o) {
		String thisName = this.getTopic();
		String oName = o.getTopic();
		int c = 0;
		int i = 0;
		
		if(o.getLevel() > this.getLevel())
		{
			c = 1;
		}
		else if (o.getLevel() == this.getLevel())
		{
			while(c == 0 && i < thisName.length() && i < oName.length())
			{
				char charThis = thisName.charAt(i);
				char charO = oName.charAt(i);
				if(charThis < charO)
				{
					c = -1;
				}
				else if (charThis > charO)
				{
					c = 1;
				}
				else
				{
					i++;
				}
			}
			
		}
		else 
		{
			c = -1;
		}
		return c;
	}
	
	public void setTopic(String topic) throws InvalidInterestException {
		if(topic.equals("")) {
			throw new InvalidInterestException("Topic may not be blank");
		}
		this.topic = topic;
	}
	
	public void setLevel(int level) throws InvalidInterestException {
		if(level < 0 || level >= 11) {
			throw new InvalidInterestException("Interest Level " + level + " is invalid; please specify between 1-10");
		}
		this.level = level;
	}
	
	public String getTopic() {
		return topic;
	}
	
	public int getLevel() {
		return level;
	}
	
	public String toString() {
		return topic + " - " + level + ", ";
	}
}
