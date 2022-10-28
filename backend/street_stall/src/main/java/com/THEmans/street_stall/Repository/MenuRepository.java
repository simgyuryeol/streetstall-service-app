package com.THEmans.street_stall.Repository;

import com.THEmans.street_stall.Domain.Menu;
import com.THEmans.street_stall.Domain.MenuID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, MenuID> {
}
