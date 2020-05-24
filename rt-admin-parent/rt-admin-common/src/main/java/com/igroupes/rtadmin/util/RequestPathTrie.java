package com.igroupes.rtadmin.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class RequestPathTrie<T> {


    /**
     * the root node of RequestPathTrie
     */
    private final TrieNode rootNode;

    @Data
    class TrieNode {
        T data = null;
        final HashMap<String, TrieNode> children;
        boolean hasWildcard; // 子路径中是不是含有通配符
        TrieNode parent = null;
        String path = null;

        /**
         * create a trienode with parent
         * as parameter
         *
         * @param parent the parent of this trienode
         */
        private TrieNode(TrieNode parent, String path) {
            children = new HashMap<String, TrieNode>();
            this.parent = parent;
            this.path = path;
        }

        /**
         * add a child to the existing node
         *
         * @param childName the string name of the child
         * @param node      the node that is the child
         */
        void addChild(String childName, TrieNode node) {
            synchronized (children) {
                if (children.containsKey(childName)) {
                    return;
                }
                if (!hasWildcard && childName.contains("$")) {
                    hasWildcard = true;
                }
                children.put(childName, node);
            }
        }


        /**
         * return the child of a node mapping
         * to the input childname
         *
         * @param childName the name of the child
         * @return the child of a node
         */
        TrieNode getChild(String childName) {
            synchronized (children) {
                return children.get(childName);
            }
        }

        @Override
        public String toString() {
            return path;
        }

    }


    /**
     * construct a new RequestPathTrie with
     * a root node of /
     */
    public RequestPathTrie() {
        this.rootNode = new TrieNode(null, "");
    }

    public boolean isRootNode() {
        if (this.rootNode == null) {
            return false;
        }
        return this.rootNode.children.size() == 0;
    }

    /**
     * add a path to the path trie
     *
     * @param path
     */
    public void addPath(String path, T data) {
        if (path == null) {
            return;
        }
        path = removePathStartTag(path);
        path = removeChildRedundantPath(path);
        String[] pathComponents = path.split("/");
        TrieNode parent = rootNode;
        String part = null;
        if (pathComponents.length <= 1) {
            throw new IllegalArgumentException("Invalid path " + path);
        }

        for (int i = 0; i < pathComponents.length; i++) {
            // 如果path是 aaa/bbb/ccc，相当于依次处理aaa, bbb, ccc
            part = pathComponents[i];
            path = removeChildRedundantPath(path);
            if (parent.getChild(part) == null) {
                // 添加子路径
                String childPath = PathUtils.normalize(parent.path, part);
                parent.addChild(part, new TrieNode(parent, childPath));
            }
            parent = parent.getChild(part);
        }
        // 表示当前节点的父节点(aaa/bbb)，已经设置了配额
        parent.setData(data);
    }

    /**
     * rtapi/system/role/filter?filter=$term
     * 如果子路径中存在?filter=$term这种情况，将?之后的去掉
     * @param path
     */
    private String removeChildRedundantPath(String path) {
        if(path.contains("?")){
           return path.substring(0, path.indexOf("?"));
        }
        return path;
    }


    private String removePathStartTag(String path) {
        if (StringUtils.isBlank(path)) {
            return path;
        }
        while (path.startsWith("/")) {
            path = path.substring(1);
        }
        return path;
    }


    /**
     * @param path the input path
     * @return the data for the input path.
     */
    public T find(String path) {
        if (path == null) {
            return null;
        }
        path = removePathStartTag(path);
        String[] pathComponents = path.split("/");
        if (pathComponents.length <= 1) {
            throw new IllegalArgumentException("Invalid path " + path);
        }
        // 从根目录查找
        List<TrieNode> parentList = Lists.list(rootNode);
        for (String pathComponent : pathComponents) {
            for (TrieNode parent : parentList) {
                // 尝试查找$开头的统配路径
                if (parent.hasWildcard) {
                    // 包含两个部分：统配+完全匹配
                    Map<String, TrieNode> childrenPath = parent.children;
                    List<TrieNode> tmpList = Lists.newArrayList();
                    for (Map.Entry<String, TrieNode> entry : childrenPath.entrySet()) {
                        if (entry.getKey().contains("$")) {
                            tmpList.add(parent.getChild(entry.getKey()));
                        }
                    }
                    TrieNode node = parent.getChild(pathComponent);
                    if (node != null && !tmpList.contains(node)) {
                        tmpList.add(node);
                    }
                    // 如果当前没有匹配上就情空
                    if (CollectionUtils.isEmpty(tmpList)) {
                        parentList = ListUtils.EMPTY_LIST;
                    } else {
                        parentList = tmpList;
                    }
                } else {
                    if (parent.getChild(pathComponent) != null) {
                        parentList = Lists.list(parent.getChild(pathComponent));
                    } else {
                        parentList = ListUtils.EMPTY_LIST;
                    }
                }
            }
        }

        List<TrieNode> validParentList = parentList.stream().filter(ele -> ele.getData() != null).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(validParentList))

        {
            return null;
        }
        if (validParentList.size() > 1)

        {
            log.error("path:{}匹配到多个路径：{}", path, pathListStr(validParentList));
            throw new IllegalArgumentException("match multiple paths");
        }
        return validParentList.get(0).

                getData();

    }


    private String pathListStr(List<TrieNode> nodeList) {
        if (CollectionUtils.isEmpty(nodeList)) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (TrieNode node : nodeList) {
            stringBuilder.append(node.path + ",");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }


}
