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
            if (input == 1){
                System.out.println("Enter Account No");
                int ac = sc.nextInt();

                System.out.println("Enter Pin");
                int pn = sc.nextInt();

                try {
                    rs = st.executeQuery("select * from account where Account_No = '"+ac+"';"); // Getting values from the SQL Table
                    while (rs.next()){
                        u = rs.getInt("Account_No");
                        p = rs.getInt("Pin");
                    }
                    if (ac == u && pn == p) { // if user is valid this condition will take him/her to the Bank Interface

                        System.out.println("============================================================");
                        System.out.println("||                 Welcome To Public Bank                 ||");
                        System.out.println("============================================================\n");

                        System.out.println("1. Transaction History");
                        System.out.println("2. Withdraw");
                        System.out.println("3. Deposit");
                        System.out.println("4. Transfer");
                        System.out.println("5. Check Balance");
                        System.out.println("6. Quit\n");


                        System.out.println("Choose an Option");

                        int ch = sc.nextInt();
                        face fc = new face();
                        switch (ch) {
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
                        System.out.println("Wrong Credentials\n");
                    }
                } catch (SQLException e) {throw new RuntimeException(e);}

            } else if (input == 2) { // New user will register here

                Scanner sc1 = new Scanner(System.in);

                System.out.println("Enter Your Name");
                String na = sc1.nextLine();

                System.out.println("Enter Your Age");
                int age = Integer.parseInt(sc1.nextLine());

                // I face the problem here while running this programme because in the
                // nextLine() method it skips the input to be read, and takes the input of Age in place of Name.
                // Hence,I did the syntax as Read the complete line as String and convert it to integer.

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

                    //System.out.println(a);

                    String save = "INSERT INTO account (Account_No,User_Name,Age,Address,Mobile_No,Pin,Balance) VALUES (" + a + ",'"+na+"',"+age+",'"+add+"',"+num+","+pi+","+de+");";
                    st.executeUpdate(save); //

                    System.out.println("Registration has completed ! \nAccount No of holder: "+a);
                } catch (Exception e) {throw new RuntimeException(e);}

                // now have to add the account number which is going to take as you have to take the previous account info and the add +1 to it

            } else if (input == 3) {
                System.exit(0);
            }
        }
    }
}
