/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.helper;

import com.optum.oss.constants.ApplicationConstants;
import com.optum.oss.dto.MenuDTO;
import com.optum.oss.dto.UserProfileDTO;
import com.optum.oss.exception.AppException;
import java.util.Set;
import org.springframework.stereotype.Component;

/**
 *
 * @author akeshavulu
 */
@Component
public class PermissionCheckHelper {

    public boolean checkUserPermission(final UserProfileDTO userDTO, final String subMenuName,
            final String permissionName) throws AppException {
        boolean retBool = false;
        if (userDTO.getUserTypeObj().getLookUpEntityName().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_ADMIN)) {
            retBool = true;
        } else {
            if (!userDTO.getSubMenuPermissionMap().isEmpty()) {
                if (userDTO.getSubMenuPermissionMap().containsKey(subMenuName)) {
                    Set<String> permissionSet = userDTO.getSubMenuPermissionMap().get(subMenuName);
                    if (permissionSet.contains(permissionName)) {
                        retBool = true;
                    }
                }
            }
        }

        return retBool;
    }

    public boolean checkUserSubMenu(final UserProfileDTO userDTO, final String subMenuName
    ) throws AppException {
        boolean retBool = false;
        if (userDTO.getUserTypeObj().getLookUpEntityName().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_ADMIN)) {
            retBool = true;
        } else {
            if (!userDTO.getSubMenuPermissionMap().isEmpty()) {
                if (userDTO.getSubMenuPermissionMap().containsKey(subMenuName)) {
                    retBool = true;
                }
            }
        }

        return retBool;
    }

    public boolean checkUserMenu(final UserProfileDTO userDTO, final String menuName
    ) throws AppException {
        boolean retBool = false;
        if (userDTO.getUserTypeObj().getLookUpEntityName().equalsIgnoreCase(ApplicationConstants.DB_USER_TYPE_ADMIN)) {
            retBool = true;
        } else {
            if (!userDTO.getMenuList().isEmpty()) {
                for (MenuDTO menu : userDTO.getMenuList()) {
                    if (menu.getMenuName().equalsIgnoreCase(menuName)) {
                        retBool = true;
                        break;
                    }
                }
            }
        }

        return retBool;
    }

    public boolean checkUserPermissionNotAdmin(final UserProfileDTO userDTO, final String subMenuName,
            final String permissionName) throws AppException {
        boolean retBool = false;

        if (!userDTO.getSubMenuPermissionMap().isEmpty()) {
            if (userDTO.getSubMenuPermissionMap().containsKey(subMenuName)) {
                Set<String> permissionSet = userDTO.getSubMenuPermissionMap().get(subMenuName);
                if (permissionSet.contains(permissionName)) {
                    retBool = true;
                }
            }
        }

        return retBool;
    }
    
    public boolean checkUserSubMenuNotAdmin(final UserProfileDTO userDTO, final String subMenuName
    ) throws AppException {
        boolean retBool = false;
            if (!userDTO.getSubMenuPermissionMap().isEmpty()) {
                if (userDTO.getSubMenuPermissionMap().containsKey(subMenuName)) {
                    retBool = true;
                }
            }

        return retBool;
    }
}
