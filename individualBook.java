package project1;
public class individualBook{
	//ISBN10	ISBN13	Title	Author	Cover	Publisher	Pages
	public String isbnTen;
	public String isbnThirteen;
	public String title;
	public String author;
	public String coverLink;
	public String publisher;
	public int pages;
	public int authorTotal;
	public String author1st;
	public String author2nd;
	public String author3rd;
	public String author4th;
	public String author5th;

	public individualBook(){
		isbnTen ="";
		isbnThirteen ="";
		title ="";
		author ="";
		coverLink ="";
		publisher ="";
		pages=0;
		authorTotal=0;
		author1st="";
		author2nd="";
		author3rd="";
		author4th="";
		author5th="";
	}
	public void printBookInfo(){
		System.out.println("Info: "+ isbnTen +" "+isbnThirteen+" "+title
				+" "+author+" "+coverLink+" "+publisher+" "+pages);
	}
	public void writeInInfo(String i10,String i13,String tit,String aut,String covlink,String pub,int pag){
		isbnTen =i10;
		isbnThirteen =i13;
		title =tit;
		author =aut;
		coverLink =covlink;
		publisher =pub;
		pages=pag;		
	}
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
	

}