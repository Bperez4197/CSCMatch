import java.util.Scanner;
import java.util.Iterator;
import java.util.LinkedList;
import java.io.Serializable;
import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;

public class Menu implements Serializable{

	private Membership membership;
	private Scanner keyboard;
	private String userInput;
	private boolean saved = true;
	private boolean edited;
	
	public Menu() {
		membership = new Membership();
		keyboard = new Scanner(System.in);
	}
	
	public void run() throws ClassNotFoundException, IOException {
		boolean flag = true;
		
		while(flag){
			
			System.out.println("a. Load the Members \nb. Save the Members \nc. List All Members \nd. Add a Member \ne. Remove a Member \n"
					+ "f. List Member \ng. Add an Interest to a Member \nh. Quit");
			userInput = keyboard.nextLine();
			
			switch(userInput.toLowerCase().charAt(0)) {
			case 'a':
				loadMembers();
				break;
			case 'b':
				saveMembers();
				break;
			case 'c':
				listAllMembers();
				break;
			case 'd':
				addMember();
				break;
			case 'e':
				removeMember();
				break;
			case 'f':
				listMember();
				break;
			case 'g':
				addInterest();
				break;
			case 'h':
				if(saved == false) {
					System.out.println("You have unsaved changes. Are you sure you want to quit? Y/N: ");
					String input = keyboard.nextLine();
					if(input.toLowerCase().equals("y")) {
						flag = false;
						break;
					}
				}else {
					break;
				}
				default:
					System.out.println("Invalid menu choice");
			}
			
		} 
			
	}
	
	private void loadMembers() throws ClassNotFoundException, IOException{
		boolean fileAvailable = false;
		String fileName;
		File membersFile;
		
		System.out.println("\n-- Loading Members --");
		
		do {
			
			System.out.print("\nWhat is the name of the file you would like to load in? ");
			fileName = keyboard.nextLine();
			membersFile = new File(fileName + ".csc");


			if(membersFile.canRead())
				fileAvailable = true;
			else if(!membersFile.exists()) {
				System.out.println("File: " + fileName + ".csc does not exist! Please enter correct file.");
				fileAvailable = false;
			}
			else {
				System.out.println("File can't be read. Please enter correct filename."); 
				fileAvailable = false;
			}
		} while(!fileAvailable);
		
		
		membership = Membership.load(fileName + ".csc");
		System.out.println("File: " + fileName + ".csc loaded successfully.");
		saved = false;
	}
	
	private void saveMembers() throws IOException{
		boolean canWrite;
		boolean loop = true;
		String fileName;
		String response;
		File membersFile;
		PrintWriter file;

		System.out.println("\n-- Saving Members --");

		do {

			boolean canCreate = true;
			file = null;

			System.out.print("\nWhat would you like to save this file as? ");
			fileName = keyboard.nextLine();
			membersFile = new File(fileName + ".csc");

			if(membersFile.exists()) {
				System.out.print(fileName + " already exists, would you like to overwrite it? Y/N: ");
				response = keyboard.nextLine().toLowerCase();
				if(response.charAt(0) == 'y')
					canCreate = true;
				else {
					canCreate = false;
					loop = true;
				}
			}else
				canCreate = true;
			
			if(canCreate)
				file = new PrintWriter(fileName + ".csc");
			
			file.close();

			canWrite = membersFile.canWrite(); 

			if(!canWrite && canCreate) { 
				System.out.println("\nCan't write to file. Please enter another filename.");
				loop = true;
			}else if(canWrite && canCreate)
				loop = false;

		} while(loop);
		
		edited = false;
		membership.save(fileName + ".csc");

		System.out.println("\nSaved file: " + fileName + ".csc successfully.");
		saved = true;
	}
	
	private void listAllMembers() {
		for(Member member: membership) {
			System.out.println(member.getName());
		}
	}
	
	private void addMember() {
		System.out.print("Name: "); 
		 String name = keyboard.nextLine(); 
		 if (membership.findMember(name) != null) { 
		  System.out.println("That member already exists"); 
		  return; 
		 } 
		 
		 int year;
		 
		 do {
			 System.out.print("Year (1-5): "); 
			 while(!keyboard.hasNextInt()) {
				 String input = keyboard.next(); 
				 System.out.println(input + " is not a valid number. Try again.");
			 }
			 year = keyboard.nextInt();
		 }while(year < 0);
		
		 
		 keyboard.nextLine();
		
		  
		 try { 
		  Member member = new Member(name, year); 
		  membership.addMember(member);
		  saved = false;
		 } catch (InvalidMemberException e) { 
		  System.out.println(e.getMessage()); 
		 } 
	}
	
	
	
	private void removeMember() {
		System.out.println("What is the name of the member you'd like to remove?");
		String userInput = keyboard.nextLine();
		Member member = membership.findMember(userInput);
		Iterator<Member> iter = membership.iterator();
		Member tempMem;
		while(iter.hasNext()) {
			tempMem = iter.next();
			if(member != null && tempMem.getName().equals(member.getName())) {
				iter.remove();
				System.out.println(member.getName() + " has been removed.");
				break;
			}else {
				System.out.println("The member's name you entered was NOT found and therefore not removed from the list.");
			}
		}
	}
	
	
	
	private void listMember() {
		System.out.println("Enter the name of the Member you'd like to list");
		String name = keyboard.nextLine();
		if(membership.findMember(name) == null) {
			System.out.println("No member by that name was found");
		}else {
			System.out.println(membership.findMember(name));
		}
		
	}
	
	
	
	private void addInterest() {
		System.out.print("Name of Member: "); 
		 String name = keyboard.nextLine(); 
		 if (membership.findMember(name) == null) { 
		  System.out.println("You must create that member before you can add interests to them."); 
		  return; 
		 } 
		 
		 Member member = membership.findMember(name);
		 String topic;
		 int level;
		 
		 
		 do {
			 System.out.print("Topic: ");
			 topic = keyboard.nextLine();
			 System.out.println("Interest Level (1-10): ");
			 while(!keyboard.hasNextInt()) {
				 String input = keyboard.next(); 
				 System.out.println(input + " is not a valid number. Try again.");
			 }
			 level = keyboard.nextInt();
			 
		 }while(level < 0);
		
		 keyboard.nextLine();
		
		 try { 
			 Interest i = new Interest(topic,level);
			 member.addInterest(i);
			 LinkedList<Member> members = membership.getMembers();
			  if(members.size() < 2) {
				  
			  }else {
				  Compatibility temp = new Compatibility(member, members.get(members.size() - (members.size() - 2)));
				  member.addMatch(temp);
			  }
		 } catch (InvalidInterestException e) { 
		  System.out.println(e.getMessage()); 
		 } 
	}
}
