package bank.semicolon.data.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@RequiredArgsConstructor
@Document(collection = "role")
public class Role {
    @Getter
    @Id
    private int roleId;

    @NonNull
    @Setter
    @Getter
    private String roleName;


}
