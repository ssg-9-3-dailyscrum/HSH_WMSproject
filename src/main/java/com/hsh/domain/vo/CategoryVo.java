package main.java.com.hsh.domain.vo;

import lombok.Data;

@Data
public class CategoryVo {
    private int categoryId;
    private int topCategoryId;
    private int categoryDepth;
    private String categoryName;
}
