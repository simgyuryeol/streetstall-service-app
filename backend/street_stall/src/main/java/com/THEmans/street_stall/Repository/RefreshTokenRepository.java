package com.THEmans.street_stall.Repository;

import com.THEmans.street_stall.Jwt.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    public RefreshToken findByRefreshToken(String RefreshToken);
}
