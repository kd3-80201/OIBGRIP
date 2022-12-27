package Task3;

import java.sql.*;
import java.util.Scanner;

public class AtmPhase {
    public static void main(String[] args) {


            Connection conn;
            Statement st;
            ResultSet rs,rs2;
            int u = 0,p = 0;
            Scanner sc = new Scanner(System.in);
            try {
                // Connecting to MySQL Workbench
                // here we have established a connection with MySQL workbench using local host port no: 3306
                // and the username is root and password is AfnanBaig@123
                // all the data is being saved in a schema called as atm_interface

                conn = DriverManager.getConnection("JDBC:mysql://localhost:3306/atm_interface", "root", "AfnanBaig@123");
                st = conn.createStatement();
            } catch (SQLException e) {throw new RuntimeException(e);}

        while (true){
            System.out.println("============================================================");
            System.out.println("||                        Public ATM                      ||");
            System.out.println("============================================================\n");

            System.out.println("1. Login ");
            System.out.println("2. Register ");
            System.out.println("3. Quit\n");
            System.out.println("Choose an Option ");

            int input = sc.nextInt();

            if (input == 1){             // if user choose to log in
                System.out.println("Enter Account No");
                int ac = sc.nextInt();

                System.out.println("Enter Pin");
                int pn = sc.nextInt();

                try {
                    rs = st.executeQuery("select * from account where Account_No = '"+ac+"';"); // Getting values from the SQL Table
                    while (rs.next()){
                        u = rs.getInt("Account_No"); // Account no will stay in variable u
                        p = rs.getInt("Pin");        // Pin will stay in variable p

                    }
                    if (ac == u && pn == p) { // if user is valid this condition will take him/her to the Bank Interface

                        System.out.println("============================================================");
                        System.out.println("||                 Welcome To Public ATM                  ||");
                        System.out.println("============================================================\n");

                        System.out.println("1. Transaction History");
                        System.out.println("2. Withdraw");
                        System.out.println("3. Deposit");
                        System.out.println("4. Transfer");
                        System.out.println("5. Check Balance");
                        System.out.println("6. Quit\n");


                        System.out.println("Choose an Option");

                        face fc = new face();

                        int ch = sc.nextInt();
                        switch (ch) {               // According to user's choice he will be taken there
                            case 1 -> fc.Transaction(u);
                            case 2 -> fc.Withdraw(u);
                            case 3 -> fc.Deposit(u);
                            case 4 -> fc.Transfer(u);
                            case 5 -> fc.Balance(u);
                            case 6 -> System.exit(0);
                            default -> System.out.println("You've Inputted the wrong option!");
                        }
                    }
                    else {
                        System.out.println("Wrong Credentials!\n");
                    }
                } catch (SQLException e) {throw new RuntimeException(e);}

            } else if (input == 2) { // if user choose to register

                Scanner sc1 = new Scanner(System.in);

                System.out.println("Enter Name");
                String na = sc1.nextLine();

                System.out.println("Enter Age");
                int age = Integer.parseInt(sc1.nextLine());

                // I face the problem here while running this programme because
                // the Scanner.nextInt method does not read the newline character in your input created by hitting "Enter"
                // and so the call to Scanner.nextLine returns after reading that newline.
                // Hence,I did the syntax as Read the complete line as String and convert it into integer.
                //You will encounter the similar behaviour when you use Scanner.nextLine after Scanner.next()

                System.out.println("Enter Your Address");
                String add = sc1.nextLine();


                System.out.println("Enter Your Mobile No");
                double num = sc1.nextDouble();


                System.out.println("Enter Pin (In Digits)");
                int pi = sc1.nextInt();

                System.out.println("Enter Amount You want to Deposit");
                double de = sc1.nextDouble();

                int a;
                try {
                    rs2 = st.executeQuery("select * from account where Account_No = (select max(Account_No) from account);");
                    rs2.next();
                    a = (rs2.getInt("Account_No")) + 1;  // This query will take the last account number of user and add 1 to it


                    String save = "INSERT INTO account (Account_No,User_Name,Age,Address,Mobile_No,Pin,Balance) VALUES (" + a + ",'"+na+"',"+age+",'"+add+"',"+num+","+pi+","+de+");";
                    st.executeUpdate(save); // All information will be saved in table called as account

                    System.out.println("Registration has completed ! \nAccount No of holder: "+a);
                } catch (Exception e) {throw new RuntimeException(e);}

            } else if (input == 3) {
                System.exit(0);
            }
            else {
                System.out.println("Choose correct option!\n");
            }
        }
    }
}
