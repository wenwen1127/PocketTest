package TestProjectTest;

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
public class TestTestProjectController {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void TestGetByTestProjectId() throws Exception {
        String result = mockMvc.perform(get("http://localhost:8080/test/testproject/get")
                .param("testproject_id", "3")        // 添加参数
                .contentType(MediaType.APPLICATION_JSON))      // 设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse().getContentAsString();        // 获取方法的返回值
    }

    @Test
    public void TestAddTestProject() throws Exception {
        String result = mockMvc.perform(post("http://localhost:8080/test/testproject/add")
                .param("testproject_name", "测试项目三")
                .param("testproject_path","/User/mac/Test/TestProject3")
                .param("section_id", "2")
                .param("project_id", "4")
                .param("version_id","1")
//                .param("create_time","2019-08-24")
                .contentType(MediaType.APPLICATION_JSON))      // 设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse().getContentAsString();        // 获取方法的返回值
    }

    @Test
    public void TestDeleteByTestProjectId() throws Exception {
        String result = mockMvc.perform(get("http://localhost:8080/test/testproject/delete")
                .param("testproject_id", "4")
                .contentType(MediaType.APPLICATION_JSON))      // 设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse().getContentAsString();        // 获取方法的返回值
    }

    @Test
    public void TestEditTestProject() throws Exception {
        String result = mockMvc.perform(put("http://localhost:8080/test/testproject/edit")
                .param("testproject_id", "2")
                .param("section_id", "6")
                .param("project_id","4")
                .contentType(MediaType.APPLICATION_JSON))      // 设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse().getContentAsString();        // 获取方法的返回值
    }

    @Test
    public void TestBatchDeleteTestProject() throws Exception {
        String result = mockMvc.perform(put("http://localhost:8080/test/testproject/batchdelete")

                .param("testproject_ids", "[1,2,3]")
                .contentType(MediaType.APPLICATION_JSON))      // 设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse().getContentAsString();        // 获取方法的返回值
    }
}
