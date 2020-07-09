package com.shuzheng.holmes.common.utils.tree;

import com.shuzheng.holmes.common.utils.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 操作树的工具
 */
public class TreeUtil {

    /**
     * 生成树
     *
     * @param list
     * @return
     */
    public static <T> List<TreeNode<T>> getTree(List<TreeNode<T>> list) {
        List<TreeNode<T>> treeList = new ArrayList<>();
        //找到根节点
        List<TreeNode<T>> tList = list.stream().filter(item -> StringUtils.isEmpty(item.getParent())).collect(Collectors.toList());
        if (tList.size() == 0) {
            throw new RuntimeException("没有找到根节点");
        }
        for (TreeNode<T> root : tList) {
            root.setRoot(root.getMe());
            TreeNode<T> treeNode = root.setChildrens(addChildren(list, root));
            treeList.add(treeNode);
        }
        return treeList;
    }

    /**
     * update by Chen Zhaofeng 修改的形参，避免造成误解
     * @param list
     * @param treeNode
     * @param <T>
     * @return
     */
    private static <T> List<TreeNode<T>> addChildren(List<TreeNode<T>> list, TreeNode<T> treeNode) {
        List<TreeNode<T>> treeNodeList = new ArrayList<>();
        for (TreeNode<T> node : list) {
            if (treeNode.getMe().equals(node.getParent())) {
                node.setRoot(treeNode.getRoot());
                node.setChildrens(addChildren(list, node));
                treeNodeList.add(node);
            }
        }
        return treeNodeList;
    }


    /**
     * 获取所有子节点
     */
    public static <T> List<T> getChildren(TreeNode<T> node) {
        List<T> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(node.getChildrens())) {
            for (TreeNode treeNode : node.getChildrens()) {
                list.addAll(getChildren(treeNode, new ArrayList<>()));
            }
        }
        return list;
    }

    private static <T> List<T> getChildren(TreeNode<T> node, List<T> treeNodeList) {
        treeNodeList.add(node.getEntity());
        if (!CollectionUtils.isEmpty(node.getChildrens())) {
            for (TreeNode treeNode : node.getChildrens()) {
                getChildren(treeNode, treeNodeList);
            }
        }
        return treeNodeList;
    }


}
