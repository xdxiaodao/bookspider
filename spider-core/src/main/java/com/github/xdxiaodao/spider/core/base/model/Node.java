package com.github.xdxiaodao.spider.core.base.model;

import com.alibaba.fastjson.JSON;
import com.github.xdxiaodao.spider.core.common.enums.NodeType;
import com.github.xdxiaodao.spider.core.common.interfaces.BookPageProcessor;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.Map;

/**
 * Created with bookspider
 * User zhangmz
 * Date 2017/5/26
 * Time 18:51
 * Desc
 */
public abstract class Node {
    private Long id;
    private Node parent;
    private String name;
    private String url;
    private Map<String, Node> childNodeMap = Maps.newConcurrentMap();
    private NodeType nodeType;

    public boolean addChildNode(Node childNode) {
        if (null == childNode || childNodeMap.containsKey(childNode.getName())) {
            return false;
        }

        childNodeMap.put(childNode.getName(), childNode);
        return true;
    }

    public Node getChildNode(String name) {
        return childNodeMap.get(name);
    }

    public Node parent(Node node) {
        this.parent = node;
        return this;
    }

    public Node name(String name) {
        this.name = name;
        return this;
    }

    public Node url(String url) {
        this.url = url;
        return this;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Node> getChildNodeMap() {
        return childNodeMap;
    }

    public void setChildNodeMap(Map<String, Node> childNodeMap) {
        this.childNodeMap = childNodeMap;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
