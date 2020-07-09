package com.shuzheng.holmes.common.utils.tree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class TreeNode<T> {
    private String root;
    private String me;
    private String parent;
    private T entity;
    private List<TreeNode<T>> childrens;

}
