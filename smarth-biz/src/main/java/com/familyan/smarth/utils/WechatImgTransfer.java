package com.familyan.smarth.utils;

import com.familyan.smarth.domain.ImageDO;
import com.familyan.smarth.manager.ImageManager;
import com.google.common.collect.Lists;
import com.lotus.wechat.WechatApi;
import com.lotus.wechat.WechatException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * Created by shaowenchao on 16/8/8.
 */
public class WechatImgTransfer implements DisposableBean{

    private final Logger logger = Logger.getLogger(WechatImgTransfer.class);

    public ExecutorService service = Executors.newFixedThreadPool(5);

    @Autowired
    private WechatApi api;
    @Autowired
    private ImageManager imageManager;

    public List<Integer> transfer(List<String> newImages) {
        List<Future<Integer>> futures = new ArrayList<>();
        for (final String mediaId : newImages) {
            Future<Integer> future = service.submit(new Callable<Integer>() {
                @Override
                public Integer call() {
                    try {
                        return httpGet("http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=" + api.accessToken().getAccessToken() + "&media_id=" + mediaId);
                    } catch (Exception e) {
                        logger.info("保存" + mediaId + "的图片失败",e);
                        return 0;
                    }
                }
            });
            futures.add(future);
        }

        List<Integer> fileNames = Lists.newArrayList();
        for (Future<Integer> future : futures) {
            try {
                fileNames.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                logger.warn(e.getMessage());
            }
        }

        return fileNames;
    }

    public Integer transfer(String mediaId) {

        try {
            return httpGet("http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=" + api.accessToken().getAccessToken() + "&media_id=" + mediaId);
        } catch (Exception e) {
            logger.info("保存" + mediaId + "的图片失败",e);
            return 0;
        }

    }

    private Integer httpGet(String url) throws WechatException {
        logger.info("Wechat httpGet: " + url);
        try {
            URL e = new URL(url);
            URLConnection connection = e.openConnection();
            connection.setConnectTimeout(1000);
            connection.setReadTimeout(30000);
            String contentType = connection.getContentType();
            //失败:Content-Type: text/plain;成功:Content-Type: image/jpeg
            if (!"text/plain".equals(contentType)) {
                InputStream in = connection.getInputStream();
                String fileName = UUID.randomUUID().toString().replaceAll("-", "");
                String tempPath = FileUtils.saveTempFile(fileName,in);
                File imageFile = new File(tempPath);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                BufferedImage bufferedImage = ImageIO.read(imageFile);
                ImageIO.write(bufferedImage, "jpeg", outputStream);
                // 对字节数组Base64编码
                BASE64Encoder encoder = new BASE64Encoder();
                String base64Img = encoder.encode(outputStream.toByteArray());// 返回Base64编码过的字节数组字符串
                // base64编码，存数据库
                ImageDO imageDO = new ImageDO();
                imageDO.setImg(base64Img);
                imageManager.add(imageDO);
                return imageDO.getId();
            }

            return 0;

        } catch (IOException var5) {
            throw new WechatException("访问微信异常", var5);
        }
    }

    @Override
    public void destroy() throws Exception {
        service.shutdown();
    }
}
