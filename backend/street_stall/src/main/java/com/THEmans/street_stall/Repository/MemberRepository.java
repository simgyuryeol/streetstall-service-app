package com.THEmans.street_stall.Repository;

import com.THEmans.street_stall.Domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
