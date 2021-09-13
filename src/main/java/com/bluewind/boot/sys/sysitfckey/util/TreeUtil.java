package com.bluewind.boot.sys.sysitfckey.util;


import com.bluewind.boot.sys.sysitfckey.entity.ItfcPermissionTree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuxingyu01
 * @date 2021-02-05-23:19
 **/
public class TreeUtil {

    /**
     * 获取菜单树(list转tree)
     *
     * @param treeList
     * @param pid
     * @return
     */
    public static List<ItfcPermissionTree> toTree(List<ItfcPermissionTree> treeList, String pid) {
        List<ItfcPermissionTree> retList = new ArrayList<>();
        for (ItfcPermissionTree parent : treeList) {
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
    private static ItfcPermissionTree findChildren(ItfcPermissionTree parent, List<ItfcPermissionTree> treeList) {
        for (ItfcPermissionTree child : treeList) {
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
    public static List<ItfcPermissionTree> toList(List<ItfcPermissionTree> treeList) {
        List<ItfcPermissionTree> result = new ArrayList<>();
        for (ItfcPermissionTree node : treeList) {
            if (node.getChildren() != null && node.getChildren().size() != 0) {
                ItfcPermissionTree eleTree = new ItfcPermissionTree();
                eleTree.setParentId(node.getParentId());
                eleTree.setPermissionId(node.getPermissionId());
                eleTree.setTitle(node.getTitle());
                result.add(eleTree);
                toListDF(node.getChildren(), result, node.getPermissionId());  //遍历子树,并加入到list中.
            } else {
                ItfcPermissionTree eleTree = new ItfcPermissionTree();
                eleTree.setParentId(node.getParentId());
                eleTree.setPermissionId(node.getPermissionId());
                eleTree.setTitle(node.getTitle());
                result.add(eleTree);
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
    private static void toListDF(List<ItfcPermissionTree> treeList, List<ItfcPermissionTree> result, String parentId) {
        for (ItfcPermissionTree node : treeList) {
            ItfcPermissionTree eleTree = new ItfcPermissionTree();
            eleTree.setParentId(parentId);
            eleTree.setPermissionId(node.getPermissionId());
            eleTree.setTitle(node.getTitle());
            result.add(eleTree);
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
    public static List<ItfcPermissionTree> findChildMenu(List<ItfcPermissionTree> menuList, String pid) {
        // 子节点
        List<ItfcPermissionTree> childMenu = new ArrayList<>();
        return treeMenuList(menuList, pid, childMenu);
    }


    /**
     * 获取子节点
     *
     * @param menuList 这里传入的是id、pid形式数据，即从菜单表里查出来的原始记录，没经过树化的数据
     * @param pid 父节点
     * @return
     */
    public static List<ItfcPermissionTree> treeMenuList(List<ItfcPermissionTree> menuList, String pid, List<ItfcPermissionTree> childMenu) {
        for (ItfcPermissionTree mu : menuList) {
            // 遍历出父id等于参数的id，add进子节点集合
            if (mu.getParentId().equals(pid)) {
                // 递归遍历下一级
                treeMenuList(menuList, mu.getPermissionId(), childMenu);
                childMenu.add(mu);
            }
        }
        return childMenu;
    }
}
