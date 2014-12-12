package main.myatm;

public interface Card {

    public boolean isBlocked();

    public Account getAccount();

    public boolean checkPin(int pinCode);    
}
