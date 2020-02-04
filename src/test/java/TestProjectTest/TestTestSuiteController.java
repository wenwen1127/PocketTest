package TestProjectTest;

import com.pkt.StartApplication;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartApplication.class)
public class TestTestSuiteController {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void TestAddTestSuite() throws Exception {
        String result = mockMvc.perform(post("http://localhost:8080/test/testproject/suite/add")
                .param("suite_name", "suite2")
                .param("testmodule_id", "7")
                .contentType(MediaType.APPLICATION_JSON))      // 设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse().getContentAsString();        // 获取方法的返回值
    }

    @Test
    public void TestGetBySuiteID() throws Exception {
        String result = mockMvc.perform(post("http://localhost:8080/test/testproject/suite/get")
                .param("suite_id", "3")
                .contentType(MediaType.APPLICATION_JSON))      // 设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse().getContentAsString();        // 获取方法的返回值
    }

    @Test
    public void TestDeleteBySuiteId() throws Exception {
        String result = mockMvc.perform(get("http://localhost:8080/test/testproject/suite/delete")
                .param("suite_id", "4")
                .contentType(MediaType.APPLICATION_JSON))      // 设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse().getContentAsString();        // 获取方法的返回值
    }

    @Test
    public void TestEditSuite() throws Exception {
        String result = mockMvc.perform(put("http://localhost:8080/test/testproject/suite/edit")
                .param("suite_id", "5")
                .param("content","*** Settings ***\n" +
                        "Library             TestiCoreLib.py\n" +
                        "Resource            BasicResource.txt\n" +
                        "Suite Setup         PrepareENV\n" +
                        "Suite Teardown      ResetENV\n" +
                        "Test Teardown       ClearTest\n" +
                        "*** Variables ***\n" +
                        "${access_OP}\n" +
                        "${Mfilepath}        /Users/mac/TestiCore/TestData/XDR/TMP/FileEventResults/amaxfilesize\n" +
                        "${enable}     1\n" +
                        "${disable}    0\n" +
                        "${interval}   200\n" +
                        "\n" +
                        "*** Test Cases ***\n" +
                        "BasicFile_00001\n" +
                        "    [Tags]    RAT      XDR\n" +
                        "    ${filepath}   ${pname}   ${pexe}   ${cmdline}   ${pid}   ${folderpath}    create file \n" +
                        "    sleep   ${interval}\n" +
                        "    ${GPB}     ${GPBin}     GetTelemetry\n" +
                        "    ${FileMeta}    GenFileMeta     ${filepath}\n" +
                        "    ${FileID}    CheckFileMeta    ${FileMeta}    ${GPB}\n" +
                        "    ${proMeta}    CheckProMeta      ${pid}    ${pname}    ${cmdline}     ${pexe}     ${GPB}\n" +
                        "    ${access_OP}    set variable    create\n" +
                        "    should be equal    ${Result}    ${true}\n" +
                        "    ${Result}      CheckParentProMeta     ${ProMeta}    ${GPB}\n" +
                        "    should be equal    ${Result}    ${true}\n" +
                        "    ValidResult    ${GPBin}    ${GPB}\n" +
                        "\n" +
                        "BasicFile_00002\n" +
                        "    [Tags]    RAT    XDR\n" +
                        "    ${filepath}   ${pname}   ${pexe}   ${cmdline}   ${pid}   ${folderpath}    create file \n" +
                        "    ${filepath}    ${pname}     ${pexe}     ${cmdline}     ${pid}    append to file    ${filepath}\n" +
                        "    ${FileMeta}    GenFileMeta     ${filepath}\n" +
                        "    sleep   ${5}\n" +
                        "    ${filepath}   ${pname}   ${pexe}   ${cmdline}   ${pid}   delete file    ${filepath}\n" +
                        "    sleep   ${interval}\n" +
                        "    ${GPB}     ${GPBin}     GetTelemetry\n" +
                        "    ${FileID}    CheckFileMeta    ${FileMeta}    ${GPB}\n" +
                        "    ${proMeta}    CheckProMeta      ${pid}    ${pname}    ${cmdline}     ${pexe}     ${GPB}\n" +
                        "    ${access_OP}    set variable    delete\n" +
                        "    ${FileActi}    CheckFileActi    ${access_OP}    ${0}    ${FileID}     ${GPB}\n" +
                        "    ${Result}     CheckProAndActi   ${FileActi}       ${proMeta}\n" +
                        "    should be equal    ${Result}    ${true}\n" +
                        "    ${Result}      CheckParentProMeta     ${ProMeta}    ${GPB}\n" +
                        "    should be equal    ${Result}    ${true}\n" +
                        "    ValidResult    ${GPBin}    ${GPB}\n" +
                        "\n" +
                        "BasicFile_00003\n" +
                        "    [Tags]    RAT    XDR\n" +
                        "    ${filepath}   ${pname}   ${pexe}   ${cmdline}   ${pid}   ${folderpath}    create file \n" +
                        "    ${filepath}    ${pname}     ${pexe}     ${cmdline}     ${pid}    append to file    ${filepath}\n" +
                        "    ${FileMeta}    GenFileMeta     ${filepath}\n" +
                        "    sleep   ${5}\n" +
                        "    ${filepath}   ${dstfilepath}    ${pname}    ${pexe}    ${cmdline}    ${pid}   rename file    ${filepath}\n" +
                        "    sleep   ${interval}\n" +
                        "    ${GPB}     ${GPBin}     GetTelemetry\n" +
                        "    ${FileID}    CheckFileMeta    ${FileMeta}    ${GPB}\n" +
                        "    ${DstFileMeta}    GenFileMeta     ${dstfilepath}\n" +
                        "    ${DstFileID}    CheckFileMeta    ${DstFileMeta}    ${GPB}\n" +
                        "    ${proMeta}    CheckProMeta      ${pid}    ${pname}    ${cmdline}     ${pexe}     ${GPB}\n" +
                        "    ${access_OP}    set variable    move\n" +
                        "    ${FileActi}    CheckFileActi    ${access_OP}     ${FileID}    ${DstFileID}    ${GPB}\n" +
                        "    ${Result}     CheckProAndActi   ${FileActi}       ${proMeta}\n" +
                        "    should be equal    ${Result}    ${true}\n" +
                        "    ${Result}      CheckParentProMeta     ${ProMeta}    ${GPB}\n" +
                        "    should be equal    ${Result}    ${true}\n" +
                        "    ValidResult    ${GPBin}    ${GPB}\n" +
                        "\n" +
                        "BasicFile_00004\n" +
                        "    [Tags]    RAT    XDR\n" +
                        "    ${filepath}   ${pname}   ${pexe}   ${cmdline}   ${pid}   ${folderpath}    create file \n" +
                        "    ${filepath}    ${pname}     ${pexe}     ${cmdline}     ${pid}    append to file    ${filepath}\n" +
                        "    ${filepath}   ${pname}    ${pexe}    ${cmdline}    ${pid}   open file    ${filepath}\n" +
                        "    ${FileMeta}    GenFileMeta     ${filepath}\n" +
                        "    sleep   ${interval}\n" +
                        "    ${GPB}     ${GPBin}     GetTelemetry\n" +
                        "    ${FileID}    CheckFileMeta    ${FileMeta}    ${GPB}\n" +
                        "    ${proMeta}    CheckProMeta       ${pid}    ${pname}    ${cmdline}     ${pexe}     ${GPB}\n" +
                        "    ${access_OP}    set variable     close\n" +
                        "    ${FileActi}    CheckFileActi     ${access_OP}     ${0}     ${FileID}     ${GPB}\n" +
                        "    ${Result}     CheckProAndActi    ${FileActi}       ${proMeta}\n" +
                        "    should be equal    ${Result}     ${true}\n" +
                        "    ${Result}      CheckParentProMeta     ${ProMeta}    ${GPB}\n" +
                        "    should be equal    ${Result}    ${true}\n" +
                        "    ValidResult    ${GPBin}    ${GPB}\n" +
                        "\n" +
                        "BasicFile_00005\n" +
                        "    [Tags]    RAT   XDR\n" +
                        "    ${filepath}    ${pname}     ${pexe}     ${cmdline}     ${pid}    ${folderpath}    create file \n" +
                        "    ${filepath}    ${pname}     ${pexe}     ${cmdline}     ${pid}    append to file    ${filepath}\n" +
                        "    ${FileMeta}    GenFileMeta     ${filepath}\n" +
                        "    sleep    ${10}\n" +
                        "    ${filepath}    ${dstfilepath}     ${pname}     ${pexe}     ${cmdline}     ${pid}     copy file     ${filepath}\n" +
                        "    sleep   ${interval}\n" +
                        "    ${GPB}     ${GPBin}     GetTelemetry\n" +
                        "    ${FileID}    CheckFileMeta    ${FileMeta}    ${GPB}\n" +
                        "    ${DstFileMeta}    GenFileMeta     ${dstfilepath}\n" +
                        "    ${DstFileID}    CheckFileMeta    ${DstFileMeta}    ${GPB}\n" +
                        "    ${proMeta}    CheckProMeta       ${pid}    ${pname}    ${cmdline}     ${pexe}     ${GPB}\n" +
                        "    ${access_OP}    set variable     close\n" +
                        "    ${FileActi}    CheckFileActi     ${access_OP}      ${0}     ${FileID}    ${GPB}\n" +
                        "    ${Result}     CheckProAndActi    ${FileActi}       ${proMeta}\n" +
                        "    should be equal    ${Result}     ${true}\n" +
                        "    ${access_OP}    set variable     create\n" +
                        "    ${FileActi}    CheckFileActi     ${access_OP}      ${0}     ${DstFileID}    ${GPB}\n" +
                        "    ${Result}     CheckProAndActi    ${FileActi}       ${proMeta}\n" +
                        "    should be equal    ${Result}     ${true}\n" +
                        "    ${Result}      CheckParentProMeta     ${ProMeta}    ${GPB}\n" +
                        "    should be equal    ${Result}    ${true}\n" +
                        "    ValidResult    ${GPBin}    ${GPB}\n" +
                        "\n" +
                        "BasicFile_00007\n" +
                        "    [Tags]     RAT    XDR\n" +
                        "    ${filepath}    ${pname}     ${pexe}     ${cmdline}     ${pid}    ${folderpath}    create file \n" +
                        "    ${filepath}    ${pname}     ${pexe}     ${cmdline}     ${pid}    append to file    ${filepath}\n" +
                        "    ${FileMeta}    GenFileMeta     ${filepath}\n" +
                        "    sleep    ${10}\n" +
                        "    ${filepath}    ${dstfilepath}    ${pname}     ${pexe}    ${cmdline}    ${pid}    CompressFile    ${filepath}    ${folderpath}\n" +
                        "    sleep   ${interval}\n" +
                        "    ${GPB}     ${GPBin}     GetTelemetry\n" +
                        "    ${FileID}    CheckFileMeta    ${FileMeta}    ${GPB}\n" +
                        "    ${DstFileMeta}    GenFileMeta     ${dstfilepath}\n" +
                        "    ${DstFileID}    CheckFileMeta    ${DstFileMeta}    ${GPB}\n" +
                        "    ${tmpFileID}    FindTMPFileIDforZIP    ${folderpath}    ${GPB}\n" +
                        "    ${proMeta}    CheckProMeta       ${pid}    ${pname}    ${cmdline}     ${pexe}     ${GPB}\n" +
                        "    ${access_OP}    set variable     create\n" +
                        "    ${FileActi}    CheckFileActi     ${access_OP}      ${0}     ${tmpFileID}    ${GPB}\n" +
                        "    ${Result}     CheckProAndActi    ${FileActi}       ${proMeta}\n" +
                        "    should be equal    ${Result}     ${true}\n" +
                        "    ${access_OP}    set variable     close\n" +
                        "    ${FileActi}    CheckFileActi     ${access_OP}      ${0}     ${FileID}    ${GPB}\n" +
                        "    ${Result}     CheckProAndActi    ${FileActi}       ${proMeta}\n" +
                        "    should be equal    ${Result}     ${true}\n" +
                        "    ${FileActi}    CheckFileActi     ${access_OP}      ${0}     ${DstFileID}    ${GPB}\n" +
                        "    ${Result}     CheckProAndActi    ${FileActi}       ${proMeta}\n" +
                        "    should be equal    ${Result}     ${true}\n" +
                        "    ${access_OP}    set variable     delete\n" +
                        "    ${FileActi}    CheckFileActi     ${access_OP}      ${0}     ${DstFileID}    ${GPB}\n" +
                        "    ${Result}     CheckProAndActi    ${FileActi}       ${proMeta}\n" +
                        "    should be equal    ${Result}     ${true}\n" +
                        "    ${FileActi}    CheckFileActi     ${access_OP}      ${0}     ${tmpFileID}    ${GPB}\n" +
                        "    ${Result}     CheckProAndActi    ${FileActi}       ${proMeta}\n" +
                        "    should be equal    ${Result}     ${true}\n" +
                        "    ${access_OP}    set variable     move\n" +
                        "    ${FileActi}    CheckFileActi     ${access_OP}      ${tmpFileID}     ${DstFileID}    ${GPB}\n" +
                        "    ${Result}     CheckProAndActi    ${FileActi}       ${proMeta}\n" +
                        "    should be equal    ${Result}     ${true}\n" +
                        "    ${Result}      CheckParentProMeta     ${ProMeta}    ${GPB}\n" +
                        "    should be equal    ${Result}    ${true}\n" +
                        "    ValidResult    ${GPBin}    ${GPB}\n" +
                        "\n" +"BasicFile_00008\n" +
                        "    [Tags]    RAT    XDR\n" +
                        "    ${filepath}    ${pname}     ${pexe}     ${cmdline}     ${pid}    ${folderpath}     create file \n" +
                        "    ${filepath}    ${pname}     ${pexe}     ${cmdline}     ${pid}    append to file    ${filepath}\n" +
                        "    ${filepath}    ${dstfilepath}    ${pname}     ${pexe}    ${cmdline}    ${pid}    CompressFile    ${filepath}    ${folderpath}\n" +
                        "    ${filepath}    ${dstfilepath}    ${pname}     ${pexe}    ${cmdline}    ${pid}    DecompressFile    ${dstfilepath}\n" +
                        "    sleep   ${interval}\n" +
                        "    ${GPB}     ${GPBin}     GetTelemetry\n" +
                        "    ${FileMeta}    GenFileMeta     ${filepath}\n" +
                        "    ${FileID}    CheckFileMeta    ${FileMeta}    ${GPB}\n" +
                        "    ${DstFileMeta}    GenFileMeta     ${dstfilepath}\n" +
                        "    ${DstFileID}    CheckFileMeta    ${DstFileMeta}    ${GPB}\n" +
                        "    ${proMeta}    CheckProMeta       ${pid}    ${pname}    ${cmdline}     ${pexe}     ${GPB}\n" +
                        "    ${access_OP}    set variable     close\n" +
                        "    ${FileActi}    CheckFileActi     ${access_OP}      ${0}     ${FileID}    ${GPB}\n" +
                        "    ${Result}     CheckProAndActi    ${FileActi}       ${proMeta}\n" +
                        "    should be equal    ${Result}     ${true}\n" +
                        "    ${access_OP}    set variable     create\n" +
                        "    ${FileActi}    CheckFileActi     ${access_OP}      ${0}     ${DstFileID}    ${GPB}\n" +
                        "    ${Result}     CheckProAndActi    ${FileActi}       ${proMeta}\n" +
                        "    should be equal    ${Result}     ${true}\n" +
                        "    ${Result}      CheckParentProMeta     ${ProMeta}    ${GPB}\n" +
                        "    should be equal    ${Result}    ${true}\n" +
                        "    ValidResult    ${GPBin}    ${GPB}\n" +
                        "\n" +
                        "*** Keywords ***\n" +
                        "PrepareENV\n" +
                        "    SetXDRStatus\n" +
                        "    SetXDROption\n" +
                        "    SetXDRProductInfoReq\n" +
                        "    restart_xdr\n" +
                        "    ClearTelemetry\n" +
                        "ResetENV\n" +
                        "    ClearTelemetry\n" +
                        "    clear_TMP\n" +
                        "ClearTest\n" +
                        "    start_xdr\n" +
                        "    SetXDROption\n" +
                        "    ClearTelemetry\n" +
                        "ValidResult\n" +
                        "    [Arguments]    ${GPBin}    ${GPB}\n" +
                        "    ${Result}    ValidTelemetry    ${GPBin}\n" +
                        "    should be equal    ${Result}    ${true}\n" +
                        "    ${Result}    ValidTimeFormat    ${GPB}\n" +
                        "    should be equal    ${Result}    ${true}\n")
                .contentType(MediaType.APPLICATION_JSON))      // 设置数据格式
                .andDo(print())     // 打印输出发出请求的详细信息
                .andExpect(status().isOk())     // 对返回值进行断言
                .andReturn().getResponse().getContentAsString();        // 获取方法的返回值
    }
}