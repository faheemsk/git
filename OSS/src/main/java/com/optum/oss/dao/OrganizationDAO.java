/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao;

import com.optum.oss.dto.OrganizationDTO;
import com.optum.oss.dto.OrganizationSourceDTO;
import com.optum.oss.exception.AppException;
import java.util.List;

/**
 *
 * @author rpanuganti
 */
public interface OrganizationDAO {

    public List<OrganizationDTO> organizationWorkList() throws AppException;

    public int saveOrganization(final OrganizationDTO organizationDTO) throws AppException;

    public int updateOrganization(final OrganizationDTO organizationDTO) throws AppException;

    public int saveOrganizationAuthoritativeSourceInfo(final String flag, final long organizationKey, final long sourceKey, final String sourceClientID, final long userProfileKey) throws AppException;

    public int saveAuthoritativeSource(final String sourceType, final String sourceName, final String description, final long userProfileKey) throws AppException;

    public OrganizationDTO getOrganizationDetailByOrgID(final long organizationKey) throws AppException;

    public List<OrganizationSourceDTO> getOrganizationSourceInfoByOrgID(final long organizationKey) throws AppException;
    
    public int validateOrganization(final String organization) throws AppException;
    
    public int validateAuthoritativeSource(final String authoritativeSource) throws AppException;
    
    public List<OrganizationDTO> parentOrganizationList() throws AppException;
    
    public int createOrgSchema(final OrganizationDTO organizationDTO) throws AppException;

    public int createOrgSchemaView(OrganizationDTO organizationDTO) throws AppException;

    public int createOrgSchemaTables(OrganizationDTO organizationDTO) throws AppException;
    
    public int createOrgSchemaETLTables(OrganizationDTO organizationDTO) throws AppException;
    
    public int createOrgSchemaLog(OrganizationDTO organizationDTO) throws AppException;
    
    public int updateOrgSchemaIndicator(OrganizationDTO organizationDTO) throws AppException; 

}
