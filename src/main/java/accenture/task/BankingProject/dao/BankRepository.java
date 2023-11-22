package accenture.task.BankingProject.dao;

import accenture.task.BankingProject.model.Bank;
import accenture.task.BankingProject.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

}
