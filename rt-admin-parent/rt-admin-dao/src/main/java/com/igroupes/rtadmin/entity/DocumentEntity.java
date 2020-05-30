package com.igroupes.rtadmin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("document")
public class DocumentEntity extends BaseEntity {
    @TableId
    private Long id;
    private String title;
    private String content;
    private String desc;
    @TableLogic // 逻辑删除
    private Integer isDel;
}
