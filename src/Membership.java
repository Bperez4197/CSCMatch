import java.util.Iterator;
import java.util.LinkedList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Membership implements Iterable<Member>, Serializable{

	private LinkedList<Member> members;
	private static LinkedList<Member> mem;
	
	public Membership() {
		members = new LinkedList<Member>();
	}
	
	public void addMember(Member member) {
		members.add(member);
	}
	
	public LinkedList<Member> getMembers(){
		return members;
	}
	
	public Iterator<Member> iterator(){
		return members.iterator();
	}
	
	public Member findMember(String name) {
		for(Member member: this) {
			if(member.getName().equals(name)) {
				return member;
			}
		}
		return null;
	}
	
	
	public void save(String fileName) throws IOException{
		FileOutputStream fos = new FileOutputStream(fileName); 
		ObjectOutputStream os = new ObjectOutputStream(fos); 
		os.writeObject(this); 
		os.flush(); 
		os.close();
	}
	
	public static Membership load(String fileName) throws IOException, ClassNotFoundException, FileNotFoundException{
		FileInputStream fis = new FileInputStream(fileName);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Membership savedFile = (Membership) ois.readObject();
		ois.close();
		return savedFile;
	}
	
	public String toString() { 
		 String result = ""; 
		 for (Member member: this) { 
		  result += member.getName() + "\n"; 
		 } 
		 return result; 
		}
}
