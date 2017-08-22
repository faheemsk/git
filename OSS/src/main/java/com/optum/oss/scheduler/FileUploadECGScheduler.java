/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.scheduler;

import com.optum.oss.helper.GetUploadedFileDetailsDAO;
import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.helper.EngagementDataUploadMailHelper;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author akeshavulu
 */
@Component
public class FileUploadECGScheduler {

    private static final Logger logger = Logger.getLogger(FileUploadECGScheduler.class);
    
    private static final FileUploadECGScheduler lockObj = new FileUploadECGScheduler();

    /*
     Start : Autowiring of Classes
     */
    @Autowired
    private EngagementDataUploadMailHelper engmentDataUploadMailHelper;

    /*
     End : Autowiring of Classes
     */
    // @Scheduled(cron = "0 0 0/2 * * *")//runs at every couple of hours
    @Scheduled(cron = "0 */3 * * * *")//runs at every 10 mins
    public void checkFileStatus() {

        if (System.getProperty("ECG_UPLOAD") != null && "Yes".equalsIgnoreCase(System.getProperty("ECG_UPLOAD"))) {
            long ecgTimeIntvl = 0;
            String timeUnits = "";
            String flag = "D";
            GetUploadedFileDetailsDAO uploadedFileDAO = new GetUploadedFileDetailsDAO();
            if (System.getProperty("ECG_SCAN_FAILED_TIME_INTERVAL") != null) {
                ecgTimeIntvl = Long.parseLong(System.getProperty("ECG_SCAN_FAILED_TIME_INTERVAL") + "");
            }
            if (System.getProperty("ECGUPLOAD_Time_UNITS") != null) {
                timeUnits = System.getProperty("ECGUPLOAD_Time_UNITS") + "";
            }
            List<ClientEngagementDTO> uploadedFilesList = new ArrayList<ClientEngagementDTO>();
            try {

                synchronized (lockObj) {
                    //DB call to fetch all files with status as scan failed.
                    uploadedFilesList = uploadedFileDAO.fetchScanFailedFilesInfo(timeUnits, ecgTimeIntvl, flag);

                    //Mail to user who upload malicious file
                    if (uploadedFilesList != null) {
                        for (ClientEngagementDTO scanFailedFilesDTO : uploadedFilesList) {
                            engmentDataUploadMailHelper.mailToAnalystOnMaliciousFileUpload(scanFailedFilesDTO);
                        }
                    }
                    //DB call to change file status to scan failed.
                    uploadedFileDAO.updateFileStatusToScanFailed(timeUnits, ecgTimeIntvl, flag);
                }
            } catch (Exception e) {
                logger.error("Exception occurred : FileUploadECGScheduler:" + e.getMessage());
            }
        }

    }
}
