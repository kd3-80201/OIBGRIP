package Task3;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class face {
Connection conn2;
Statement st2,st3,st4;
ResultSet rs2,rs3,rs4;
Scanner sc = new Scanner(System.in);

    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    java.util.Date date = new Date();
    String dt = dateFormat.format(date);

    public face() {
        try {
            // Connecting to MySQL Workbench
            // here we have established a connection with MySQL workbench using local host port no: 3306
            // and the username is root and password is AfnanBaig@123
            // all the data is being saved in a schema called as atm_interface

            conn2 = DriverManager.getConnection("JDBC:mysql://localhost:3306/atm_interface", "root", "AfnanBaig@123");
            st2 = conn2.createStatement();
            st3 = conn2.createStatement();
            st4 = conn2.createStatement();

        } catch (SQLException e) {throw new RuntimeException(e);}

    }
    public void Transaction(int account){
        System.out.println("====================================================================");
        System.out.println("||                        Transaction History                     ||");
        System.out.println("====================================================================\n");

        try {
            System.out.println(".......................................................................................................");
            System.out.printf(" %-15s | %-15s | %-15s | %-16s | %-16s | %-16s  |","Account_No","Credit","Debit","Transaction Date","Credited To","Debited From");
            System.out.println("\n.....................................................................................................");

            String history = "select * from transaction_table where Account_No = "+account+";";
                    // query to gather all information of transaction table according to given account no
            rs2 = st2.executeQuery(history);

            while (rs2.next()){

                String a = rs2.getString("Account_No");
                String b = rs2.getString("Credit");
                String c = rs2.getString("Debit");
                String d = rs2.getString("Transaction_Date");
                String e = rs2.getString("Credited_To");
                String f = rs2.getString("Debited_From");
                        // values of the respective attribute will be stored on each variable

                System.out.printf(" %-15s | %-15s | %-15s | %-16s | %-16s | %-16s  |\n",a,b,c,d,e,f);
            }
        } catch (SQLException e) {throw new RuntimeException(e);}


    }


    public void Withdraw(int account){

        System.out.println("============================================================");
        System.out.println("||                         Withdraw                       ||");
        System.out.println("============================================================\n");
        try {
            rs2 = st2.executeQuery("select * from account where Account_No= "+account+";");
            rs2.next();
            String title = rs2.getString("User_Name");  // username will stay in variable title
            double bal = rs2.getDouble("Balance");     // user balance will stay in variable bal

            System.out.println(" User Name: "+title);
                    // Account holder name will be displayed after inputting account no.

            System.out.println("Enter amount to withdraw");
            int wt = sc.nextInt();

            if (wt > bal){      // if the user has insufficient balance he can't withdraw cash
                System.out.println("Sorry! Insufficient Balance.");
            }
            else {
                st2.executeUpdate("update account set Balance = Balance - "+wt+";");
                        // transferred cash is going to subtract from user Account Balance using this query

                String gg = "Insert into transaction_table (Account_No,Debit,Transaction_Date) values ("+account+","+wt+",'"+dt+"');";
                st3.executeUpdate(gg);
                        // user's activities is gonna stored on a table called transaction_table
                System.out.println("Amount is Withdrawn! Thank You For Using Public Bank");
            }
        } catch (SQLException e) {throw new RuntimeException(e);}

    }
    public void Deposit(int account){

        System.out.println("============================================================");
        System.out.println("||                          Deposit                       ||");
        System.out.println("============================================================\n");

        try {
            rs2 = st2.executeQuery("select User_Name from account where Account_No= "+account+";");
            rs2.next();
            String title = rs2.getString("User_Name");
            System.out.println(" User Name: "+title);

            System.out.println("Enter Amount To Deposit ");
            int amt = sc.nextInt();

            st2.executeUpdate("update account set Balance = Balance + "+amt+";");
                    //Deposit amount is going to add into user Account Balance

            String hh = "Insert into transaction_table (Account_No,Credit,Transaction_Date) values ("+account+","+amt+",'"+dt+"');";
            st3.executeUpdate(hh);      //Activities of account holder will be stored in transaction table

            System.out.println("Amount is Deposited! Thank You For Using Public Bank");
        } catch (SQLException e) {throw new RuntimeException(e);}

    }
    public void Transfer(int account){

        System.out.println("============================================================");
        System.out.println("||                         Transfer                       ||");
        System.out.println("============================================================\n");

        System.out.println("Enter Recipient Account No");
        int NumAccount = sc.nextInt();

        try { // here transfer the values and compare them if there account number is correct or not and debit the money in transaction history and subtract the value in account's balance attribute

            rs3 = st3.executeQuery("select * from account where Account_No= "+NumAccount+";");

            if (rs3.next()){

                rs2 = st2.executeQuery("select * from account where Account_No = " +NumAccount);
                rs2.next();
                String NaUser = rs2.getString("User_Name");


                System.out.println("Name of Recipient: " + NaUser);

                System.out.println("Enter Transfer Amount");
                int TAmount = sc.nextInt();

                rs4 = st4.executeQuery("select * from account where Account_No="+account+";");
                rs4.next();
                double bal = rs4.getDouble("Balance");

                if (TAmount > bal) {
                    System.out.println("Sorry! Insufficient Balance");
                } else {
                    String ii = "insert into transaction_table (Account_No,Debit,Transaction_Date,Credited_To) values(" + account + "," + TAmount + ",'" + dt + "'," + NumAccount + ");"; // this side is from the sender who is transferring
                    st3.executeUpdate("update account set Balance = Balance - " + TAmount + " where Account_No = " + account + ";");
                    // money to an account number, and you have to add an attribute as
                    st2.executeUpdate(ii);

                    String jj = "insert into transaction_table (Account_No,Credit,Transaction_Date,Debited_From) values (" + NumAccount + "," + TAmount + ",'" + dt + "'," + account + ");"; // this amount is saved by the
                    st2.executeUpdate(jj);

                    System.out.println("Your Amount is transferred! Thank You For Using Public Bank ");
                }
            }

            else {
                System.out.println("Incorrect Account No!!!\n");
            }

        } catch (SQLException e) {throw new RuntimeException(e);}

    }
    public void Balance(int account){
        try {

            System.out.println("============================================================");
            System.out.println("||                          Balance                       ||");
            System.out.println("============================================================\n");
            rs3 = st2.executeQuery("select Balance from account where Account_No = "+account+";");
            rs3.next();
            int showAmount  = rs3.getInt("Balance");
            System.out.println("Bank Balance: "+showAmount);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

