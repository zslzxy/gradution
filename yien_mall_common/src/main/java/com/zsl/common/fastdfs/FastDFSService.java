package com.zsl.common.fastdfs;

import com.zsl.common.utils.Msg;
import org.springframework.web.multipart.MultipartFile;

public interface FastDFSService {

    Msg fileUpload(MultipartFile uploadFile);

}
