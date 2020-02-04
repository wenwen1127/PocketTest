package SectionTest;


import com.pkt.StartApplication;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartApplication.class)
public class TestSectionController {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void TestGetBySectionId() throws Exception {
        String result = mockMvc.perform(get("http://localhost:8080/section/get")
                .param("section_id", "1")        // 添加参数
                .contentType(MediaType.APPLICATION_JSON))      // 设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse().getContentAsString();        // 获取方法的返回值

//        Assert.assertEquals("12", result);
    }

    @Test
    public void TestAddSection() throws Exception {
        String result = mockMvc.perform(post("http://localhost:8080/section/add")
                .param("section_name", "部门10")
                .param("level", "2")
                .param("parent_id", "2")        // 添加参数
                .contentType(MediaType.APPLICATION_JSON))      // 设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse().getContentAsString();        // 获取方法的返回值

//        Assert.assertEquals("12", result);
    }

    @Test
    public void TestDeleteSection() throws Exception {
        String result = mockMvc.perform(get("http://localhost:8080/section/delete")
                .param("section_id", "10")
                .contentType(MediaType.APPLICATION_JSON))      // 设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse().getContentAsString();        // 获取方法的返回值

//        Assert.assertEquals("12", result);
    }

    @Test
    public void TestEditSection() throws Exception {
        String result = mockMvc.perform(put("http://localhost:8080/section/edit")
                .param("section_id", "6")
                .param("level", "4")
                .param("section_name", "部门五")
                .contentType(MediaType.APPLICATION_JSON))      // 设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse().getContentAsString();        // 获取方法的返回值

//        Assert.assertEquals("12", result);
    }

    @Test
    public void TestQuerySubSection() throws Exception {
        String result = mockMvc.perform(get("http://localhost:8080/section/querysectionList")
                .param("section_id", "1")
                .contentType(MediaType.APPLICATION_JSON))      // 设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse().getContentAsString();        // 获取方法的返回值

    }

//    @Test
//    public void TestQueryFullSectionById() throws Exception {
//        String result = mockMvc.perform(get("http://localhost:8080/section/queryfullsection")
//                .param("section_id", "6")
//                .contentType(MediaType.APPLICATION_JSON))      // 设置数据格式
//                .andDo(print())     // 打印输出发出请求的详细信息
//                .andExpect(status().isOk())     // 对返回值进行断言
//                .andReturn().getResponse().getContentAsString();        // 获取方法的返回值
//
////        Assert.assertEquals("12", result);
//    }

}