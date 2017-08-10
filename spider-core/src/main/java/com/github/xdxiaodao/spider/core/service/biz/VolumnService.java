package com.github.xdxiaodao.spider.core.service.biz;

import com.github.xdxiaodao.spider.core.model.Volumn;

/**
 * Created with bookspider
 * User zhangmuzhao
 * Date 2017/8/8
 * Time 18:17
 * Desc
 */
public interface VolumnService {

    /**
     * 添加卷信息
     * @param volumn 卷信息
     * @return 卷ID
     */
    public int addVolumn(Volumn volumn);

    public Volumn getVolumn();
}
