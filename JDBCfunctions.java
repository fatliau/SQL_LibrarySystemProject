package project1;
import java.sql.*;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JDBCfunctions {
	private Statement stmt;
	
	static int maximunItem=200;
	int searchResultCount=0;
	
	public static Date addDays(Date date, int days)
	{
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.DATE, days); //minus number would decrement the days
	    return cal.getTime();
	}
	public int daysGap(Date d1, Date d2)
	{
		long duration  = d2.getTime() - d1.getTime();
		long diffInDays = TimeUnit.MILLISECONDS.toDays(duration);
	    int result = (int) (long) diffInDays;
		return result;
	}
	public void initializeDB(){
		try{
			//Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded.");
			Connection connection=DriverManager.getConnection("jdbc:mysql://localhost/librarySys","root","newpass");//root,newpass
			System.out.println("Database connected");
			stmt =connection.createStatement();
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
		//connection.close();
	}
	public String query(){
		String result="";
		try{
			ResultSet resultSet =stmt.executeQuery
					("SELECT ISBN, TITLE FROM BOOK");

			while(resultSet.next()){
				System.out.println(resultSet.getString(1)+"\t"+resultSet.getString(2));
				result+=resultSet.getString(1)+"\t"+resultSet.getString(2)+"\n";
			}
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
	public String returnBook(String ISBN, String title, String author, String card_id){
		//UPDATE Book_loans SET Date_in = '17-03-19' WHERE Isbn="9724119378";
		String result="";
		java.util.Date utilToday = new java.util.Date();
		java.sql.Date sqlToday = new java.sql.Date(utilToday.getTime());
		int loan_id = searchInLoanYetReturnLoanId(ISBN);
		try{
			String updateString ="UPDATE Book_loans "
					+ "SET Date_in = " + "\"" + sqlToday +"\""
					+ " WHERE Isbn = " + "\"" + ISBN +"\""
					+ " AND loan_id = " + "\"" + loan_id +"\""+";";
			stmt.executeUpdate(updateString);
			result="return book "+title+" successful.";
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
		return result;
	}

	public String[] searchBookIfLoanOut(String ISBN, String title, String author){
		//SELECT B.Isbn, B.Title, A.AName, BL.Date_due 
		//FROM Book AS B, Book_authors AS BA, Author AS A, Book_loans AS BL 
		//WHERE BL.Isbn=B.Isbn AND BA.isbn=B.isbn AND A.author_id=BA.author_id 
		//AND BL.Date_in is NULL AND BL.Date_out IS NOT NULL AND B.isbn="9724119378";
		String[] output=new String[maximunItem]; //maximum output maximunItem
    	int outputCount=0;
    	String rsIsbn;
    	String rsTitle;
    	String rsAuthor;
    	try{
    		
    		String queryString ="SELECT B.Isbn, B.Title, A.AName, BL.Date_due "
    				+ "FROM Book AS B, Book_authors AS BA, Author AS A, Book_loans AS BL "
    				+ "WHERE BL.Isbn=B.Isbn AND BA.isbn=B.isbn AND A.author_id=BA.author_id "
    				+ "AND BL.Date_in is NOT NULL AND BL.Date_out IS NOT NULL"
    				+ " AND B.Isbn = "+"\""+ISBN+"\""
    				+ " AND B.Title LIKE "+"'%"+title+"%'"
    				+ " AND A.AName LIKE "+"'%"+author+"%'"+";";
    				
			ResultSet rs=stmt.executeQuery(queryString);
			while(rs.next()){
				rsIsbn=rs.getString("Isbn");
				rsTitle=rs.getString("Title");
				rsAuthor=rs.getString("AName");
				output[outputCount]="Isbn: "+rsIsbn+", Title: "+rsTitle+", Author: "+rsAuthor;
				outputCount++;
			}
    	}
    	catch(SQLException ex){
    		ex.printStackTrace();
    	}
    	if(outputCount==0){
    		String[] output2=new String[1];
    		output2[0]="Not available in library";
    		searchResultCount=0;
    		return output2;
    	}
    	else{
    		String[] output2=new String[outputCount];
    		for(int i=0;i<outputCount;i++){
    			output2[i]=output[i];
    		}
    		for(int i=0; i<output2.length;i++){
    			System.out.println(output2[i]);
    		}
    		searchResultCount=outputCount;
    		return output2;
    	}  	
	}
	public String searchAvailableResult(int returnCount, int neverLoanCount){
		String result="";
		if(returnCount==0){
			result=("Available");
		}
		else{
			result=("Not Available");
		}
		return result;
	}
	public int searchInLoanReturn(String isbn){
		//SELECT COUNT(*) AS COUNT, B.Isbn, B.Title, A.AName, BL.Date_due 
		//FROM Book AS B, Book_authors AS BA, Author AS A, Book_loans AS BL 
		//WHERE BL.Isbn=B.Isbn AND BA.isbn=B.isbn AND A.author_id=BA.author_id 
		//AND BL.Date_in is NULL AND BL.Date_out IS NOT NULL AND B.isbn="9724119378";
		int result=0;
		try{
    		String queryString ="SELECT COUNT(*) AS COUNT "
    				+ "FROM Book AS B, Book_authors AS BA, Author AS A, Book_loans AS BL "
    				+ "WHERE BL.Isbn=B.Isbn AND BA.isbn=B.isbn AND A.author_id=BA.author_id "
    				+ "AND BL.Date_in is NULL AND BL.Date_out IS NOT NULL"
    				+ " AND B.Isbn = "+"\""+isbn+"\""+";";
			ResultSet rs=stmt.executeQuery(queryString);
			while(rs.next()){
				result=rs.getInt("COUNT");
			}
    	}
    	catch(SQLException ex){
    		ex.printStackTrace();
    	}
		return result;
	}
	public int searchInLoanYetReturnLoanId(String isbn){
		//SELECT MAX(loan_id) AS loan_id 
		//FROM Book AS B, Book_authors AS BA, Author AS A, Book_loans AS BL 
		//WHERE BL.Isbn=B.Isbn AND BA.isbn=B.isbn AND A.author_id=BA.author_id 
		//AND BL.Date_in is NULL AND BL.Date_out IS NOT NULL 
		//AND B.isbn="9724119378";
		int result=0;
		try{
    		String queryString ="SELECT MAX(loan_id) AS loan_id "
    				+ "FROM Book AS B, Book_authors AS BA, Author AS A, Book_loans AS BL "
    				+ "WHERE BL.Isbn=B.Isbn AND BA.isbn=B.isbn AND A.author_id=BA.author_id "
    				+ "AND BL.Date_in is NULL AND BL.Date_out IS NOT NULL"
    				+ " AND B.Isbn = "+"\""+isbn+"\""+";";
			ResultSet rs=stmt.executeQuery(queryString);
			while(rs.next()){
				result=rs.getInt("loan_id");
				System.out.println("max for result is "+result);
			}
    	}
    	catch(SQLException ex){
    		ex.printStackTrace();
    	}
		return result;
	}
	public int searchNeverLoan(String isbn){
		//SELECT COUNT(*) FROM (Book AS B LEFT JOIN Book_loans AS BL ON BL.Isbn=B.Isbn), Book_authors AS BA, Author AS A WHERE BA.isbn=B.isbn AND A.author_id=BA.author_id AND B.isbn="9721013412" AND B.isbn NOT IN ( SELECT isbn FROM Book_loans WHERE isbn="9721013412" );
		//SELECT COUNT(*) FROM (Book AS B LEFT JOIN Book_loans AS BL ON BL.Isbn=B.Isbn), Book_authors AS BA, Author AS A WHERE BA.isbn=B.isbn AND A.author_id=BA.author_id AND B.isbn="9724119378" AND B.isbn NOT IN ( SELECT isbn FROM Book_loans WHERE isbn="9724119378" );
		int result=0;
		try{
			String queryString ="SELECT COUNT(*) AS COUNT "
					+ "FROM (Book AS B LEFT JOIN Book_loans AS BL ON BL.Isbn=B.Isbn), Book_authors AS BA, Author AS A "
					+ "WHERE BA.isbn=B.isbn AND A.author_id=BA.author_id AND B.isbn = "+ "\""+isbn+"\""
					+ " AND B.isbn NOT IN ( SELECT isbn FROM Book_loans WHERE isbn = "+"\""+isbn+"\" );";
			ResultSet rs=stmt.executeQuery(queryString);
			while(rs.next()){
				result=rs.getInt("COUNT");
			}
    	}
    	catch(SQLException ex){
    		ex.printStackTrace();
    	}
		return result;
	}
	public String searchBookReturnOneString(String ISBN, String title, String author){
		String output="";
		String[] temp=new String[maximunItem]; //maximum output maximunItem
		
		if(searchInLoanReturn(ISBN)==0){
			temp=searchBook2(ISBN, title,author);
			if(searchResultCount==0){
				return temp[0];
			}
			else{
				for(int i=0;i<searchResultCount;i++){
					output=output.concat(temp[i]).concat("\n");
				}
				return output;
			}
		}
		else
			return output="Not Available";
	}
    public String[] searchBook2(String ISBN, String title, String author){
    	String[] output=new String[maximunItem]; //maximum output maximunItem
    	int outputCount=0;
    	String rsIsbn;
    	String rsTitle;
    	String rsAuthor;
    	try{
    		String queryString ="SELECT B.Isbn, B.Title, A.Aname, A.Author_id FROM Book AS B, Book_Authors AS BA, Author AS A "
    				+ "WHERE B.Isbn=BA.Isbn AND BA.Author_id=A.Author_id"
    				+ " AND B.Isbn = "+"\""+ISBN+"\""
    				+ " AND B.Title LIKE "+"'%"+title+"%'"
    				+ " AND A.AName LIKE "+"'%"+author+"%'"+";";
			ResultSet rs=stmt.executeQuery(queryString);
			while(rs.next()){
				rsIsbn=rs.getString("Isbn");
				rsTitle=rs.getString("Title");
				rsAuthor=rs.getString("AName");
				output[outputCount]="Isbn: "+rsIsbn+", Title: "+rsTitle+", Author: "+rsAuthor;
				outputCount++;
			}
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
    	String[] output2=new String[outputCount];
    	for(int i=0;i<outputCount;i++){
    		output2[i]=output[i];
    	}
    	for(int i=0; i<output2.length;i++){
    		System.out.println(output2[i]);
    	}
    	searchResultCount=outputCount;
    	return output2;
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
	public String selectFromWithSubString(String attribute, String table, String tupleConA, String tupleConB){
		String result=null;
		try{
			String queryString ="SELECT "+attribute+" FROM "+table+" WHERE "+tupleConA+" LIKE " +"'%"+tupleConB+"%'"+";";
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
	public void insertBorrower(String card_id, String ssn, String bname, String address,String phone){
/*Card_id | int(11)      | NO   | PRI | NULL    |       |
| Ssn     | char(11)     | NO   |     | NULL    |       |
| BName   | varchar(50)  | NO   |     | NULL    |       |
| Address | varchar(200) | YES  |     | NULL    |       |
| Phone   | char(14)     | YES  |     | NULL    |       |*/
		try{
			String updateString ="INSERT INTO Borrower VALUES ('"+card_id+"','"+ssn+"','"+bname+"','"+address+"','"+phone+"');";
			stmt.executeUpdate(updateString);
		}
		//insert into borrower (card_id,ssn,bname) values ('145','12345678901','Jacky');
		catch(SQLException ex){
			ex.printStackTrace();
		}	
	}
	
	public String insertBook_loansFromButton(String isbn,int card_id){
		String result="";
		if(threeLoansCheck(card_id)==true){
			if(searchInLoanReturn(isbn)==0){

				java.util.Date utilDateOut = new java.util.Date();
				java.util.Date utilDateDue = addDays(utilDateOut,14);
				java.sql.Date sqlDateOut = new java.sql.Date(utilDateOut.getTime());
				java.sql.Date sqlDateDue = new java.sql.Date(utilDateDue.getTime());

				insertBook_loansNoidNoDin(isbn ,card_id, sqlDateOut,sqlDateDue);
				result="Successful";
			}
			else
				result="NOT AVAILABLE. This book is not in the library.";
		}
		else{
			System.out.println("EXCEED the 3 book-loans limmit !!");
			result="EXCEED LIMMIT. This user has already borrowed 3 books.";
		}
		return result;
	}
	public boolean threeLoansCheck(int card_id){
		boolean result=false;
		int total=0;
		try{
			String queryString ="SELECT COUNT(*) FROM Book_loans WHERE card_id = "+"\""+card_id+"\""+";";
			ResultSet rs=stmt.executeQuery(queryString);
			while(rs.next()){
			total=Integer.parseInt(rs.getString(1));
			System.out.println(total);
			}
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
		if(total<3){
			result=true;
		}
		return result;
	}
	public void insertBook_loansNoidNoDin(String isbn,int card_id,Date Dout,Date Ddue){
		int theid=countFromBook_Loans()+1;
		insertBook_loansNoDin(theid, isbn ,card_id, Dout,Ddue);
	}
	public void insertBook_loansNoDin(int loan_id, String isbn,int card_id,Date Dout,Date Ddue){
		try{
			String updateString ="INSERT INTO Book_loans (loan_id,isbn,card_id,date_out,date_due) VALUES ('"+
		loan_id+"','"+isbn+"','"+card_id+"','"+Dout+"','"+Ddue+"');";
			stmt.executeUpdate(updateString);
		}
		//INSERT INTO Book_loans (loan_id,isbn,card_id,date_out,date_due,date_in) Values ('123','0061076031','145','17-03-17','17-05-17',null);
		catch(SQLException ex){
			ex.printStackTrace();
		}	
	}
	public int countFromBook_Loans(){
		int total=0;
		String count="COUNT";
		try{
			String queryString ="SELECT COUNT(*) AS "+count+" FROM Book_loans;";
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
	public void insertBook_loans(int loan_id, String isbn,String card_id,Date Dout,Date Ddue, Date Din){
/*Loan_id  | int(11)  | NO   | PRI | NULL    |       |
| Isbn     | char(10) | NO   | MUL | NULL    |       |
| Card_id  | int(11)  | NO   | MUL | NULL    |       |
| Date_out | date     | YES  |     | NULL    |       |
| Date_due | date     | YES  |     | NULL    |       |
| Date_in  | date     | YES  |     | NULL    |       |*/
		try{
			String updateString ="INSERT INTO Book_loans VALUES ('"+
		loan_id+"','"+isbn+"','"+card_id+"','"+Dout+"','"+Ddue+"','"+Din+"');";
			stmt.executeUpdate(updateString);
		}
		//INSERT INTO Book_loans (loan_id,isbn,card_id,date_out,date_due,date_in) Values ('123','0061076031','145','17-03-17','17-05-17',null);
		catch(SQLException ex){
			ex.printStackTrace();
		}	
	}
	
	public String insertNewBorrower(String thessn, String Bname, String address, String phone){
		//SELECT * FROM Borrower WHERE ssn="968-11-7531"; null
		//SELECT * FROM Borrower WHERE ssn="968-11-7539"; exist
		String result="";
		boolean checkSsn=true;
		int newCard_id = getMaxBorrwerNo()+1;
		try{
			String queryString ="SELECT Ssn FROM Borrower WHERE Ssn = "+"\""+thessn+"\""+";";	
			ResultSet rs=stmt.executeQuery(queryString);
			while(rs.next()){
				checkSsn = false;
			}
    	}
		catch(SQLException ex){
			ex.printStackTrace();
		}
		if(checkSsn==false){
			result="duplicate ssn";
		}
		else{
			result="OK";
			insertValueBorrowerWithCheck(newCard_id, thessn,Bname,address,phone);
		}
		return result;
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
	public int getMaxBorrwerNo(){
		int result=-1;
		try{
			String queryString ="SELECT MAX(Card_id) AS Card_id FROM BORROWER;";	
			ResultSet rs=stmt.executeQuery(queryString);
			while(rs.next()){
				result=rs.getInt("Card_id");
			}
    	}
    	catch(SQLException ex){
    		ex.printStackTrace();
    	}
		return result;
	}
    public void insertBook(String isbnTen,String title){
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
			insertBook_Authors(author_id,isbnTen);
		}
		else{
			System.out.println("author_id reference not found!!!");	
			System.out.println("his/her name is "+FrenchName);
		}
	}
	public void insertBook_Authors(int id,String isbnTen){
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
			insertAuthor(countFromAuthor()+1,authorName);
			result=true;
		}
		return result;
	}
	public void insertAuthor(int id,String AName){
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
	public void getDueDate(String isbn){
		java.util.Date utilToday = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilToday.getTime());
		//SELECT BL.loan_id ,BL.Date_due, B.isbn , B.title 
		//FROM Book_loans AS BL, Book AS B WHERE B.Isbn=BL.Isbn AND BL.Date_in IS NULL;
		String[] output=new String[maximunItem]; //maximum output maximunItem
    	int outputCount=0;
    	int rsLoan_id=-1;
    	Date rsDate_due=today;
    	String rsIsbn="";
    	String rsTitle;
    	try{
    		String queryString ="SELECT BL.loan_id AS loan_id ,BL.Date_due AS due, B.isbn AS isbn , B.title AS title "
    				+ "FROM Book_loans AS BL, Book AS B WHERE B.Isbn=BL.Isbn AND BL.Date_in IS NULL "
    				+ "AND B.isbn = "+"\""+isbn+"\""+";";
    		ResultSet rs=stmt.executeQuery(queryString);
    		while(rs.next()){
    			rsLoan_id=rs.getInt("loan_id");
    			rsDate_due=rs.getDate("due");
    			rsIsbn=rs.getString("isbn");
    			rsTitle=rs.getString("title");
    			output[outputCount]="Isbn: "+rsIsbn+", Title: "+rsTitle+", Author: ";
    			outputCount++;
    		}
			if(today.after(rsDate_due)){
				System.out.println("outputCount is "+outputCount);
				System.out.println("today is after dueday");
				int overDueDays=daysGap(rsDate_due, today);
				float fine_amt = (float) 0.25*overDueDays;
				createNewFine(rsLoan_id,fine_amt,false);
				System.out.println("Fine will be"+fine_amt);
			}
			else{
				System.out.println("outputCount is "+outputCount);
				System.out.println("not yet over due");
			}
    	}
		catch(SQLException ex){
			ex.printStackTrace();
		}
    	searchResultCount=outputCount;
	}
	public void getAlloverdueLoan(){
		java.util.Date utilToday = new java.util.Date();
		java.sql.Date today = new java.sql.Date(utilToday.getTime());
		//SELECT BL.loan_id ,BL.Date_due, B.isbn , B.title 
		//FROM Book_loans AS BL, Book AS B WHERE B.Isbn=BL.Isbn AND BL.Date_in IS NULL;
		String[] output=new String[maximunItem]; //maximum output maximunItem
    	int outputCount=0;
    	int [] rsLoan_id=new int[maximunItem];
    	Date []rsDate_due=new Date[maximunItem];
    	String []rsIsbn=new String[maximunItem];
    	String []rsTitle=new String[maximunItem];
    	try{
    		String queryString ="SELECT BL.loan_id AS loan_id ,BL.Date_due AS due, B.isbn AS isbn , B.title AS title "
    				+ "FROM Book_loans AS BL, Book AS B WHERE B.Isbn=BL.Isbn AND BL.Date_in IS NULL;";
    		ResultSet rs=stmt.executeQuery(queryString);
    		while(rs.next()){
    			rsDate_due[outputCount]=rs.getDate("due");
    			rsLoan_id[outputCount]=rs.getInt("loan_id");
    			rsIsbn[outputCount]=rs.getString("isbn");
    			rsTitle[outputCount]=rs.getString("title");
    			output[outputCount]="Isbn: "+rsIsbn+", Title: "+rsTitle+", Author: ";
    			outputCount++;
    		}
    	}
		catch(SQLException ex){
			ex.printStackTrace();
		}
    	for(int i=0;i<outputCount;i++){
    		if(today.after(rsDate_due[i])){
    			System.out.println("i is "+i+" loan_id is "+rsLoan_id[i]);
    			System.out.println("today is after dueday");
    			int overDueDays=daysGap(rsDate_due[i], today);
    			float fine_amt = (float) 0.25*overDueDays;
    			createNewFine(rsLoan_id[i],fine_amt,false);
    			System.out.println("Fine will be"+fine_amt);
    		}
    		else{
    			System.out.println("outputCount is "+outputCount);
    			System.out.println("not yet over due");
    		}
    	} 	
    	searchResultCount=outputCount;
	}
	public void createNewFine(int loan_id,float fine_amt,boolean paid){
		try{
			String updateString ="INSERT INTO Fines VALUES ('"+
		loan_id+"','"+fine_amt+"','"+0+"');";
			stmt.executeUpdate(updateString);
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}		
	}
	public String retriveUserFineSummary(int card_id){
		//SELECT SUM(F.Fine_amt) AS SUM, card_id FROM Fines AS F, book_loans AS BL WHERE BL.loan_id=F.loan_id GROUP BY card_id;
		String output="";
    	float rsSum=0;
		try{
    		String queryString ="SELECT SUM(F.Fine_amt) AS SUM, card_id FROM Fines AS F, book_loans AS BL "
    				+ "WHERE BL.loan_id=F.loan_id "
    				+ "AND BL.card_id = "+"\""+card_id+"\""
    				+ " GROUP BY card_id;";
    		ResultSet rs=stmt.executeQuery(queryString);
    		while(rs.next()){
    			rsSum=rs.getFloat("SUM");
    		}
    	}
		catch(SQLException ex){
			ex.printStackTrace();
		}
		output= "This user has fine in total: "+rsSum;
		System.out.println(output);
		return output;
	}
	public String payAmount(float amt,int card_id){
		String output="";
		float rsSum=0;
		int[] loan_id = new int[maximunItem];
		int count=0;
		try{
			String queryString ="SELECT SUM(F.Fine_amt) AS SUM, BL.loan_id AS id FROM Fines AS F, book_loans AS BL "
					+ "WHERE BL.loan_id=F.loan_id "
					+ "AND BL.card_id = "+"\""+card_id+"\""
					+ " GROUP BY card_id;";
			ResultSet rs=stmt.executeQuery(queryString);
			while(rs.next()){
				rsSum=rs.getFloat("SUM");
				loan_id[count]=rs.getInt("id");
				count++;
			}
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
		if(amt!=rsSum){
			output="Amount is not correct.";
		}
		else{
			for(int i=0;i<count;i++){
				try{//UPDATE Fines SET Paid = True WHERE loan_id=3;
					String queryUpdate ="UPDATE Fines SET Paid = True WHERE loan_id= "+
							+loan_id[i]+";";
					stmt.executeUpdate(queryUpdate);
					output="All Fines are paid.";
					System.out.println(output+loan_id[i]);
				}
				catch(SQLException ex){
					ex.printStackTrace();
				}
			}
			
		}
			return output;
		}
	
	public static void main(String []args){
		
		/*
		java.util.Date utilDateOut = new java.util.Date();
		java.util.Date utilDateDue = addDays(utilDateOut,14);
		java.util.Date utilDateIn = addDays(utilDateOut,13);
		java.sql.Date sqlDateOut = new java.sql.Date(utilDateOut.getTime());
		java.sql.Date sqlDateDue = new java.sql.Date(utilDateDue.getTime());
		java.sql.Date sqlDateIn = new java.sql.Date(utilDateIn.getTime());
		 */
		JDBCfunctions sim=new JDBCfunctions();
		sim.initializeDB();
		//sim.getDueDate("9974560004");
		//sim.getDueDate("9810455674");
		//sim.getAlloverdueLoan();
		sim.retriveUserFineSummary(994);

		/*
		String[]temp = new String[maximunItem];
		temp = sim.searchBookIfLoanOut("9724119378", "", "");
		for(int i=0;i<temp.length;i++)
			System.out.println(temp[i]);
		*/
		//System.out.println(""+sim.insertNewBorrower("968-11-7531","Jacky","Dallas","(682) 702-9264"));
		//System.out.print("Mazimum card_id is "+sim.getMaxBorrwerNo());
		
		//sim.returnBook("9724119378", "", "", "");
		/*
		System.out.println("9724119378 is Loan not yet Return? "+ sim.searchInLoanReturn("9724119378"));
		System.out.println("9724119378 with not yet Return loan ID: "+ sim.searchInLoanYetReturnLoanId("9724119378"));
		System.out.println("9724119378 is Loan Never loan out? "+ sim.searchNeverLoan("9724119378"));
		
		System.out.println("9721013412 is Loan not yet Return? "+ sim.searchInLoanReturn("9721013412"));
		System.out.println("9721013412 with not yet Return loan ID: "+ sim.searchInLoanYetReturnLoanId("9721013412"));
		System.out.println("9721013412 is Loan Never loan out? "+ sim.searchNeverLoan("9721013412"));
		 */
		
		//sim.insertBook_loans("124", "0439095026" ,"145", sqlDateOut,sqlDateDue,sqlDateIn);
		 
	}


}
