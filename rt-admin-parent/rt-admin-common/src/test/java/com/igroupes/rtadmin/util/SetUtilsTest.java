package com.igroupes.rtadmin.util;

import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;

@RunWith(SpringRunner.class)
public class SetUtilsTest {

    @Test
    public void intersect(){
        HashSet<Integer> set1 = Sets.newHashSet(1, 2, 3);
        HashSet<Integer> set2 = Sets.newHashSet(1, 2, 4);
        Assert.assertEquals(2,SetUtils.intersect(set1,set2).size());
        Assert.assertEquals(1,SetUtils.diff(set1,set2).size());
        Assert.assertEquals(4,SetUtils.union(set1,set2).size());
    }


    @Test
    public void union(){

    }


    @Test
    public void diff(){

    }

}