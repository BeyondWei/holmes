package com.shuzheng.holmes.common.utils;

import lombok.Data;

@Data
public class LinkNode<T> {
    private LinkNode last;
    private LinkNode next;
    private T entity;
}
