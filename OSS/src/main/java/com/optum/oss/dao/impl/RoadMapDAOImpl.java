/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.optum.oss.dao.impl;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dao.RoadMapDAO;
import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.dto.RoadMapDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.model.FindingsModel;
import com.optum.oss.model.SecurityModel;
import com.optum.oss.util.CommonUtil;
import com.optum.oss.util.DateUtil;
import com.optum.oss.util.EncryptDecrypt;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author sbhagavatula
 */
public class RoadMapDAOImpl implements RoadMapDAO{
    
    public static final Logger logger = Logger.getLogger(RoadMapDAOImpl.class);
    
    private JdbcTemplate jdbcTemplate;
    /*
     Start : Setter getters for private variables
     */

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    /*
     End : Setter getters for private variables
     */

    @Autowired
    private DateUtil dateUtil;
    
    @Autowired
    private EncryptDecrypt encryptDecrypt;
    
    /**
     * This methos is used to fetch roadmap categories.
     * It is used to display roadmap categories list.
     * It is used to fetch details of a specific roadmap category for editing roadmap category
     * 
     * @param roadMapDTO
     * @return
     * @throws AppException
     */
    @Override
    public List<RoadMapDTO> fetchRoadmapCategory(RoadMapDTO roadMapDTO) throws AppException {
        logger.info("Start: RoadMapDAOImpl : fetchRoadmapCategory");
        List<RoadMapDTO> roadMapCatList = null;
        try{
            roadMapCatList = new ArrayList<>();
            
            String proc = "{CALL RoadMap_CategoryByEngmtCD(?,?,?,?)}";
            List<Map<String, Object>> categoryMap = jdbcTemplate.queryForList(proc,
                        new Object[]{roadMapDTO.getClientEngagementDTO().getEngagementCode(),
                            roadMapDTO.getOrgSchema(),
                            roadMapDTO.getCsaDomainCode(),
                            roadMapDTO.getRootCauseCode()});
            for(Map<String,Object> roadmapCategory : categoryMap){
                RoadMapDTO roadMapDto = new RoadMapDTO();
                roadMapDto.setCsaDomainName(roadmapCategory.get("CSA_DOM_NM")+"");
                roadMapDto.setCsaDomainCode(roadmapCategory.get("CSA_DOM_CD")+"");
                roadMapDto.setRootCauseName(roadmapCategory.get("VULN_CATGY_NM")+"");
                roadMapDto.setRootCauseCode(roadmapCategory.get("VULN_CATGY_CD")+"");
                roadMapDto.getCveInformationDTO().setSeverityName(roadmapCategory.get("VULN_SEV_NM")+"");
                roadMapDto.getCveInformationDTO().setCostEffortName(roadmapCategory.get("RMDTN_CST_EFFRT_NM")+"");
                roadMapDto.getCveInformationDTO().setCostEffortCode(roadmapCategory.get("RMDTN_CST_EFFRT_CD")+"");
                roadMapDto.setTimeLineName(roadmapCategory.get("ROADMAP_TMLN_NM")+"");
                roadMapDto.setTimeLineCode(roadmapCategory.get("ROADMAP_TMLN_CD")+"");
                roadMapDto.getClientEngagementDTO().setPublishedDate(dateUtil.dateConvertionFromDBToJSP(roadmapCategory.get("ROADMAP_PUBL_DT")+" 00:00:00"));
                roadMapDto.setEffectiveDate(dateUtil.dateConvertionFromDBToJSP(roadmapCategory.get("ROADMAP_EFF_DT")+" 00:00:00"));
                roadMapDto.setComments(roadmapCategory.get("ROADMAP_COMMT") == null?"":roadmapCategory.get("ROADMAP_COMMT")+"");
                roadMapCatList.add(roadMapDto);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : fetchRoadmapCategory : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "fetchRoadmapCategory() :: " + e.getMessage());
        }
        logger.info("End: RoadMapDAOImpl : fetchRoadmapCategory");
        return roadMapCatList;
    }

    /**
     * This method is used to generate roadmap categories
     * 
     * @param roadMapObj
     * @return
     * @throws AppException
     */
    @Override
    public List<RoadMapDTO> generateRoadmapCategory(RoadMapDTO roadMapObj) throws AppException {
        logger.info("Start: RoadMapDAOImpl : generateRoadmapCategory");
        List<RoadMapDTO> roadMapCatList = null;
        try{
            roadMapCatList = new ArrayList<>();
            
            String proc = "{CALL RoadMap_VulnerabilityCategory(?,?)}";
            List<Map<String, Object>> categoryMap = jdbcTemplate.queryForList(proc,
                        new Object[]{roadMapObj.getClientEngagementDTO().getEngagementCode(),roadMapObj.getOrgSchema()});
            for(Map<String,Object> roadmapCategory : categoryMap){
                RoadMapDTO roadMapDto = new RoadMapDTO();
                roadMapDto.setCsaDomainCode(roadmapCategory.get("CSA_DOM_CD")+"");
                roadMapDto.setRootCauseName(roadmapCategory.get("VULN_CATGY_NM")+"");
                roadMapDto.setRootCauseCode(roadmapCategory.get("VULN_CATGY_CD")+"");
                roadMapDto.getCveInformationDTO().setSeverityName(roadmapCategory.get("VULN_SEV_NM")+"");
                roadMapDto.getCveInformationDTO().setSeverityCode(roadmapCategory.get("VULN_SEV_CD")+"");
                roadMapDto.getCveInformationDTO().setCostEffortCode(roadmapCategory.get("RMDTN_CST_EFFRT_CD")+"");
                roadMapDto.getCveInformationDTO().setCostEffortName(roadmapCategory.get("RMDTN_CST_EFFRT_NM")+"");
                roadMapDto.setTimeLineCode(roadmapCategory.get("ROADMAP_TMLN_CD")+"");
                roadMapDto.setTimeLineName(roadmapCategory.get("ROADMAP_TMLN_NM")+"");
                
                if(roadmapCategory.get("CLNT_PUBL_DT") != null){
                    Date pubDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(roadmapCategory.get("CLNT_PUBL_DT")+"");
                    roadMapDto.getClientEngagementDTO().setPublishedDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(pubDate));
                }else{
                    roadMapDto.getClientEngagementDTO().setPublishedDate(null);
                }
                
                if(roadmapCategory.get("EffectiveDate") != null){
                    Date effDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(roadmapCategory.get("EffectiveDate")+"");
                    roadMapDto.setEffectiveDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(effDate));
                }else{
                    roadMapDto.setEffectiveDate(null);
                }
                
                roadMapDto.getClientEngagementDTO().setEngagementCode(roadmapCategory.get("CLNT_ENGMT_CD")+"");
                roadMapDto.setVulCount(Integer.parseInt(roadmapCategory.get("VULCOUNT")+""));
                roadMapDto.setOrgSchema(roadMapObj.getOrgSchema());
                roadMapDto.getClientEngagementDTO().setClientID(roadMapObj.getClientEngagementDTO().getClientID());
                roadMapCatList.add(roadMapDto);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : generateRoadmapCategory : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "generateRoadmapCategory() :: " + e.getMessage());
        }
        logger.info("End: RoadMapDAOImpl : generateRoadmapCategory");
        return roadMapCatList;
    }

    /**
     * This method is used to insert/update/delete roadmap category
     * 
     * @param roadmapList
     * @param userDto
     * @param flag
     * @return
     * @throws AppException
     */
    @Override
    public int saveUpdateRoadmapCategory(List<RoadMapDTO> roadmapList, UserProfileDTO userDto,
            String flag) throws AppException {
        logger.info("Start: RoadMapDAOImpl : saveRoadmapCategory");
        int retVal = 0;
        try {
            String saveRoadmapProc = "{CALL RoadMap_INS_CLNT_ROADMAP_FACT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";

            for (RoadMapDTO roadMapObj : roadmapList) {
                retVal = jdbcTemplate.queryForObject(saveRoadmapProc,
                        new Object[]{
                            ApplicationConstants.DB_ROW_STATUS_ACTIVE_VALUE,
                            roadMapObj.getClientEngagementDTO().getClientID(),
                            roadMapObj.getClientEngagementDTO().getEngagementCode(),
                            roadMapObj.getCsaDomainCode(),
                            roadMapObj.getRootCauseCode(),
                            roadMapObj.getCveInformationDTO().getSeverityCode(),
                            roadMapObj.getCveInformationDTO().getCostEffortCode(),
                            roadMapObj.getTimeLineCode(),
                            roadMapObj.getEffectiveDate(),
                            roadMapObj.getClientEngagementDTO().getPublishedDate(),
                            roadMapObj.getVulCount(),
                            roadMapObj.getComments(),
                            userDto.getUserProfileKey(),
                            flag,
                            roadMapObj.getOrgSchema()
                        },
                        Integer.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : saveRoadmapCategory : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "saveRoadmapCategory() :: " + e.getMessage());
        }
        logger.info("End: RoadMapDAOImpl : saveRoadmapCategory");
        return retVal;
    }
    
    /**
     * Timeline list master data
     * @return
     * @throws AppException
     */
    @Override
    public List<MasterLookUpDTO> timeLineList() throws AppException {
        logger.info("Start: RoadMapDAOImpl : timeLineList");
        List<MasterLookUpDTO> costEffortList = null;
        try {
            String timeLineProc = "{CALL RoadMap_ListTimeline()}";
            List<Map<String, Object>> costEffortMap = jdbcTemplate.queryForList(timeLineProc);
            costEffortList = new ArrayList<>(costEffortMap.size());

            for (Map<String, Object> costEffort : costEffortMap) {
                MasterLookUpDTO masterLookUpDTO = new MasterLookUpDTO();
                masterLookUpDTO.setMasterLookupCode((String) costEffort.get("ROADMAP_TMLN_CD"));
                masterLookUpDTO.setLookUpEntityName((String) costEffort.get("ROADMAP_TMLN_NM"));
                costEffortList.add(masterLookUpDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : timeLineList : " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "timeLineList() :: " + e.getMessage());
        }
        logger.info("End: RoadMapDAOImpl : timeLineList");
        return costEffortList;
    }
    
    @Override
    public List<FindingsModel> getFindingsByCategory(SecurityModel securityModel) throws AppException {
        logger.info("Start: RoadMapDAOImpl: getFindingsByCategory()");
        List<FindingsModel> findingList = null;
        String findingIdProcName = "{CALL Report_FindingByCategory(?,?,?)}";
        int sNo = 1;
        try {
            List<Map<String, Object>> resultList = null;
                resultList = jdbcTemplate.queryForList(findingIdProcName, new Object[]{encryptDecrypt.decrypt(securityModel.getEngagementsDTO().getEngagementKey()), securityModel.getCategoryCode(),securityModel.getEngSchemaName()});
            if (null != resultList && resultList.size() > 0) {
                findingList = new ArrayList<>(resultList.size());
                for (Map<String, Object> resultMap : resultList) {
                    FindingsModel findingsModel = new FindingsModel();
                        findingsModel.setId(CommonUtil.replaceNullWithEmpty(resultMap.get("CLNT_VULN_INSTC_KEY")));
//                        findingsModel.setId(CommonUtil.replaceNullWithEmpty(resultMap.get("ID")));
                         findingsModel.setRecommendation(CommonUtil.removeHtmlTagsFromString(resultMap.get("RECOM_COMMT_TXT")));
                    
                    findingsModel.setFinding(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_NM")));
                    findingsModel.setRisklevel(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_SEV_NM")));
                    findingsModel.setRootCauseDetail(CommonUtil.replaceNullWithEmpty(resultMap.get("VULN_CATGY_NM")));
                    findingsModel.setDescription(CommonUtil.removeHtmlTagsFromString(resultMap.get("VULN_DESC")));
                    findingsModel.setHitrust(CommonUtil.replaceCommaByCommaAndSpace(resultMap.get("HITRUST")));
                    findingsModel.setHipaa(CommonUtil.replaceCommaByCommaAndSpace(resultMap.get("HIPAA")));
                    findingsModel.setNist(CommonUtil.replaceCommaByCommaAndSpace(resultMap.get("NIST")));
                    findingsModel.setIrs(CommonUtil.replaceCommaByCommaAndSpace(resultMap.get("IRS")));
                    findingsModel.setMars(CommonUtil.replaceCommaByCommaAndSpace(resultMap.get("MARSE")));
                    findingsModel.setSox(CommonUtil.replaceCommaByCommaAndSpace(resultMap.get("SOC2")));
                    findingsModel.setIso(CommonUtil.replaceCommaByCommaAndSpace(resultMap.get("ISO")));
                    findingsModel.setPci(CommonUtil.replaceCommaByCommaAndSpace(resultMap.get("PCIDSS")));
                    findingsModel.setCsa(CommonUtil.replaceCommaByCommaAndSpace(resultMap.get("CSACCM")));
                    findingsModel.setFisma(CommonUtil.replaceCommaByCommaAndSpace(resultMap.get("FISMA")));
                    findingsModel.setGlba("");
                    findingsModel.setFedRamp(CommonUtil.replaceCommaByCommaAndSpace(resultMap.get("FedRAMP")));
                    findingsModel.setCostEffort(CommonUtil.replaceCommaByCommaAndSpace(resultMap.get("RMDTN_CST_EFFRT_NM")));
                 
                    findingList.add(findingsModel);
                     sNo++;
                    
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : RoadMapDAOImpl: getFindingsByCategory(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "RoadMapDAOImpl: getFindingsByCategory(): " + e.getMessage());
        }
        logger.info("End: RoadMapDAOImpl: getFindingsByCategory()");
        return findingList;
    }
    @Override
    public List roadMapDataForExportCSV(SecurityModel securityModel) throws AppException {
logger.info("Start: RoadMapDAOImpl: roadMapDataForExportCSV()");
        String procName = "{CALL Report_FindingByCategory(?,?,?)}";
        List dbhlist = securityModel.getCsvDBHeaders();
        List csvlist = securityModel.getCsvHeaders();
        List finalList = new ArrayList();
        finalList.add(csvlist);
        try {
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(procName, new Object[]{encryptDecrypt.decrypt(securityModel.getEngagementsDTO().getEngagementKey()), securityModel.getCategoryCode(),securityModel.getEngSchemaName()});
            if (null != resultList && resultList.size() > 0) {
                for (Map<String, Object> resultMap : resultList) {
                    List entryList = new ArrayList();
                    for (Object str : dbhlist) {
                        String s = str.toString();
                        entryList.add(CommonUtil.removeHtmlTagsFromString(resultMap.get(s)));

                    }
                    finalList.add(entryList);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : RoadMapDAOImpl: roadMapDataForExportCSV(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "RoadMapDAOImpl: roadMapDataForExportCSV(): " + e.getMessage());
        }
        logger.info("End: RoadMapDAOImpl: roadMapDataForExportCSV()");
        return finalList;
    }
}
