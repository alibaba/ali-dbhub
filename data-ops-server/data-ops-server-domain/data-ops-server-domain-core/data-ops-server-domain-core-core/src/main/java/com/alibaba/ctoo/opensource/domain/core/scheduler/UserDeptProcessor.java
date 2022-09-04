package com.alibaba.ctoo.opensource.domain.core.scheduler;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.ctoo.opensource.domain.repository.dataobject.UserDO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.UserParam;
import com.alibaba.ctoo.opensource.domain.repository.mapper.UserMapper;
import com.alibaba.ctoo.opensource.common.constants.MixConstants;
import com.alibaba.ctoo.opensource.domain.api.model.DeptDTO;
import com.alibaba.ctoo.opensource.domain.api.service.user.UserCacheDomainService;
import com.alibaba.ctoo.opensource.integration.service.EmpIntegrationService;
import com.alibaba.easytools.base.enums.DeletedIdEnum;
import com.alibaba.easytools.spring.scheduler.AbstractBaseJavaProcessor;
import com.alibaba.schedulerx.worker.domain.JobContext;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

/**
 * 更新用户部门信息
 * 每日凌晨 更新一次
 *
 * @author qiuyuyu
 * @date 2022/03/08
 */
@Slf4j
@Component
public class UserDeptProcessor extends AbstractBaseJavaProcessor {
    @Resource
    private UserCacheDomainService userCacheDomainService;
    @Resource
    private EmpIntegrationService empIntegrationService;
    @Resource
    private UserMapper userMapper;

    @Override
    protected void doProcess(JobContext context) {
        int index = 0;
        long startId = 0L;
        boolean hasNextPage;
        int batchCount = 20;
        do {
            UserParam userParam = new UserParam();
            userParam.createCriteria()
                .andIdGreaterThan(startId)
                .andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode());
            userParam.appendOrderByClause(UserParam.OrderCondition.ID, UserParam.SortType.ASC);
            userParam.setPagination(1, batchCount);
            List<UserDO> userList = userMapper.selectByParam(userParam);
            if (CollectionUtils.isNotEmpty(userList)) {
                for (UserDO userDO : userList) {
                    updateUser(userDO);
                }
                startId = userList.get(userList.size() - 1).getId();
            }
            log.info("第{}页，查询{}条数据", index, userList.size());
            hasNextPage = userList.size() >= batchCount;
        } while (hasNextPage && index++ < MixConstants.MAXIMUM_ITERATIONS * 1000);
    }

    private void updateUser(UserDO userDO) {
        try {
            List<DeptDTO> deptList = empIntegrationService.queryByUserIdList(Lists.newArrayList(userDO.getUserId()))
                .getData();
            // 未查询到用户信息直接返回
            if (CollectionUtils.isEmpty(deptList)) {
                log.info("未查询到用户{}信息", userDO.getUserId());
                return;
            }
            // 用户无部门信息直接返回
            if (!ObjectUtils.allNotNull(deptList.get(0).getDeptName(), deptList.get(0).getDeptNo())) {
                log.info("暂未查询到用户{}部门", userDO.getUserId());
                return;
            }
            log.info("更新用户{}", userDO.getUserId());
            UserDO updateUser = new UserDO();
            updateUser.setId(userDO.getId());
            updateUser.setDeptNo(deptList.get(0).getDeptNo());
            updateUser.setDeptName(deptList.get(0).getDeptName());
            userMapper.updateByPrimaryKeySelective(updateUser);
            // 移除缓存
            userCacheDomainService.cacheEvict(userDO.getUserId());
            // 怕被集团限制
            Thread.sleep(10);
            log.info("更新用户{}成功", userDO.getUserId());
        } catch (Exception e) {
            // 个别用户更新异常 不中断
            log.error("用户：{}更新信息失败.", userDO.getUserId(), e);
        }
    }
}
