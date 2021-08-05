package com.liuxingyu.meco;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }


    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    RestTemplate restTemplate;


    /**
     * 测试使用JdbcTemplate做sql查询
     * 做了动态数据源之后，这里依然是好用的
     */
//    @Test
//    public void jdbcTest() {
//        // 查询List列表
//        List<Map<String, Object>> mapList = jdbcTemplate.queryForList("select * from sys_user_info ");
//        System.out.println("查询List列表 ---->" + mapList.get(0));
//
//        // 查询单个值String
//        String ssss = jdbcTemplate.queryForObject("select username from sys_user_info where id=?", String.class, 4);
//        System.out.println("查询单个值String ----->" + ssss);
//
//        // 查询单个值Map
//        Map user = jdbcTemplate.queryForMap("select * from stus where id=?",4);
//        System.out.println("查询单个值Map ----->" + user);
//
//        // 更新操作
//        int num = jdbcTemplate.update("update stus set username = ? where id = ? ","哈哈哈",4);
//
//        // 插入操作
//        int num2 = jdbcTemplate.update("insert into stus(username,password) values (?,?)","嘿嘿嘿","heiheihei");
//
//    }


    /**
     * 测试使用RestTemplate做http请求
     */
//    @Test
//    public void testRestTemplate() throws JsonProcessingException {
//        // 测试getForObject
//        String userId = "zhangsan";
//        String getUrl = "http://localhost:9630/cms-server/person/getAllPerson";
//        String accessTokenJson = restTemplate.getForObject(getUrl + "?userId={userId}", String.class, userId);
//        System.out.println("testRestTemplate --> getForObject --> " + accessTokenJson);
//
//        // 测试getForEntity,且占位符参数放在map里
//        Map<String, String> mmap = new HashMap<>();
//        mmap.put("userId", "zhangsan");
//        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:9630/cms-server/person/getAllPerson?userId={userId}", String.class, mmap);
//        System.out.println("testRestTemplate --> getForEntity --> " + responseEntity.getBody());
//
//        // 测试带header的get请求
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("token", "123");
//        ResponseEntity<String> response = restTemplate.exchange(
//                "http://localhost:9630/cms-server/person/getAllPerson?userId={userId}",
//                HttpMethod.GET,
//                new HttpEntity<String>(headers),
//                String.class,
//                userId);
//        System.out.println("testRestTemplate --> 测试带header的get请求 --> " + response.getBody());
//
//
//
//        // 测试postForObject - 以MultiValueMap传参
//        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
//        map.add("userId", "zhangsan");
//        String authJson = restTemplate.postForObject("http://localhost:9630/cms-server/person/postAllPerson", map, String.class);
//        System.out.println("testRestTemplate --> postForObject --> " + authJson);
//
//
//        // 测试postForObject - 以MultiValueMap传参，带header
//        MultiValueMap<String, String> mapsss = new LinkedMultiValueMap<>();
//        mapsss.add("userId", "zhangsan");
//        HttpHeaders headerr = new HttpHeaders();
//        headerr.add("token", "dai_header");
//        headerr.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        HttpEntity<MultiValueMap<String, String>> entitysss = new HttpEntity<>(mapsss, headerr);
//        String authJsonsss = restTemplate.postForObject("http://localhost:9630/cms-server/person/postAllPerson", entitysss, String.class);
//        System.out.println("testRestTemplate --> postForObject带header --> " + authJsonsss);
//
//
//
//        // 测试postForEntity，postForEntity可以设置header，功能更复杂
//        MultiValueMap<String, String> multmap= new LinkedMultiValueMap<String, String>();
//        multmap.add("userId","zhangsan");
//        HttpHeaders header = new HttpHeaders();
//        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        header.add("token", "posttoken");
//        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(multmap, header);
//        ResponseEntity<String> res = restTemplate.postForEntity("http://localhost:9630/cms-server/person/postAllPerson", entity, String.class);
//        System.out.println("testRestTemplate --> postForEntity --> " + res.getBody());
//
//
//
//        // 测试postForObject，以body传参，设置header
//        Map<String,String> param = new HashMap<String,String>();
//        param.put("userId", "zhangsan");
//        String json = JSONObject.toJSONString(param);
//        HttpHeaders headers3 = new HttpHeaders();
//        headers3.setContentType(MediaType.APPLICATION_JSON);
//        headers3.add("token", "posttokenbody");
//        HttpEntity<String> formEntity = new HttpEntity<String>(json, headers3);
//
//        String result = restTemplate.postForObject("http://localhost:9630/cms-server/person/bodyAllPerson", formEntity, String.class);
//        System.out.println("testRestTemplate --> postForObject以body传参 --> " + result);
//
//
//
//    }







}
