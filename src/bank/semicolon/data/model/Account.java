package bank.semicolon.data.model;



import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.security.SecureRandom;



@Data
@Document
public class Account {


    private String accountPin;
    private  String emailAddress;
    @Getter
    @Id
    private  String accountNumber;
    private  BigDecimal balance;

    private  AccountType accountType;

    public Account(AccountType accountType, String emailAddress) {
        this.balance = new BigDecimal("0.0");
        this.accountType = accountType;
        this.accountNumber = "";
        this.emailAddress = emailAddress;
        this.accountPin = "0000";
    }



    public void generateAccountNumber(){
        SecureRandom random = new SecureRandom();
        Long value = random.nextLong(100_000_000_0L,999_999_999_9L);
        this.accountNumber = String.valueOf(value);
    }


    public String toString(User user){
        return String.format("""
                            Account-Name:  %s%5s
                            Account-Number: %s
                            Account-Type:  %s
                            Account Balance:  %s
                            """,user.getFirstName(),user.getLastName(),getAccountNumber(),getAccountType(),getBalance());
    }

    public void topAmount(BigDecimal amount){
        balance = balance.add(amount);
    }

    private int confirmBalance(BigDecimal amount){
        return (balance.compareTo(amount));
    }
    public BigDecimal transfer(BigDecimal amount){
        if (confirmBalance(amount) == 0 || confirmBalance(amount) == 1){
            balance = balance.subtract(amount);
            return amount;
        } return null;
    }

   public BigDecimal withdrawAmount(BigDecimal amount){
        if (confirmBalance(amount) == 0 || confirmBalance(amount) == 1){
            balance = balance.subtract(amount);
            return amount;
        }return null;
   }






}
