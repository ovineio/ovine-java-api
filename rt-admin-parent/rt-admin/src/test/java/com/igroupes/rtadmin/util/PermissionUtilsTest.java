package com.igroupes.rtadmin.util;

import org.junit.Test;

import java.util.Set;

public class PermissionUtilsTest {
    @Test
    public void apiToPathTrie(){
        String apiDetail = "GET rtapi/system/role/item,GET rtapi/system/role/filter?filter=$term,GET rtapi/system/user/item,POST rtapi/system/user/item,PUT rtapi/system/user/item/$id,DELETE rtapi/system/user/item/$id,GET rtapi/system/user/tree,POST rtapi/system/role/item/$id,PUT rtapi/system/role/item/$id,DELETE rtapi/system/role/item/$id,GET rtapi/system/role/item/$id/limit,PUT rtapi/system/role/item/$id/limit,PUT rtapi/system/role/member,GET rtapi/system/log/item";
        RequestPathTrie<Set<String>> setRequestPathTrie =
                PermissionUtils.apiToPathTrie(apiDetail);
        Set<String> strings = setRequestPathTrie.find("/rtapi/system/user/item");
        System.out.println(strings);
        System.out.println(setRequestPathTrie);
    }
}

