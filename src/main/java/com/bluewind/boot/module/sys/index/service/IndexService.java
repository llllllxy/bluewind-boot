package com.bluewind.boot.module.sys.index.service;

import com.bluewind.boot.common.config.security.SecurityUtil;
import com.bluewind.boot.module.sys.index.mapper.IndexMapper;
import com.bluewind.boot.common.utils.lang.StringUtils;
import com.bluewind.boot.module.sys.index.util.MenuTreeUtil;
import com.bluewind.boot.module.sys.index.vo.MenuVo;
import com.bluewind.boot.module.sys.sysconfig.entity.SysConfig;
import com.bluewind.boot.module.sys.sysconfig.service.SysConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2020-03-22-15:27
 **/
@Service
public class IndexService {
    final static Logger logger = LoggerFactory.getLogger(IndexService.class);
    @Autowired
    private IndexMapper indexMapper;

    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 根据用户名查询一个账户
     */
    public Map findAccountByUserId(String userId) {
        return indexMapper.findAccountByUserId(userId);
    }

    /**
     * 修改密码
     */
    public int doUpdatePassword(Map map){
        return indexMapper.doUpdatePassword(map);
    }

    @Value("${server.servlet.context-path}")
    private String contextPath;


    /**
     * 初始化菜单
     *
     * @return
     */
    public Map<String, Object> menuInit() {
        String userId = SecurityUtil.getSysUserId();
        if (logger.isInfoEnabled()) {
            logger.info("IndexService -- menuInit -- userId = {} , contextPath = {}", userId, contextPath);
        }

        if (null == userId || "".equals(userId)) {
            return null;
        }

        Map<String, Object> map = new HashMap<>(16);
        Map<String,Object> home = new HashMap<>(16);
        Map<String,Object> logo = new HashMap<>(16);

        List<Map> menuList = indexMapper.menuInit(userId);

        // 在herf上拼接上context-path项目上下文
        if (StringUtils.isBlank(contextPath)) {
            contextPath = "";
        }
        if (menuList != null && !menuList.isEmpty()) {
            for (Map item : menuList) {
                String href = (String) item.get("href");
                if (StringUtils.isNotBlank(href)) {
                    item.put("href", contextPath + href);
                }
            }
        } else {
            Map<String, Object> returnMap = new HashMap<>();
            returnMap.put("code", -1);
            returnMap.put("message", "用户信息异常");
            return returnMap;
        }


        List<MenuVo> menuInfo = new ArrayList<>();

        for (Map temp : menuList) {
            MenuVo menuVO = new MenuVo();
            menuVO.setPermissionId((String)temp.get("permission_id"));
            menuVO.setParentId((String) temp.get("parent_id"));
            menuVO.setHref((String) temp.get("href"));
            menuVO.setTitle((String) temp.get("title"));
            menuVO.setIcon((String) temp.get("icon"));
            menuVO.setTarget((String) temp.get("target"));
            menuInfo.add(menuVO);
        }
        // 将菜单转成树结构
        map.put("menuInfo", MenuTreeUtil.toTree(menuInfo, "0"));

        SysConfig sysConfig = sysConfigService.getSysConfig();

        home.put("title", "首页");
        // 控制器路由,自行定义
        home.put("href", contextPath + sysConfig.getHomepageHref());

        logo.put("title", sysConfig.getSystemName());
        logo.put("image", sysConfig.getSystemLogo());
        logo.put("href", "");

        map.put("homeInfo", home);
        map.put("logoInfo", logo);
        return map;
    }

}
