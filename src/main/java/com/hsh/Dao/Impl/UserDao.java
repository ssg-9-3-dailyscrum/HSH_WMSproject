package com.hsh.member.dao; // 올바른 member 모듈의 dao 패키지 경로

import com.hsh.domain.vo.UserVo; // User 테이블의 데이터 객체
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserDAO {

    int register(Connection conn, UserVo user) throws SQLException;

    int update(Connection conn, UserVo user) throws SQLException;


    int delete(Connection conn, Long memberId) throws SQLException; // 실제 구현은 status='N'으로 UPDATE


    UserVo findById(Connection conn, Long memberId) throws SQLException;


    List<UserVo> findAll(Connection conn) throws SQLException;


    List<UserVo> findByRole(Connection conn, String role) throws SQLException;


    int updateStatus(Connection conn, Long memberId, String status) throws SQLException;
