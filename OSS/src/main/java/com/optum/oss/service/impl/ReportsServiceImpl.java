/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service.impl;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dao.impl.ManageEngagementsDAOImpl;
import com.optum.oss.dto.ClientEngagementDTO;
import com.optum.oss.dto.ClientReportsUploadDTO;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.helper.ClientEngagementMailHelper;
import com.optum.oss.service.ReportsService;
import com.optum.oss.transact.dao.impl.ReportsDAOImpl;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mrejeti
 */
@Service
public class ReportsServiceImpl implements ReportsService {

    @Autowired
    private ReportsDAOImpl reportsDAO;
    
    @Autowired
    private ClientEngagementMailHelper clientEngagementMailHelper;
    
    @Autowired
    private ManageEngagementsDAOImpl manageEngagementsDAO;
    
    /*
     End: Autowiring the required Class instances
     */

    /**
     * This method for fetch Analyst Validation work list
     *
     * @param userDto
     * @param clientReportsUploadDTO
     * @return Map
     * @throws AppException
     */

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public Map fetchClientReportsUploadWorkList(UserProfileDTO userDto, ClientReportsUploadDTO clientReportsUploadDTO) throws AppException {
        List<ClientReportsUploadDTO> list = reportsDAO.fetchClientReportsUploadWorkList(userDto, clientReportsUploadDTO);

        return engagementServicesListToMapConversion(list);
    }

    public Map engagementServicesListToMapConversion(List<ClientReportsUploadDTO> clientReportsDto) {

        Map<String, LinkedHashSet<String>> engagementMap = new LinkedHashMap<>();
        Map<String, ArrayList<String>> serviceCountMap = new LinkedHashMap<>();
        Map<String, ArrayList<ClientReportsUploadDTO>> serviceMap = new LinkedHashMap<>();

        /*Start engagement Map Construction*/
        for (ClientReportsUploadDTO clientReports : clientReportsDto) {

            if (engagementMap.containsKey(clientReports.getOrganizationKey() + ApplicationConstants.SYMBOL_SPLITING
                    + clientReports.getOrganizationName())) {

                LinkedHashSet<String> engagementSet = engagementMap.get(clientReports.getOrganizationKey()
                        + ApplicationConstants.SYMBOL_SPLITING + clientReports.getOrganizationName());
                engagementSet.add(clientReports.getClientEngagementCode() + ApplicationConstants.SYMBOL_SPLITING
                        + clientReports.getClientEngagementName());
                /* services Count for Each Organization */
                ArrayList<String> serviceCount = serviceCountMap.get(clientReports.getOrganizationKey()
                        + ApplicationConstants.SYMBOL_SPLITING + clientReports.getOrganizationName());
                serviceCount.add(clientReports.getClientEngagementCode());

            } else {
                LinkedHashSet<String> engagementSet = new LinkedHashSet<>();
                engagementSet.add(clientReports.getClientEngagementCode() + ApplicationConstants.SYMBOL_SPLITING
                        + clientReports.getClientEngagementName());

                ArrayList<String> serviceCount = new ArrayList<>();
                serviceCount.add(clientReports.getClientEngagementCode());

                engagementMap.put(clientReports.getOrganizationKey() + ApplicationConstants.SYMBOL_SPLITING
                        + clientReports.getOrganizationName(), engagementSet);
                serviceCountMap.put(clientReports.getOrganizationKey() + ApplicationConstants.SYMBOL_SPLITING
                        + clientReports.getOrganizationName(), serviceCount);
            }

        }
        /*End engagement Map Construction*/

        /*Start Engagement Service Map Construction*/
        for (ClientReportsUploadDTO manageServiceUserDTO : clientReportsDto) {

            if (serviceMap.containsKey(manageServiceUserDTO.getClientEngagementCode() + ApplicationConstants.SYMBOL_SPLITING
                    + manageServiceUserDTO.getClientEngagementName())) {

                ArrayList<ClientReportsUploadDTO> serviceList = serviceMap.get(manageServiceUserDTO.getClientEngagementCode()
                        + ApplicationConstants.SYMBOL_SPLITING + manageServiceUserDTO.getClientEngagementName());
                serviceList.add(manageServiceUserDTO);

            } else {
                ArrayList<ClientReportsUploadDTO> serviceList = new ArrayList<>();

                serviceList.add(manageServiceUserDTO);
                serviceMap.put(manageServiceUserDTO.getClientEngagementCode() + ApplicationConstants.SYMBOL_SPLITING
                        + manageServiceUserDTO.getClientEngagementName(), serviceList);
            }
        }
        /*End Engagement Service Map Construction*/
        Map map = new LinkedHashMap();
        map.put("engagementMap", engagementMap);
        map.put("serviceMap", serviceMap);
        map.put("organizationServieCount", serviceCountMap);
        // logger.info("End: AnalystValidationServiceImpl: engagementServicesListToMapConversion() to convert list to map");
        return map;
    }

    @Override
    public int saveUploadReportFile(ClientReportsUploadDTO saveReports, UserProfileDTO userDto) throws AppException {

        return reportsDAO.saveUploadReportFile(saveReports, userDto);

    }

    @Override
    public List<MasterLookUpDTO> fetchReportNames(String engagementCode) throws AppException {
        return reportsDAO.fetchReportNames(engagementCode);
    }

    @Override
    public List<ClientReportsUploadDTO> fetchUploadFileList(String engagementCode) throws AppException {
        return reportsDAO.fetchUploadFileList(engagementCode);

    }

    @Override
    public int deleteUploadedFileDetails(long uploadFilekey) throws AppException {
        return reportsDAO.deleteUploadedFileDetails(uploadFilekey);
    }

//    @Override
//    public int updatesaveReportFile(ClientReportsUploadDTO saveReports, UserProfileDTO userDto) throws AppException {
//
//        return reportsDAO.updateSaveReportFile(saveReports, userDto);
//    }
    @Override
    public int publishReportFile(ClientReportsUploadDTO saveReports, UserProfileDTO userDto) throws AppException {
        List<ClientReportsUploadDTO> reportsList = this.convertingStringDTO(saveReports);
        return reportsDAO.publishReportFile(reportsList, userDto);

    }

    /**
     * This method for converting string data to DTO
     *
     * @param permissionData
     * @return List<PermissionDTO>
     * @throws AppException
     */
    private List<ClientReportsUploadDTO> convertingStringDTO(ClientReportsUploadDTO saveReports) throws AppException {
        List<ClientReportsUploadDTO> permissionList = new ArrayList<>();
        try {
            if (null == saveReports.getReportNameCheckId() && !StringUtils.isEmpty(saveReports.getStatus())) {
                String reportCheckIds[] = new String[]{saveReports.getStatus().replaceAll(",", "")};
                saveReports.setReportNameCheckId(reportCheckIds);
            }
            if (null != saveReports.getReportNameCheckId()) {
                for (String s : saveReports.getReportNameCheckId()) {
                    if (!StringUtils.isEmpty(s)) {
                        String[] permission = s.split("@");
                        if (null != permission && 6 == permission.length) {
                            ClientReportsUploadDTO clientReportsUploadDTO = new ClientReportsUploadDTO();
                            clientReportsUploadDTO.setUploadFileKey(Long.parseLong(permission[0]));
                            clientReportsUploadDTO.setRowStatusKey(Long.parseLong(permission[1]));
                            clientReportsUploadDTO.setClientEngagementCode(saveReports.getEncClientEngagementCode());
                            clientReportsUploadDTO.setReportName(permission[2]);
                            clientReportsUploadDTO.setFileName(permission[3]);
                            clientReportsUploadDTO.setFilePath(permission[4]);
                            if (permission[5].equalsIgnoreCase(ApplicationConstants.DB_CONSTANT_FILE_STATUS_KEY_NEW.toString())) {
                                clientReportsUploadDTO.setStatus(ApplicationConstants.DB_ROW_STATUS_PUBLISHED_VALUE.toString());
                            } else if (permission[5].equalsIgnoreCase(ApplicationConstants.DB_ROW_STATUS_PUBLISHED_VALUE.toString())) {
                                clientReportsUploadDTO.setStatus(ApplicationConstants.DB_ROW_STATUS_NOTPUBLISHED_VALUE.toString());
                            } else if (permission[5].equalsIgnoreCase(ApplicationConstants.DB_ROW_STATUS_NOTPUBLISHED_VALUE.toString())) {
                                clientReportsUploadDTO.setStatus(ApplicationConstants.DB_ROW_STATUS_PUBLISHED_VALUE.toString());
                            }
                            permissionList.add(clientReportsUploadDTO);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "convertingStringDTO() : " + e.getMessage());
        }
        return permissionList;
    }
    
    /**
     * This method for update engagement status to publish
     *
     * @param engKey
     * @param engStatusKey
     * @return int
     * @throws AppException
     */
    @Override
    public int updateEngagmentStatusToPublish(final String engKey, final long engStatusKey) throws AppException {
        int retVal = 0;
        retVal = reportsDAO.updateEngagmentStatusToPublish(engKey, engStatusKey);
        if (retVal > 0) {
            // SEND EMAIL TO PARTNER AND CLIENT ENGAGMENT LEAD S OF FINAL REPORT PUBLICATION
            ClientEngagementDTO engagementDto = new ClientEngagementDTO();
            engagementDto.setEngagementCode(engKey);
            //engagementDto = manageEngagementsDAO.getClientEngagementAssignedUserByEngmtKey(engagementDto); have to change again
            clientEngagementMailHelper.mailToClientEngagementLeadOfEngagementPublish(engagementDto);
            clientEngagementMailHelper.mailToPartnerEngagementLeadOfEngagementPublish(engagementDto, engagementDto.getEngagementPartnerUserLi());
        }
        return retVal;
    }
    
    /**
     * This method for search / filter Client Reports Upload work list
     *
     * @param userDto
     * @param clientReportsUploadDTO
     * @return Map
     * @throws AppException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public Map searchClientReportsUploadWorkList(UserProfileDTO userDto, ClientReportsUploadDTO clientReportsUploadDTO) throws AppException {
        
        if (null == clientReportsUploadDTO.getOrganizationName()) {
            clientReportsUploadDTO.setOrganizationName("");
        }
        if (null == clientReportsUploadDTO.getClientEngagementName()) {
            clientReportsUploadDTO.setClientEngagementName("");
        }
        if (null == clientReportsUploadDTO.getClientEngagementCode()) {
            clientReportsUploadDTO.setClientEngagementCode("");
        }
        if (null == clientReportsUploadDTO.getReportName()) {
            clientReportsUploadDTO.setReportName("");
        }
        if (null == clientReportsUploadDTO.getFileName()) {
            clientReportsUploadDTO.setFileName("");
        }
        if (null == clientReportsUploadDTO.getUpdateDate()) {
            clientReportsUploadDTO.setUpdateDate("");
        }
        if (null == clientReportsUploadDTO.getStatus()) {
            clientReportsUploadDTO.setStatus("");
        }
        List<ClientReportsUploadDTO> list = reportsDAO.searchClientReportsUploadWorkList(userDto, clientReportsUploadDTO);
        return engagementServicesListToMapConversion(list);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class},
            isolation = Isolation.DEFAULT)
    public int validateDuplicateFileName(String fileName, long reportkey, String engCode) throws AppException {
        return reportsDAO.validateDuplicateFileName(fileName, reportkey,engCode);
    }
}

