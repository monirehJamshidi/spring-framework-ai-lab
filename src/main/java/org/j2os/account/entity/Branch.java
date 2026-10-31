package org.j2os.account.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "branch")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID branchId;

    private String branchName;

    private String branchAddress;

    @OneToMany(
            mappedBy = "branch",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REMOVE
            },
            orphanRemoval = true
    )
    @Builder.Default
    private List<Account> accounts = new ArrayList<>();
}
