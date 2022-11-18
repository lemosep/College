
//@author Eduardo Lemos Paschoalini
//1373489
//Turma 2 - AEDSII

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException; 
import java.nio.charset.StandardCharsets; 
import java.nio.file.Files; 
import java.nio.file.Path; 
import java.nio.file.Paths; 
import java.util.ArrayList; 
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Date;
import java.util.HashMap;
import java.text.ParseException;
import java.text.SimpleDateFormat;  
import java.util.Date;

public class Questao7 {
	
	public static HashMap<Integer, Game> map = new HashMap<>();
	
	
	static boolean ended(String s) {
		return (s.equals("FIM"));
	}
	
	public static void main(String[] args) throws Exception {
		cleanInfo();
        //create entry1 and entry2 to storage the given data
		String[] entry1 = new String[1000];
		String[] entry2 = new String[1000];
		Scanner scanner = new Scanner(System.in);
		GameQueue queue = new GameQueue(5); // create list of objects
        int sz;

		int entryNumber1 = 0;
        int entryNumber2 = 0;
		
		//fill entry1 variable with the IDs
		do {
			entry1[entryNumber1] = scanner.nextLine();
		} while(ended(entry1[entryNumber1++]) != true);
		entryNumber1--;
		
		//fill entry 2
        sz = Integer.parseInt(scanner.nextLine());
        for(int i = 0; i < sz; i++) {
            entry2[entryNumber2++] = scanner.nextLine();
        }
        
        
        //assign game objects from hashMap to GameQueue
        for(int i = 0; i < entryNumber1; i++) {
            Game game = map.get(Integer.valueOf(entry1[i]));
            if(queue.queueIsFull() == false) {
    			queue.remove();
    			queue.insert(game);
    			queue.printAverage();
    		} else {
    			queue.insert(game);
    			queue.printAverage();
    		}
        }
        
        
        //treat insertion and removal conditions
        for(int i = 0; i < entryNumber2; i++) {
        	if(entry2[i].startsWith("I")) {
        		String[] spl = entry2[i].split(" ");
        		int value = Integer.valueOf(spl[1]);
        		
        		
        		if(queue.queueIsFull() == false) {
        			queue.remove();
        			queue.insert(map.get(value));
        			System.out.println("I " + value);
        			queue.printAverage();
        		} else {
        			queue.insert(map.get(value));
        			queue.printAverage();
        		}
        		
        	}
        	if(entry2[i].startsWith("R")) {
        		System.out.println("R");
        		Game game = queue.remove();
        		System.out.println("(R) " + game.getName());
        	}
        }
        
   
        //print game list after entry and removals
        for(int i = 0; i < queue.amount(); i++) {
        	Game game = queue.getGame(i);
            System.out.println("[" + i + "] " + game.getApp_ID() + " " + game.getName() + " " + toString(game.getData()) + " " + game.getOwners() + " " + 
            game.getAge() + " " + price(game.getPrice()) + " " + game.getDlcs() + " " + game.getLanguages() +  " " + game.getWebsite() + " " +
            game.getWindows() + " " + game.getMac() + " " + game.getLinux() + " " + game.getUpVotes() + " " + timeConvert(game.getAvg_Pt()) +
            " " + game.getDevelopers() + " " + game.getGenre());
        }
        
	}
	
	
	//clean information provided
	static void cleanInfo() throws Exception {
		Game[] game = new Game[6000];
		
		//create scanner
		Scanner scanner = new Scanner(new File("games.csv"));
		 scanner.useDelimiter(",");
		 
		 int i = 0;
		 
		 //create while method
		 while(scanner.hasNextLine()) {
			
			 String s = scanner.nextLine();
			 
			//add regex
			 String[] splitted = s.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
			 
			 //we get the id
			 int id  = Integer.valueOf(splitted[0]);
			 
			 //the name
			 String name = splitted[1];
			 
			 //set the date
			 Date data;
			 //create conditions
			 if(splitted[2].contains(",")) {
				 data = toDate(splitted[2].replace("\"", ""),1);
			 }else {
				 data = toDate(splitted[2].replace("\"", ""),0);
			 }
			 
			 //owners
			 String owners = splitted[3];
			 
			 //age
			 int age = Integer.valueOf(splitted[4]);
			 
			 //price value
			 float price;
			 //create conditions
			 if(Float.valueOf(splitted[5]) != 0) {
				 price = Float.valueOf(splitted[5]);
			 }else {
				 price = Float.valueOf("0.00");
			 }
			 
			 //dlcs
			 int dlcs = Integer.valueOf(splitted[6]);
			 
			 //create languages list!!!
			 List<String> lan = new ArrayList<>();
			 String language = splitted[7].replace("\"", "").replace("'", "").replace("[", "").replace("]", "".replace("\r\n", ""));
			 
			 for(int j = 0; j < language.split(",").length;j++) {
				 //add string in its current position
				 lan.add(language.split(",")[j]);
			 }
			 //add arraylist to array of strings
			 String[] languages = lan.toArray(new String[0]);
			 
			 //website
			 String website;
			 //create conditions
			 if(splitted[8] != "") {
				 website = splitted[8];
			 }else {
				 website = null;
			 }
			 
			 //windows, mac & linux
			 boolean windows = Boolean.valueOf(splitted[9]);
			 boolean mac = Boolean.valueOf(splitted[10]);
			 boolean linux = Boolean.valueOf(splitted[11]);
			 
			 //upvotes - for that, we need first and second values
			 float upvotes;
			 float first = Integer.valueOf(splitted[12]);
			 float second = Integer.valueOf(splitted[13]);
			 
			 //create conditions for upvotes
			 if(first != 0) {
				 upvotes = first/(first+second);
				 upvotes *= 100;
				 upvotes = Math.round(upvotes);
			 }else {
				 upvotes = 0;
			 }
			 
			 //average playtime
			 int avg_pt;
			 //create conditions
			 if(splitted[14] != "") {
				 avg_pt = Integer.valueOf(splitted[14]);
			 }else {
				 avg_pt = Integer.valueOf(null);
			 }
			 
			 //devs
			 String developers = splitted[15].replace("\"", "");
			 
			 //genres
			 //first off, we create a list
			 List<String> gen = new ArrayList<>();
			 if(splitted.length == 17) {
				 String genre = splitted[16].replace("\"", "");
				 for(int j = 0; j < genre.split(",").length; j++) {
					 gen.add(genre.split(",")[j]);
				 }
			 }else {
				 gen.add(null);
			 }
			 String genres[] = gen.toArray(new String[0]);
			 
			 //create game objects
			 game[i] = new Game(id, age, dlcs, avg_pt, name, owners, website, developers, price, upvotes, languages, genres, windows, mac, linux, data);
			 map.put(id,game[i]);
			 i++;
		 }
		 scanner.close();
	}

	private static Date toDate(String data, int type) throws Exception {
		Date date;
        if (type == 0) {
            date = new SimpleDateFormat("MM yyyy", Locale.ENGLISH).parse(data.replace("Jan", "01").replace("Feb", "02").replace("Mar", "03")
            .replace("Apr", "04").replace("May", "05").replace("Jun", "06")
            .replace("Jul", "07").replace("Aug", "08").replace("Sep", "09")
            .replace("Oct", "10").replace("Nov", "11").replace("Dec", "12"));
        } else {
            date = new SimpleDateFormat("MM dd, yyyy", Locale.ENGLISH).parse(data.replace("Jan", "01").replace("Feb", "02").replace("Mar", "03")
            .replace("Apr", "04").replace("May", "05").replace("Jun", "06")
            .replace("Jul", "07").replace("Aug", "08").replace("Sep", "09")
            .replace("Oct", "10").replace("Nov", "11").replace("Dec", "12"));
        }
        return date;
		
	}
	
	static String toString(Date date) throws Exception {
		String s = new SimpleDateFormat("MMM/yyyy").format(date);
		return s.replace("abr.", "Apr").replace("mai.", "May").replace("jun.", "Jun")
        .replace("jul.", "Jul").replace("ago.", "Aug").replace("set.", "Sep")
        .replace("out.", "Oct").replace("nov.", "Nov").replace("dez.", "Dec");
	}
	
	static String timeConvert(float time) {
		if(time == 0) {
			return null;
		}
		int hours, minutes;
		
		hours  = (int) time/60;
		minutes = (int)time%60;
		
		if(hours > 0 && minutes > 0) {
			return hours + "h" + " " + minutes + "m";
		}else if(hours > 0 && minutes == 0) {
			return hours + "h";
		}else if(hours == 0 && minutes > 0) {
			return minutes + "m";
		}
		return null;
	}
	
	static String price(float price) {
		int p = (int) price;
		float dif = price - p;
		if(price == 0) {
			return "0.00";
		}else if(dif == 0) {
			return String.valueOf(price) + "0";
		}else {
			return String.valueOf(price);
		}
	}

}

  class Game{
	private int app_id, age, dlcs, avg_pt;
    private String name, owners, website, developers;
    private Date release_date;
    private float price, upvotes;
    private String[] languages, genres;
    private boolean windows, mac, linux;
	
	//constructors
    public Game() throws Exception {
        String[] lan = {"English"};
        String[] gen = {"TBD"};
        String d = "Jan, 01 1";
        setApp_ID(0);
        setAge(0);
        setDlcs(0);
        setAvg_Pt(0);
        setName("Game");
        setOwners("TBA");
        setWebsite("www.game.com");
        setDevelopers("TBA");
        setPrice(0);
        setUpvotes(0);
        setLanguages(lan);
        setGenre(gen);
        setWindows(false);
        setMac(false);
        setLinux(false);
        
    }
    
    public Game(int id, int age, int dlcs, int avg_pt, String name, String owners, String website, String developers, float price, float upvotes, String[] language, String[] genres, boolean windows, boolean mac, boolean linux, Date data) throws Exception {
        setApp_ID(id);
        setAge(age);
        setDlcs(dlcs);
        setAvg_Pt(avg_pt);
        setName(name);
        setOwners(owners);
        setWebsite(website);
        setDevelopers(developers);
        setPrice(price);
        setUpvotes(upvotes);
        setLanguages(language);
        setGenre(genres);
        setWindows(windows);
        setMac(mac);
        setLinux(linux);
        setData(data);
        
    }
    

    public void setApp_ID(int id) {
        if (id >= 0) {
            app_id = id;
        }
    }

    public int getApp_ID() {
        return app_id;
    }

    public void setAge(int age) {
        if (age >= 0) {
            this.age = age;
        }
    }

    public int getAge() {
        return age;
    }

    public void setDlcs(int dlcs) {
        if (dlcs >= 0) {
            this.dlcs = dlcs;
        }
    }

    public int getDlcs() {
        return dlcs;
    }

    public void setAvg_Pt(int avg_pt) {
        if (avg_pt >= 0) {
            this.avg_pt = avg_pt;
        }
    }

    public int getAvg_Pt() {
        return avg_pt;
    }

    public void setName(String name) {
        if (name.length() >= 1) {
            this.name = name;
        }
    }

    public String getName() {
        return name;
    }

    public void setOwners(String owners) {
        if (name.length() >= 1) {
            this.owners = owners;
        }
    }

    public String getOwners() {
        return owners;
    }

    public void setWebsite(String website) {
        if (website != null) {
            this.website = website;
        }
    }

    public String getWebsite() {
        if (website != null) {
            return website;
        } else {
            return "null";
        }
    }

    public void setDevelopers(String developers) {
        if (name.length() >= 1) {
            this.developers = developers;
        }
    }

    public String getDevelopers() {
        return developers;
    }

    public void setData(Date data) {
        release_date = data;
    }

    public Date getData() {
        return release_date;
    }

    public void setPrice(float price) {
        if (price >= 0) {
            this.price = price;
        }
    }

    public float getPrice() {
        return price;
    }

    public void setUpvotes(float upvotes) {
        if (upvotes >= 0) {
            this.upvotes = upvotes;
        }
    }

    public float getUpvotes() {
        return upvotes;
    }

    public String getUpVotes() {
        int num = (int)getUpvotes();
        return num+"%";
    }

    public void setLanguages(String[] languages) {
        if (languages.length >= 0) {
            this.languages = languages;
        }
    }

    public String getLanguages() {
        String s = "[";
        String s2 = "";
        for (int i = 0; i < languages.length; i++) {
            s+=languages[i] + ",";
        }
        for (int i = 0; i < s.length() - 1; i++) {
            s2+=s.charAt(i);
        }
        s2+="]";
        return s2;
    }

    public void setGenre(String[] genres) {
        if (genres.length >= 0) {
            this.genres = genres;
        }
    }

    public String getGenre() {
        String s = "[";
        String s2 = "";
        for (int i = 0; i < genres.length; i++) {
            s+=genres[i] + ", ";
        }
        for (int i = 0; i < s.length() - 2; i++) {
            s2+=s.charAt(i);
        }
        s2+="]";
        return s2;
    }

    public void setWindows(boolean windows) {
        this.windows = windows;
    }

    public boolean getWindows() {
        return windows;
    }

    public void setMac(boolean mac) {
        this.mac = mac;
    }

    public boolean getMac() {
        return mac;
    }

    public void setLinux(boolean linux) {
        this.linux = linux;
    }

    public boolean getLinux() {
        return linux;
    }
	
}


//create class queue
class GameQueue {
    private Game[] arr;
    private int first,last,currentSize,length;
    
    //create size constructor
    GameQueue(){this(5);}
    
    GameQueue(int size) {
    	arr = new Game[size+1];
    	first = last = currentSize = 0;
    	length = size+1;
    }
    
    boolean queueIsFull() {
    	if(((last+1)%arr.length) == first) {
    		return false;
    	}else {
    		return true;
    	}
    }
    
    //insertion method
    void insert(Game x) throws Exception {
    
    	//add object to last position and shift
        arr[last] = x;
       	last = (last+1)%arr.length;	
       	currentSize++;
//       	System.out.println(currentSize + " adding");
       	
    }

    
    //remove objects from our queue, a First-in First-out Structure
    Game remove() throws Exception {
		
    	//how do we remove something if the queue is EMPTY?!
    	if(first == last) {
    		throw new Exception("Error");
    	}
    	    	
    	Game ans = this.arr[first];
        		
        //shift position
    	first = (first+1) % arr.length;
//    	System.out.println(currentSize + " REMOVING");
        currentSize--;
       	return ans;	
	}
    
    //prints average of avg_pt of objects inside queue
    void printAverage() {
    	
    	double total = 0;
    	
    	//check if elements are in place
    	for(int i = 0; i < currentSize; i++) {
//    		System.out.println(arr[i].getApp_ID());
    	}
    	
    	int start = first;
    	
    	while(start != last) {
    		total += arr[start].getAvg_Pt();
    		start = (start+1)%length;
    	}
    	
    	
    	long avg = Math.round(total/currentSize);
    	System.out.println(avg);
    	
    }
    
    
    
    //check amount of objects inside array
    int amount() {
    	return currentSize;
    }
    
    
    //get game in specific position
    Game getGame(int pos){
    	return arr[pos];
    }

}
 