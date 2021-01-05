package com.sb.web.controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** @title: RequestController @Author Wen @Date: 2021/1/5 9:36 @Version 1.0 */
@RestController
public class RequestController {

  /**
   * @PathVariable 支持单个取参
   *
   * @param id
   * @param username
   * @return
   */
  /*  @GetMapping("/car/{id}/owner/{username}")
  public Map<String, Object> getCar(
      @PathVariable("id") Integer id, @PathVariable("username") String username) {
    HashMap<String, Object> map = new HashMap<>();

    map.put("id", id);
    map.put("name", username);

    return map; //{"name":"lisi","id":1}
  }*/

  /**
   * 支持集合取参
   *
   * @param pv
   * @return
   */
  @GetMapping("/car/{id}/owner/{username}")
  public Map<String, Object> getCar(@PathVariable Map<String, Object> pv) {
    HashMap<String, Object> map = new HashMap<>();

    map.put("pv", pv);

    return map; // {"pv":{"id":"1","username":"lisi"}}
  }

  /**
   * 支持获取提定头部
   *
   * @param userAgent
   * @return
   */
  @GetMapping("/car")
  public Map<String, Object> getCarHeader(@RequestHeader("User-Agent") String userAgent) {
    HashMap<String, Object> map = new HashMap<>();

    map.put("user-agent", userAgent);

    return map; // {"user-agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36
    // (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36"}
  }

  /**
   * 获取所有的浏览头
   *
   * @param headers
   * @return
   */
  @GetMapping("/car/headers")
  public Map<String, Object> getCarHeaders(@RequestHeader Map<String, Object> headers) {
    HashMap<String, Object> map = new HashMap<>();

    map.put("user-headers", headers);

    return map; // {"user-headers":{"host":"localhost:8080","connection":"keep-alive","sec-ch-ua":"\"Google Chrome\";v=\"87\", \" Not;A Brand\";v=\"99\", \"Chromium\";v=\"87\"","sec-ch-ua-mobile":"?0","upgrade-insecure-requests":"1","user-agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36","accept":"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9","sec-fetch-site":"none","sec-fetch-mode":"navigate","sec-fetch-user":"?1","sec-fetch-dest":"document","accept-encoding":"gzip, deflate, br","accept-language":"zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7","cookie":"Hm_lvt_05a00fb1cd3344f314c9299fcdf8d950=1595488388; ECS[visit_times]=1; Phpstorm-aadd9b1f=cb597d65-3439-475b-a561-96311b524484"}}
  }

  /**
   * url:/car/param?age=18&inters=11&inters=22 获取指定参数,支持以集合方式获取同名传参
   *
   * @param age
   * @param inters
   * @return
   */
  @GetMapping("/car/param")
  public Map<String, Object> getCarRequestParam(
      @RequestParam("age") Integer age, @RequestParam("inters") List<String> inters) {
    HashMap<String, Object> map = new HashMap<>();

    map.put("age", age);
    map.put("inters", inters);
    return map; // {"inters":["11","22"],"age":18}
  }

  /** url:/car/param?age=18&inters=11&inters=22 获取参数集合 */
  @GetMapping("/car/params")
  public Map<String, Object> getCarRequestParams(@RequestParam Map<String, String> params) {
    HashMap<String, Object> map = new HashMap<>();

    map.put("params", params);
    return map; // {"params":{"age":"18","inters":"11"}}
  }

  /**
   * 获取指定cookie名,返回String类型
   *
   * @param name
   * @return
   */
  @GetMapping("/car/cookie")
  public Map<String, Object> getCarCookie(@CookieValue("Phpstorm-aadd9b1f") String name) {
    HashMap<String, Object> map = new HashMap<>();

    map.put("cookie name", name);
    return map; // {"cookie name":"cb597d65-3439-475b-a561-96311b524484"}
  }

  /**
   * 获取指定cookie名,返回Cookie类型
   *
   * @return
   */
  @GetMapping("/car/cookies")
  public Map<String, Object> getCarCookie(@CookieValue("Phpstorm-aadd9b1f") Cookie cookie) {
    HashMap<String, Object> map = new HashMap<>();

    map.put("cookie name", cookie.getName());
    map.put("cookie value", cookie.getValue());
    return map; // {"cookie name":"cb597d65-3439-475b-a561-96311b524484"}
  }

  /**
   * 获取表单提交过来的body信息
   * @param content
   * @return
   */
  @PostMapping("/save")
  public Map postMethod(@RequestBody String content) {
    HashMap<String, Object> map = new HashMap<>();
    map.put("content", content);
    return map; //{"content":"userName=zhangsan&email=test%40test.com"}
  }

  //1、语法： 请求路径：/cars/sell;low=34;brand=byd,audi,yd
  //2、SpringBoot默认是禁用了矩阵变量的功能
  //      手动开启：原理。对于路径的处理。UrlPathHelper进行解析。
  //              removeSemicolonContent（移除分号内容）支持矩阵变量的
  //3、矩阵变量必须有url路径变量才能被解析
  @GetMapping("/cars/{path}")
  public Map carsSell(@MatrixVariable("low") Integer low,
                      @MatrixVariable("brand") List<String> brand,
                      @PathVariable("path") String path){
    Map<String,Object> map = new HashMap<>();

    map.put("low",low);
    map.put("brand",brand);
    map.put("path",path);
    return map;
  }

  // /boss/1;age=20/2;age=10
  //当参数是有相同名时,需要指定矩阵参数名是跟在哪个参数后
  @GetMapping("/boss/{bossId}/{empId}")
  public Map boss(@MatrixVariable(value = "age",pathVar = "bossId") Integer bossAge,
                  @MatrixVariable(value = "age",pathVar = "empId") Integer empAge){
    Map<String,Object> map = new HashMap<>();

    map.put("bossAge",bossAge);
    map.put("empAge",empAge);
    return map;

  }
}
