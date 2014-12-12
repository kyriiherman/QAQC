//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package main.myatm;

public class ATM {
    private double moneyInATM;
    private Card card;

    ATM(double moneyInATM) {
        if (moneyInATM<0){
            throw new IllegalArgumentException();
        }else {
            this.moneyInATM = moneyInATM;
            this.card = null;
        }
    }

    public double getMoneyInATM() {
        return this.moneyInATM;
    }

    public boolean validateCard(Card card, int pinCode) {
        if(card == null) {
            throw new NullPointerException("Enter real card");
        } else if(card.isBlocked()) {
            return false;
        } else {
            this.card = card;
            return true;
        }
    }

    public double checkBalance() throws NoCardInserted {
        if(this.card == null) {
            throw new NoCardInserted();
        } else {
            return this.card.getAccount().getBalance();
        }
    }

    public double getCash(double amount) throws NoCardInserted, NotEnoughMoneyInAccount, NotEnoughMoneyInATM {
        if(this.card == null) {
            throw new NoCardInserted();
        } else {
            Account account = this.card.getAccount();
           // account.withdrow(amount);
          //  account.getBalance();
            if(account.getBalance() < amount) {
                throw new NotEnoughMoneyInAccount();
            } else if(this.getMoneyInATM() < amount) {
                throw new NotEnoughMoneyInATM();
            } else {
                this.moneyInATM -= account.withdrow(amount);
                return account.getBalance();
            }
        }
    }
}
