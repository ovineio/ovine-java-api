package com.igroupes.rtadmin.util;

import org.junit.Assert;
import org.junit.Test;

public class RequestPathTrieTest {

    @Test
    public void find() {
        RequestPathTrie requestPathTrie = new RequestPathTrie();
        requestPathTrie.addPath("aa/bb/cc/$id","path1");
        requestPathTrie.addPath("aa/bb/cc/$id","path2");
        requestPathTrie.addPath("aa/bb/cc/$id/123","path3");
//        Assert.assertEquals(requestPathTrie.find("/aa/bb/cc/$id"),"path2");
        Assert.assertEquals(requestPathTrie.find("aa/bb/cc/100"),"path2");
        Assert.assertEquals(requestPathTrie.find("aa/bb/cc/100/123"),"path3");
        Assert.assertEquals(requestPathTrie.find("aa/bb/cc/100/1234"),null);
    }
}
