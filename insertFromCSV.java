package project1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class insertFromCSV extends Application {

	private Statement stmt;
	private TextField tfSSN = new TextField();
	private TextField tfDepartID= new TextField();
	private Label lblStatus = new Label();

	int tuplesNo=25000;
	int borrowerNo=1000;
	
	public static void main(String []args){
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage){
		initializeDB();

		Button btInsertBook = new Button("InsertBook");
		HBox hbox1=new HBox(5);
		hbox1.getChildren().addAll(new Label("Table Name"),tfSSN,new Label("Primaey Key"),tfDepartID,btInsertBook);

		
		Button btInsertBorrower = new Button("InsertBorrower");
		HBox hbox2=new HBox(5);
		hbox2.getChildren().addAll(new Label("          "),new Label("                  "),btInsertBorrower);

		
		VBox vbox =new VBox(10);
		vbox.getChildren().addAll(hbox1,lblStatus,hbox2);
		
		tfSSN.setPrefColumnCount(6);
		tfDepartID.setPrefColumnCount(6); //Dno
		btInsertBook.setOnAction(e -> insertFromBookCsv());
		btInsertBorrower.setOnAction(e -> insertFromBorrowerCsv());

		Scene scene = new Scene(vbox,600,200);
		primaryStage.setTitle("Create Table");
		primaryStage.setScene(scene);
		primaryStage.show();		
	}

	private void initializeDB(){

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded");

			Connection connection=DriverManager.getConnection("jdbc:mysql://localhost/librarySys","root","newpass");
			System.out.println("Database connected");
			stmt=connection.createStatement();
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	/*
	public void insertFromBorrowerCsv(){
		individualBorrower[] theBorrower = new individualBorrower[borrowerNo];
		theBorrower =readBorrowerCsv();
		for(int i=0;i<borrowerNo;i++){
			theBorrower[i].printBorrowerInfo();
		}
		
	}
	*/
	public void insertFromBorrowerCsv(){
		individualBorrower[] theBorrower = new individualBorrower[borrowerNo];
		theBorrower = readBorrowerCsv();

		for(int i=0;i<borrowerNo;i++){
			//insert into Borrower
			insertValueBorrowerWithCheck(theBorrower[i].borrower_id,theBorrower[i].ssn,
					theBorrower[i].fullName,theBorrower[i].address,theBorrower[i].phone);
		}
		
	}
	public individualBorrower[] readBorrowerCsv(){
		//borrower_id,ssn,first_name,last_name,email,address,city,state,phone
		individualBorrower[] theBorrower = new individualBorrower[borrowerNo];

		String csvFile = "/Users/JC/Documents/UTD CS/CS6360_Database Design/Project1/borrowersDeleteLine0.csv";
		String line = "";
		String cvsSplitBy = ",";// use comma (,) as separator

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			int borrowerCount =0; 	
			while ((line = br.readLine()) != null && borrowerCount<borrowerNo) {
				String[] borrowersInfo = line.split(cvsSplitBy);                

				theBorrower[borrowerCount] = new individualBorrower();
				if(borrowerCount>=0){ //0 line is the table title, so skip
					//borrowersInfo[2]=borrowersInfo[2].replace("&amp;","&");
					//borrowersInfo[3]=borrowersInfo[3].replace("&amp;","&");
					//borrowersInfo[2]=borrowersInfo[2].replace("'","''");
					//borrowersInfo[3]=borrowersInfo[3].replace("'","''");
					theBorrower[borrowerCount].writeInInfo(Integer.parseInt(borrowersInfo[0]),borrowersInfo[1],
							borrowersInfo[2],borrowersInfo[3],borrowersInfo[4],borrowersInfo[5],borrowersInfo[6],
							borrowersInfo[7],borrowersInfo[8]);

				}
				else{

				}
				theBorrower[borrowerCount].combineName();
				theBorrower[borrowerCount].combineAddress();
				borrowerCount++;
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return theBorrower;
	}

	public void insertFromBookCsv(){

		individualBook[] theBook = new individualBook[tuplesNo];
		theBook = readCsv();

		for(int i=0;i<tuplesNo;i++){
			
			//insert into BOOK
			insertValueBookWithCheck(theBook[i].isbnTen,theBook[i].title);
			//insert into AUTHOR, BOOK_AUTHORS
			theBook[i].seperateAuthors();	
			
			switch(theBook[i].authorTotal){
			case 1:
				insertAuthorWithCheck(theBook[i].author1st);
				insertBook_AuthorFetchId(theBook[i].author1st, theBook[i].isbnTen);
				break;
			case 2:
				insertAuthorWithCheck(theBook[i].author1st);
				insertBook_AuthorFetchId(theBook[i].author1st, theBook[i].isbnTen);
				insertAuthorWithCheck(theBook[i].author2nd);
				insertBook_AuthorFetchId(theBook[i].author2nd, theBook[i].isbnTen);
				break;
			case 3:
				insertAuthorWithCheck(theBook[i].author1st);
				insertBook_AuthorFetchId(theBook[i].author1st, theBook[i].isbnTen);
				insertAuthorWithCheck(theBook[i].author2nd);
				insertBook_AuthorFetchId(theBook[i].author2nd, theBook[i].isbnTen);
				insertAuthorWithCheck(theBook[i].author3rd);
				insertBook_AuthorFetchId(theBook[i].author3rd, theBook[i].isbnTen);
				break;
			case 4:
				insertAuthorWithCheck(theBook[i].author1st);
				insertBook_AuthorFetchId(theBook[i].author1st, theBook[i].isbnTen);
				insertAuthorWithCheck(theBook[i].author2nd);
				insertBook_AuthorFetchId(theBook[i].author2nd, theBook[i].isbnTen);
				insertAuthorWithCheck(theBook[i].author3rd);
				insertBook_AuthorFetchId(theBook[i].author3rd, theBook[i].isbnTen);
				insertAuthorWithCheck(theBook[i].author4th);
				insertBook_AuthorFetchId(theBook[i].author4th, theBook[i].isbnTen);
				break;
			case 5:
				insertAuthorWithCheck(theBook[i].author1st);
				insertBook_AuthorFetchId(theBook[i].author1st, theBook[i].isbnTen);
				insertAuthorWithCheck(theBook[i].author2nd);
				insertBook_AuthorFetchId(theBook[i].author2nd, theBook[i].isbnTen);
				insertAuthorWithCheck(theBook[i].author3rd);
				insertBook_AuthorFetchId(theBook[i].author3rd, theBook[i].isbnTen);
				insertAuthorWithCheck(theBook[i].author4th);
				insertBook_AuthorFetchId(theBook[i].author4th, theBook[i].isbnTen);
				insertAuthorWithCheck(theBook[i].author5th);
				insertBook_AuthorFetchId(theBook[i].author5th, theBook[i].isbnTen);
				break;
			}
				
			System.out.println("isbn10 : "+theBook[i].isbnTen);
		}
	}

	public individualBook[] readCsv(){
		individualBook[] theBook = new individualBook[tuplesNo];

		String csvFile = "/Users/JC/Documents/UTD CS/CS6360_Database Design/Project1/booksDeleteLine0.csv";
		String line = "";
		String cvsSplitBy = "\\t";// use tab (\\t) as separator

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			int bookCount =0; 	
			while ((line = br.readLine()) != null && bookCount<tuplesNo) {
				String[] borrowersInfo = line.split(cvsSplitBy);                

				theBook[bookCount] = new individualBook();
				if(bookCount>=0){ //0 line is the table title, so skip
					//ISBN10	ISBN13	Title	Author	Cover	Publisher	Pages
					borrowersInfo[2]=borrowersInfo[2].replace("&amp;","&");
					borrowersInfo[3]=borrowersInfo[3].replace("&amp;","&");
					borrowersInfo[2]=borrowersInfo[2].replace("'","''");
					borrowersInfo[3]=borrowersInfo[3].replace("'","''");
					theBook[bookCount].writeInInfo(borrowersInfo[0],borrowersInfo[1],borrowersInfo[2],borrowersInfo[3],
							borrowersInfo[4],borrowersInfo[5],Integer.parseInt(borrowersInfo[6]));
					//System.out.println("isbn10 : "+borrowersInfo[0]);
				}
				else{

				}
				theBook[bookCount].seperateAuthors();
				bookCount++;
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return theBook;
	}
	public boolean insertValueBorrowerWithCheck(int theid, String thessn, String Bname, String address, String phone){
		boolean result=false;
		if(existSelectFrom("Card_id","Borrower","Card_id",Integer.toString(theid))==false){
			if(existSelectFrom("Ssn","Borrower","Ssn",thessn)==false){
				insertValueBorrower(theid,thessn,Bname,address,phone);
				result=true;
			}
			else{
				System.out.println("Duplicate ISBN !!!     "+thessn);
			}
		}
		else
			System.out.println("Duplicate borrower ID !!!     "+theid);
		return result;
	}
	public void insertValueBorrower(int id, String thessn, String Bname, String address, String phone){
		try{
			String updateString ="INSERT INTO Borrower VALUES ('"+id+"','"+thessn+"','"+Bname+"','"+address+"','"+phone+"');";
			stmt.executeUpdate(updateString);
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}	
	}
	
	public boolean insertValueBookWithCheck(String isbnTen,String title){
		boolean result=false;
		if(existSelectFrom("Isbn","Book","Isbn",isbnTen)==false){
			insertValueBook(isbnTen,title);
			result=true;
		}
		else{
			System.out.println("Duplicate ISBN !!!     "+isbnTen);
		}
		return result;
	}
	public void insertValueBook(String isbnTen,String title){
		try{
			String updateString ="INSERT INTO Book VALUES ('"+isbnTen+"','"+title+"');";
			stmt.executeUpdate(updateString);
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}	
	}
	public void insertBook_AuthorFetchId(String authorName,String isbnTen){
		int author_id;
		String FrenchName=authorName.replace("''","'");
		if(existSelectFrom("Author_id", "AUTHOR", "AName", FrenchName)==true){
			author_id=Integer.parseInt(selectFrom("Author_id", "AUTHOR", "AName", FrenchName));
			insertValueBook_Authors(author_id,isbnTen);
		}
		else{
			System.out.println("author_id reference not found!!!");	
			System.out.println("his/her name is "+FrenchName);
		}
	}
	public void insertValueBook_Authors(int id,String isbnTen){
		try{
			String updateString ="INSERT INTO Book_Authors VALUES ('"+id+"','"+isbnTen+"');";
			stmt.executeUpdate(updateString);
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}	
	}
	public boolean insertAuthorWithCheck(String authorName){
		boolean result=false;
		if(existSelectFrom("AName","Author","AName",authorName)==false){
			insertValueAuthor(countFromAuthor()+1,authorName);
			result=true;
		}
		return result;
	}
	public void insertValueAuthor(int id,String AName){
		try{
			String updateString ="INSERT INTO Author VALUES ('"+id+"','"+AName+"');";
			stmt.executeUpdate(updateString);
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	
	public int countFromAuthor(){
		int total=0;
		String count="COUNT";
		try{
			String queryString ="SELECT COUNT(*) AS "+count+" FROM Author;";
			ResultSet rs=stmt.executeQuery(queryString);
			while(rs.next()){
			total=Integer.parseInt(rs.getString(count));
			System.out.println(total);
			}
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
		return total;
	}

	public String selectFrom(String attribute, String table, String tupleConA, String tupleConB){
		String result="";
		try{
			String queryString ="SELECT "+attribute+" FROM "+table+" WHERE "+tupleConA+" = " +"\""+tupleConB+"\""+";";

			ResultSet rs=stmt.executeQuery(queryString);

			while(rs.next()){
				result=rs.getString(attribute);
				//System.out.println(result);

			}
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}	

		return result;
	}
	public boolean existSelectFrom(String attribute, String table, String tupleConA, String tupleConB){
		if(selectFrom(attribute,table,tupleConA,tupleConB)!="")
			return true;
		else
			return false;

	}
}