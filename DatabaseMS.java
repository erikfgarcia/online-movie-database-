// --------------------------------------------------------
// 
// --------------------------------------------------------
import java.util.Scanner;
import java.util.InputMismatchException;
import java.sql.*;
import java.math.*;
import java.io.*;
import oracle.jdbc.driver.*;

/**
 *This is a Database Management System program  
 *
 * @author Erik F Garcia.
 *
 */
public class DatabaseMS{ 

//Database info	
static Connection conn = null;
static String username="";
static String password="";
static String url="jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";

	/**
 	 *   
 	 *
 	 * @param args arguments.
 	 */
	public static void main(String[] args) {						
			
		Scanner in = new Scanner(System.in);
		System.out.println("Welcome to Eflix!! Unlimited movies, TV shows, and more.");
		
		
		
		try{
			System.out.println("Please enter your username:");
			username = in.nextLine();
			System.out.println("Please enter your password:");
			password = in.nextLine();
			            		 
			executeQuery("commit");
			       
               }
               catch(SQLException | ClassNotFoundException  e) {
                                System.out.println("\n"+e.getMessage());
				in.close();System.exit(0);
                        }

		
		
		
		
		
		
		
		while(true) {
			System.out.println("\n");
			int choice = doMenu(in);
			try {
				switch(choice) {
					case 1:  tableContent(in); break;
					case 2:  addRecord(in);    break;
					case 3:  updateRecords(in); break;
					case 4:  deleteRecords(in); break;
					case 5:  searchMovie(in); break;
					case 6:  profileInfo(in); break;
					case 7:  in.close();System.exit(0);
				}
			}
			catch(SQLException  | ClassNotFoundException e) {
				System.out.println("\n"+e.getMessage());
			}
		}
 	}

	/**
 	 *  
 	 *
 	 *   
 	 *  
 	 */
	
	public static int doMenu(Scanner in) {
		while(true) {
			try {
				System.out.println("\nWelcome! What would you like to do?");
				System.out.println(" 1) View Tables");
				System.out.println(" 2) Add Records");
				System.out.println(" 3) Update Records");
				System.out.println(" 4) Remove Records");
				System.out.println(" 5) Search Database");
				System.out.println(" 6) Show Rental History");
				System.out.println(" 7) Exit The Program");
				System.out.println("\n--------------------------- ");
				int choice = in.nextInt();
				in.nextLine();
				
				if(choice < 1 || choice > 7) {
					System.out.println("Invalid selection");
					continue;
				}
				
				return choice;
			}
			catch(InputMismatchException  e) {
				System.out.println("Invalid selection");
				in.nextLine();
			}
		}
	}


	public static void tableContent(Scanner in) throws ClassNotFoundException, SQLException  {
                
		Class.forName( "oracle.jdbc.driver.OracleDriver" ) ;
                conn = DriverManager.getConnection( "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu", username, password ) ;		
		Statement stmt = conn.createStatement() ;
		ResultSet rs = stmt.executeQuery( "SELECT table_name FROM user_tables") ;
	
                System.out.println("DATABASE TABLES\n");

		while( rs.next() )
          		System.out.println( rs.getString(1));

                rs.close();
                conn.close();        

		int choice=0;
                 
		while(true) {
                        try {
				System.out.println("----------------------------");
                                System.out.println("What would you like to do?");
				System.out.println(" 1) See Table Content");
               			System.out.println(" 2) Main Menu");
                                System.out.println("--------------------------- ");
                                choice = in.nextInt();
                                in.nextLine();

                                if(choice < 1 || choice > 2) {
                                        System.out.println("Invalid selection");
                                        continue;
                                }    
			//	if(choice==1)
                        //		printTable(in);
			//	else
					break;
                        }
                        catch(InputMismatchException  e) {
                                System.out.println("Invalid selection");
                                in.nextLine();
                        }
                }
		if(choice==1)
			printTable(in);
		
	}


	public static void printTable(Scanner in) throws ClassNotFoundException, SQLException{

		System.out.println("Enter the table name: ");
         	String table = in.nextLine();
		table = table.toUpperCase();   	
         	String query = "SELECT * FROM "+table;
	        String getNumber ="select count(*) from user_tab_columns where table_name= '"+table+"'";
	        int numColumns = 0;	

	  	Class.forName( "oracle.jdbc.driver.OracleDriver" );
          	conn = DriverManager.getConnection( "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu", username, password ) ;
          	Statement stmt = conn.createStatement();
          	ResultSet rs = stmt.executeQuery(getNumber);


	  	while( rs.next())
                	numColumns = rs.getInt(1);
		
		rs = stmt.executeQuery(query);

		
                while( rs.next()){

			for(int i =0; i < numColumns; i++)
		        	System.out.print(rs.getString(i+1)+"\t\t");

			System.out.println(""); 
		}
		
		rs.close();
        	conn.close();
	}

	public static void addRecord(Scanner in) throws ClassNotFoundException, SQLException{

         	while(true) {
                        try {
                                System.out.println("Where would you like to add a record?");
                                System.out.println(" 1) Account (Member)");
                                System.out.println(" 2) Movie");
                                System.out.println(" 3) Actor");
                                System.out.println(" 4) Profile");
                                System.out.println(" 5) Credit Card");
				System.out.println(" 6) Genre");
				System.out.println(" 7) View");
				System.out.println(" 8) Casting");
			        System.out.println(" 9) Main Menu");
                                System.out.println("\n--------------------------- ");
                                int choice = in.nextInt();
                                in.nextLine();

                                if(choice < 1 || choice > 9) {
                                        System.out.println("Invalid selection");
                                        continue;
                                }
                                
                             	  if(choice == 9)
					 break;

				 addRecordHelper(in , choice);
				 break;
                        }
                        catch(InputMismatchException  e) {
                                System.out.println("Invalid selection");
                                in.nextLine();
                        }
                }


	 }

	public static void addRecordHelper(Scanner in, int choice) throws ClassNotFoundException, SQLException{

	        String query = "";
		boolean movie = false;
		boolean account = false;
		String movie_id= "";
		String member_id="";
		switch(choice) {//add account
                		case 1: 
                                      
					System.out.println("Enter a new Member ID");
					member_id = in.nextLine();
					System.out.println("Enter First Name");
					String first_name = in.nextLine();
					System.out.println("Enter Last Name");
                                        String last_name = in.nextLine();
                                        query="INSERT INTO Member_(member_id, first_name, last_name) VALUES('"+member_id+"','"+first_name+"','"+last_name+"')";
					account = true;
                                break;
				//add movie
                                case 2:   
					System.out.println("Enter a new Movie ID");
                                        movie_id = in.nextLine();
                                        System.out.println("Enter Movie Name");
                                        String movie_name = in.nextLine();
                                        System.out.println("Enter Producer Name");
                                        String Producer = in.nextLine();
                                        //System.out.println("Enter a average rating");
                                        //String average_rating = in.nextLine();
                                        System.out.println("Enter Production Year");
                                        int Production_year = in.nextInt();
					movie = true;
                                        
  query="INSERT INTO Movie(movie_id, movie_name, Producer, average_rating, Production_year) VALUES('"+movie_id+"','"+movie_name+"','"+Producer+"', null,"+Production_year+")"; 
				break;
				//actor	  
                                case 3: 
                    		        System.out.println("Enter a new Actor ID");
                                        String actor_id = in.nextLine();
                                        System.out.println("Enter First Name");
                                        String actor_first_name = in.nextLine();
                                        System.out.println("Enter Last Name");
                                        String actor_last_name = in.nextLine();
			                query="INSERT INTO Actor(actor_id, first_name ,last_name) VALUES('"+actor_id+"','"+actor_first_name+"','"+actor_last_name+"')";
				break;
				//profile
				case 4:  
				       
					System.out.println("Enter The Member ID");
                                        String p_member_id = in.nextLine();
                                        System.out.println("Enter Profile Name");
                                        String profile_first_name = in.nextLine();
                                        System.out.println("Enter Favorite Movie Genre");
                                        String favorite_movie_genre = in.nextLine();
	query = "INSERT INTO Profiles(member_id, profile_name, favorite_movie_genre) VALUES('"+p_member_id+"','"+profile_first_name+"','"+favorite_movie_genre+"')";
				break;
                                //Credit card
                                case 5:

					System.out.println("Enter a new Member ID");
                                        String c_member_id = in.nextLine();
                                        System.out.println("Enter Credit Card Number");
                                        String number_ = in.nextLine();
                                        System.out.println("Enter Expiration Date: day-month-year like 01-may-2020");
                                        String exp_date = in.nextLine();
					System.out.println("Enter Security Code");
                                        String security_code = in.nextLine();				     
				
	query="INSERT INTO Credit_Card(member_id, number_, exp_date, security_code) VALUES ('"+c_member_id+"', '"+number_+"','"+exp_date+"' ,'"+security_code+"')";
				
				break;
                                //genre
				case 6:
					System.out.println("Enter a new Movie ID");
                                        String g_movie_id = in.nextLine();
                                        System.out.println("Enter Genre");
                                        String genre_name = in.nextLine();
		query="INSERT INTO Genre(movie_id, genre_name) VALUES('"+g_movie_id+"','"+genre_name+"')";		
				break;
                                //view
				case 7:   
					System.out.println("Enter a new Movie ID");
                                        String v_movie_id = in.nextLine();
                                        System.out.println("Enter Member ID");
                                        String v_member_id = in.nextLine();
                                        System.out.println("Enter Profile Name");
                                        String v_profile_name = in.nextLine();
                                        System.out.println("Enter Rating (Between 1 to 5)");
                                        float rating = in.nextFloat();

					
query="INSERT INTO View_(movie_id, member_id, profile_name, rating) VALUES('"+v_movie_id+"', '"+v_member_id+"','"+v_profile_name+"',"+rating+")";
				break;
                                 //movie
				case 8:
                                        System.out.println("Enter Movie ID");
                                        String c_movie_id = in.nextLine();
                                        System.out.println("Enter Actor ID");
                                        String c_actor_id = in.nextLine(); 
					query="INSERT INTO Casting(movie_id, actor_id) VALUES('"+c_movie_id+"', '"+c_actor_id+"')";                                        
				break;	

                        }

		
				executeQuery(query);//  
				System.out.println("------------------------------------------------------------------------------------------");



                if(account){  
			
			while(true){	
			 System.out.println("Entering Profiles..... You can add up to 5 profiles...............");
                                System.out.println("Press ENTER to continue or enter 'n' to quit");
                                String answ = in.nextLine();

                                if(answ.equals("n"))
                                        break;

                                     accountProfile(in, member_id);

                        }

                        System.out.println("------------------------------------------------------------------------------------------");

		}
	 
	        if(movie){

            		while(true){ // 
				System.out.println("Entering the movie Casting...............");
                        	System.out.println("Press ENTER to continue or enter 'n' to quit");
                                String answ = in.nextLine();
                                
				if(answ.equals("n"))
					break;

                                     movieCasting(in, movie_id);

			}

			System.out.println("------------------------------------------------------------------------------------------");

			while(true){

 				System.out.println("Entering the movie genre................");
                                System.out.println("Press ENTER to continue or enter'n' to quit");
                                String answ = in.nextLine();
 
                                if(answ.equals("n"))
                                         break;

                                      movieGenre(in, movie_id);                      

			}

		}	
	 
	 }



 	 public static void accountProfile(Scanner in, String member_id) throws ClassNotFoundException, SQLException{
                        
               System.out.println("Enter Profile Name");
               String profile_first_name = in.nextLine();
               System.out.println("Enter Favorite Movie Genre");
               String favorite_movie_genre = in.nextLine();
   String query = "INSERT INTO Profiles(member_id, profile_name, favorite_movie_genre) VALUES('"+member_id+"','"+profile_first_name+"','"+favorite_movie_genre+"')";
	 executeQuery(query);


         }


	public static void movieCasting(Scanner in, String movie_id) throws ClassNotFoundException, SQLException{
                                         
	        System.out.println("Enter Actor ID");
                String actor_id = in.nextLine();
                String query="INSERT INTO Casting(movie_id, actor_id) VALUES('"+movie_id+"', '"+actor_id+"')";
                executeQuery(query);   
	 }


	public static void movieGenre(Scanner in, String movie_id) throws ClassNotFoundException, SQLException{
                
                 System.out.println("Enter Genre");
                 String genre_name = in.nextLine();
                 String query="INSERT INTO Genre(movie_id, genre_name) VALUES('"+movie_id+"','"+genre_name+"')";
		 executeQuery(query);

	 }

	public static void updateRecords(Scanner in) throws ClassNotFoundException, SQLException{

                   while(true) {
                        try {
                                System.out.println("Select the table to update records from:");
                                System.out.println(" 1) Account");
                                System.out.println(" 2) Movie");
                                System.out.println(" 3) Actor");
                                System.out.println(" 4) Profile");
                                System.out.println(" 5) Credit Card");
                                System.out.println(" 6) Genre");
                                System.out.println(" 7) View");
                                System.out.println(" 8) Casting");
                                System.out.println(" 9) Main Menu");
                                System.out.println("\n--------------------------- ");
                                int choice = in.nextInt();
                                in.nextLine();

                                if(choice < 1 || choice > 9) {
                                        System.out.println("Invalid selection");
                                        continue;
                                }


                                 if(choice == 9)
                                         break;

                                 updateRecordsHelper(in , choice);
                                         break;
                        }
                        catch(InputMismatchException  e) {
                                System.out.println("Invalid selection");
                                in.nextLine();
                        }

		}
	}

	public static void updateRecordsHelper(Scanner in, int choice) throws ClassNotFoundException, SQLException{

		
                 String query ="";

		 switch(choice) {
                                        case 1:  
                                        	System.out.println("From Acount(Member_) you can update first_name, last_name");
                                                System.out.println("Enter the Member ID");
						String member_id = in.nextLine(); 
						System.out.println("Enter the new first name");
					        String first_name = in.nextLine(); 
						System.out.println("Enter the new last name");
						String last_name = in.nextLine();
			query ="UPDATE Member_ SET first_name ='"+first_name +"', last_name = '"+last_name+"' WHERE member_id = '"+member_id+"'";



					break;
					case 2:  
                                         	System.out.println("From Movie you can update Producer and Production Year");
						System.out.println("Enter the Movie ID ");
						String movie_id = in.nextLine();
						System.out.println("Enter the new Producer");
						String Producer = in.nextLine();
						System.out.println("Enter the new Production Year");
						String Production_year = in.nextLine();
			query ="UPDATE Movie SET Producer ='"+Producer+"', Production_year = '"+Production_year+"' WHERE movie_id = '"+movie_id+"'";
						
					break;

					case 3: 
                                        
						System.out.println("From Actor you can update First Name ,Last Name) ");
						System.out.println("Enter the Actor ID");
						String actor_id = in.nextLine();
						System.out.println("Enter the new Actor First Name");
						String actor_first_name = in.nextLine();
						System.out.println("Enter the new ActorLast Name");
                                                String actor_last_name = in.nextLine();
			query ="UPDATE Actor SET first_name ='"+actor_first_name+"', last_name = '"+actor_last_name+"' WHERE actor_id = '"+actor_id+"'";
					 break;

					case 4:
                                        	System.out.println("From profiles you can update profile_name, favorite_movie_genre");
						System.out.println("Enter the Member ID");
						String p_member_id = in.nextLine();
						System.out.println("Enter the curernt Profile Name");
						String profile_name = in.nextLine();
						System.out.println("Enter the new Profile Name");
						String n_profile_name = in.nextLine();
						System.out.println("Enter the Favorite Movie Genre");
                                                String favorite_movie_genre = in.nextLine();
query ="UPDATE Profiles SET profile_name ='"+n_profile_name+"', favorite_movie_genre = '"+favorite_movie_genre+"' WHERE member_id = '"+p_member_id+"' AND profile_name= '"+profile_name+"'";
			
                       
				        break;	
					
					case 5: 

                                         	System.out.println("From Credit card you can update  exp_date, security_code");
						System.out.println("Enter the Member ID");
						String c_member_id = in.nextLine();
						System.out.println("Enter the Credit Card Number");
						String number_ = in.nextLine();
						System.out.println("Enter the new Expiration Date (like 01-may-2020)");
				                String exp_date = in.nextLine();		
					 	System.out.println("Enter the new Security Code");
                                                String security_code = in.nextLine();
  	query ="UPDATE Credit_Card SET exp_date ='"+exp_date+"', security_code= '"+security_code+"' WHERE member_id = '"+c_member_id+"' AND number_= '"+number_+"'";

	                                break;
                                        
					case 6: 
                                         	System.out.println("From Genre you can update genre_name ");
						System.out.println("Enter the Movie ID");
						String g_movie_id = in.nextLine();
						System.out.println("Enter the current Genre Name");
						String genre_name = in.nextLine();
						System.out.println("Enter the new Genre Name");
                                                String n_genre_name = in.nextLine();


	query ="UPDATE Genre SET genre_name ='"+n_genre_name+"'  WHERE movie_id = '"+g_movie_id+"' AND genre_name = '"+genre_name+"'";

					break;
					
					case 7: 
						 System.out.println("From View_ you can update rating ");			 
						 System.out.println("Enter the Movie ID");
						 String v_movie_id = in.nextLine();
						 System.out.println("Enter the Member ID");
						 String v_member_id = in.nextLine();
						 System.out.println("Enter the Profile Name");
						 String v_profile_name = in.nextLine();
						 System.out.println("Enter the new Rating");
						 float rating = in.nextFloat();

 query ="UPDATE View_ SET rating ='"+rating+"'  WHERE movie_id = '"+v_movie_id+"' AND member_id = '"+v_member_id+"' AND profile_name = '"+v_profile_name+"'";

					 break;

					case 8:

					System.out.println("Enter Movie ID");
                                        String c_movie_id = in.nextLine();
                                        System.out.println("Enter Current Actor ID");
                                        String c_actor_id = in.nextLine();
					System.out.println("Enter New Actor ID");
                                        String n_c_actor_id = in.nextLine();
              query="UPDATE Casting SET actor_id ='"+n_c_actor_id+"'  WHERE movie_id = '"+c_movie_id+"' AND actor_id = '"+c_actor_id+"'";
				
				
				       break;

                                }

		         executeQuery(query);		
		
		}


	public static void deleteRecords(Scanner in) throws ClassNotFoundException, SQLException{

                 while(true) {
                        try {
                                System.out.println("Select the table to delete records from:");
                                System.out.println(" 1) Account");
                                System.out.println(" 2) Movie");
                                System.out.println(" 3) Actor");
                                System.out.println(" 4) Profile");
                                System.out.println(" 5) Credit Card");
                                System.out.println(" 6) Genre");
                                System.out.println(" 7) View");
                                System.out.println(" 8) Casting");
                                System.out.println(" 9) Main Menu");
                                System.out.println("\n--------------------------- ");
                                int choice = in.nextInt();
                                in.nextLine();

                                if(choice < 1 || choice > 9) {
                                        System.out.println("Invalid selection");
                                        continue;
                                }

                                
                                 if(choice == 9)
                                         break;

				 deleteRecordsHelper(in , choice);
				         break;
                        }
                        catch(InputMismatchException  e) {
                                System.out.println("Invalid selection");
                                in.nextLine();
                        }
                }

	}

 	public static void deleteRecordsHelper(Scanner in, int choice) throws ClassNotFoundException, SQLException{
                           String query=""; 
                              switch(choice) {
                                        case 1:  
					 System.out.println("You will delete a account from the database!!!");
					 System.out.println("Enter the Member ID to delete");
                                         String member_id = in.nextLine();       
					 query="DELETE FROM Member_ WHERE member_id='"+member_id+"'";
						break;
                                
					case 2: 
					 System.out.println("You will delete an movie from the database!!!");
                                         System.out.println("Enter the Movie ID to delete");
                                         String movie_id = in.nextLine();
                                         query="DELETE FROM Movie WHERE movie_id='"+movie_id+"'";

					
						break;
                                
					case 3:  
					     System.out.println("You will delete an actor from the database!!!");
                                         System.out.println("Enter the Actor ID to delete");
                                         String actor_id = in.nextLine();
                                         query="DELETE FROM Actor WHERE actor_id='"+actor_id+"'";
  
						break;
                                
					case 4:  
					 System.out.println("You will delete a profile from the database!!!");
                                         System.out.println("Enter the Member ID who owns the profile to delete");
                                         String p_member_id = in.nextLine();
					 System.out.println("Enter the profile name to delete");
                                         String profile_name = in.nextLine();
                                         query="DELETE FROM Profiles WHERE member_id='"+p_member_id+"' AND profile_name='"+profile_name+"'";

						break;
                                
					case 5: 
					     System.out.println("You will delete a credit card from the database!!!");
                                         System.out.println("Enter the Member ID who owms the credit cars to delete");
                                         String c_member_id = in.nextLine();
					 System.out.println("Enter the credit card Mumber to delete");
                                         String c_number = in.nextLine();
                                         query="DELETE FROM Credit_Card WHERE member_id='"+c_member_id+"'AND number_ = '"+c_number+"'";
  
						break;
                                
					case 6: 
					 System.out.println("You will delete a genre associated with a movie from the database!!!");
                                         System.out.println("Enter the Movie ID");
                                         String g_movie_id = in.nextLine();
                                         System.out.println("Enter the Genre Name");
                                         String genre_name = in.nextLine();
                                         query="DELETE FROM Genre WHERE movie_id='"+g_movie_id+"' AND genre_name= '"+genre_name+"'";
						break;
                                
					case 7:  
					 System.out.println("You will delete a view from the database!!!");
                                         System.out.println("Enter the Movie ID that has the view");
                                         String v_movie_id = in.nextLine();
					 System.out.println("Enter the Member ID who owms the profile that wacthed the movie");
                                         String v_member_id = in.nextLine();
					 System.out.println("Enter the Profile Name who watched the movie");
                                         String v_profile_name = in.nextLine();
                            query="DELETE FROM View_ WHERE movie_id='"+v_movie_id+"' AND member_id='"+v_member_id+"' AND profile_name='"+v_profile_name+"'";					
						break;

					case 8:

                                        System.out.println("Enter Movie ID");
                                        String c_movie_id = in.nextLine();
                                        System.out.println("Enter Actor ID");
                                        String c_actor_id = in.nextLine();
                                        query="DELETE FROM casting WHERE movie_id='"+c_movie_id+"' AND actor_id='"+c_actor_id+"'";  

					break;
				
                                }

			      executeQuery(query);

		 }


	public static void searchMovie(Scanner in) throws ClassNotFoundException, SQLException {

		 while(true) {
                        try {
                                System.out.println("search movie by::");
                                System.out.println(" 1) Movie Name");
                                System.out.println(" 2) Actor");
                                System.out.println(" 3) Main Menu");
                                System.out.println("\n--------------------------- ");
                                int choice = in.nextInt();
                                in.nextLine();

                                if(choice < 1 || choice > 3) {
                                        System.out.println("Invalid selection");
                                        continue;
                                }


                                 if(choice == 3)
                                         break;

                                 searchMovieHelper(in , choice);
                                         break;
                        }
                        catch(InputMismatchException  e) {
                                System.out.println("Invalid selection");
                                in.nextLine();
                        }
                }

	}	
        
	public static void searchMovieHelper(Scanner in, int choice) throws ClassNotFoundException, SQLException {

		
		String query ="";
		int numColumns = 8;
		String[] title = {"> Movie ID:", "Movie Name:", "Producer:", "Average Rating:", "Production Year:","Actor ID:","Name:",""};

		if(choice == 1){ //serach by movie name 

                  	System.out.println("Enter Movie Name (or a fraction)");
                  	String movie_name = in.nextLine();
    		query ="SELECT temp.movie_id, temp.movie_name, temp.producer, temp.average_rating, temp.production_year, A.actor_id, A.first_name, A.last_name "
			      +"FROM (SELECT * "
			      	    +"FROM Movie M "
			            +"WHERE M.movie_name LIKE '%"+movie_name+"%')temp, Casting C, Actor A "
			      +"WHERE C.movie_id = temp.movie_id AND C.actor_id = A.actor_id";


                        
		}else if (choice ==2){//by actor

                        System.out.println("Enter Actor Name (or a fraction)");
	                String first_name = in.nextLine();
			System.out.println("Enter Actor Last Name (or a fraction)");
                        String last_name = in.nextLine();
                	query ="SELECT M.movie_id, M.movie_name, M.producer, M.average_rating, M.production_year, A.actor_id, A.first_name, A.last_name" 
			     +"	FROM Movie M, Actor A, (SELECT *" 
						     +" FROM Casting C"
						     +" WHERE C.actor_id in (SELECT actor_id"
									  +" FROM Actor"
									  +" WHERE first_name LIKE '%"+first_name+"%' AND last_name LIKE '%"+last_name+"%'))temp"
			     +"	WHERE temp.movie_id = M.movie_id AND temp.actor_id = A.actor_id";			      
		

               }


	       Class.forName( "oracle.jdbc.driver.OracleDriver" );
                        conn = DriverManager.getConnection( "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu", username, password ) ;
                        Statement stmt = conn.createStatement();
                        ResultSet rs =  stmt.executeQuery(query);

                        while( rs.next()){

                        for(int i =0; i < numColumns; i++)
                                System.out.print(title[i]+""+rs.getString(i+1)+" ");

                        System.out.println("");
                }

                rs.close();
                conn.close();



	}


 	public static void profileInfo(Scanner in)throws ClassNotFoundException, SQLException {

	while(true) {
                        try {
                                System.out.println(" 1) Profile Info");
                                System.out.println(" 2) Rental History");
                                System.out.println(" 3) Main Menu");
                                System.out.println("\n--------------------------- ");
                                int choice = in.nextInt();
                                in.nextLine();

                                if(choice < 1 || choice > 3) {
                                        System.out.println("Invalid selection");
                                        continue;
                                }


                                 if(choice == 3)
                                         break;

                                 profileInfoHelper(in , choice);
                                         break;
                        }
                        catch(InputMismatchException  e) {
                                System.out.println("Invalid selection");
                                in.nextLine();
                        }
                }

 	}


 	public static void  profileInfoHelper(Scanner in , int choice)throws ClassNotFoundException, SQLException {
 		String query="";

		if(choice == 1){//info

			System.out.println("Enter the member ID:");
			String member_id = in.nextLine();
			System.out.println("Enter the Profile Name:");
                	String profile_name = in.nextLine();

          		query="SELECT M.first_name, M.last_name, P.profile_name, P.favorite_movie_genre "
	 			+"FROM Profiles P, Member_ M "
				+"WHERE M.member_id = P.member_id AND M.member_id = '"+member_id+"' AND profile_name= '"+profile_name+"'";

	  		Class.forName( "oracle.jdbc.driver.OracleDriver" );
                	conn = DriverManager.getConnection( "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu", username, password ) ;
                 	Statement stmt = conn.createStatement();
                 	ResultSet rs = stmt.executeQuery(query);

                 	while( rs.next()){
                        	System.out.println("");
                         	System.out.println("> Member Name: "+rs.getString(1)+ " "+rs.getString(2));
                         	System.out.println("> Profile Name: "+rs.getString(3));
                         	System.out.println("> Favorite Genre: "+rs.getString(4));
                         	System.out.println("");
                 	}

                 	rs.close();
                 	conn.close();

		}else if (choice ==2){//rental history
         		System.out.println("Enter the member ID:");
                	String member_id = in.nextLine();
                	System.out.println("Enter the Profile Name:");
                	String profile_name = in.nextLine();
//"Member ID:","Profile Name:

String[] title = {"> Movie ID:", "Movie Name:", "Producer:", "Average Rating:", "Production Year:","Profile Rating:"};
//temp.member_id, temp.profile_name

		query ="SELECT M.movie_id, M.movie_name, M.producer, M.average_rating, M.production_year, temp.rating "
		      		+"FROM Movie M, (SELECT * "
	      					+"FROM View_ V "
		       				+"WHERE V.member_Id ='"+member_id+"' AND V.profile_name = '"+profile_name+"')temp "
						+"WHERE temp.movie_id = M.Movie_id";

		Class.forName( "oracle.jdbc.driver.OracleDriver" );
                conn = DriverManager.getConnection( "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu", username, password ) ;
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while( rs.next()){

                        for(int i =0; i < 6; i++)
                                System.out.print(title[i]+""+rs.getString(i+1)+"|");

                        System.out.println("");
                }

                rs.close();
                conn.close();

               }
 	}


  	public static void executeQuery(String query) throws ClassNotFoundException, SQLException {
	  
                Class.forName( "oracle.jdbc.driver.OracleDriver" );
                conn = DriverManager.getConnection( "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu", username, password ) ;
                Statement stmt = conn.createStatement();
                ResultSet rs=  stmt.executeQuery(query);
                rs.close();
                conn.close();  
  	}	  

}
