package com.ruoyi.common.core.utils.trees;

import java.util.ArrayList;
import java.util.List;

/**
 * Tree node
 */
public class TreeNode {

    /**
     * ID
     */
    public Long id;

    /**
     * parentId
     */
    public Long pid;

    /**
     * Node name
     */
    public String label;

    /**
     * Child nodes
     */
    public List<TreeNode> children = new ArrayList<>();
}