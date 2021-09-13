package com.bluewind.boot.sys.index.util;

import com.bluewind.boot.sys.index.vo.MenuVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuxingyu01
 * @date 2021-01-05-20:03
 **/
public class TreeUtil {

    /**
     * 获取菜单树(list转tree)
     *
     * @param treeList
     * @param pid
     * @return
     */
    public static List<MenuVo> toTree(List<MenuVo> treeList, String pid) {
        List<MenuVo> retList = new ArrayList<>();
        for (MenuVo parent : treeList) {
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
    private static MenuVo findChildren(MenuVo parent, List<MenuVo> treeList) {
        for (MenuVo child : treeList) {
            if (parent.getPermissionId().equals(child.getParentId())) {
                if (parent.getChild() == null) {
                    parent.setChild(new ArrayList<>());
                }
                parent.getChild().add(findChildren(child, treeList));
            }
        }
        return parent;
    }


    /**
     * 通过菜单树获取list(tree转list)
     *
     * @param menuList: 要转换的树结构数据
     * @return
     */
    public static List<MenuVo> toList(List<MenuVo> menuList) {
        List<MenuVo> result = new ArrayList<>();
        for (MenuVo node : menuList) {
            if (node.getChild() != null && node.getChild().size() != 0) {
                MenuVo menuVo = new MenuVo();
                menuVo.setParentId(node.getParentId());
                menuVo.setPermissionId(node.getPermissionId());
                menuVo.setTitle(node.getTitle());
                result.add(menuVo);
                toListDF(node.getChild(), result, node.getPermissionId());  //遍历子树,并加入到list中.
            } else {
                MenuVo menuVo = new MenuVo();
                menuVo.setParentId(node.getParentId());
                menuVo.setPermissionId(node.getPermissionId());
                menuVo.setTitle(node.getTitle());
                result.add(menuVo);
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
    private static void toListDF(List<MenuVo> treeList, List<MenuVo> result, String parentId) {
        for (MenuVo node : treeList) {
            MenuVo menuVo = new MenuVo();
            menuVo.setParentId(parentId);
            menuVo.setPermissionId(node.getPermissionId());
            menuVo.setTitle(node.getTitle());
            result.add(menuVo);
            if (node.getChild() != null && node.getChild().size() != 0) {
                toListDF(node.getChild(), result, node.getPermissionId());  //遍历子树,并加入到list中.
            }
        }
    }


    /**
     * 获取某个父节点下的所有子节点
     *
     * @param menuList 这里传入的是id、pid形式数据，即从菜单表里查出来的原始记录，没经过树化的数据
     * @param pid      父节点
     * @return
     */
    public static List<MenuVo> findChildMenu(List<MenuVo> menuList, String pid) {
        //子节点
        List<MenuVo> childMenu = new ArrayList<>();
        return treeMenuList(menuList, pid, childMenu);
    }


    /**
     * 获取子节点
     *
     * @param menuList 这里传入的是id、pid形式数据，即从菜单表里查出来的原始记录，没经过树化的数据
     * @param pid      父节点
     * @return
     */
    public static List<MenuVo> treeMenuList(List<MenuVo> menuList, String pid, List<MenuVo> childMenu) {
        for (MenuVo mu : menuList) {
            //遍历出父id等于参数的id，add进子节点集合
            if (mu.getParentId().equals(pid)) {
                //递归遍历下一级
                treeMenuList(menuList, mu.getPermissionId(), childMenu);
                childMenu.add(mu);
            }
        }
        return childMenu;
    }

}
