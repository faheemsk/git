/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.util;

import com.optum.oss.exception.AppException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 *
 * @author mrejeti
 */
public class SessionTimeOutDAO {

    public void inValidateUsersessionInfo(final long userID, final String dbFlag, final String sessionID)
            throws AppException {
        DBConnection connection = new DBConnection();
        Connection con = connection.getConnection();
        CallableStatement proc = null;
        ResultSet rs = null;
        String procName = "{CALL UPDATE_USER_SESS_LOG(?,?,?,?)}";
        try {
            proc = (CallableStatement) con.prepareCall(procName);
            proc.setObject(1, dbFlag);
            proc.setObject(2, userID);
            proc.setObject(3, "");
            proc.setObject(4, sessionID);
            proc.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            connection.closeAll(rs, proc, con);
        }
    }
}
