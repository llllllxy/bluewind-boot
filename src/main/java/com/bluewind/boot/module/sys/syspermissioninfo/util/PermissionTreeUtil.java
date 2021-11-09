package com.bluewind.boot.module.sys.syspermissioninfo.util;

import com.bluewind.boot.module.sys.syspermissioninfo.entity.LayuiTree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuxingyu01
 * @date 2021-02-05-23:19
 **/
public class PermissionTreeUtil {
    /**
     * 获取菜单树(list转tree)
     *
     * @param treeList
     * @param pid
     * @return
     */
    public static List<LayuiTree> toTree(List<LayuiTree> treeList, String pid) {
        List<LayuiTree> retList = new ArrayList<>();
        for (LayuiTree parent : treeList) {
            if (pid.equals(parent.getParentId())) {
                retList.add(findChildren(parent, treeList));
            }
        }
        return retList;
    }

    /**
     * 获取子节点
     *
     * @param parent
     * @param treeList
     * @return
     */
    private static LayuiTree findChildren(LayuiTree parent, List<LayuiTree> treeList) {
        for (LayuiTree child : treeList) {
            if (parent.getPermissionId().equals(child.getParentId())) {
                // 这里是为了将父节点的选中状态置为false，为了适配前端eleTree
                // parent.setChecked(false); // eleTree设置了isDefaultChangePstatus属性，好像就不需要这么干了
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(findChildren(child, treeList));
            }
        }
        return parent;
    }


    /**
     * 菜单树tree转list
     *
     * @param treeList: 要转换的树结构数据
     * @return
     */
    public static List<LayuiTree> toList(List<LayuiTree> treeList) {
        List<LayuiTree> result = new ArrayList<>();
        for (LayuiTree node : treeList) {
            if (node.getChildren() != null && node.getChildren().size() != 0) {
                LayuiTree layuiTree = new LayuiTree();
                layuiTree.setParentId(node.getParentId());
                layuiTree.setPermissionId(node.getPermissionId());
                layuiTree.setTitle(node.getTitle());
                result.add(layuiTree);
                toListDF(node.getChildren(), result, node.getPermissionId());  //遍历子树,并加入到list中.
            } else {
                LayuiTree layuiTree = new LayuiTree();
                layuiTree.setParentId(node.getParentId());
                layuiTree.setPermissionId(node.getPermissionId());
                layuiTree.setTitle(node.getTitle());
                result.add(layuiTree);
            }
        }
        return result;
    }


    /**
     * 深度优先遍历树
     * 一个递归方法
     *
     * @param treeList:要转换的树结构数据
     * @param result:保存结果的列表结构数据，初始传list
     * @param parentId:当前遍历节点的父级节点id，初始为null(因为根节点无parentId)
     */
    private static void toListDF(List<LayuiTree> treeList, List<LayuiTree> result, String parentId) {
        for (LayuiTree node : treeList) {
            LayuiTree layuiTree = new LayuiTree();
            layuiTree.setParentId(parentId);
            layuiTree.setPermissionId(node.getPermissionId());
            layuiTree.setTitle(node.getTitle());
            result.add(layuiTree);
            if (node.getChildren() != null && node.getChildren().size() != 0) {
                toListDF(node.getChildren(), result, node.getPermissionId());  //遍历子树,并加入到list中.
            }
        }
    }


    /**
     * 获取某个父节点下的所有子节点
     *
     * @param menuList 这里传入的是id、pid形式数据，即从菜单表里查出来的原始记录，没经过树化的数据
     * @param pid 父节点
     * @return
     */
    public static List<LayuiTree> findChildList(List<LayuiTree> menuList, String pid) {
        // 子节点
        List<LayuiTree> childMenu = new ArrayList<>();
        return recursionChildList(menuList, pid, childMenu);
    }


    /**
     * 获取子节点
     *
     * @param menuList 这里传入的是id、pid形式数据，即从菜单表里查出来的原始记录，没经过树化的数据
     * @param pid 父节点
     * @return
     */
    private static List<LayuiTree> recursionChildList(List<LayuiTree> menuList, String pid, List<LayuiTree> childMenu) {
        for (LayuiTree mu : menuList) {
            // 遍历出父id等于参数的id，add进子节点集合
            if (mu.getParentId().equals(pid)) {
                // 递归遍历下一级
                recursionChildList(menuList, mu.getPermissionId(), childMenu);
                childMenu.add(mu);
            }
        }
        return childMenu;
    }


    /**
     * 获取某个子节点上的所有父节点
     *
     * @param menuList 这里传入的是id、pid形式数据，即从菜单表里查出来的原始记录，没经过树化的数据
     * @param childId 子节点
     * @return
     */
    public static List<LayuiTree> findParentList(List<LayuiTree> menuList, String childId) {
        // 子节点
        List<LayuiTree> parentMenu = new ArrayList<>();
        return recursionParentList(menuList, childId, parentMenu);
    }

    private static List<LayuiTree> recursionParentList(List<LayuiTree> menuList, String childId, List<LayuiTree> parentMenu) {
        for (LayuiTree mu : menuList) {
            if (mu.getPermissionId().equals(childId)) {
                recursionParentList(menuList, mu.getParentId(), parentMenu);
                parentMenu.add(mu);
            }
        }
        return parentMenu;
    }

}
