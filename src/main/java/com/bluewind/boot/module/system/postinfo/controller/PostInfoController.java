package com.bluewind.boot.module.system.postinfo.controller;

import com.bluewind.boot.common.annotation.LogAround;
import com.bluewind.boot.common.base.BaseController;
import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.common.utils.DictUtils;
import com.bluewind.boot.common.utils.idgen.IdGenerate;
import com.bluewind.boot.module.system.postinfo.entity.PostInfo;
import com.bluewind.boot.module.system.postinfo.service.PostInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-12-01-14:11
 * @description 岗位信息管理
 **/
@Controller
@RequestMapping("/postinfo")
public class PostInfoController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(PostInfoController.class);

    @Autowired
    private PostInfoService postInfoService;


    /**
     * 岗位管理页面初始化
     *
     * @return
     */
    @ApiOperation(value = "岗位管理页面初始化", notes = "岗位管页面初始化")
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String init(Model model) {
        // 获取下拉栏枚举值
        List<Map<String,String>> baseDictList = DictUtils.getDictList("syspostinfo_status");
        model.addAttribute("baseDictList", baseDictList);
        return "system/postinfo/list";
    }


    /**
     * 岗位数据分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @LogAround("岗位数据分页查询")
    @ApiOperation(value = "岗位数据分页查询", notes = "岗位数据分页查询")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResult list(@RequestParam("page") Integer pageNum,
                           @RequestParam("limit") Integer pageSize,
                           @RequestParam(required = false, defaultValue = "", value = "postCode") String postCode,
                           @RequestParam(required = false, defaultValue = "", value = "postName") String postName,
                           @RequestParam(required = false, defaultValue = "", value = "status") String status,
                           @RequestParam(required = false, defaultValue = "", value = "sortName") String sortName,
                           @RequestParam(required = false, defaultValue = "", value = "sortOrder") String sortOrder) {
        PageHelper.startPage(pageNum, pageSize);

        Map<String, String> paraMap = new HashMap<>();
        paraMap.put("postCode", postCode);
        paraMap.put("postName", postName);
        paraMap.put("status", status);
        paraMap.put("sortName", sortName);
        paraMap.put("sortOrder", sortOrder);

        List<PostInfo> accounts = postInfoService.getSysPostInfoList(paraMap);

        PageInfo<PostInfo> pageinfo = new PageInfo<>(accounts);
        // 取出查询结果
        List<PostInfo> rows = pageinfo.getList();
        int total = (int) pageinfo.getTotal();
        Map<String, Object> result = new HashMap<>();
        result.put(RESULT_ROWS, rows);
        result.put(RESULT_TOTLAL, total);

        return BaseResult.success(result);
    }


    /**
     * 岗位信息新增页面初始化
     *
     * @return
     */
    @ApiOperation(value = "岗位信息新增页面初始化", notes = "岗位信息新增页面初始化")
    @RequestMapping(value = "/forAdd", method = RequestMethod.GET)
    public String forAdd(Model model) {
        // 获取下拉栏枚举值
        List<Map<String,String>> baseDictList = DictUtils.getDictList("syspostinfo_status");
        model.addAttribute("baseDictList", baseDictList);
        return "system/postinfo/add";
    }



    /**
     * 岗位信息新增
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult add(@RequestParam("postName") String postName,
                          @RequestParam("postCode") String postCode,
                          @RequestParam("postSort") Integer postSort,
                          @RequestParam("status") String status,
                          @RequestParam(required = false, defaultValue = "", value = "descript") String descript){
        PostInfo postInfo = new PostInfo();
        postInfo.setPostId(IdGenerate.nextId());
        postInfo.setPostName(postName);
        postInfo.setPostCode(postCode);
        postInfo.setPostSort(postSort);
        postInfo.setStatus(status);
        postInfo.setDescript(descript);
        postInfo.setCreateUser(getSysUserId());

        int num = postInfoService.add(postInfo);

        if (num > 0) {
            return BaseResult.success("新增岗位信息'" + postName + "'成功！");
        } else {
            return BaseResult.failure("新增岗位信息，请联系后台管理员！");
        }
    }


    /**
     * 岗位信息修改
     *
     * @return
     */
    @ApiOperation(value = "岗位信息修改页面初始化", notes = "岗位信息修改页面初始化")
    @RequestMapping(value = "/forUpdate/{postId}", method = RequestMethod.GET)
    public String forUpdate(Model model, @PathVariable String postId) {
        // 获取下拉栏枚举值
        List<Map<String,String>> baseDictList = DictUtils.getDictList("syspostinfo_status");
        PostInfo postInfo = postInfoService.getOne(postId);
        model.addAttribute("baseDictList", baseDictList);
        model.addAttribute("postInfo", postInfo);
        return "system/postinfo/update";
    }


    /**
     * 岗位信息更新
     *
     * @return
     */
    @ApiOperation(value = "岗位信息修改", notes = "岗位信息修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult update(@RequestParam("postId") String postId,
                             @RequestParam("postName") String postName,
                             @RequestParam("postCode") String postCode,
                             @RequestParam("postSort") Integer postSort,
                             @RequestParam("status") String status,
                             @RequestParam(required = false, defaultValue = "", value = "descript") String descript){
        PostInfo postInfo = new PostInfo();
        postInfo.setPostId(postId);
        postInfo.setPostName(postName);
        postInfo.setPostCode(postCode);
        postInfo.setPostSort(postSort);
        postInfo.setStatus(status);
        postInfo.setDescript(descript);
        postInfo.setCreateUser(getSysUserId());

        int num = postInfoService.update(postInfo);

        if (num > 0) {
            return BaseResult.success("修改岗位信息'" + postName + "'成功！");
        } else {
            return BaseResult.failure("修改岗位信息，请联系后台管理员！");
        }
    }


    /**
     * 岗位信息删除
     *
     * @return
     */
    @ApiOperation(value = "岗位信息删除", notes = "岗位信息删除")
    @RequestMapping(value = "/delete/{postId}/{postName}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult delete(@PathVariable String postId,@PathVariable String postName) {
        int num = postInfoService.delete(postId);
        if (num > 0) {
            return BaseResult.success("删除岗位信息'" + postName + "'成功！");
        } else {
            return BaseResult.failure("杀出岗位信息，请联系后台管理员！");
        }
    }

}
