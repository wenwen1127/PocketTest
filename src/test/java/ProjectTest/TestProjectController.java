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
public class TestProjectController {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void TestGetByProjectId() throws Exception {
        String result = mockMvc.perform(get("http://localhost:8080/project/get")
                .param("project_id", "1")        // 添加参数
                .contentType(MediaType.APPLICATION_JSON))      // 设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse().getContentAsString();        // 获取方法的返回值

//        Assert.assertEquals("12", result);
    }

    @Test
    public void TestAddProject() throws Exception {
        String result = mockMvc.perform(post("http://localhost:8080/project/add")
                .param("project_name", "项目6")
                .param("project_manager","Danel_Liu")
                .param("section_id", "1")        // 添加参数
                .contentType(MediaType.APPLICATION_JSON))      // 设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse().getContentAsString();        // 获取方法的返回值

//        Assert.assertEquals("12", result);
    }

    @Test
    public void TestDeleteProject() throws Exception {
        String result = mockMvc.perform(get("http://localhost:8080/project/delete")
                .param("project_id", "5")
                .contentType(MediaType.APPLICATION_JSON))      // 设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse().getContentAsString();        // 获取方法的返回值

//        Assert.assertEquals("12", result);
    }

    @Test
    public void TestEditProject() throws Exception {
        String result = mockMvc.perform(put("http://localhost:8080/project/edit")
                .param("project_id", "1")
                .param("project_name", "项目一")
                .param("project_manager","Li_Si")
                .contentType(MediaType.APPLICATION_JSON))      // 设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse().getContentAsString();        // 获取方法的返回值

//        Assert.assertEquals("12", result);
    }
}