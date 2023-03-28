package com.example.demo.club.repository;

import com.example.demo.club.domain.Cd;
import com.example.demo.club.repository.custom.CdRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CdRepository extends JpaRepository<Cd,Long>, CdRepositoryCustom {
}
