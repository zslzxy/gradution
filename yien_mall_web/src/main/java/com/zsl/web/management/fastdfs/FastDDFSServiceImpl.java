package com.zsl.web.management.fastdfs;

import com.alibaba.dubbo.config.annotation.Service;
import com.zsl.common.fastdfs.FastDFSService;
import com.zsl.common.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

/**
 * 文件上传控制器 * <p>Title: UploadController</p> * <p>Description: </p> *
 *
 * @author Lusifer * @version 1.0.0 * @date 2018/3/4 21:44
 */
@Component("fastDFSService")
public class FastDDFSServiceImpl implements FastDFSService {

	@Value("${fastdfs.base.url}")
	private String FASTDFS_BASE_URL;

	@Autowired
	private StorageService storageService;

	@Override
	public Msg fileUpload(MultipartFile uploadFile) {
		if (uploadFile != null) {
			String oName = uploadFile.getOriginalFilename();
			String extName = oName.substring(oName.indexOf(".") + 1);
			HashMap<String, Object> map = new HashMap<>();
			try {
				String uploadUrl = storageService.upload(uploadFile.getBytes(), extName);
				return Msg.success().addInfo("图片上传成功").addMap("url", FASTDFS_BASE_URL + uploadUrl);
			} catch (IOException e) {
				map.put("error", 1);
				map.put("message", "上传失败");
				return Msg.success().addInfo("图片上传失败").addMap("url", null);
			}
		}
		return Msg.success().addInfo("图片上传失败").addMap("url", null);
	}
}
