package cn.bdqn.controller;

import cn.bdqn.pojo.User;
import cn.bdqn.service.UserService;
import cn.bdqn.util.PageBean;
import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
    private static Logger logger=Logger.getLogger(UserController.class);

    @Resource(name="userService")
    private UserService userService;

    @RequestMapping("/login.html")
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/logindo.html")
    public String logindo(@RequestParam("userCode") String userCode,
                        @RequestParam("userPassword")String userPassword,
                        HttpSession session,Model model){
        boolean userCode1 = userService.findUserCode(userCode);
        if(userCode1){
            User user = userService.login(userCode, userPassword);
            if(user==null){
                model.addAttribute("msg","密码不正确");
                return login();
            }
            session.setAttribute("user",user );
            return "redirect:/user/frame.html";
        }else {
            model.addAttribute("msg","用户名不存在");
            return login();
        }
    }



    @RequestMapping("/frame.html")
    public String frame(){
        return "frame";
    }


    @RequestMapping("/logout.do")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/user/login.html";
    }



    @ExceptionHandler(value = {RuntimeException.class})
    public String handlerException(RuntimeException e, HttpServletRequest request){
        request.setAttribute("e",e );
        return "error";
    }


    @RequestMapping(value = "/user.do")
    public String userPage(Model model,
                           @RequestParam(value = "userName",required = false) String userName,
                           @RequestParam(value = "userRole",required = false) Integer userRole,
                           @RequestParam(value = "pageIndex",required = false,defaultValue = "1") Integer pageIndex,
                           @RequestParam(value = "pageSize",required = false,defaultValue = "5") Integer pageSize
                           ){
        PageBean<User> userList = userService.findUserByRoleAndLikeName(userRole, userName, pageIndex, pageSize);
        model.addAttribute("pageBean", userList);
        return "userlist";
    }

    @RequestMapping(value = "/userlist.html")
    public String userList(){
      return "";
    }


    @RequestMapping(value = "userview.do/{uid}")
    @ResponseBody
    public String view(@PathVariable(value = "uid") Integer id,Model model){
        User user = userService.findUserId(id);
        model.addAttribute("user",user);
        String json=JSON.toJSONString(user);
        return json;
    }


    @RequestMapping(value = "/pwdmodify.html")
    public String pwdmodify(){
        return "pwdmodify";
    }


//    判断密码是否正确
    @RequestMapping(value = "/pwdmodify.do",method = RequestMethod.POST)
    @ResponseBody
    public String pwdDo(@RequestParam(value = "oldpassword") String password,
//                        @RequestParam(value = "newpassword") String newPwd,
//                        @RequestParam(value = "rnewpassword") String rnewPwd,
                        HttpSession session,Model model){
        User user= (User) session.getAttribute("user");
        User login = userService.login(user.getUserCode(), password);

        if(user==null){
            model.addAttribute("result","sessionerror");
        }
        if(login!=null){
            //旧密码正确
            model.addAttribute("result","true");
        }else {
            //密码为空
            if(password==null){
                model.addAttribute("result","error");
            }else {
                //旧密码不正确
                model.addAttribute("result","false");
            }
        }
        return JSON.toJSONString(model);
    }


    @RequestMapping(value = "modifyPw")
    public String modifyPw(@RequestParam(value = "oldpassword") String password,
                        @RequestParam(value = "newpassword") String newPwd,
                        @RequestParam(value = "rnewpassword") String rnewPwd,
                           HttpSession session,Model model){
        User user = (User) session.getAttribute("user");
        int i=-1;
        if(user!=null&&password!=null&&rnewPwd!=null){
            i = userService.updatePw(user.getId(), newPwd);
        }else {
            model.addAttribute("msg","请登陆");
            return "error";
        }

        if(i>0){
            model.addAttribute("result","true");
            return "login";
        }
        return null;
    }


}
