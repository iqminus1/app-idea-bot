package uz.pdp.appideabot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appideabot.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}