package com.tradeconsole.swc.operations;

import com.tradeconsole.swc.entity.Account;
import com.tradeconsole.swc.entity.Nominee;
import com.tradeconsole.swc.entity.User;
import com.tradeconsole.swc.service.AccountService;
import com.tradeconsole.swc.service.UserService;
import com.tradeconsole.swc.util.PasswordReader;

import java.io.Console;
import java.util.Scanner;


public class UserOperations {
    private UserService userService;
    private AccountService accountService;
    private Scanner scanner;
    private static User loggedInUser;
    private static final double INITIAL_BALANCE = 5000.0;

    public UserOperations(UserService userService, AccountService accountService, Scanner scanner) {
        this.userService = userService;
        this.accountService = accountService;
        this.scanner = scanner;
    }

    public void signUp() {
        String username = promptForInput("Enter username:");
        String password = PasswordReader.readPassword("Enter password:");
        if (password == null) {
            System.out.println("Error reading password.");
            return;
        }
        String firstName = promptForInput("Enter first name:");
        String lastName = promptForInput("Enter last name:");
        String email = promptForInput("Enter email:");
        String mobileNumber = promptForInput("Enter mobile number:");

        if (!isValidEmail(email)) {
            System.out.println("Invalid email format.");
            return;
        }

        if (!isValidMobileNumber(mobileNumber)) {
            System.out.println("Invalid mobile number format.");
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setMobileNumber(mobileNumber);

        userService.signUp(user);
        
        
     // Initialize account with dummy balance
        Account account = new Account();
        account.setUtilizeBalance(0.0);
        account.setAvailableBalance(INITIAL_BALANCE);
        account.setUser(user);
        accountService.createAccount(account);
        System.out.println("User registered successfully with a balance of " + INITIAL_BALANCE + " INR!");
    }
   
    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    private boolean isValidMobileNumber(String mobileNumber) {
        return mobileNumber != null && mobileNumber.matches("\\d{10}");
    }

    private static void logIn() {
        String username = promptForInput("Enter username:");
        
        String password = PasswordReader.readPassword("Enter password:");
        if (password == null) {
            System.out.println("Error reading password.");
            return;
        }
    
        		
        
    }
    

    private static String promptForInput(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine().trim();
    }


    private static void addNomineeDetails() {
        if (loggedInUser == null) {
            System.out.println("Please log in first.");
            return;
        }

        String nomineeName = promptForInput("Enter nominee name:");
        String nomineeEmail = promptForInput("Enter nominee email:");
        String nomineeUsername = promptForInput("Enter nominee username:");
        String nomineeMobileNumber = promptForInput("Enter nominee mobile number:");

        if (!isValidEmail(nomineeEmail)) {
            System.out.println("Invalid nominee email format.");
            return;
        }

        if (!isValidMobileNumber(nomineeMobileNumber)) {
            System.out.println("Invalid nominee mobile number format.");
            return;
        }

        Nominee nominee = new Nominee();
        nominee.setNomineeName(nomineeName);
        nominee.setNomineeEmail(nomineeEmail);
        nominee.setNomineeUsername(nomineeUsername);
        nominee.setNomineeMobileNumber(nomineeMobileNumber);

        userService.addNominee(loggedInUser.getUserId(), nominee);
        System.out.println("Nominee details added successfully!");
    }
  
    
}