package com.ruoyi.system.service;

import java.util.Map;

/**
 * 外部图书 API 服务
 *
 * @author ruoyi
 */
public interface BookApiService
{
    /**
     * 根据 ISBN 从 Google Books 查询图书信息
     *
     * @param isbn ISBN编号
     * @return 图书信息Map（含 bookName, author, press, publishDate, description, coverImage, isbn）
     */
    public Map<String, Object> fetchBookByIsbn(String isbn);
}
