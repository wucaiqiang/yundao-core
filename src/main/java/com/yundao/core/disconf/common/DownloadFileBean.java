package com.yundao.core.disconf.common;

import com.baidu.disconf.client.common.model.DisConfCommonModel;
import com.baidu.disconf.client.config.DisClientConfig;
import com.baidu.disconf.client.config.DisClientSysConfig;
import com.baidu.disconf.client.core.processor.DisconfCoreProcessor;
import com.baidu.disconf.client.fetcher.FetcherFactory;
import com.baidu.disconf.client.fetcher.FetcherMgr;
import com.baidu.disconf.core.common.constants.DisConfigTypeEnum;
import com.baidu.disconf.core.common.path.DisconfWebPathMgr;
import com.yundao.core.config.system.SystemFileConfig;
import com.yundao.core.log.Log;
import com.yundao.core.log.LogFactory;
import com.yundao.core.utils.BooleanUtils;
import com.yundao.core.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 下载文件类
 *
 * @author wupengfei wupf86@126.com
 */
public class DownloadFileBean {

    /**
     * 公共app的名字
     */
    private static final String COMMON_APP_NAME = "common";

    /**
     * 下载的路径
     */
    private static String downloadPath = SystemFileConfig.getValue("common.download.path");

    private static Log log = LogFactory.getLog(DownloadFileBean.class);

    /**
     * 公共版本号
     */
    private static String commonVersion;

    /**
     * 客户配置
     */
    private static DisClientConfig instance = DisClientConfig.getInstance();

    static {
        // 获取下载的路径
        if (BooleanUtils.isBlank(downloadPath)) {
            downloadPath = "file:/opt/website/config";
        }
        downloadPath = FileUtils.getRealPath(downloadPath, COMMON_APP_NAME);

        // 获取版本号的配置
        commonVersion = SystemFileConfig.getValue("common.version");
        if (BooleanUtils.isBlank(commonVersion)) {
            commonVersion = "1.0.0.0";
        }
    }

    /**
     * 待下载的文件名
     */
    private List<String> fileNames = new ArrayList<>();

    /**
     * @return 下载的路径
     */
    public static String getDownloadPath() {
        return downloadPath + "/" + commonVersion;
    }

    /**
     * 初始化方法
     */
    public void init() {
        try {
            // 创建common目录
            String downloadPath = getDownloadPath();
            FileUtils.mkdirs(new File(downloadPath));

            // 下载设置的文件
            FetcherMgr fetcherMgr = FetcherFactory.getFetcherMgr();
//            WatchMgr watchMgr = WatchFactory.getWatchMgr(fetcherMgr);

            // 设置共用模型
            DisConfCommonModel disConfCommonModel = new DisConfCommonModel();
            disConfCommonModel.setApp(COMMON_APP_NAME);
            disConfCommonModel.setEnv(instance.ENV);
            disConfCommonModel.setVersion(commonVersion);

            String server = DisClientSysConfig.getInstance().CONF_SERVER_STORE_ACTION;
            for (String each : fileNames) {
                log.info("开始下载文件each=" + each);
                String url = DisconfWebPathMgr.getRemoteUrlParameter(server, COMMON_APP_NAME, commonVersion, instance.ENV, each, DisConfigTypeEnum.FILE);
                String filePath = fetcherMgr.downloadFileFromServer(url, each, downloadPath);
                log.info("下载文件filePath=" + filePath);

//                Map<String, Object> kvMap = FileTypeProcessorUtils.getKvMap(SupportFileTypeEnum.PROPERTIES, each);
//                watchMgr.watchPath(new DisconfFileCoreProcessor(), disConfCommonModel, each, DisConfigTypeEnum.FILE, GsonUtils.toJson(kvMap));
            }
        }
        catch (Exception e) {
            log.error("下载文件时异常", e);
        }
    }

    /**
     * 销毁方法
     */
    public void destroy() {

    }

    public void setFileNames(List<String> fileNames) {
        this.fileNames = fileNames;
    }

    private static class DisconfFileCoreProcessor implements DisconfCoreProcessor {

        @Override
        public void processAllItems() {

        }

        @Override
        public void processOneItem(String s) {

        }

        @Override
        public void updateOneConfAndCallback(String s) throws Exception {
            log.info("重新加载文件的内容s=" + s);

        }

        @Override
        public void inject2Conf() {

        }
    }
}
