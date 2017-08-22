/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.dao;

import com.optum.oss.dto.MasterLookUpDTO;
import com.optum.oss.exception.AppException;
import java.util.List;

/**
 *
 * @author hpasupuleti
 */
public interface MasterLookUpDAO {

    public List<MasterLookUpDTO> fetchMasterLookUpEntitiesByEntityType(final String entityType)
            throws AppException;

    public List fetchCountries() throws AppException;

    public List<String> fetchStatesByCountry(final String country) throws AppException;
    
    public int fetchEntityIdByEntityName(final String entityTye, final String entityName) throws AppException;

}
