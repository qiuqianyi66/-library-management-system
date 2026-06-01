package com.ruoyi.common.core.domain.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.annotation.Excel.Type;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.xss.Xss;

/**
 * 图书对象 tb_book
 *
 * @author ruoyi
 */
public class Book extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 图书ID */
    @Excel(name = "图书序号", type = Type.EXPORT, cellType = ColumnType.NUMERIC, prompt = "图书编号")
    private Long bookId;

    /** 图书名称 */
    @Excel(name = "图书名称")
    private String bookName;

    /** ISBN编号 */
    @Excel(name = "ISBN编号")
    private String isbn;

    /** 图书类型 */
    @Excel(name = "图书类型", readConverterExp = "文学=文学,社科=社科,科技=科技,教育=教育,艺术=艺术,其他=其他")
    private String type;

    /** 作者 */
    @Excel(name = "作者")
    private String author;

    /** 出版社 */
    @Excel(name = "出版社")
    private String press;

    /** 出版日期 */
    @Excel(name = "出版日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date publishDate;

    /** 价格 */
    @Excel(name = "价格", cellType = ColumnType.NUMERIC)
    private BigDecimal price;

    /** 库存数量 */
    @Excel(name = "库存数量", cellType = ColumnType.NUMERIC)
    private Integer stockCount;

    /** 书架位置 */
    @Excel(name = "书架位置")
    private String shelfLocation;

    /** 封面图片 */
    private String coverImage;

    /** 描述 */
    @Excel(name = "描述")
    private String description;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public Book()
    {

    }

    public Book(Long bookId)
    {
        this.bookId = bookId;
    }

    public Long getBookId()
    {
        return bookId;
    }

    public void setBookId(Long bookId)
    {
        this.bookId = bookId;
    }

    @Xss(message = "图书名称不能包含脚本字符")
    @NotBlank(message = "图书名称不能为空")
    @Size(min = 0, max = 100, message = "图书名称长度不能超过100个字符")
    public String getBookName()
    {
        return bookName;
    }

    public void setBookName(String bookName)
    {
        this.bookName = bookName;
    }

    @Xss(message = "ISBN编号不能包含脚本字符")
    @Size(min = 0, max = 50, message = "ISBN编号长度不能超过50个字符")
    public String getIsbn()
    {
        return isbn;
    }

    public void setIsbn(String isbn)
    {
        this.isbn = isbn;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    @Xss(message = "作者不能包含脚本字符")
    @Size(min = 0, max = 50, message = "作者长度不能超过50个字符")
    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    @Xss(message = "出版社不能包含脚本字符")
    @Size(min = 0, max = 100, message = "出版社长度不能超过100个字符")
    public String getPress()
    {
        return press;
    }

    public void setPress(String press)
    {
        this.press = press;
    }

    public Date getPublishDate()
    {
        return publishDate;
    }

    public void setPublishDate(Date publishDate)
    {
        this.publishDate = publishDate;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public Integer getStockCount()
    {
        return stockCount;
    }

    public void setStockCount(Integer stockCount)
    {
        this.stockCount = stockCount;
    }

    @Size(min = 0, max = 50, message = "书架位置长度不能超过50个字符")
    public String getShelfLocation()
    {
        return shelfLocation;
    }

    public void setShelfLocation(String shelfLocation)
    {
        this.shelfLocation = shelfLocation;
    }

    public String getCoverImage()
    {
        return coverImage;
    }

    public void setCoverImage(String coverImage)
    {
        this.coverImage = coverImage;
    }

    @Size(min = 0, max = 500, message = "描述长度不能超过500个字符")
    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("bookId", getBookId())
            .append("bookName", getBookName())
            .append("isbn", getIsbn())
            .append("type", getType())
            .append("author", getAuthor())
            .append("press", getPress())
            .append("publishDate", getPublishDate())
            .append("price", getPrice())
            .append("stockCount", getStockCount())
            .append("shelfLocation", getShelfLocation())
            .append("coverImage", getCoverImage())
            .append("description", getDescription())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
