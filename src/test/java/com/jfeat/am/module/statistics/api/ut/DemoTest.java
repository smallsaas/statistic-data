package com.jfeat.am.module.statistics.api.ut;

/**
 * Created by vincenthuang on 18/10/2017.
 */

import com.jfeat.am.base.BaseJunit;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by jackyhuang on 2017/10/16.
 */
public class DemoTest extends BaseJunit {

    @Before
    public void initData() {

    }

    @Test
    public void testCase()  throws Exception {
        String json = "";
        //RequestBuilder request = post("/api/applicants").content(json);
        //MvcResult result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
    }
}
