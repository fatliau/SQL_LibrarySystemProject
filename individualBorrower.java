package project1;
public class individualBorrower{
	//borrower_id,ssn,first_name,last_name,email,address,city,state,phone
	public int borrower_id;
	public String ssn;
	public String Fname;
	public String Lname;
	public String fullName;
	public String email;
	public String address;
	public String city;
	public String state;
	public String phone;
	
	
	public individualBorrower(){
		borrower_id=0;
		ssn="";
		Fname="";
		Lname="";
		fullName="";
		email="";
		address="";
		city="";
		state="";
		phone="";
	}
	public void printBorrowerInfo(){
		System.out.println("Info: "+ borrower_id +" "+ssn+" "+fullName+" "+address+" "+phone);
	}
	public void printfullBorrowerInfo(){
		System.out.println("Info: "+ borrower_id +" "+ssn+" "+Fname
				+" "+Lname+" "+email+" "+address+" "+city+" "+state+" "+phone);
	}
	public void writeInInfo(int idNumber,String thessn,String Firstname,String Lastname,String theemail,String add,String cit,String sta,String pho){
		borrower_id=idNumber;
		ssn=thessn;
		Fname=Firstname;
		Lname=Lastname;
		email=theemail;
		address=add;
		city=cit;
		state=sta;
		phone=pho;	
	}
	public void combineName(){
		fullName=Fname.concat(" ").concat(Lname);
	}
	public void combineAddress(){
		address=address.concat(" ").concat(city).concat(" ").concat(state);
	}
	
	/*
	public void seperateAuthors(){
		String theseAuthors=this.author;

		//System.out.print(theseAuthors);
		//System.out.println("Is "+theseAuthors+" contains multiple Authors? "+theseAuthors.contains(","));
		
		//count how many "," to separate the authors
		int lastIndex=0;
		int count=0;
		while(lastIndex != -1){
		    lastIndex = theseAuthors.indexOf(",",lastIndex);
		    if(lastIndex != -1){
		        count ++;
		        lastIndex += ",".length();
		    }
		}
		authorTotal=count+1;
		
		String[] individualAuthor = new String[authorTotal];
		
		if(theseAuthors.contains(",")){
			individualAuthor= theseAuthors.split(",");
		}
		
		switch(authorTotal){
		case 1: author1st=theseAuthors;break;
		case 2: author1st=individualAuthor[0];
		author2nd=individualAuthor[1];break;
		case 3: author1st=individualAuthor[0];
		author2nd=individualAuthor[1];
		author3rd=individualAuthor[2];break;
		case 4: author1st=individualAuthor[0];
		author2nd=individualAuthor[1];
		author3rd=individualAuthor[2];
		author4th=individualAuthor[3];break;
		case 5: author1st=individualAuthor[0];
		author2nd=individualAuthor[1];
		author3rd=individualAuthor[2];
		author4th=individualAuthor[3];
		author5th=individualAuthor[4];break;	
		}	
	}
	public void printBookAuthors(){
		System.out.println(authorTotal+" Authors: "+ author1st +", "+ author2nd +", "+ author3rd +", "+author4th+", "+author5th);
	}
	*/
	

}