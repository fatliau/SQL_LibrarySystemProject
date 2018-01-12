package project1;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LibraryMain extends Application {
	Stage thestage;
	//scene1
	TextField tfs1ISBN,tfs1Title,tfs1Author;
	Button bts1Search,bts1SwitchTo2;
	Label lbls1SearchResult,lbls1Scene;
	Scene scene1;
	//scene2
	TextField tfs2ISBN,tfs2Title,tfs2Author,tfs2CardID,tfs2Bname;
	Button bts2Borrow,bts2SwitchTo3;
	Label lbls2RequireCheck,lbls2BorrowResult,lbls2Scene;
	Scene scene2;
	//scene3
	TextField tfs3ISBN,tfs3Title,tfs3Author,tfs3CardID,tfs3Bname;
	Button bts3Return,bts3SwitchTo4;
	Label lbls3FineCheck,lbls3ReturnResult,lbls3Scene;
	Scene scene3;
	//scene4
	TextField tfs4Name,tfs4Ssn1,tfs4Ssn2,tfs4Ssn3,tfs4Address,tfs4Phone1,tfs4Phone2,tfs4Phone3;
	Button bts4CreateNew,bts4SwitchTo5;
	Label lbls4RequireCheck,lbls4CreateResult,lbls4Scene;
	Scene scene4;
	//scene5
	TextField tfs5ISBN,tfs5Title,tfs5Author,tfs5CardID,tfs5Bname;
	Button bts5CheckOutLoan,bts5EstimateFine,bts5SwitchTo1;
	Label lbls5LoanFineResult,lbls5Scene;
	TextField tfs5PayAmount;
	Button bts5PayFine;
	Label lbls5PayFineResult;
	Scene scene5;
    public static void main(String[] args) {
        Application.launch(args);
    }    
    @Override
    public void start(Stage primaryStage) {      
        thestage=primaryStage;
        //FirstScene
    	tfs1ISBN = new TextField();
    	tfs1ISBN.setPrefColumnCount(10);
    	HBox s1hbox1=new HBox(5);
    	s1hbox1.getChildren().addAll(new Label("ISBN"),tfs1ISBN);
    	tfs1Title = new TextField();
    	tfs1Title.setPrefColumnCount(20);
    	HBox s1hbox2=new HBox(5);
    	s1hbox2.getChildren().addAll(new Label("Title"),tfs1Title);
    	tfs1Author = new TextField();
    	tfs1Author.setPrefColumnCount(10);
    	HBox s1hbox3=new HBox(5);
    	s1hbox3.getChildren().addAll(new Label("Author"),tfs1Author);
    	bts1Search=new Button("Book Search");
    	bts1Search.setOnAction(e-> searchBook(e));
    	bts1SwitchTo2=new Button("To Next Scene");
    	bts1SwitchTo2.setOnAction(e-> ButtonClicked(e));
    	HBox s1hbox4=new HBox(5);
    	s1hbox4.getChildren().addAll(bts1Search,bts1SwitchTo2);
    	lbls1SearchResult=new Label("");
    	lbls1Scene=new Label("Search Book in Stock");
		VBox s1vbox =new VBox(10);
		s1vbox.getChildren().addAll(lbls1Scene,s1hbox1,s1hbox2,s1hbox3,s1hbox4,lbls1SearchResult);
    	scene1 = new Scene(s1vbox, 400, 500);
    	//SecondScene
    	tfs2ISBN = new TextField();
    	tfs2ISBN.setPrefColumnCount(10);
    	HBox s2hbox1=new HBox(5);
    	s2hbox1.getChildren().addAll(new Label("ISBN"),tfs2ISBN);
    	tfs2Title = new TextField();
    	tfs2Title.setPrefColumnCount(20);
    	HBox s2hbox2=new HBox(5);
    	s2hbox2.getChildren().addAll(new Label("Title"),tfs2Title);
    	tfs2Author = new TextField();
    	tfs2Author.setPrefColumnCount(10);
    	HBox s2hbox3=new HBox(5);
    	s2hbox3.getChildren().addAll(new Label("Author"),tfs2Author);
    	tfs2CardID = new TextField();
    	tfs2CardID.setPrefColumnCount(10);
    	HBox s2hbox4=new HBox(5);
    	s2hbox4.getChildren().addAll(new Label("CardID"),tfs2CardID);
    	tfs2Bname = new TextField();
    	tfs2Bname.setPrefColumnCount(10);
    	HBox s2hbox5=new HBox(5);
    	s2hbox5.getChildren().addAll(new Label("Borrower"),tfs2Bname);
    	bts2Borrow=new Button("Book Borrow");
    	bts2Borrow.setOnAction(e-> borrowBook(e));
    	bts2SwitchTo3=new Button("To Next Scene");
    	bts2SwitchTo3.setOnAction(e-> ButtonClicked(e));
    	HBox s2hbox6=new HBox(5);
    	s2hbox6.getChildren().addAll(bts2Borrow,bts2SwitchTo3);
    	lbls2RequireCheck=new Label("");
    	lbls2BorrowResult=new Label("");
    	lbls2Scene=new Label("Book Borrowing");
		VBox s2vbox =new VBox(10);
		s2vbox.getChildren().addAll(lbls2Scene,s2hbox1,s2hbox2,s2hbox3,s2hbox4,s2hbox5,s2hbox6,
				lbls2RequireCheck,lbls2BorrowResult);
    	scene2 = new Scene(s2vbox, 400, 500);
    	//ThirdScene
    	tfs3ISBN = new TextField();
    	tfs3ISBN.setPrefColumnCount(10);
    	HBox s3hbox1=new HBox(5);
    	s3hbox1.getChildren().addAll(new Label("ISBN"),tfs3ISBN);
    	tfs3Title = new TextField();
    	tfs3Title.setPrefColumnCount(20);
    	HBox s3hbox2=new HBox(5);
    	s3hbox2.getChildren().addAll(new Label("Title"),tfs3Title);
    	tfs3Author = new TextField();
    	tfs3Author.setPrefColumnCount(10);
    	HBox s3hbox3=new HBox(5);
    	s3hbox3.getChildren().addAll(new Label("Author"),tfs3Author);
    	tfs3CardID = new TextField();
    	tfs3CardID.setPrefColumnCount(10);
    	HBox s3hbox4=new HBox(5);
    	s3hbox4.getChildren().addAll(new Label("CardID"),tfs3CardID);
    	tfs3Bname = new TextField();
    	tfs3Bname.setPrefColumnCount(10);
    	HBox s3hbox5=new HBox(5);
    	s3hbox5.getChildren().addAll(new Label("Borrower"),tfs3Bname);
    	bts3Return=new Button("Book Return");
    	bts3Return.setOnAction(e-> returnBook(e));
    	bts3SwitchTo4=new Button("To Next Scene");
    	bts3SwitchTo4.setOnAction(e-> ButtonClicked(e));
    	HBox s3hbox6=new HBox(5);
    	s3hbox6.getChildren().addAll(bts3Return,bts3SwitchTo4);
    	lbls3FineCheck=new Label("");
    	lbls3ReturnResult=new Label("");
    	lbls3Scene=new Label("Book Returning");
		VBox s3vbox =new VBox(10);
		s3vbox.getChildren().addAll(lbls3Scene,s3hbox1,s3hbox2,s3hbox3,s3hbox4,s3hbox5,s3hbox6,
				lbls3FineCheck,lbls3ReturnResult);
    	scene3 = new Scene(s3vbox, 400, 500);
    	//FourthScene
    	tfs4Name = new TextField();
    	tfs4Name.setPrefColumnCount(10);
    	HBox s4hbox1=new HBox(5);
    	s4hbox1.getChildren().addAll(new Label("Borrower Name"),tfs4Name);
    	tfs4Ssn1 = new TextField();tfs4Ssn1.setPrefColumnCount(3);
    	tfs4Ssn2 = new TextField();tfs4Ssn2.setPrefColumnCount(2);
    	tfs4Ssn3 = new TextField();tfs4Ssn3.setPrefColumnCount(4);
    	HBox s4hbox2=new HBox(5);
    	s4hbox2.getChildren().addAll(new Label("SSN"),tfs4Ssn1,new Label("-"),tfs4Ssn2,new Label("-"),tfs4Ssn3);
    	tfs4Address = new TextField();
    	tfs4Address.setPrefColumnCount(30);
    	HBox s4hbox3=new HBox(5);
    	s4hbox3.getChildren().addAll(new Label("Address"),tfs4Address);  	
    	tfs4Phone1 = new TextField();
    	tfs4Phone1.setPrefColumnCount(3);
    	tfs4Phone2 = new TextField();
    	tfs4Phone2.setPrefColumnCount(3);
    	tfs4Phone3 = new TextField();
    	tfs4Phone3.setPrefColumnCount(4);
    	HBox s4hbox4=new HBox(5);
    	s4hbox4.getChildren().addAll(new Label("Phone ("),tfs4Phone1,new Label(") "),tfs4Phone2,new Label(" - "),tfs4Phone3);
    	bts4CreateNew=new Button("Create New Borrower");
    	bts4CreateNew.setOnAction(e-> addNewBorrower(e));
    	bts4SwitchTo5=new Button("To Next Scene");
    	bts4SwitchTo5.setOnAction(e-> ButtonClicked(e));
    	HBox s4hbox5=new HBox(5);
    	s4hbox5.getChildren().addAll(bts4CreateNew,bts4SwitchTo5);
    	lbls4RequireCheck=new Label("");
    	lbls4CreateResult=new Label("");
    	lbls4Scene=new Label("Borrower List Management");
		VBox s4vbox =new VBox(10);
		s4vbox.getChildren().addAll(lbls4Scene,s4hbox1,s4hbox2,s4hbox3,s4hbox4,s4hbox5,
				lbls4RequireCheck,lbls4CreateResult);
    	scene4 = new Scene(s4vbox, 400, 500);
    	//FifthScene
    	tfs5ISBN = new TextField();
    	tfs5ISBN.setPrefColumnCount(10);
    	HBox s5hbox1=new HBox(5);
    	s5hbox1.getChildren().addAll(new Label("ISBN"),tfs5ISBN);
    	tfs5Title = new TextField();
    	tfs5Title.setPrefColumnCount(20);
    	HBox s5hbox2=new HBox(5);
    	s5hbox2.getChildren().addAll(new Label("Title"),tfs5Title);
    	tfs5Author = new TextField();
    	tfs5Author.setPrefColumnCount(10);
    	HBox s5hbox3=new HBox(5);
    	s5hbox3.getChildren().addAll(new Label("Author"),tfs5Author);
    	tfs5CardID = new TextField();
    	tfs5CardID.setPrefColumnCount(10);
    	HBox s5hbox4=new HBox(5);
    	s5hbox4.getChildren().addAll(new Label("CardID"),tfs5CardID);
    	tfs5Bname = new TextField();
    	tfs5Bname.setPrefColumnCount(10);
    	HBox s5hbox5=new HBox(5);
    	s5hbox5.getChildren().addAll(new Label("Borrower"),tfs5Bname);
    	bts5CheckOutLoan=new Button("Update All Fine");
    	bts5CheckOutLoan.setOnAction(e-> updateAllBookFine(e));
    	bts5EstimateFine=new Button("Estimate This User Fine");
    	bts5EstimateFine.setOnAction(e-> estimateThisUserFine(e));
    	HBox s5hbox6=new HBox(5);
    	s5hbox6.getChildren().addAll(bts5CheckOutLoan,bts5EstimateFine);
    	tfs5PayAmount = new TextField();
    	tfs5PayAmount.setPrefColumnCount(5);
    	HBox s5hbox7=new HBox(5);
    	s5hbox7.getChildren().addAll(new Label("Pay Amount"),tfs5PayAmount);
    	bts5SwitchTo1=new Button("To Next Scene");
    	bts5SwitchTo1.setOnAction(e-> ButtonClicked(e));
    	bts5PayFine=new Button("Pay Fine");
    	bts5PayFine.setOnAction(e-> PayAmount(e));
    	HBox s5hbox8=new HBox(5);
    	s5hbox8.getChildren().addAll(bts5PayFine,bts5SwitchTo1);
    	lbls5LoanFineResult=new Label("");
    	lbls5PayFineResult=new Label("");
    	lbls5Scene=new Label("Fine Management");
		VBox s5vbox =new VBox(10);
		s5vbox.getChildren().addAll(lbls5Scene,s5hbox1,s5hbox2,s5hbox3,s5hbox4,s5hbox5,s5hbox6,lbls5LoanFineResult,
				s5hbox7,s5hbox8,lbls5PayFineResult);
    	scene5 = new Scene(s5vbox, 400, 500);
        //setup of stage
        primaryStage.setTitle("LIBRARY SYSTEM -CS6360 Project1");
        primaryStage.setScene(scene1);
        primaryStage.show();
    }
    
    //All Switch Scene Button
    public void ButtonClicked(ActionEvent e)
    {
        if (e.getSource()==bts1SwitchTo2)
            thestage.setScene(scene2);
        else if(e.getSource()==bts2SwitchTo3)
            thestage.setScene(scene3);
        else if(e.getSource()==bts3SwitchTo4)
        	thestage.setScene(scene4);
        else if(e.getSource()==bts4SwitchTo5)
        	thestage.setScene(scene5);
        else
        	thestage.setScene(scene1);
    }
    //scene1 Button
    public void searchBook(ActionEvent e){
    	
    	lbls1SearchResult.setText("Button Clicked");
		JDBCfunctions sim=new JDBCfunctions();
		sim.initializeDB();
		lbls1SearchResult.setText(
				sim.searchBookReturnOneString(
						tfs1ISBN.getText(),tfs1Title.getText(),tfs1Author.getText()));

    }
  //scene2 Button
    public void borrowBook(ActionEvent e){
    	lbls2RequireCheck.setText("Button Clicked");
		JDBCfunctions sim=new JDBCfunctions();    
		sim.initializeDB();
		String message= sim.insertBook_loansFromButton(tfs2ISBN.getText(),Integer.parseInt(tfs2CardID.getText()));
		lbls2BorrowResult.setText(message);
    }
    //scene3 Button
    public void returnBook(ActionEvent e){
    	lbls3FineCheck.setText("Button Clicked");
		JDBCfunctions sim=new JDBCfunctions();    
		sim.initializeDB();
		String message= sim.returnBook(tfs3ISBN.getText(), tfs3Title.getText(), tfs3Author.getText(), tfs3CardID.getText());
		lbls3ReturnResult.setText(message);
    	
    	//TextField tfs3ISBN,tfs3Title,tfs3Author,tfs3CardID,tfs3Bname;
		//Button bts3Return,bts3SwitchTo4;
		//Label lbls3FineCheck,lbls3ReturnResult,lbls3Scene;
    }
    //scene4 Button
    public void addNewBorrower(ActionEvent e){ 
    	
    	lbls4RequireCheck.setText("Button Clicked");
		JDBCfunctions sim=new JDBCfunctions();    
		sim.initializeDB();
		String ssn=tfs4Ssn1.getText()+"-"+tfs4Ssn2.getText()+"-"+tfs4Ssn3.getText();
		String phone="("+tfs4Phone1.getText()+") "+tfs4Phone2.getText()+"-"+tfs4Phone3.getText();
		String message= sim.insertNewBorrower(ssn, tfs4Name.getText(), tfs4Address.getText(), phone);
		lbls4CreateResult.setText(message);

    	//TextField tfs4Name,tfs4Ssn1,tfs4Ssn2,tfs4Ssn3,tfs4Address,tfs4Phone1,tfs4Phone2,tfs4Phone3;
    	//Button bts4CreateNew,bts4SwitchTo5;
    	//Label lbls4RequireCheck,lbls4CreateResult,lbls4Scene;
    }
    //scene5 Button
    public void updateAllBookFine(ActionEvent e){

    	lbls5LoanFineResult.setText("Update all fines with loans");
    	JDBCfunctions sim=new JDBCfunctions();    
    	sim.initializeDB();
    	sim.getAlloverdueLoan();
    	
    	//TextField tfs5ISBN,tfs5Title,tfs5Author,tfs5CardID,tfs5Bname;
    	//Button bts5CheckOutLoan,bts5EstimateFine,bts5SwitchTo1;
    	//Label lbls5LoanFineResult,lbls5Scene;
    }
    public void estimateThisUserFine(ActionEvent e){
    	
    	JDBCfunctions sim=new JDBCfunctions();    
    	sim.initializeDB();
    	String message=sim.retriveUserFineSummary(Integer.parseInt(tfs5CardID.getText()));
    	lbls5LoanFineResult.setText(message);
    }
    public void PayAmount(ActionEvent e){
    	
       	JDBCfunctions sim=new JDBCfunctions();    
    	sim.initializeDB();
    	String message=sim.payAmount(Float.parseFloat(tfs5PayAmount.getText()),Integer.parseInt(tfs5CardID.getText()));
    	lbls5PayFineResult.setText(message);
    	//TextField tfs5PayAmount;
    	//Button bts5PayFine;
    	//Label lbls5PayFineResult;
    }

    


}