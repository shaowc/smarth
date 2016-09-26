package com.familyan.smarth.manager;

import com.familyan.smarth.domain.ImageDO;

import java.util.List;

/**
 * Created by shaowenchao on 16/9/25.
 */
public interface ImageManager {

    void add(ImageDO imageDO);

    ImageDO findById(Integer id);

    List<ImageDO> findByIds(List<Integer> ids);

}
