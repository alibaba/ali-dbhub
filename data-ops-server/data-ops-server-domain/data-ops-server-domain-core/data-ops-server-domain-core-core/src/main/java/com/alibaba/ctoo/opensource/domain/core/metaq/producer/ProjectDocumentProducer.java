package com.alibaba.ctoo.opensource.domain.core.metaq.producer;

import javax.annotation.Resource;

import com.alibaba.ctoo.opensource.domain.api.metaq.ProjectDocumentMetaqDTO;
import com.alibaba.easytools.spring.metaq.AbstractEasyMetaProducer;
import com.alibaba.fastvalidator.jsr.bean.model.Meta;

import com.taobao.metaq.client.MetaProducer;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author qiuyuyu
 * @date 2022/04/10
 */
@Component
public class ProjectDocumentProducer extends AbstractEasyMetaProducer<ProjectDocumentMetaqDTO> {
    @Getter
    @Value("${spring.metaq.producer.project-document.topic}")
    private String topic;
    @Override
    public void produce(ProjectDocumentMetaqDTO msg) {
        super.produce(msg);
    }

}
