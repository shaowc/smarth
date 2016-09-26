package com.familyan.smarth.manager.impl;

import com.familyan.smarth.dao.ImageDao;
import com.familyan.smarth.domain.ImageDO;
import com.familyan.smarth.manager.ImageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by shaowenchao on 16/9/25.
 */
@Service
public class ImageManagerImpl implements ImageManager {

    @Autowired
    private ImageDao imageDao;

    @Override
    public void add(ImageDO imageDO) {
        imageDao.insert(imageDO);
    }

    @Override
    public ImageDO findById(Integer id) {
        return imageDao.findById(id);
    }

    @Override
    public List<ImageDO> findByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty())
            return Collections.emptyList();

        return imageDao.findByIds(ids);
    }
}
