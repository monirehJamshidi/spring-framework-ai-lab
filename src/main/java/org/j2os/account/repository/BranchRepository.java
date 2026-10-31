package org.j2os.account.repository;

import org.j2os.account.entity.Branch;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BranchRepository extends JpaRepository<Branch, UUID> {

    @Query("""
        select distinct b
       from Branch b
       left join fetch b.accounts
       """)
    List<Branch> findAllWithAccounts();

    @Override
    @EntityGraph(attributePaths = "accounts")
    List<Branch> findAll();
}
