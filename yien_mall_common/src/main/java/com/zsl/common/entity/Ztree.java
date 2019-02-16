package com.zsl.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elasticsearch.search.aggregations.metrics.geobounds.InternalGeoBounds;

/**
 * @author ${张世林}
 * @date 2019/01/26
 * 作用：创建一个用于记录对象类型的zTree
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ztree {

    private String id;

    private Integer pId;

    private String name;

    private String url;

    private String click;

}
