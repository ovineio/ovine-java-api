package com.igroupes.rtadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Splitter;
import com.igroupes.rtadmin.constant.RtAdminConstant;
import com.igroupes.rtadmin.dto.UserInfo;
import com.igroupes.rtadmin.dto.response.FilterRoleResponse;
import com.igroupes.rtadmin.dto.response.PermissionListResponse;
import com.igroupes.rtadmin.dto.response.RoleListResponse;
import com.igroupes.rtadmin.dto.request.*;
import com.igroupes.rtadmin.entity.SystemPermissionEntity;
import com.igroupes.rtadmin.entity.SystemRoleEntity;
import com.igroupes.rtadmin.entity.SystemRolePermissionEntity;
import com.igroupes.rtadmin.entity.SystemUserRoleEntity;
import com.igroupes.rtadmin.enums.ErrorCode;
import com.igroupes.rtadmin.file.exception.RtAdminException;
import com.igroupes.rtadmin.service.IPermissionService;
import com.igroupes.rtadmin.service.IUserService;
import com.igroupes.rtadmin.service.raw.SystemPermissionService;
import com.igroupes.rtadmin.service.raw.SystemRolePermissionService;
import com.igroupes.rtadmin.service.raw.SystemRoleService;
import com.igroupes.rtadmin.service.raw.SystemUserRoleService;
import com.igroupes.rtadmin.util.*;
import com.igroupes.rtadmin.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.net.URLDecoder;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PermissionServiceImpl implements IPermissionService {
    @Autowired
    private SystemUserRoleService systemUserRoleService;
    @Autowired
    private IUserService userService;
    @Autowired
    private SystemRoleService systemRoleService;
    @Autowired
    private SystemPermissionService systemPermissionService;
    @Autowired
    private SystemRolePermissionService systemRolePermissionService;


    /**
     * 存在父子角色，父角色的权限一定要大于子角色的权限
     *
     * @param userInfo
     * @param request
     * @return
     */
    @Transactional
    @Override
    public ResultVO addRole(UserInfo userInfo, RoleAddRequest request) {
        SystemRoleEntity systemRoleEntity = new SystemRoleEntity();
        systemRoleEntity.setName(request.getName());
        systemRoleEntity.setDesc(request.getDesc());
        systemRoleEntity.setAddUser(userInfo.getId());
        systemRoleEntity.setUpdateUser(userInfo.getId());
        SystemRoleEntity role = systemUserRoleService.getRoleById(userInfo.getId());
        Requires.requireNonNull(role, "role");
        systemRoleEntity.setParentChain(RtAdminUtils.parentChain(role.getId(), role.getParentChain()));
        systemRoleEntity.setParentId(role.getId());
        systemRoleService.save(systemRoleEntity);
        return ResultVO.success();
    }


    @Override
    public ResultVO getRoleList(UserInfo userInfo, @Valid RoleListRequest request) {
        if (StringUtils.isNotBlank(request.getRoleIds())) {
            try {
                // roleId经过框架处理，会转为url编码
                request.setRoleIds(URLDecoder.decode(request.getRoleIds(), RtAdminConstant.URL_DECODE_CHARSET));
            } catch (Exception ex) {
                throw new RtAdminException(ErrorCode.PARAM_ERROR);
            }
            Pattern pattern = Pattern.compile("(\\d|,)+");
            if (!pattern.matcher(request.getRoleIds()).matches()) {
                log.error("roleIds:{} format error", request.getRoleIds());
                throw new RtAdminException(ErrorCode.PARAM_ERROR);
            }
        }
        Page<SystemRoleEntity> page = new Page<>(request.getPage(), request.getSize());
        QueryWrapper<SystemRoleEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("add_user", userInfo.getId());
        if (StringUtils.isNotBlank(request.getRoleIds())) {
            wrapper.in("id", Splitter.on(",").splitToList(request.getRoleIds()));
        }
        IPage<SystemRoleEntity> systemRoleEntityPage = systemRoleService.page(page, wrapper);
        return ResultVO.success(getRoleListByPage(systemRoleEntityPage));
    }

    @Override
    public ResultVO getFilterRole(UserInfo userInfo, FilterRoleRequest request) {
        List<SystemRoleEntity> filterRoleList = systemRoleService.getFilterRole(userInfo.getId(), request.getFilter());
        FilterRoleResponse roleResponse = new FilterRoleResponse();
        if (CollectionUtils.isEmpty(filterRoleList)) {
            roleResponse.setList(ListUtils.EMPTY_LIST);
            return ResultVO.success(roleResponse);
        }
        List<FilterRoleResponse.FilterRoleResponseDetail> detailList = filterRoleList.stream().map(record -> {
            FilterRoleResponse.FilterRoleResponseDetail filterRoleResponseDetail = new FilterRoleResponse.FilterRoleResponseDetail();
            filterRoleResponseDetail.setId(record.getId());
            filterRoleResponseDetail.setName(record.getName());
            return filterRoleResponseDetail;
        }).collect(Collectors.toList());
        roleResponse.setList(detailList);
        return ResultVO.success(roleResponse);
    }

    @Transactional
    @Override
    public ResultVO updateRole(UserInfo userInfo, Long roleId, RoleUpdateRequest request) {
        SystemRoleEntity systemRoleEntity = checkUserRole(userInfo.getId(), roleId);
        systemRoleEntity.setName(request.getName());
        systemRoleEntity.setDesc(request.getDesc());
        systemRoleService.updateById(systemRoleEntity);
        return ResultVO.success();
    }

    @Transactional
    @Override
    public ResultVO updateRoleMember(UserInfo userInfo, RoleMemberUpdateRequest request) {
        checkUserRole(userInfo.getId(), request.getRoleId());
        // 删除这些用户和角色的映射关系
        systemUserRoleService.deleteUserList(request.getUserIds(), request.getRoleId());
        // 添加新的用户角色关系
        for (Long userId : request.getUserIds()) {
            SystemUserRoleEntity systemUserRoleEntity = new SystemUserRoleEntity();
            systemUserRoleEntity.setRoleId(request.getRoleId());
            systemUserRoleEntity.setUserId(userId);
            systemUserRoleEntity.setAddUser(userInfo.getId());
            systemUserRoleEntity.setUpdateUser(userInfo.getId());
            systemUserRoleService.save(systemUserRoleEntity);
        }
        return ResultVO.success();
    }

    private RoleListResponse getRoleListByPage(IPage<SystemRoleEntity> systemRoleEntityPage) {
        RoleListResponse roleListResponse = new RoleListResponse();
        PageDTOUtil.setPageResponse(systemRoleEntityPage, roleListResponse);
        List<RoleListResponse.RoleListResponseDetail> detail = systemRoleEntityPage.getRecords().stream().map(record -> {
            RoleListResponse.RoleListResponseDetail roleListResponseDetail = new RoleListResponse.RoleListResponseDetail();
            roleListResponseDetail.setDesc(record.getDesc());
            roleListResponseDetail.setId(record.getId());
            roleListResponseDetail.setName(record.getName());
            roleListResponseDetail.setCreateTime(record.getAddTime());
            roleListResponseDetail.setUpdateTime(record.getUpdateTime());
            return roleListResponseDetail;
        }).collect(Collectors.toList());
        roleListResponse.setList(detail);
        return roleListResponse;
    }

    /**
     * 如果更新的权限存在绑定用户，
     *
     * @param userInfo
     * @param roleId
     * @param request
     * @return
     */
    @Transactional
    @Override
    public ResultVO updateLimit(UserInfo userInfo, Long roleId, PermissionUpdateRequest request) {
        SystemRoleEntity systemRoleEntity = checkUserRole(userInfo.getId(), roleId);
        if (systemRoleEntity == null) {
            throw new RtAdminException(ErrorCode.PARAM_ERROR);
        }
        SystemPermissionEntity systemPermissionEntity = systemRolePermissionService.getPermissionById(roleId);
        if (systemPermissionEntity == null) {
            SystemPermissionEntity permissionEntity = new SystemPermissionEntity();
            permissionEntity.setLimitDetail(request.getLimit());
            permissionEntity.setApi(request.getApi());
            // 更新权限信息
            systemPermissionService.save(permissionEntity);
            SystemRolePermissionEntity systemRolePermissionEntity = new SystemRolePermissionEntity();
            systemRolePermissionEntity.setRoleId(roleId);
            systemRolePermissionEntity.setPermId(permissionEntity.getId());
            systemRolePermissionService.save(systemRolePermissionEntity);
        } else {
            // 先修改当前角色的权限
            systemPermissionEntity.setApi(request.getApi());
            systemPermissionEntity.setLimitDetail(request.getLimit());
            systemPermissionService.updateById(systemPermissionEntity);

            // 修改子角色权限
            List<SystemPermissionEntity> childPermList = systemRoleService.getChildPermList(RtAdminUtils.parentChain(systemRoleEntity.getId(), systemRoleEntity.getParentChain()));
            if (CollectionUtils.isEmpty(childPermList)) {
                return ResultVO.success();
            }

            RequestPathTrie<Set<String>> cutApiPathTrie = PermissionUtils.cutApiPathTrie(systemPermissionEntity.getApi(), request.getApi());
            Set<String> cutLimitSet = PermissionUtils.cutLimitSet(systemPermissionEntity.getLimitDetail(), request.getLimit());

            if (cutApiPathTrie.isRootNode() && cutLimitSet.size() == 0) {
                // 没有改变任何
                return ResultVO.success();
            }

            for (SystemPermissionEntity childPerm : childPermList) {
                log.info("由于父权限被修改，子权限：{}需要被递归修改",childPerm);
                childPerm.setLimitDetail(PermissionUtils.cutChildLimit(childPerm.getLimitDetail(), cutLimitSet));
                childPerm.setApi(PermissionUtils.cutChildApi(childPerm.getApi(), cutApiPathTrie));
                systemPermissionService.updateById(childPerm);
            }
        }

        return ResultVO.success();
    }


    @Override
    public ResultVO getLimitList(UserInfo userInfo, Long roleId, PermissionListRequest request) {
        checkUserRole(userInfo.getId(), roleId);
        SystemPermissionEntity systemPermissionEntity = systemRolePermissionService.getPermissionById(roleId);
        PermissionListResponse response = new PermissionListResponse();
        if (systemPermissionEntity == null) {
            response.setLimit("");
        } else {
            response.setLimit(systemPermissionEntity.getLimitDetail());
        }
        return ResultVO.success(response);
    }

    @Transactional
    @Override
    public ResultVO deleteRole(UserInfo userInfo, Long roleId) {
        SystemRoleEntity systemRoleEntity = checkUserRole(userInfo.getId(), roleId);
        Requires.requireNonNull(systemRoleEntity, "system role");
        SystemUserRoleEntity systemUserRoleEntityFind = new SystemUserRoleEntity();
        systemUserRoleEntityFind.setRoleId(roleId);

        SystemUserRoleEntity systemUserRoleEntity = systemUserRoleService.getOne(new QueryWrapper<>(systemUserRoleEntityFind));
        // 如果当前权限已经绑定了用户，不用被删除
        if (systemUserRoleEntity != null) {
            log.error("role id :{} already bind  user ", systemRoleEntity.getId());
            throw new RtAdminException(ErrorCode.ROLE_BIND_USER);
        }
        if (getChildRoleList(systemRoleEntity).size() > 0) {
            log.error("role id :{} has child  user ", systemRoleEntity.getId());
            throw new RtAdminException(ErrorCode.ROLE_CHILD_EXIST);
        }
        systemRoleService.removeById(roleId);
        return ResultVO.success();
    }

    /**
     * 用户id和角色
     */
    private SystemRoleEntity checkUserRole(Long userId, Long roleId) {
        Requires.requireNonNull(userId, "user id");
        Requires.requireNonNull(roleId, "role id");
        SystemRoleEntity systemRoleEntity = systemRoleService.getById(roleId);
        if (!systemRoleEntity.getAddUser().equals(userId)) {
            throw new RtAdminException(ErrorCode.PERMISSION_DENIED);
        }
        return systemRoleEntity;
    }

    private List<SystemRoleEntity> getChildRoleList(Long roleId) {
        Requires.requireNonNull(roleId, "role id");
        SystemRoleEntity parentRole = systemRoleService.getById(roleId);
        return getChildRoleList(parentRole);
    }

    private List<SystemRoleEntity> getChildRoleList(SystemRoleEntity parentRole) {
        if (parentRole == null) {
            return ListUtils.EMPTY_LIST;
        }
        return systemRoleService.getChildRoleList(RtAdminUtils.parentChain(parentRole.getId(), parentRole.getParentChain()));
    }
}
