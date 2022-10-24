package com.THEmans.street_stall.Repository;

import com.THEmans.street_stall.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
    public User findByUserid(String userid);
}
