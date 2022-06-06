package com.bluewind.boot.module.system.index.service;

import com.bluewind.boot.common.config.security.SecurityUtil;
import com.bluewind.boot.common.utils.lang.StringUtils;
import com.bluewind.boot.module.system.config.entity.Config;
import com.bluewind.boot.module.system.config.service.ConfigService;
import com.bluewind.boot.module.system.index.mapper.IndexMapper;
import com.bluewind.boot.module.system.index.vo.MenuVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private ConfigService configService;

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
            // 此处修改是为了避免用户首次注册时，因为无菜单信息，进不去首页的情况。
            // Map<String, Object> returnMap = new HashMap<>();
            // returnMap.put("code", -1);
            // returnMap.put("message", "用户信息异常");
            // return returnMap;
            menuList = new ArrayList<>();
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
        map.put("menuInfo", toTree(menuInfo, "0"));

        Config config = configService.getSysConfig();

        home.put("title", "首页");
        // 控制器路由,自行定义
        home.put("href", contextPath + config.getHomepageHref());

        logo.put("title", config.getSystemName());
        logo.put("image", config.getSystemLogo());
        logo.put("href", "");

        map.put("homeInfo", home);
        map.put("logoInfo", logo);
        return map;
    }


    /**
     * 获取菜单的树状结构（双重遍历法list转tree）
     * @param menuList 菜单列表
     * @param topPid 顶级ID
     * @return
     */
    private List<MenuVo> toTree(List<MenuVo> menuList, String topPid) {
        Map<String, List<MenuVo>> menuMap = new HashMap<>();

        menuList.forEach(node -> {
            List<MenuVo> children = menuMap.getOrDefault(node.getParentId(), new ArrayList<>());
            children.add(node);
            menuMap.put(node.getParentId(), children);
        });

        menuList.forEach(node -> node.setChild(menuMap.get(node.getPermissionId())));

        List<MenuVo> result = menuList.stream().filter(v -> v.getParentId().equals(topPid)).collect(Collectors.toList());
        return result;
    }

}
