/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.helper;

import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.util.DBConnection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class GetUploadedFileDetailsDAO {
    
      private static Logger log = Logger.getLogger(GetUploadedFileDetailsDAO.class.getName());

    public ClientEngagementDTO fetchUploadedFileInfo(final String fileName, final int engmntCode)
            throws AppException {
        DBConnection connection = new DBConnection();
        Connection con = connection.getConnection();
        CallableStatement proc = null;
        ResultSet rs = null;
        ClientEngagementDTO clientEngmntDTO = new ClientEngagementDTO();
        String procName = "{CALL Get_UploadedFileSize(?,?)}";
        try {
            proc = (CallableStatement) con.prepareCall(procName);
            proc.setObject(1, fileName);
            proc.setObject(2, engmntCode);
            rs = proc.executeQuery();
            while(rs.next()){
                clientEngmntDTO.setUploadFileSize(Long.parseLong(rs.getString("FL_SZ") + ""));
                clientEngmntDTO.setFileStatusKey(Long.parseLong(rs.getString("FL_STS_KEY") + ""));
                clientEngmntDTO.setEngagementCode(rs.getString("CLNT_ENGMT_CD") + "");
                clientEngmntDTO.setSecurityServiceCode(rs.getString("SECUR_SRVC_CD") + "");
                clientEngmntDTO.setClientName(rs.getString("ORG_NM") + "");
            }
           
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.closeAll(rs, proc, con);
        }
         return clientEngmntDTO;
    }

    public int updateFileStatus(final String fileName, final String engmntCode, final int fileStatusKey, int fileID)
            throws AppException {
        DBConnection connection = new DBConnection();
        Connection con = connection.getConnection();
        CallableStatement proc = null;
         ResultSet rs = null;
        int retval = 0;
        String procName = "{CALL UpdateFileStatus(?,?)}";
        try {
            proc = (CallableStatement) con.prepareCall(procName);
            proc.setObject(1, fileID);
            proc.setObject(2, fileStatusKey);
          //  proc.setObject(3, fileStatusKey);
            //log.info("------"+fileName +"--"+ engmntCode+"--" +fileStatusKey);
            proc.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.closeAll(rs,proc, con);
        }
         return retval;
    }
    
   
    public List<ClientEngagementDTO> fetchScanFailedFilesInfo(final String timeUnits, final long timeInterval, final String flag)
            throws AppException {
        DBConnection connection = new DBConnection();
        Connection con = connection.getConnection();
        CallableStatement proc = null;
        ResultSet rs = null;
        List<ClientEngagementDTO> uploadedFilesList = new ArrayList<ClientEngagementDTO>();
        
        String procName = "{CALL Get_ScanFailedFiles(?,?,?)}";
        try {
            proc = (CallableStatement) con.prepareCall(procName);
            proc.setObject(1, timeUnits);
            proc.setObject(2, timeInterval);
            proc.setObject(3, flag);
            rs = proc.executeQuery();
            while(rs.next()){
//                A.ORG_KEY,ORG_NM,CLNT_ENGMT_CD,A.SECUR_SRVC_CD,A.UPLOAD_USER_ID,[dbo].[fnGetUserNameByID](A.UPLOAD_USER_ID) UploadedUser,
//                     FL_NM,FL_FLDR_PTH,FL_COMMT,FL_UPLOAD_DT,A.APPL_FL_UPLOAD_LOG_KEY,A.FL_STS_KEY,A.FL_SZ

                ClientEngagementDTO clientEngmntDTO = new ClientEngagementDTO();
                clientEngmntDTO.setClientID(Long.parseLong(rs.getString("ORG_KEY") + ""));
                clientEngmntDTO.setClientName(rs.getString("ORG_NM") + "");
                clientEngmntDTO.setEngagementCode(rs.getString("CLNT_ENGMT_CD") + "");
                clientEngmntDTO.setSecurityServiceCode(rs.getString("SECUR_SRVC_CD") + "");
                clientEngmntDTO.setCreatedByUserID(Long.parseLong(rs.getString("UPLOAD_USER_ID") + ""));
                clientEngmntDTO.setCreatedByUser(rs.getString("UploadedUser") + "");
                clientEngmntDTO.setAnalystEmailID(rs.getString("UploadedUserEmail") + "");
                clientEngmntDTO.setUploadFileName(rs.getString("FL_NM") + "");
                clientEngmntDTO.setUploadFilePath(rs.getString("FL_FLDR_PTH") + "");
                clientEngmntDTO.setUploadComments(rs.getString("FL_COMMT") + "");
                clientEngmntDTO.setCreatedDate(rs.getString("FL_UPLOAD_DT") + "");
                clientEngmntDTO.setFileUploadID(Long.parseLong(rs.getString("APPL_FL_UPLOAD_LOG_KEY") + ""));
                clientEngmntDTO.setFileStatusKey(Long.parseLong(rs.getString("FL_STS_KEY") + "")); 
                clientEngmntDTO.setUploadFileSize(Long.parseLong(rs.getString("FL_SZ") + ""));
               // clientEngmntDTO.setEngagementLeadClientEmailId(rs.getString("Engagement Lead Email"));
               // clientEngmntDTO.setEngagementLeadClientUserID(Long.parseLong(rs.getString("Engagement LeadID")));
                //clientEngmntDTO.setEngagementLeadClientUserName(rs.getString("Engagement Lead"));
                uploadedFilesList.add(clientEngmntDTO);
            }
           
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.closeAll(rs, proc, con);
        }
         return uploadedFilesList;
    }
    
    public void updateFileStatusToScanFailed(final String timeUnits, final long ecgTimeInterval, final String flag)
            throws AppException {
        DBConnection connection = new DBConnection();
        Connection con = connection.getConnection();
        CallableStatement proc = null;
         ResultSet rs = null;
        String procName = "{CALL UPDATE_ScanFailedStatus(?,?,?)}";
        try {
            proc = (CallableStatement) con.prepareCall(procName);
            proc.setObject(1, timeUnits);
            proc.setObject(2, ecgTimeInterval);
            proc.setObject(3, flag);
            proc.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.closeAll(rs,proc, con);
        }
    }
    
     public List<ClientEngagementDTO> fetchReportsScanFilesInfo(final String timeUnits, final long timeInterval, final String flag)
            throws AppException {
        DBConnection connection = new DBConnection();
        Connection con = connection.getConnection();
        CallableStatement proc = null;
        ResultSet rs = null;
        List<ClientEngagementDTO> uploadedFilesList = new ArrayList<ClientEngagementDTO>();

        String procName = "{CALL Get_ScanFailedFiles(?,?,?)}";
        try {
            proc = (CallableStatement) con.prepareCall(procName);
            proc.setObject(1, timeUnits);
            proc.setObject(2, timeInterval);
            proc.setObject(3, flag);
            rs = proc.executeQuery();
            while (rs.next()) {

                ClientEngagementDTO clientEngmntDTO = new ClientEngagementDTO();
                clientEngmntDTO.setClientID(Long.parseLong(rs.getString("ORG_KEY") + ""));
                clientEngmntDTO.setClientName(rs.getString("ORG_NM") + "");
                clientEngmntDTO.setEngagementCode(rs.getString("CLNT_ENGMT_CD") + "");
                clientEngmntDTO.setCreatedByUserID(Long.parseLong(rs.getString("CREAT_USER_ID") + ""));
                clientEngmntDTO.setCreatedByUser(rs.getString("UploadedUser") + "");
                clientEngmntDTO.setAnalystEmailID(rs.getString("UploadedUserEmail") + "");
                clientEngmntDTO.setUploadFileName(rs.getString("FL_NM") + "");
                clientEngmntDTO.setUploadFilePath(rs.getString("FL_FLDR_PTH") + "");
                clientEngmntDTO.setFileUploadID(Long.parseLong(rs.getString("RPT_FL_UPLOAD_LOG_KEY") + ""));
                clientEngmntDTO.setFileStatusKey(Long.parseLong(rs.getString("RPT_STS_KEY") + ""));
                clientEngmntDTO.setUploadFileSize(Long.parseLong(rs.getString("FL_SZ") + ""));
                clientEngmntDTO.setCreatedDate(rs.getString("CREAT_DT") + "");
               // clientEngmntDTO.setEngagementLeadClientEmailId(rs.getString("Engagement Lead Email"));
               // clientEngmntDTO.setEngagementLeadClientUserID(Long.parseLong(rs.getString("Engagement LeadID")));
               // clientEngmntDTO.setEngagementLeadClientUserName(rs.getString("Engagement Lead"));
               

                uploadedFilesList.add(clientEngmntDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.closeAll(rs, proc, con);
        }
        return uploadedFilesList;
    }
    
     public int updateReportsFileStatus(final String fileID, final int fileStatusKey)
            throws AppException {
        DBConnection connection = new DBConnection();
        Connection con = connection.getConnection();
        CallableStatement proc = null;
        ResultSet rs = null;
        int retval = 0;
        String procName = "{CALL Report_UpdateFileStatus(?,?)}";
        try {
            proc = (CallableStatement) con.prepareCall(procName);
            proc.setObject(1, fileID);
            proc.setObject(2, fileStatusKey);
            //log.info("------" + fileID + "--" + fileStatusKey );
            proc.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.closeAll(rs, proc, con);
        }
        return retval;
    }
     
    public ClientEngagementDTO fetchReportsUploadedFileInfo(final String fileID)
            throws AppException {
        DBConnection connection = new DBConnection();
        Connection con = connection.getConnection();
        CallableStatement proc = null;
        ResultSet rs = null;
        ClientEngagementDTO clientEngmntDTO = new ClientEngagementDTO();
        String procName = "{CALL Report_ListReportUploadlogByCode(?)}";
        try {
            proc = (CallableStatement) con.prepareCall(procName);
            proc.setObject(1, fileID);
            rs = proc.executeQuery();
            while(rs.next()){
                clientEngmntDTO.setFileUploadID(Long.parseLong(rs.getString("RPT_FL_UPLOAD_LOG_KEY") + ""));
                clientEngmntDTO.setUploadFileName(rs.getString("FL_NM") + "");
                clientEngmntDTO.setUploadFilePath(rs.getString("FL_FLDR_PTH") + "");
                clientEngmntDTO.setUploadFileSize(Long.parseLong(rs.getString("FL_SZ") + ""));
                clientEngmntDTO.setFileStatusKey(Long.parseLong(rs.getString("RPT_STS_KEY") + ""));
//                clientEngmntDTO.setEngagementCode(rs.getString("CLNT_ENGMT_CD") + "");
//                clientEngmntDTO.setClientName(rs.getString("ORG_NM") + "");
            }
//           A.RPT_FL_UPLOAD_LOG_KEY,B.RPT_NM,A.CREAT_DT UpdatedOn,D.LKP_ENTY_NM, A.RPT_STS_KEY,A.UPDT_USER_ID,A.FL_NM,A.FL_FLDR_PTH,A.FL_SZ

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.closeAll(rs, proc, con);
        }
         return clientEngmntDTO;
    } 
    
//    A.RPT_FL_UPLOAD_LOG_KEY,B.RPT_NM,A.CREAT_DT UpdatedOn,D.LKP_ENTY_NM, A.RPT_STS_KEY,A.UPDT_USER_ID,A.FL_NM,A.FL_FLDR_PTH,A.FL_SZ

}
