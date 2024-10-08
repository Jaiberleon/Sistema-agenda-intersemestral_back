package com.example.demo.repository;

import com.example.demo.model.AgendasValid;
import com.example.demo.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AgendasValidRepository extends JpaRepository<AgendasValid,Long> {

    @Query(value = "select * from agendas_valid where user_id =:userid  " , nativeQuery = true)
    List<AgendasValid> findAllByuser(@Param("userid") Long userId);

}
