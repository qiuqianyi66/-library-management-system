package com.ruoyi.system.service.impl;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.system.service.BookApiService;

/**
 * 外部图书 API 服务实现
 * 对接 Google Books API 获取图书信息
 *
 * @author ruoyi
 */
@Service
public class BookApiServiceImpl implements BookApiService
{
    private static final Logger log = LoggerFactory.getLogger(BookApiServiceImpl.class);

    // Google Books API 端点（无需 API Key 也可用，但有频率限制）
    private static final String GOOGLE_BOOKS_URL = "https://www.googleapis.com/books/v1/volumes?q=isbn:";

    @Override
    public Map<String, Object> fetchBookByIsbn(String isbn)
    {
        Map<String, Object> result = new HashMap<>();
        if (isbn == null || isbn.trim().isEmpty())
        {
            return result;
        }

        try
        {
            String cleanIsbn = isbn.replaceAll("[^0-9Xx]", "");
            String url = GOOGLE_BOOKS_URL + cleanIsbn;
            String response = HttpUtils.sendGet(url);

            if (response == null || response.isEmpty())
            {
                return result;
            }

            JSONObject json = JSON.parseObject(response);
            int totalItems = json.getIntValue("totalItems");
            if (totalItems == 0)
            {
                return result;
            }

            JSONArray items = json.getJSONArray("items");
            JSONObject volumeInfo = items.getJSONObject(0)
                    .getJSONObject("volumeInfo");

            // 书名
            result.put("bookName", volumeInfo.getString("title"));

            // 作者
            JSONArray authors = volumeInfo.getJSONArray("authors");
            if (authors != null && !authors.isEmpty())
            {
                result.put("author", authors.getString(0));
            }

            // 出版社
            result.put("press", volumeInfo.getString("publisher"));

            // 出版日期
            result.put("publishDate", volumeInfo.getString("publishedDate"));

            // 描述
            result.put("description", volumeInfo.getString("description"));

            // ISBN
            JSONArray industryIds = volumeInfo.getJSONArray("industryIdentifiers");
            if (industryIds != null)
            {
                for (int i = 0; i < industryIds.size(); i++)
                {
                    JSONObject idObj = industryIds.getJSONObject(i);
                    if ("ISBN_13".equals(idObj.getString("type")))
                    {
                        result.put("isbn", idObj.getString("identifier"));
                        break;
                    }
                }
            }
            if (!result.containsKey("isbn"))
            {
                result.put("isbn", cleanIsbn);
            }

            // 封面图
            JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
            if (imageLinks != null)
            {
                String thumbnail = imageLinks.getString("thumbnail");
                if (thumbnail != null)
                {
                    // 替换 http→https，去掉 &zoom=1 等参数获取大图
                    result.put("coverImage", thumbnail.replace("http:", "https:"));
                }
            }

            // 分类
            JSONArray categories = volumeInfo.getJSONArray("categories");
            if (categories != null && !categories.isEmpty())
            {
                result.put("type", categories.getString(0));
            }

            log.info("Google Books API 查询成功: ISBN={}, 书名={}", cleanIsbn, result.get("bookName"));
        }
        catch (Exception e)
        {
            log.warn("Google Books API 查询失败: ISBN={}, 错误={}", isbn, e.getMessage());
        }

        return result;
    }
}
