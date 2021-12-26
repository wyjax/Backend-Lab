package com.wyjax.querydsl.store.repository;

import com.wyjax.querydsl.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreRepository extends JpaRepository<Store, Long>, JpaSpecificationExecutor<Store> {

    @Query("select s from Store s where s.name = :name")
    Store findStore(@Param("name") String name);
}