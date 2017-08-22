/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.transact.dao;

import com.optum.oss.dto.OrganizationDTO;
import com.optum.oss.exception.AppException;

/**
 *
 * @author rpanuganti
 */
public interface OrganizationTransactDAO {

    public int saveOrganizationDeatils(final OrganizationDTO organizationDTO) throws AppException;

    public int updateOrganizationDetails(final OrganizationDTO organizationDTO) throws AppException;

    public int createOrgSchema(final OrganizationDTO organizationDTO) throws AppException;
    
    public int createOrgSchemaView(OrganizationDTO organizationDTO) throws AppException;

    public int createOrgSchemaTables(OrganizationDTO organizationDTO) throws AppException;
    
    public int createOrgSchemaETLTables(OrganizationDTO organizationDTO) throws AppException;
    
}
