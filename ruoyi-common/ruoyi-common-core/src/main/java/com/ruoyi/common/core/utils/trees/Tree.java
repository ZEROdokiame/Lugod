package com.ruoyi.common.core.utils.trees;

import java.util.ArrayList;
import java.util.List;

/**
 * 树形菜单生成工具类
 * @param <T>
 */
public class Tree <T>{

    /**
     * 扁平化的树形结构
     */
    protected List<TreeNode> flatTrees = new ArrayList<>();

    /**
     * 所有根节点
     */
    public List<TreeNode> rootTrees = new ArrayList<>();

    /**
     * Id key标识
     */
    protected String idFlag;

    /**
     * pid key标识
     */
    protected String pidFlag;

    /**
     * name key标识
     */
    protected String nameFlag;

    /**
     * 使用默认属性名
     */
    public Tree() {
        this("MenuId", "ParentId", "MenuName");
    }

    /**
     * 自定义修改属性名
     * @param idFlag
     * @param pidFlag
     * @param nameFlag
     */
    public Tree(String idFlag, String pidFlag, String nameFlag) {
        this.idFlag = idFlag;
        this.pidFlag = pidFlag;
        this.nameFlag = nameFlag;
    }

    /**
     * 生成树形菜单
     * @return
     */
    public List<TreeNode> generateTrees(List<T> anyTrees) throws Exception {

        // 处理给定的树形结构
        screenTrees(anyTrees);

        // 找到父节点下面所有子节点 - 递归
        generateRecursionTrees();

        return rootTrees;
    }

    /**
     * 从任意扁平树结构中生成待处理的扁平化的树形结构 以及 根节点
     * @param anyTrees
     * @return
     */
    protected void screenTrees(List<T> anyTrees) throws Exception {
        for (T anyTree: anyTrees) {
            TreeNode treeNode = new TreeNode();

            try {
                treeNode.id = (Long) anyTree.getClass().getMethod("get" + idFlag).invoke(anyTree);
                treeNode.pid = (Long) anyTree.getClass().getMethod("get" + pidFlag).invoke(anyTree);
                treeNode.label = (String) anyTree.getClass().getMethod("get" + nameFlag).invoke(anyTree);
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }

            // 提取root节点和子节点
            if (treeNode.pid == 0L) {
                this.rootTrees.add(treeNode);
            } else {
                this.flatTrees.add(treeNode);
            }
        }
    }

    /**
     * 使用递归方式生成树形结构
     */
    protected void generateRecursionTrees() {
        for (TreeNode rootTreeNode : rootTrees) {
            recursionFunction(rootTreeNode);
        }
    }

    /**
     * 递归函数
     * @param rootTreeNode
     */
    protected void recursionFunction(TreeNode rootTreeNode) {

        List<TreeNode> usedTreeNode = new ArrayList<>();

        for(TreeNode treeNode : flatTrees) {
            if (treeNode.pid == rootTreeNode.id) {
                rootTreeNode.children.add(treeNode);
                // 标记使用过的node
                usedTreeNode.add(treeNode);
            }
        }

        // 删除标记的node
        flatTrees.removeAll(usedTreeNode);

        // 终止条件
        if (usedTreeNode.size() == 0) {
            return;
        }

        // 递归
        for (TreeNode treeNode: usedTreeNode) {
            recursionFunction(treeNode);
        }
    }

}
