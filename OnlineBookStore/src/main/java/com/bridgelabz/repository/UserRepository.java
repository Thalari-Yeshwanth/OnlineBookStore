package com.bridgelabz.repository;

import com.bridgelabz.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Long> {

    @Query(value="SELECT * FROM onlinebookstore.user where email_id=:emailId",nativeQuery=true)
    Optional<UserModel> findByEmail(String emailId);

}
