/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

/**
 *
 * @author cnaidu
 */
public class DBConnection {

    private static final Logger log = Logger.getLogger(DBConnection.class);

    public Connection getConnection() {
        Connection con = null;
        try {
            InitialContext context = new InitialContext();
            DataSource ds = (DataSource) context.lookup("jdbc/ossDS");
            con = ds.getConnection();
            if (!con.isClosed()) {
                log.info("Successfully connected to MySQL server using TCP/IP...");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    public void closeAll(ResultSet rs, CallableStatement cstmt, Connection con) {
        try {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (cstmt != null) {
                cstmt.close();
                cstmt = null;
            }
            if (con != null) {
                con.close();
                con = null;
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

    public void closePreparedStmt(ResultSet rs, PreparedStatement pStmt, Connection con) {
        try {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (pStmt != null) {
                pStmt.close();
                pStmt = null;
            }
            if (con != null) {
                con.close();
                con = null;
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

    public void closeResultset(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
                rs = null;
            }
        } catch (Exception e) {
            log.error(e);
        }

    }

    public void closeCallableStatement(CallableStatement cstmt) {
        try {
            if (cstmt != null) {
                cstmt.close();
                cstmt = null;
            }
        } catch (Exception e) {
            log.error(e);

        }

    }
}
