import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

interface stockExchange{
    String getTicker();
    String getSector();
    double getHigh();
    double getLow();
    double getOpen();
    double getClose();
}

class order{
    private user orderUser;
    private String type = "";
    private stockMarket scrip;
    private int quantity = 0;
    private double rate = 0;
    //constructor of order
    public order(String orderUser, String type, String scrip, int quantity, double rate){
        this.quantity = quantity;
        this.rate = rate;
        this.type = type;
        //finds stock from the array list then adds it to order
        for(int i=0;i<main.stockArrayList.size();i++){
            if(main.stockArrayList.get(i).getTicker().equals(scrip)){
                this.scrip = main.stockArrayList.get(i);
            }
        }
        //finds user from the user array list to add it order
        for(int i=0;i<main.userArrayList.size();i++){
            if(main.userArrayList.get(i).getName().equals(orderUser)){
                this.orderUser = main.userArrayList.get(i);
            }
        }
    }

    public static void printAllOrder(){
        //prints every order placed
        for(int i=0;i<main.orderArrayList.size();i++){
            System.out.println("Type: "+main.orderArrayList.get(i).type +
                    " User: " + main.orderArrayList.get(i).getOrderUser().getName()+
                    " Scrip: " + main.orderArrayList.get(i).getScrip().getTicker() +
                    " Qty: " + main.orderArrayList.get(i).getQuantity() +
                    " Rate: " + main.orderArrayList.get(i).rate);
        }
    }


    public static void addToValidOrderList(order order1){
        if(order1.getType().equals("buy")){
            main.buyOrderBook.add(order1);
        }
        else if(order1.getType().equals("sell")){
            main.sellOrderBook.add(order1);
        }
    }

    public double getRate() {
        return rate;
    }

    public int getQuantity() {
        return quantity;
    }

    public stockMarket getScrip() {
        return scrip;
    }

    public String getType() {
        return type;
    }

    public user getOrderUser() {
        return orderUser;
    }

    public static void printOrderBook(){
        System.out.println("\nOrder Book:");
        //first prints all valid orders from buy orderbook
        for(int i=0;i<main.buyOrderBook.size();i++){
            System.out.println("Buy order "+ main.buyOrderBook.get(i).getScrip().getTicker()+
                    ":"+main.buyOrderBook.get(i).getQuantity()+" at " + main.buyOrderBook.get(i).getRate());
        }
        //then prints from sell order book
        for(int i=0;i<main.sellOrderBook.size();i++){
            System.out.println("Sell order "+ main.sellOrderBook.get(i).getScrip().getTicker()+
                    ":"+main.sellOrderBook.get(i).getQuantity()+" at " + main.sellOrderBook.get(i).getRate());
        }

    }
    public static void deleteScripFromOrderBook(String ticker){
        //if a scrip is deleted, this function is called
        //first deletes from buyorder book
        for(int i=0;i<main.buyOrderBook.size();i++){
            if(main.buyOrderBook.get(i).getScrip().getTicker().equals(ticker)){
                main.buyOrderBook.remove(i);
            }
        }
        //then deletes the scrip from sell orderbook
        for(int i=0;i<main.sellOrderBook.size();i++){
            if(main.sellOrderBook.get(i).getScrip().getTicker().equals(ticker)){
                main.sellOrderBook.remove(i);
            }
        }
    }

    public static void deleteUserFromOrderBook(String name){
        //if a user is deleted then it has t be deleted from orderbooks also
        //first deleted from buy order book
        for(int i=0;i<main.buyOrderBook.size();i++){
            if(main.buyOrderBook.get(i).getOrderUser().getName().equals(name)){
                main.buyOrderBook.remove(i);
            }
        }

        //then user deleted from sell order book
        for(int i=0;i<main.sellOrderBook.size();i++){
            if(main.sellOrderBook.get(i).getOrderUser().getName().equals(name)){
                main.sellOrderBook.remove(i);
            }
        }
    }

    public static void addValidOrdersToOrderBook(order order1){
        try{
            //this try block check all the exceptions for each input orders
            if(order1.getScrip().getLowerCircuit()>order1.rate){
                throw new lowerCircuitViolation();
                //if order ka rate<lower circuit of that company
            }
            else if (order1.getScrip().getUpperCircuit()<order1.rate){
                throw new upperCircuitViolation();
                //if order ka rate is > upper circuit
            }
            else if((order1.rate*order1.quantity)>order1.getOrderUser().getFunds() && order1.type.equals("buy")){
                throw new insufficientFundsException();
                //if user gives an order for which he cannot pay
                // if type is buy and rate*qty is less than user funds
            } else if (order1.type.equals("sell") && order1.quantity>order1.getOrderUser().getHoldingsHashMap().get(order1.getScrip().getTicker()) ) {
                throw new shortSellingException();
                //if person does not have shares of the company but sells it
                //if order type=sell and user holdings has less than qty of order
            } else {
                //if no exepption is there
                //orders added to buy orderbook and sell orderbook seprately
                addToValidOrderList(order1);
                System.out.println("order placed for user: " + order1.getOrderUser().getName() +
                        ", Type: " + order1.getType()+ ", Scrip: " + order1.getScrip().getTicker() +
                        ", Qty: " + order1.getQuantity() + ", Rate: " + order1.getRate());
            }
        } catch (lowerCircuitViolation lowerCircuitViolation) {
            //handles the lower circuit violation
            System.out.println("Order rejected for User: " + order1.getOrderUser().getName()+ " Reason: Lower circuit violation");
        } catch (upperCircuitViolation upperCircuitViolation) {
            //handles upper circuit violation
            System.out.println("Order rejected for User: " + order1.getOrderUser().getName()+ " Reason: Upper circuit violation");
        } catch (insufficientFundsException insufficientFundsException ) {
            //handles if user has less funds than his order
            System.out.println("Order rejected for User: " + order1.getOrderUser().getName() + " Reason: Insufficient Funds");
        } catch (shortSellingException shortSellingException){
            //handles the case when user sells more tha his available shares in holdings
            System.out.println("Order rejected for User: " + order1.getOrderUser().getName() + " Reason: Short Selling Out of scope");
        } catch (NullPointerException NullPointerException){
            //thorws only when there are no holdings
            System.out.println("Order rejected as there are no stocks found in User holdings, User: " + order1.getOrderUser().getName());
        }
    }

    public static void executeValidOrders(){
        order buyOrder;
        order sellOrder;
        System.out.println("\nExecuted orders:");
        for(int i=0;i<main.buyOrderBook.size();i++){
            buyOrder = main.buyOrderBook.get(i);
            for(int j=0;j<main.sellOrderBook.size();j++){
                sellOrder = main.sellOrderBook.get(j);
                if(buyOrder.getScrip().getTicker().equals(sellOrder.getScrip().getTicker()) && buyOrder.rate>=sellOrder.rate){
                    //order executed
                    //find number od shares to be transferred
                    //update funds for users =========> done1
                    //update holdings of users =========> done2
                    //maybe update OHLC of scrip
                    //add updated order in orderbook if shares not equal ==========> done3
                    //remove outdated order from orderbook- do at end
                    //print success execution message

                    //updating funds for users
                    int numberOfSharesToBeTransferred = Math.min(main.buyOrderBook.get(i).quantity,main.sellOrderBook.get(j).quantity);
                    double transactionValue = numberOfSharesToBeTransferred*(main.sellOrderBook.get(j).getRate());
                    int buyIndex = main.userArrayList.indexOf(buyOrder.getOrderUser());
                    int sellIndex = main.userArrayList.indexOf(sellOrder.getOrderUser());
                    main.userArrayList.get(buyIndex).updateUserfunds(main.buyOrderBook.get(i).getOrderUser().getFunds()-transactionValue);
                    main.userArrayList.get(sellIndex).updateUserfunds(main.sellOrderBook.get(j).getOrderUser().getFunds()+transactionValue);

                    //update user holdings
                    int oldNumberOfSharesWithBuyer=0;
                    int oldNumberOfSharesWithSeller=0;
                    try{
                        oldNumberOfSharesWithBuyer = main.buyOrderBook.get(i).getOrderUser().getHoldingsHashMap().get(buyOrder.getScrip().getTicker());
                    }catch (NullPointerException NullPointerException){
                    }
                    try {
                        oldNumberOfSharesWithSeller = main.sellOrderBook.get(j).getOrderUser().getHoldingsHashMap().get(buyOrder.getScrip().getTicker());
                    }
                    catch (NullPointerException NullPointerException) {
                    }

                    main.buyOrderBook.get(i).getOrderUser().getHoldingsHashMap().remove(main.buyOrderBook.get(i).getScrip().getTicker());
                    main.buyOrderBook.get(i).getOrderUser().getHoldingsHashMap().put(main.buyOrderBook.get(i).getScrip().getTicker(),oldNumberOfSharesWithBuyer+numberOfSharesToBeTransferred);

                    main.sellOrderBook.get(j).getOrderUser().getHoldingsHashMap().remove(main.sellOrderBook.get(j).getScrip().getTicker());
                    if(oldNumberOfSharesWithSeller-numberOfSharesToBeTransferred!=0)
                        main.sellOrderBook.get(j).getOrderUser().getHoldingsHashMap().put(main.sellOrderBook.get(j).getScrip().getTicker(),oldNumberOfSharesWithSeller-numberOfSharesToBeTransferred);
                    //main.buyOrderBook.get(i).getOrderUser().getHoldingsHashMap().replace(buyOrder.getScrip().getTicker(), oldNumberOfSharesWithBuyer+numberOfSharesToBeTransferred);
                    //main.sellOrderBook.get(j).getOrderUser().getHoldingsHashMap().replace(sellOrder.getScrip().getTicker(), oldNumberOfSharesWithSeller-numberOfSharesToBeTransferred);

                    //start with updating orderbook
                    order newBuyOrder = new order(main.buyOrderBook.get(i).getOrderUser().getName(),
                            main.buyOrderBook.get(i).getType(),main.buyOrderBook.get(i).getScrip().getTicker(),
                            main.buyOrderBook.get(i).getQuantity()-numberOfSharesToBeTransferred,
                            main.buyOrderBook.get(i).getRate());
                    if(newBuyOrder.getQuantity()!=0){
                        order.addToValidOrderList(newBuyOrder);
                    }
                    order newSellOrder = new order(main.sellOrderBook.get(j).getOrderUser().getName(),
                            main.sellOrderBook.get(j).getType(), main.sellOrderBook.get(j).getScrip().getTicker(),
                            main.sellOrderBook.get(j).getQuantity()-numberOfSharesToBeTransferred,
                            main.sellOrderBook.get(j).getRate());
                    if(newSellOrder.getQuantity()!=0){
                        order.addToValidOrderList(newSellOrder);
                    }

                    //updating OHLC values
                    for(int k=0;k<main.stockArrayList.size();k++){
                        if(main.stockArrayList.get(k).getTicker().equals(buyOrder.getScrip().getTicker())){
                            if(main.stockArrayList.get(k).getHigh()<sellOrder.getRate()){
                                main.stockArrayList.get(k).setHigh(sellOrder.getRate());
                                main.stockArrayList.get(k).setClose(sellOrder.getRate());
                            }
                            if(main.stockArrayList.get(k).getLow()>sellOrder.getRate()){
                                main.stockArrayList.get(k).setLow(sellOrder.getRate());
                                main.stockArrayList.get(k).setClose(sellOrder.getRate());
                            }
                        }
                    }

                    //removing old orders from orderbook
                    main.buyOrderBook.remove(i);
                    main.sellOrderBook.remove(j);

                    //printing success message
                    System.out.println(numberOfSharesToBeTransferred + " qty of Scrip: " + buyOrder.getScrip().getTicker() +
                            " sold for: " + sellOrder.getRate() + "; Buyer: " + buyOrder.getOrderUser().getName() +
                            ", Seller: " + sellOrder.getOrderUser().getName());
                }
            }
        }
    }

}

class lowerCircuitViolation extends Exception{
    public lowerCircuitViolation(){
    }
}

class upperCircuitViolation extends Exception{
    public upperCircuitViolation(){}
}

class insufficientFundsException extends Exception{
    public insufficientFundsException(){}
}

class shortSellingException extends Exception{
    public shortSellingException(){}
}

class userDoesNotExistException extends Exception{
    public userDoesNotExistException(){}
}

class scripNotFoundException extends Exception{
    public scripNotFoundException(){}
}

class user{
    private static int  total_users =0;
    private String name = "";
    private double funds = 0;
    private int numberOfHoldings = 0;
    private int  customerID = 0;
    private HashMap<String,Integer> holdingsHashMap = new HashMap<>();
    public user(String name, double funds, String holdings){//user constructor
        this.name = name;
        this.funds = funds;
        //random customer ID
        customerID = (int) (100000+Math.random()*900000);
        if(!(holdings.equals("None"))){
            //send all holdings to function to store it
            addUserHoldings(holdings);
            numberOfHoldings = holdingsHashMap.size();
        }
        else{
            numberOfHoldings = 0;
        }
        user.total_users++;
        //example: User added: Mimi, Funds: 1000.0, Number of holdings: 3, Customer ID: 657842, Holdings: {TCS=5, INFY=10, SBI=20}
        System.out.print("User added: " + this.name + ", Funds: "
                +this.funds+", Number of holdings: "+ numberOfHoldings + ", Customer ID: "+customerID);
        System.out.println(", Holdings: " +holdingsHashMap);
    }



    public void addUserHoldings(String holdings){
        String holdingStringArray[] = holdings.split("[(:)(,)]");
        //first checking if scrip exists then adding it to user holding hash map
        for(int j=0;j<holdingStringArray.length;j=j+2) {
            for (int i = 0; i < main.stockArrayList.size(); i++) {
                if (main.stockArrayList.get(i).getTicker().equals(holdingStringArray[j])) {
                    holdingsHashMap.put(holdingStringArray[j], Integer.parseInt(holdingStringArray[j+1]));
                }
            }
        }
    }

    public double getFunds() {
        return funds;
    }

    public static int getTotal_users() {
        return total_users;
    }

    public HashMap<String, Integer> getHoldingsHashMap() {
        return holdingsHashMap;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getNumberOfHoldings() {
        return numberOfHoldings;
    }

    public String getName() {
        return name;
    }

    public static String getUserName(int customerID) {
        for (int i = 0; i < main.userArrayList.size(); i++) {
            if (main.userArrayList.get(i).getCustomerID() == customerID)
                return main.userArrayList.get(i).getName();
        }
        System.out.println("User not found");
        return "user_not_found";
    }

    public static double getUserFunds(int customerID) {
        //find customer funds using customer ID
        for (int i = 0; i < main.userArrayList.size(); i++) {
            if (main.userArrayList.get(i).getCustomerID() == customerID)
                return main.userArrayList.get(i).getFunds();
        }
        System.out.println("User not found");
        return -1;
    }

    public static void getUserHoldings(int customerID) {
        //using customer funds using customer ID
        for (int i = 0; i < main.userArrayList.size(); i++) {
            if (main.userArrayList.get(i).getCustomerID() == customerID)
                System.out.println(main.userArrayList.get(i).getHoldingsHashMap());
        }
    }

    public static double getUserFunds(String name) {
        //getting funds using name
        for (int i = 0; i < main.userArrayList.size(); i++) {
            if (main.userArrayList.get(i).getName() == name)
                return main.userArrayList.get(i).getFunds();
        }
        System.out.println("User not found");
        return -1;
    }

    public static void getUserHoldings(String name) {
        //getting holdings
        for (int i = 0; i < main.userArrayList.size(); i++) {
            if (main.userArrayList.get(i).getName().equals(name))
                System.out.println(main.userArrayList.get(i).getHoldingsHashMap());
        }
    }

    public static void printAllUsers(){
        //example: Name: Mimi, Funds: 1000.0, Holdings: {INFY=10, SBI=20}
        System.out.println("\nUsers:");
        for (int i=0;i<main.userArrayList.size();i++){
            System.out.println("Name: " + main.userArrayList.get(i).getName() +
                    ", Funds: " + main.userArrayList.get(i).getFunds() +
                    ", Holdings: " + main.userArrayList.get(i).getHoldingsHashMap().toString());
        }
    }

    public static void deleteScripFromAllHoldings(String ticker){
        //example: Deleted scrip: M&M
        for(int i=0;i<main.userArrayList.size();i++){
            if(main.userArrayList.get(i).holdingsHashMap.containsKey(ticker)){
                main.userArrayList.get(i).holdingsHashMap.remove(ticker);
            }
        }
    }
    public static void deleteUser(String name){
        //delete user by name
        int counter=0;
        for(int i=0;i<main.userArrayList.size();i++){
            if(main.userArrayList.get(i).getName().equals(name)){
                counter++;
            }
        }
        try {
            //if user does not exist
            if (counter == 0) {
                throw new userDoesNotExistException();
            }
        }catch(userDoesNotExistException userDoesNotExistException){
            System.out.println("User not found: "+name);
        }
        for(int i=0;i<main.userArrayList.size();i++){
            //deleting user from user array
            if(main.userArrayList.get(i).getName().equals(name)){
                main.userArrayList.remove(i);
                order.deleteUserFromOrderBook(name);
                System.out.println("Deleted user: " + name);
                break;
            }
        }
    }
    public void updateUserfunds(double updatedFunds){
        this.funds = updatedFunds;
    }

    public static void showOpenOrdersOf(String name) {
        //prints the open orders of user by name
        int counter = 0;
        System.out.println("\nOpen orders of User: " + name);
        //finds from buy order book
        for (int i = 0; i < main.buyOrderBook.size(); i++) {
            if (main.buyOrderBook.get(i).getOrderUser().getName().equals(name)) {
                System.out.println("Buy order Scrip: " + main.buyOrderBook.get(i).getScrip().getTicker()
                        + ", Quantity: " + main.buyOrderBook.get(i).getQuantity()
                        + ", Rate: " + main.buyOrderBook.get(i).getRate()+
                        ", Markets: " + main.buyOrderBook.get(i).getScrip().getMarkets());
                counter++;
            }
        }
        //finds from sell order book
        for (int i = 0; i < main.sellOrderBook.size(); i++) {
            if (main.sellOrderBook.get(i).getOrderUser().getName().equals(name)) {
                System.out.println("Sell order Scrip: " + main.sellOrderBook.get(i).getScrip().getTicker()
                        + ", Quantity: " + main.sellOrderBook.get(i).getQuantity()
                        + ", Rate: " + main.sellOrderBook.get(i).getRate() +
                        ", Markets: " + main.sellOrderBook.get(i).getScrip().getMarkets());
                counter++;
            }
        }
        //prints number of open orders at the end
        if (counter == 0) {
            System.out.println("User: " + name + " has no open orders.");
        } else if (counter == 1)
            System.out.println("User: " + name + " has 1 open order as listed above.");
        else
            System.out.println("User: " + name + " has " + counter + " open orders as listed above.");
    }

}


class stockMarket implements stockExchange {
    private String markets = "...";
    private double high = 0;
    private double low = 0;
    private double open = 0;
    private double close = 0;
    private String sector = "";
    private String ticker = "";
    private double lowerCircuit;
    private double upperCircuit;

    //stockMarket stores the scrips
    stockMarket(String ticker,String sector, double open, double high, double low, double close){
        this.ticker = ticker;
        this.close = close;
        this.open = open;
        this.high = high;
        this.low = low;
        this.sector = sector;
        this.lowerCircuit = close-(close/10.0);
        this.upperCircuit = close+(close/10.0);
        //generate a random number
        int random = (int)(Math.random()*10000);
        int remainder = random%3;
        //depending on that random number, it is assigned the markets
        if(remainder==0){
            this.markets = "NSE only";
        }
        else if(remainder==1){
            this.markets = "BSE only";
        }
        else if(remainder==2){
            this.markets = "NSE & BSE both";
        }
    }
    public String getTicker(){
        return this.ticker;
    }

    public String getMarkets() {
        return markets;
    }

    public String getSector() { return  this.sector; }

    public double getHigh(){ return high; }

    public double getLow(){ return  low; }

    public double getOpen(){ return open; }

    public double getClose(){ return close; }

    public double getLowerCircuit() {
        return lowerCircuit;
    }

    public double getUpperCircuit() {
        return upperCircuit;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public static void getSector(String stockSector) {
        //using name of company, finding its sector
        for (int i = 0; i < main.stockArrayList.size(); i++) {
            if (main.stockArrayList.get(i).getTicker().equals(stockSector))
                System.out.println(main.stockArrayList.get(i).getSector());
        }
    }

    public static void getHigh(String stockTicker) {
        //getting high using ticker
        for (int i = 0; i < main.stockArrayList.size(); i++) {
            if (main.stockArrayList.get(i).getTicker().equals(stockTicker))
                System.out.println(main.stockArrayList.get(i).getHigh());
        }
    }

    public static void getLow(String stockTicker) {
        //getting low using ticker
        for (int i = 0; i < main.stockArrayList.size(); i++) {
            if (main.stockArrayList.get(i).getTicker().equals(stockTicker))
                System.out.println(main.stockArrayList.get(i).getLow());
        }
    }

    public static void getOpen(String stockTicker) {
        //getting open price using ticker
        for (int i = 0; i < main.stockArrayList.size(); i++) {
            if (main.stockArrayList.get(i).getTicker().equals(stockTicker))
                System.out.println(main.stockArrayList.get(i).getOpen());
        }
    }

    public static void getClose(String stockTicker) {
        //getting close price using ticker
        for (int i = 0; i < main.stockArrayList.size(); i++) {
            if (main.stockArrayList.get(i).getTicker().equals(stockTicker))
                System.out.println(main.stockArrayList.get(i).getClose());
        }
    }
    public static void getLowerCircuit(String stockTicker) {
        //getting lower circuit using ticker
        for (int i = 0; i < main.stockArrayList.size(); i++) {
            if (main.stockArrayList.get(i).getTicker().equals(stockTicker))
                System.out.println(main.stockArrayList.get(i).getLowerCircuit());
        }
    }
    public static void getUpperCircuit(String stockTicker) {
        //getting upper circuit using ticker
        for (int i = 0; i < main.stockArrayList.size(); i++) {
            if (main.stockArrayList.get(i).getTicker().equals(stockTicker))
                System.out.println(main.stockArrayList.get(i).getUpperCircuit());
        }
    }
    public static void showAllScripsOf(String sector){
        //prints all scrips of a perticular sector
        System.out.println("\nScrips listed in category: "+ sector);
        for(int i=0;i<main.stockArrayList.size();i++){
            if(main.stockArrayList.get(i).getSector().equals(sector)){
                System.out.println(main.stockArrayList.get(i).getTicker() +
                        ", OHLC = <" + main.stockArrayList.get(i).open +
                        ", " + main.stockArrayList.get(i).high +
                        ", " + main.stockArrayList.get(i).low +
                        ", " + main.stockArrayList.get(i).close + ">"+
                        ", Markets: " + main.orderArrayList.get(i).getScrip().getMarkets());
            }
        }
        System.out.println("");
    }

    public static void deleteScrip(String ticker){
        //deleting a perticular scrip
        int counter=0;
        for(int i=0;i<main.stockArrayList.size();i++){
            if(main.stockArrayList.get(i).getTicker().equals(ticker)){
                counter++;
            }
        }
        try{
            if(counter==0){
                throw new scripNotFoundException();
            }
        } catch (scripNotFoundException e){
            System.out.println("Scrip not found: "+ticker);
        }
        for(int i=0;i<main.stockArrayList.size();i++){
            if(main.stockArrayList.get(i).getTicker().equals(ticker)){
                main.stockArrayList.remove(i);
                user.deleteScripFromAllHoldings(ticker);
                order.deleteScripFromOrderBook(ticker);
                System.out.println("Deleted scrip: " + ticker);
            }
        }
    }
}

class highLow extends softwareProgram {
    // just for the 2nd part of ques
    double high;
    double low;
    double difference;
    public highLow(double high, double low){
        this.high=high;
        this.low=low;
        this.difference=high-low;
        main.highLowArrayList.add(this);
    }
}

class softwareProgram{
    public static void addScrip(String ticker, String sector, double open, double high, double low, double close){
        stockMarket Stock1 = new stockMarket(ticker,sector,open,high,low,close);
        main.stockArrayList.add(Stock1);
        System.out.print("Scrip Added: " + ticker + ", Open: " + open + "," +
                " High: " + high + ", Low: " + low + ", Close: " + close +", Markets: " + Stock1.getMarkets() +", Total Scrips: ");
        System.out.println(main.stockArrayList.size() );

    }

    public static void addUser(String name, double funds, String holdings){
        user user1 = new user(name, funds, holdings);
        main.userArrayList.add(user1);
    }

    public static void placeOrder(String orderUser, String type, String scrip, int quantity,double rate){
        order order1 = new order(orderUser,type,scrip,quantity,rate);
        main.orderArrayList.add(order1);
        order.addValidOrdersToOrderBook(order1);
    }
    public static void printAllScrips(){
        System.out.println("\nListed scrips:");
        for(int i=0;i<main.stockArrayList.size();i++){
            System.out.println("Scrip: " + main.stockArrayList.get(i).getTicker() +
                    ", Sector: " + main.stockArrayList.get(i).getSector() +
                    ", O:"+main.stockArrayList.get(i).getOpen()+
                    ", H:" + main.stockArrayList.get(i).getHigh() +
                    ", L:" + main.stockArrayList.get(i).getLow() +
                    ", C:" + main.stockArrayList.get(i).getClose() +
                    ", Markets: " + main.stockArrayList.get(i).getMarkets());
        }
    }
    public static void printAllOrders(){
        order.printAllOrder();
    }

    public static void deleteUser(String name){
        user.deleteUser(name);
    }

    public static void printOrderBook(){
        order.printOrderBook();
    }

    public static void showAllScripsOf(String sector){
        stockMarket.showAllScripsOf(sector);
    }
    public static void deleteScrip(String ticker){
        stockMarket.deleteScrip(ticker);
    }

    public static void printAllScripsOf(String sector){
        stockMarket.showAllScripsOf(sector);
    }

    public static void printAllUsers(){
        user.printAllUsers();
    }

    public static void executeValidOrders(){
        order.executeValidOrders();
    }

    public static void showOpenOrdersOf(String name){
        user.showOpenOrdersOf(name);
    }

    public static void calculateAverage(){
        double openSum=0;
        double closeSum=0;
        for(int i=0;i<15;i++){
            openSum = openSum + main.fifteenDayDataArray[i][1];
        }
        for(int i=0;i<15;i++){
            closeSum = closeSum + main.fifteenDayDataArray[i][5];
        }
        System.out.println("Open average of 15 days: "+((int)((openSum/15.0)*100)/100.0));
        System.out.println("Close average of 15 days: "+((int)((closeSum/15.0)*100)/100.0));
        System.out.println("Overall average: " + ((int)(((openSum+closeSum)/30.0)*100)/100.0));

    }

    public static void MaxReturnPotential(){
        //sum of abs difference of daily open and close price
        double returnAmount=0;
        double openPrice;
        double closePrice;
        for(int i=0;i<main.fifteenDayDataArray.length;i++){
            openPrice = main.fifteenDayDataArray[i][1];
            closePrice = main.fifteenDayDataArray[i][5];
            returnAmount = returnAmount + Math.abs(openPrice-closePrice);
        }
        System.out.println("Max return potential over 15 days: " + (int)((returnAmount)*100)/100.0 + " per share");
        System.out.println("Max return potential percentage: "+(int)(((returnAmount/main.fifteenDayDataArray[0][1])*100)*100)/100.0+ "%");
    }

    public static void maxDrawDown(){
        //find longest streak of loss that a person can face
        double high=main.fifteenDayDataArray[0][5];
        double low = main.fifteenDayDataArray[1][5];
        for(int i=1;i<15;i++){
            if(main.fifteenDayDataArray[i][5]<=low){
                low=main.fifteenDayDataArray[i][5];
            }
            else if(main.fifteenDayDataArray[i][5]>low){
                highLow HL = new highLow(high,low);
                //creates a new object of highlow class
                high=main.fifteenDayDataArray[i][5];
                low=high;
            }
        }
        double maxDifference=0;

        //finding max difference out of the Arraylist of highlow objects
        for(int i=0;i<main.highLowArrayList.size();i++){
            if(main.highLowArrayList.get(i).difference>maxDifference){
                maxDifference=main.highLowArrayList.get(i).difference;
            }
        }
        System.out.println("Max Drawdown: " + (int)((maxDifference)*100)/100.0);
    }

}


public class main {
    public static ArrayList<stockMarket> stockArrayList = new ArrayList<>();
    public static ArrayList<user> userArrayList = new ArrayList<>();
    public static ArrayList<order> orderArrayList = new ArrayList<>();
    public static ArrayList<order> buyOrderBook = new ArrayList<>();
    public static ArrayList<order> sellOrderBook = new ArrayList<>();
    public static double[][] fifteenDayDataArray  = new double[15][6];
    public static ArrayList<highLow> highLowArrayList = new ArrayList<>();

    public static void main(String[] args) {
        inputFile();
    }

    public static void inputFile() {
        try{
            File file = new File("C:/Users/dell/Desktop/sample_input_d.txt");
            readFile(file);
        }catch(FileNotFoundException fileNotFoundException){
            System.out.println("Input file for part 1 not found");
        }

        try {
            File file2 = new File("C:/Users/dell/Desktop/WIPRO_15days_data.csv");
            readFile2(file2);
        } catch (FileNotFoundException fileNotFoundException){
            System.out.println("Input file for part 2 not found");
        }

    }

    public static void readFile2(File file) throws FileNotFoundException {
        Scanner input = new Scanner(file);
        int j=0;
        while (input.hasNext()) {
            String s1 = input.nextLine();
            s1 = s1.replaceAll("[ ]", "");
            String strArray[] = s1.split("[,]");
            if(!strArray[0].equals("Ticker")){
                for(int i=0;i<6;i++){
                    fifteenDayDataArray[j][i] = Double.parseDouble(strArray[i+2]);
                }
                j++;
            }
        }
        System.out.println("\nINFY 15 Day Data:");
        softwareProgram.calculateAverage();
        softwareProgram.MaxReturnPotential();
        softwareProgram.maxDrawDown();
    }

    public static void readFile(File file) throws FileNotFoundException {
        Scanner input = new Scanner(file);
        while (input.hasNext()) {
            String s1 = input.nextLine();
            s1 = s1.replaceAll("[ ]","");
            String strArray[] = s1.split("[,:]");

            if(strArray[0].equals("Addscrip")){
                softwareProgram.addScrip(strArray[1],strArray[3],Double.parseDouble(strArray[5])
                        ,Double.parseDouble(strArray[7]),Double.parseDouble(strArray[9]),Double.parseDouble(strArray[11]));
            }
            else if(strArray[0].equals("Adduser")){
                if(s1.contains("{") ) {
                    int bOpen = s1.indexOf("{");
                    int bClose = s1.indexOf("}");
                    String strArrayHoldings = s1.substring(bOpen+1,bClose);
                    // String strArrayHoldings[] = s1.("{(.+?)}");
                    String strFunds[] = strArray[3].split("[a-z]");
                    softwareProgram.addUser(strArray[1], Double.parseDouble(strFunds[0]), strArrayHoldings);
                }
                else{
                    String strFunds[] = strArray[3].split("[a-z]");
                    softwareProgram.addUser(strArray[1],Double.parseDouble(strFunds[0]), "None");
                }
            }
            else if(strArray[0].equals("Placeorder")){ //String orderUser, String type, String scrip, int quantity,double rate
                String strArrayOrders[] = s1.split("[(,:)]");

                softwareProgram.placeOrder(strArrayOrders[2],strArrayOrders[4],strArrayOrders[6],
                        Integer.parseInt(strArrayOrders[8]), Double.parseDouble(strArrayOrders[10]));
            }
            else if(strArray[0].equals("ShowOrderbook")){
                softwareProgram.printOrderBook();
            }
            else if(strArray[0].equals("Execute")){
                softwareProgram.executeValidOrders();
            }
            else if(strArray[0].equals("Showsector")){
                softwareProgram.printAllScripsOf(strArray[1]);
            }
            else if(strArray[0].equals("Deletescrip")){
                softwareProgram.deleteScrip(strArray[1]);
            }
            else if(strArray[0].equals("DeleteUser")){
                softwareProgram.deleteUser(strArray[1]);
            }
            else if(strArray[0].equals("ShowScrips")){
                softwareProgram.printAllScrips();
            }
            else if(strArray[0].equals("ShowUsers")){
                softwareProgram.printAllUsers();
            }
            else if(strArray[0].equals("ShowOrders")){
                softwareProgram.showOpenOrdersOf(strArray[1]);
            }
        }
        input.close();
    }
}
