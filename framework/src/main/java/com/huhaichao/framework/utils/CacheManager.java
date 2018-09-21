package com.huhaichao.framework.utils;

import com.blankj.utilcode.util.CacheDiskUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SDCardUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.Utils;

import java.io.File;

/**
 * @author HuHaiChao
 */

public class CacheManager {
    private static final String TAG = "CacheManager";
    private static volatile CacheManager cacheManager = null;
    private static final String PICTURE = "picture";
    private static final String WEB = "web";
    private static final String LOG = "log";
    private static final String NETWORK = "network";
    private static final String APPCACHE = "appcache";
    private static final String DATABASES = "databases";
    private static final String GEOLOCATION = "geolocation";

    private CacheManager() {
    }

    /**
     * 双重检验锁 DCL
     *
     * @return
     */
    public static CacheManager getInstance() {
        if (cacheManager == null) {
            synchronized (CacheManager.class) {
                if (cacheManager == null) {
                    cacheManager = new CacheManager();
                }
            }
        }
        return cacheManager;
    }

    /**
     * ================================临时数据存放在cache目录下====================================
     */

    /**
     * 内存缓存目录
     */
    public File getMemoryCacheDir() {
        return Utils.getApp().getCacheDir();
    }

    /**
     * 磁盘缓存目录
     */
    public File getDiskCacheDir() {
        return Utils.getApp().getExternalCacheDir();
    }

    /**
     * 获取当前App专属的缓存路径
     */
    public String getCachePath() {
        if (SDCardUtils.isSDCardEnableByEnvironment()) {
            return getDiskCachePath();
        } else {
            return getMemoryCachePath();
        }
    }

    /**
     * 获取内存缓存目录地址
     */
    public String getMemoryCachePath() {
        return getMemoryCacheDir().getAbsolutePath();
    }

    /**
     * 获取磁盘缓存目录地址
     */
    public String getDiskCachePath() {
        return getDiskCacheDir().getAbsolutePath();
    }

    /**
     * 创建指定缓存目录
     */
    public File createCacheDir(String cacheDirName) {
        File file = new File(getCachePath(), cacheDirName);
        if (FileUtils.createOrExistsDir(file)) {
            return file;
        }
        return null;
    }

    /**
     * 获取指定缓存目录的路径（不存在则创建）
     */
    public String getCacheDirPath(String cacheDirName) {
        File file = createCacheDir(cacheDirName);
        if (file != null) {
            return file.getAbsolutePath();
        }
        return null;
    }

    /**
     * 获取内存缓存大小
     */
    public Long getMemoryCacheSize() {
        return CacheDiskUtils.getInstance(getMemoryCacheDir()).getCacheSize();
    }

    /**
     * 获取指定内存目录缓存大小
     */
    public Long getMemoryCacheSize(String cacheDirName) {
        String s = getMemoryCachePath() + File.separator + cacheDirName;
        return CacheDiskUtils.getInstance(new File(s)).getCacheSize();
    }

    /**
     * 获取磁盘缓存大小
     */
    public Long getDiskCacheSize() {
        return CacheDiskUtils.getInstance(getDiskCacheDir()).getCacheSize();
    }

    /**
     * 获取指定磁盘目录缓存大小
     */
    public Long getDiskCacheSize(String cacheDirName) {
        String s = getDiskCachePath() + File.separator + cacheDirName;
        return CacheDiskUtils.getInstance(new File(s)).getCacheSize();
    }

    /**
     * 获取缓存大小：内存和SD卡
     */
    public String getCacheSize() {
        Long memoryCacheSize = getMemoryCacheSize();
        Long diskCacheSize = getDiskCacheSize();
        Long size = memoryCacheSize + diskCacheSize;
        return ConvertUtils.byte2FitMemorySize(size);
    }

    /**
     * 清理缓存：内存 和 磁盘
     */
    public void clearCache() {
        if (clearMemoryCache()) {
            LogUtils.dTag(TAG, TAG + "-->内存缓存清理成功");
        } else {
            LogUtils.dTag(TAG, TAG + "-->内存缓存清理失败");
        }

        if (clearDiskCache()) {
            LogUtils.dTag(TAG, TAG + "-->磁盘缓存清理成功");

        } else {
            LogUtils.dTag(TAG, TAG + "-->磁盘缓存清理失败");
        }
    }

    /**
     * 清理内存缓存
     */
    public boolean clearMemoryCache() {
        return CacheDiskUtils.getInstance(getMemoryCacheDir()).clear();
    }

    /**
     * 清理指定内存缓存目录
     */
    public boolean clearMemoryCache(String cacheDirName) {
        String s = getMemoryCacheDir() + File.separator + cacheDirName;
        return CacheDiskUtils.getInstance(new File(s)).clear();
    }

    /**
     * 清理磁盘缓存
     */
    public boolean clearDiskCache() {
        return CacheDiskUtils.getInstance(getDiskCacheDir()).clear();
    }

    /**
     * 清理指定磁盘缓存目录
     */
    public boolean clearDiskCache(String cacheDirName) {
        String s = getDiskCacheDir() + File.separator + cacheDirName;
        return CacheDiskUtils.getInstance(new File(s)).clear();
    }

    /**
     * ==============================   长时间保存的数据存储在files   ==============================
     */

    // TODO: 2018/8/4

    /**
     * 获取当前App专属files路径
     */
    public String getFilesPath() {
        if (SDCardUtils.isSDCardEnableByEnvironment()) {
            return Utils.getApp().getExternalFilesDir(null).getAbsolutePath();
        } else {
            return Utils.getApp().getFilesDir().getAbsolutePath();
        }
    }

    /**
     * 自动创建type目录，默认返回（type的）父目录
     */
    public String getFilesDirPath(String type) {
        //空白字符包括:空格,换行符,tab键
        if (StringUtils.isSpace(type)) {
            return null;
        }

        if (SDCardUtils.isSDCardEnableByEnvironment()) {
            return Utils.getApp().getExternalFilesDir(type).getAbsolutePath();
        } else {
            return Utils.getApp().getFileStreamPath(type).getAbsolutePath();
        }
    }

    /**
     * ========================================= 通用目录路径 ======================================
     */

    /**
     * 获取图片缓存路径
     */
    public String getPictureCachePath() {
        return getCacheDirPath(PICTURE);
    }

    /**
     * 获取log目录路径
     */
    public String getLogFilesPath() {
        return getFilesDirPath(LOG);
    }

    /**
     * 获取网络缓存路径
     */
    public String getPragmeCachePath() {
        return getCacheDirPath(NETWORK);
    }

    /**
     * 获取H5缓存路径
     */
    public String getAppCachePath() {
        return getCacheDirPath(APPCACHE);
    }

    /**
     * 获取数据库缓存路径
     */
    public String getDatabasesPath() {
        return getCacheDirPath(DATABASES);
    }

    /**
     * 获取定位的数据库缓存路径
     */
    public String getGeolocation() {
        return getCacheDirPath(GEOLOCATION);
    }
}
