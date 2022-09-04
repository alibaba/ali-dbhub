package com.alibaba.ctoo.opensource.domain.core.scheduler;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.ctoo.opensource.common.constants.MixConstants;
import com.alibaba.ctoo.opensource.domain.api.param.project.document.RefreshDocumentParam;
import com.alibaba.ctoo.opensource.domain.api.service.project.ProjectDomainManageService;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ProjectDocumentDO;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ProjectDocumentParam;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ProjectDocumentParam.OrderCondition;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ProjectDocumentParam.SortType;
import com.alibaba.ctoo.opensource.domain.repository.mapper.ProjectDocumentMapper;
import com.alibaba.easytools.base.enums.DeletedIdEnum;
import com.alibaba.easytools.spring.scheduler.AbstractBaseJavaProcessor;
import com.alibaba.schedulerx.worker.domain.JobContext;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Component;

/**
 * 文档刷新
 *
 * @author 是仪
 */
@Slf4j
@Component
public class DocumentRefreshProcessor extends AbstractBaseJavaProcessor {
    @Resource
    private ProjectDocumentMapper projectDocumentMapper;
    @Resource
    private ProjectDomainManageService projectDomainManageService;

    @Override
    protected void doProcess(JobContext context) {
        // 今天凌晨
        Date beginOfToday = DateUtil.beginOfDay(DateUtil.date());

        int index = 0;
        long startId = 0L;
        boolean hasNextPage;
        do {
            ProjectDocumentParam param = new ProjectDocumentParam();
            param.createCriteria()
                .andIdGreaterThan(startId)
                .andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode());
            param.appendOrderByClause(OrderCondition.ID, SortType.ASC);
            param.setPagination(1, MixConstants.DEFAULT_PAGE_SIZE);
            List<ProjectDocumentDO> projectDocumentList = projectDocumentMapper.selectByParam(param);
            if (CollectionUtils.isNotEmpty(projectDocumentList)) {
                for (ProjectDocumentDO projectDocument : projectDocumentList) {
                    try {
                        doRefreshDocument(projectDocument, beginOfToday);
                    } catch (Exception e) {
                        log.error("刷新文档失败:{}", projectDocument.getProjectId(), e);
                    }
                }
                startId = projectDocumentList.get(projectDocumentList.size() - 1).getId();
            }
            hasNextPage = projectDocumentList.size() >= MixConstants.DEFAULT_BATCH_COUNT;
        } while (hasNextPage && index++ < MixConstants.MAXIMUM_ITERATIONS);
        log.info("刷新文档完毕完成");
    }

    private void doRefreshDocument(ProjectDocumentDO projectDocument, Date beginOfToday) {
        if (projectDocument.getGmtRefresh() != null && DateUtils.truncatedCompareTo(
            projectDocument.getGmtRefresh(), beginOfToday, Calendar.DAY_OF_MONTH) >= 0) {
            log.info("项目:{}已经刷新过了，跳过", projectDocument.getProjectId());
            return;
        }

        RefreshDocumentParam refreshDocumentParam = new RefreshDocumentParam();
        refreshDocumentParam.setProjectId(projectDocument.getProjectId());
        projectDomainManageService.refreshDocument(refreshDocumentParam);
    }
}
