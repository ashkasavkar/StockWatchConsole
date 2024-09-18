package com.tradeconsole.swc;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.tradeconsole.swc.entity.Account;
import com.tradeconsole.swc.entity.History;
import com.tradeconsole.swc.entity.Nominee;
import com.tradeconsole.swc.entity.Portfolio;
import com.tradeconsole.swc.entity.StockDisplay;
import com.tradeconsole.swc.entity.User;
import com.tradeconsole.swc.service.AccountService;
import com.tradeconsole.swc.service.FavStockService;
import com.tradeconsole.swc.service.HistoryService;
import com.tradeconsole.swc.service.PortfolioService;
import com.tradeconsole.swc.service.StockDisplayService;
import com.tradeconsole.swc.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class App {
    private static UserService userService = new UserService();
    private static StockDisplayService stockDisplayService = new StockDisplayService();
    private static PortfolioService portfolioService = new PortfolioService();
    private static FavStockService favStockService = new FavStockService();
    private static AccountService accountService = new AccountService();
    private static HistoryService historyService = new HistoryService();
    
    private static Scanner scanner = new Scanner(System.in);
    private static User loggedInUser;
    private static List<StockDisplay> STOCKS;
    
    private static final double INITIAL_BALANCE = 5000.0;

    public static void main(String[] args) {
        System.out.println("Welcome to the Stock Market Tracker!");

        loadStockData();
        
        
        while (true) {
            showMainMenu();
            int choice = getUserInput();

            switch (choice) {
                case 1:
                    signUp();
                    break;
                case 2:
                    logIn();
                    break;
                case 3:
                    addNomineeDetails();
                    break;
                case 4:
                    viewStockBySymbol();
                    break;
                case 5:
                    System.out.println("Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void loadStockData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            STOCKS = mapper.readValue(new File("src/main/resources/IndianStock.json"), new TypeReference<List<StockDisplay>>(){});
         // Save loaded stocks to the database
            for (StockDisplay stock : STOCKS) {
                stockDisplayService.saveStockDisplay(stock);
            }
            
            System.out.println("Stock data loaded and saved to the database successfully.");
        } catch (IOException e) {
            System.err.println("Error loading stock data: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void showMainMenu() {
        System.out.println("1. Sign Up");
        System.out.println("2. Log In");
        System.out.println("3. Add Nominee Details");
        System.out.println("4. View Stocks");
        System.out.println("5. Exit");
    }

    private static int getUserInput() {
        int choice = -1;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
        return choice;
    }

    private static void signUp() {
        String username = promptForInput("Enter username:");
        String password = promptForInput("Enter password:");
       
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

    private static boolean isValidEmail(String email) {
        return email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    private static boolean isValidMobileNumber(String mobileNumber) {
        return mobileNumber != null && mobileNumber.matches("\\d{10}");
    }

    private static void logIn() {
        String username = promptForInput("Enter username:");
        
        String password = promptForInput("Enter password:");
        
        		
        loggedInUser = userService.login(username, password);
        if (loggedInUser != null) {
            System.out.println("Login successful!");
            showUserMenu();
        } else {
            System.out.println("Invalid credentials. Please try again.");
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
    

    private static void viewStockBySymbol() {
        System.out.println("Available Stock Symbols:");
        for (int i = 0; i < STOCKS.size(); i++) {
            System.out.println((i + 1) + ". " + STOCKS.get(i).getStockSymbol());
        }
        System.out.println("Select a stock by entering its number (0 to return to main menu):");
        int choice = getUserInput();

        if (choice == 0) {
            return;
        }

        if (choice < 1 || choice > STOCKS.size()) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }

        StockDisplay selectedStock = STOCKS.get(choice - 1);
        stockDisplayService.displayStock(selectedStock);

        if (loggedInUser != null) {
            String action = promptForInput("Do you want to add this stock to your portfolio or favorites list? (portfolio/favorites/none)");
            if (action.equalsIgnoreCase("portfolio")) {
                addStockToPortfolio(selectedStock);
            } else if (action.equalsIgnoreCase("favorites")) {
                manageFavoriteStocks(selectedStock);
            } else {
                System.out.println("No changes made.");
            }
        } else {
            System.out.println("Please log in to manage your portfolio or favorites.");
        }
    }

    private static int getPositiveInteger(String prompt) {
        System.out.println(prompt);
        int value = -1;
        try {
            value = Integer.parseInt(scanner.nextLine());
            if (value <= 0) {
                System.out.println("Value must be positive.");
                value = -1;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a positive integer.");
        }
        return value;
    }

    private static double getPositiveDouble(String prompt) {
        System.out.println(prompt);
        double value = -1;
        try {
            value = Double.parseDouble(scanner.nextLine());
            if (value <= 0) {
                System.out.println("Value must be positive.");
                value = -1;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a positive number.");
        }
        return value;
    }

    private static void showUserMenu() {
        while (true) {
        	System.out.println("1. View Stocks (and add to Portfolio or Favorites)");
            System.out.println("2. View Portfolio");
            System.out.println("3. View History");
            System.out.println("4. Manage Favorite Stocks");
            System.out.println("5. Log Out");
            System.out.println("6. Exit");

            int choice = getUserInput();

            switch (choice) {
                case 1:
                	viewStockBySymbol();
                    break;
                case 2:
                	viewPortfolio();
                    break;
                case 3:
                	viewHistory();
                    break;
                case 4:
                    manageFavoriteStocksMenu();
                    break;
                case 5:
                    loggedInUser = null;
                    System.out.println("Logged out successfully.");
                    return;
                case 6:
                    System.out.println("Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void addStockToPortfolio(StockDisplay stock) {
    	if (loggedInUser == null) {
            System.out.println("Please log in first.");
            return;
        }

       
        int quantity = getPositiveInteger("Enter quantity to buy:");
        double buyPrice = stock.getStockPrice();
        double totalInvestment = quantity * buyPrice;

        Account account = accountService.getAccountByUserId(loggedInUser.getUserId());
        if (account == null) {
            System.out.println("Error: Account not found.");
            return;
        }

        if (account.getAvailableBalance() < totalInvestment) {
            System.out.println("Insufficient funds. Please check your account balance.");
            return;
        }
        
        Portfolio portfolio = new Portfolio();
        portfolio.setUser(loggedInUser);
        portfolio.setStock(stock);
        portfolio.setQuantity(quantity);
        portfolio.setBuyPrice(buyPrice);
        portfolio.setInvestment(totalInvestment);

        portfolioService.createPortfolio(portfolio);

        account.setAvailableBalance(account.getAvailableBalance() - totalInvestment);
        accountService.updateAccount(account);

        System.out.println("Stock added to portfolio successfully!");

        /// Add to history
        History history = new History();
        history.setProfit(0.0);
        history.setLoss(0.0);
        history.setUser(loggedInUser);
        historyService.addHistory(history);
        System.out.println("Stock added to portfolio successfully!");
    }
    
    private static void manageFavoriteStocks(StockDisplay stock) {
        if (loggedInUser == null) {
            System.out.println("Please log in first.");
            return;
        }

        String action = promptForInput("Do you want to add or remove this stock from your favorites? (add/remove/none)");
        try {
            if (action.equalsIgnoreCase("add")) {
                favStockService.addStockToFavorites(loggedInUser, stock);
                System.out.println("Stock added to favorites!");
            } else if (action.equalsIgnoreCase("remove")) {
                favStockService.removeStockFromFavorites(loggedInUser, stock);
                System.out.println("Stock removed from favorites!");
            } else {
                System.out.println("No changes made to favorites.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred while managing favorite stocks: " + e.getMessage());
            e.printStackTrace();
        }
    }
   

    private static void viewPortfolio() {
        if (loggedInUser == null) {
            System.out.println("Please log in first.");
            return;
        }

        List<Portfolio> portfolios = portfolioService.getPortfoliosByUserId(loggedInUser.getUserId());
        if (portfolios.isEmpty()) {
            System.out.println("Your portfolio is empty.");
            return;
        }

        System.out.println("Your Portfolio:");
        for (int i = 0; i < portfolios.size(); i++) {
            Portfolio portfolio = portfolios.get(i);
            StockDisplay stock = portfolio.getStock();
            if (stock != null) {
                System.out.println((i + 1) + ". Stock Symbol: " + stock.getStockSymbol());
                System.out.println("   Stock Name: " + stock.getStockName());
                System.out.println("   Quantity: " + portfolio.getQuantity());
                System.out.println("   Buy Price: ₹" + portfolio.getBuyPrice());
                System.out.println("   Investment: ₹" + portfolio.getInvestment());
                System.out.println();
            } else {
                System.out.println("Stock details not found for ID: " + portfolio.getStock().getStockId());
            }
        }

        String action = promptForInput("Would you like to update or delete a stock from your portfolio? (update/delete/none)");
        if (action.equalsIgnoreCase("update")) {
            updatePortfolio(portfolios);
        } else if (action.equalsIgnoreCase("delete")) {
            deleteFromPortfolio(portfolios);
        } else {
            System.out.println("No changes made to portfolio.");
        }
    }

    private static void updatePortfolio(List<Portfolio> portfolios) {
        int choice = getPositiveInteger("Enter the number of the stock you want to update:");
        if (choice < 1 || choice > portfolios.size()) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }

        Portfolio portfolio = portfolios.get(choice - 1);
        int newQuantity = getPositiveInteger("Enter the new quantity:");
        if (newQuantity <= 0) {
            System.out.println("Invalid quantity. Please try again.");
            return;
        }

        double totalInvestment = newQuantity * portfolio.getBuyPrice();
        double balanceDifference = totalInvestment - portfolio.getInvestment();

        Account account = accountService.getAccountByUserId(loggedInUser.getUserId());
        if (account.getAvailableBalance() < balanceDifference) {
            System.out.println("Insufficient funds to increase quantity.");
            return;
        }

        portfolio.setQuantity(newQuantity);
        portfolio.setInvestment(totalInvestment);
        portfolioService.updatePortfolio(portfolio);

        account.setAvailableBalance(account.getAvailableBalance() - balanceDifference);
        accountService.updateAccount(account);

        System.out.println("Portfolio updated successfully!");
    }

    private static void deleteFromPortfolio(List<Portfolio> portfolios) {
        int choice = getPositiveInteger("Enter the number of the stock you want to delete:");
        if (choice < 1 || choice > portfolios.size()) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }

        Portfolio portfolio = portfolios.get(choice - 1);
        double investment = portfolio.getInvestment();

        Account account = accountService.getAccountByUserId(loggedInUser.getUserId());
        if (account != null) {
            account.setAvailableBalance(account.getAvailableBalance() + investment);
            accountService.updateAccount(account);
        }

        portfolioService.deletePortfolio(portfolio.getId());
        System.out.println("Stock removed from portfolio successfully!");
    }
    private static void viewHistory() {
        if (loggedInUser == null) {
            System.out.println("Please log in first.");
            return;
        }

        List<History> histories = historyService.getHistoryByUserId(loggedInUser.getUserId());
        if (histories.isEmpty()) {
            System.out.println("No history available.");
            return;
        }

        System.out.println("Your History:");
        for (History history : histories) {
            System.out.println("Profit: " + history.getProfit() +
                               ", Loss: " + history.getLoss());
        }
    }

    

    private static void manageFavoriteStocksMenu() {
        if (loggedInUser == null) {
            System.out.println("Please log in first.");
            return;
        }

        List<StockDisplay> favoriteStocks = favStockService.getFavoriteStocks(loggedInUser);
        if (favoriteStocks.isEmpty()) {
            System.out.println("No favorite stocks found.");
            return;
        }

        System.out.println("Your Favorite Stocks:");
        for (StockDisplay stock : favoriteStocks) {
            System.out.println("Stock: " + stock.getStockSymbol() +
                               ", Price: " + stock.getStockPrice() +
                               ", Percent Change: " + stock.getStockPercent() +
                               ", Previous Close: " + stock.getPreviousClosePrice());
        }

        String action = promptForInput("Do you want to add or remove a stock from favorites? (add/remove/none)");
        if (action.equalsIgnoreCase("add") || action.equalsIgnoreCase("remove")) {
            String stockSymbol = promptForInput("Enter the stock symbol:");
            StockDisplay stock = stockDisplayService.getStockBySymbol(stockSymbol);
            if (stock == null) {
                System.out.println("Stock not found.");
                return;
            }

            if (action.equalsIgnoreCase("add")) {
                favStockService.addStockToFavorites(loggedInUser, stock);
                System.out.println("Stock added to favorites!");
            } else {
                favStockService.removeStockFromFavorites(loggedInUser, stock);
                System.out.println("Stock removed from favorites!");
            }
        } else {
            System.out.println("No changes made to favorites.");
        }
    }
}