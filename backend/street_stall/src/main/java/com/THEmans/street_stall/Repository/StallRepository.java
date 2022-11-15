package com.THEmans.street_stall.Repository;

import com.THEmans.street_stall.Domain.Stall;
import com.THEmans.street_stall.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import java.util.List;
import java.util.Optional;

public interface StallRepository extends JpaRepository<Stall,Long> {
    @Nullable
    Optional<Stall> findByUser_Userid(String user);
    Stall findByUser(String stallid);
    List<Stall> findAllBystallnameLike(String stallname);
}
