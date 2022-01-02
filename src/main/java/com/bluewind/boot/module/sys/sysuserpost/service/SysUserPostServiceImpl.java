package com.bluewind.boot.module.sys.sysuserpost.service;

import com.bluewind.boot.module.sys.sysuserpost.entity.SysUserPost;
import com.bluewind.boot.module.sys.sysuserpost.mapper.SysUserPostMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuxingyu01
 * @date 2022-01-02-10:10
 **/
@Service
public class SysUserPostServiceImpl implements SysUserPostService {

    @Autowired
    private SysUserPostMapper sysUserPostMapper;

    /**
     * 用户岗位信息绑定
     * @param userId
     * @param postStrs
     * @return
     */
    public int userPostBind(String userId, String postStrs) {
        // 先全部删除用户旧的岗位
        int num = sysUserPostMapper.deleteUserPostByUserId(userId);

        // 保存用户新赋予的岗位
        if (StringUtils.isNotBlank(postStrs)) {
            String[] postArr = postStrs.split(",");
            List<SysUserPost> list = new ArrayList<>();
            SysUserPost sysUserPost = null;
            for (String item : postArr) {
                sysUserPost = new SysUserPost();
                sysUserPost.setUserId(userId);
                sysUserPost.setPostId(item);
                list.add(sysUserPost);
            }
            int numm = sysUserPostMapper.batchSaveUserPost(list);
            return numm;
        } else {
            return 0;
        }

    }

}
