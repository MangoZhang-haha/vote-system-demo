package com.controller;

import com.domain.Result;
import com.utils.FileUtils;
import com.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Mango
 * @Date: 2021/3/22 14:45:24
 */
@RestController
@RequestMapping("/file")
@Api(tags = "文件上传控制器")
public class FileController {

    @ApiOperation("上传临时文件，返回文件路径")
    @PostMapping("/tmpUpload")
    public Result tmpUpload(@RequestParam(value = "file", required = false) @ApiParam("file") MultipartFile multipartFile) throws IOException {
        if (multipartFile == null) {
            return ResultUtil.error("请选择文件上传");
        }
        if (StringUtils.isNotEmpty(multipartFile.getOriginalFilename())) {
            String url = FileUtils.uploadTmp(multipartFile);
            return ResultUtil.success(url);
        } else {
            return ResultUtil.error("文件不能为空");
        }
    }

    @ApiOperation("删除文件")
    @DeleteMapping("/fileDelete")
    public Result fileDelete(@RequestParam("path") @ApiParam("path") String path) {
        FileUtils.deleteFile(path);
        return ResultUtil.success();
    }
}
