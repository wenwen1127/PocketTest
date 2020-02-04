package ProjectTest;

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
public class TestProjectVersionController {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void TestGetByVersionId() throws Exception {
        String result = mockMvc.perform(get("http://localhost:8080/project/version/get")
                .param("version_id", "1")        // 添加参数
                .contentType(MediaType.APPLICATION_JSON))      // 设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse().getContentAsString();        // 获取方法的返回值

//        Assert.assertEquals("12", result);
    }

    @Test
    public void TestAddProjectVersion() throws Exception {
        String result = mockMvc.perform(post("http://localhost:8080/project/version/add")
                .param("version_number", "3.0.1236")        // 添加参数
                .param("plan_launchdate","2019-07-01" )
                .param("actually_launchdate","2019-06-20")
                .param("plan_devstart","2019-07-01")
                .param("plan_devend","2018-08-23")
                .param("actually_devstart","2019-04-23")
                .param("actually_devend","2018-02-23")
                .param("plan_teststart","2019-06-23")
                .param("plan_testend","2018-08-23")
//                .param("actually_teststart","2019-05-10")
//                .param("actually_testend","2018-02-18")
                .param("testcasenum","53")
                .param("imprint","此版本增加了...")
                .param("remark","xxx 变更为 xxx")
                .param("project_id","1")
                .contentType(MediaType.APPLICATION_JSON))      // 设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse().getContentAsString();        // 获取方法的返回值

//        Assert.assertEquals("12", result);
    }

    @Test
    public void TestDeleteByVersionId() throws Exception {
        String result = mockMvc.perform(get("http://localhost:8080/project/version/delete")
                .param("version_id", "2")        // 添加参数
                .contentType(MediaType.APPLICATION_JSON))      // 设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse().getContentAsString();        // 获取方法的返回值

//        Assert.assertEquals("12", result);
    }

    @Test
    public void TestEditProjectVersion() throws Exception {
        String result = mockMvc.perform(get("http://localhost:8080/project/version/edit")
                .param("version_id","4")
                .param("plan_launchdate","2019-11-11" )
                .param("actually_launchdate","2019-12-12")        // 添加参数
                .contentType(MediaType.APPLICATION_JSON))      // 设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse().getContentAsString();        // 获取方法的返回值

//        Assert.assertEquals("12", result);
    }



}