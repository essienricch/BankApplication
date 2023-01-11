package bank.semicolon.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Admin {

    @Id
    private String adminId;
    private String adminName;
    private String password;


}
