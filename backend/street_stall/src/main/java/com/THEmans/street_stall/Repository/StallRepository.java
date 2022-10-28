package com.THEmans.street_stall.Repository;

import com.THEmans.street_stall.Domain.Stall;
import com.THEmans.street_stall.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StallRepository extends JpaRepository<Stall,Long> {
    public Stall findByStallid(User stallid);
}
