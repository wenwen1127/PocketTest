package UserTest;

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
public class TestUserController {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void TestRegister() throws Exception {
        String result = mockMvc.perform(post("http://localhost:8080/user/register")
                .param("user_name", "cindy")        // 添加参数
                .param("user_phone", "184362846495")
                .param("user_mail", "cindy@126.com")
                .param("user_head", "384rbfddc355f3")
                .param("user_password", "123456")
                .contentType(MediaType.APPLICATION_JSON))      // 设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse().getContentAsString();        // 获取方法的返回值

//        Assert.assertEquals("12", result);
    }
    @Test
    public void TestLogin() throws Exception {
        String result = mockMvc.perform(get("http://localhost:8080/user/login")
                .param("user_phone", "12345678910")
                .param("user_password", "111111")
                .contentType(MediaType.APPLICATION_JSON))      // 设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse().getContentAsString();        // 获取方法的返回值

//        Assert.assertEquals("12", result);
    }
    @Test
    public void TestLogout() throws Exception {
        String result = mockMvc.perform(get("http://localhost:8080/user/logout")
                .contentType(MediaType.APPLICATION_JSON))      // 设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse().getContentAsString();        // 获取方法的返回值
    }
    @Test
    public void TestgetUserById() throws Exception {
        String result = mockMvc.perform(get("http://localhost:8080/user/get")
                .param("user_id", "3")
                .contentType(MediaType.APPLICATION_JSON))      // 设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse().getContentAsString();        // 获取方法的返回值

//        Assert.assertEquals("12", result);
    }
    @Test
    public void TestChangePassword() throws Exception {
        String result = mockMvc.perform(put("http://localhost:8080/user/changepwd")
                .param("user_id", "3")
                .param("user_password","111112")
                .param("new_password","147258")
                .contentType(MediaType.APPLICATION_JSON))      // 设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse().getContentAsString();        // 获取方法的返回值

//        Assert.assertEquals("12", result);
    }
    @Test
    public void TestEditUser() throws Exception {
        String result = mockMvc.perform(put("http://localhost:8080/user/edit")
                .param("user_id", "3")
                .param("user_phone","12345678910")
                .param("user_name","ZhangSan")
                .contentType(MediaType.APPLICATION_JSON))      // 设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse().getContentAsString();        // 获取方法的返回值

//        Assert.assertEquals("12", result);
    }
}