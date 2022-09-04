package com.alibaba.ctoo.opensource.domain.repository.dao;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.ctoo.opensource.domain.repository.dataobject.ArticleVersionDO;
import com.alibaba.ctoo.opensource.domain.api.enums.ActivatedEnum;
import com.alibaba.ctoo.opensource.domain.repository.dataobject.ArticleVersionParam;
import com.alibaba.ctoo.opensource.domain.repository.mapper.ArticleVersionMapper;
import com.alibaba.easytools.base.enums.DeletedIdEnum;

import org.springframework.stereotype.Repository;

/**
 * 文章dao
 *
 * @author zyb
 */
@Repository
public class ArticleVersionDAO {

    @Resource
    private ArticleVersionMapper articleVersionMapper;

    public void unactivatedByArticelId(Long id) {
        if (id == null) {
            return;
        }
        ArticleVersionParam articleVersionParam = new ArticleVersionParam();
        articleVersionParam.createCriteria()
            .andArticleIdEqualTo(id)
            .andDeletedIdEqualTo(DeletedIdEnum.NOT_DELETED.getCode())
            .andActivatedEqualTo(ActivatedEnum.ACTIVATED.getCode());

        List<ArticleVersionDO> articleVersionList = articleVersionMapper.selectByParam(articleVersionParam);
        for (ArticleVersionDO articleVersion : articleVersionList) {
            ArticleVersionDO articleVersionUpdate = new ArticleVersionDO();
            articleVersionUpdate.setId(articleVersion.getId());
            articleVersionUpdate.setActivated(ActivatedEnum.UNACTIVATED.getCode());
            articleVersionMapper.updateByPrimaryKeySelective(articleVersionUpdate);
        }





    }


}
