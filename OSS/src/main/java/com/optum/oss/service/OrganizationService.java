/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.service;

import com.optum.oss.dto.OrganizationDTO;
import com.optum.oss.dto.OrganizationSourceDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import java.util.List;

/**
 *
 * @author rpanuganti
 */
public interface OrganizationService {

    public List<OrganizationDTO> organizationWorkList() throws AppException;

    public int saveOrganization(final OrganizationDTO organizationDTO) throws AppException;

    public int updateOrganization(final OrganizationDTO organizationDTO) throws AppException;

    public int saveOrganizationAuthoritativeSourceInfo(final String flag, final long organizationKey, final long sourceKey, final String sourceClientID, final long userProfileKey) throws AppException;

    public int saveAuthoritativeSource(final String sourceType, final String sourceName, final String description, final long userProfileKey) throws AppException;

    public OrganizationDTO getOrganizationDetailByOrgID(final long organizationKey) throws AppException;

    public int updateOrganizationDetails(final OrganizationDTO organizationDTO) throws AppException;

    public int saveOrganizationDetails(OrganizationDTO organizationDTO,final UserProfileDTO userProfileDto) throws AppException;
    
    public int validateOrganization(final String organization) throws AppException;
    
    public int validateAuthoritativeSource(final String authoritativeSource) throws AppException;
    
    public List<OrganizationDTO> parentOrganizationList() throws AppException;

    public int createOrgSchema(final OrganizationDTO organizationDTO) throws AppException;
    
    public int onBoardingProcess(final OrganizationDTO organizationDTO,UserProfileDTO userProfileDto) throws AppException;
}
