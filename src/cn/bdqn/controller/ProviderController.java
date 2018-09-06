package cn.bdqn.controller;
import cn.bdqn.pojo.Provider;
import cn.bdqn.pojo.User;
import cn.bdqn.service.ProviderService;
import cn.bdqn.util.PageBean;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

@Controller
@RequestMapping(value = "/provider")
public class ProviderController {
    private static Logger logger=Logger.getLogger(ProviderController.class);


    @Resource(name="providerService")
    private ProviderService providerService;


    /**
     * 分页显示
     * @param model
     * @param proCode
     * @param ProName
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/providerList.html")
    public String addPro(Model model,
                         @RequestParam(value = "proCode",required = false) String proCode,
                         @RequestParam(value = "ProName",required = false) String ProName,
                         @RequestParam(value = "pageIndex",required = false,defaultValue = "1") Integer pageIndex,
                         @RequestParam(value = "pageSize",required = false,defaultValue = "5") Integer pageSize
                         ){
        PageBean<Provider> pageBean = providerService.findProviderByPage(proCode, ProName, pageIndex, pageSize);
        model.addAttribute("pageBean",pageBean);
        return "providerlist";
    }


    //显示详情
    @RequestMapping(value = "/providerview/{proId}")
    public String view(@PathVariable(value = "proId") Integer id,Model model){
        Provider pro = providerService.findProById(id);
        model.addAttribute("provider",pro);
        return "providerview";
    }


    //跳到增加页面
    @RequestMapping(value = "/providerAdd.html")
    public String add(@ModelAttribute("provider") Provider provider){
        return "provideradd";
    }

    //增加提交处理
    @RequestMapping(value = "/prodo",method = RequestMethod.POST)
    public String addDo(Provider provider, Model model, HttpSession session, @RequestParam(value = "proPaths",required = false) MultipartFile[] multipartFiles){
        //营业照
//        String proPath=null;
        //工作照
//        String workPath=null;

        //获取项目文件夹的服务器的绝对路径
        String realPath=session.getServletContext().getRealPath("statics"+File.separator+"upload");
//        String realPath1="E:/image"; //存在服务器

        logger.debug("realPath:"+realPath);
        boolean flag=false;
        for (int i = 0; i < multipartFiles.length; i++) {
            MultipartFile multipartFile = multipartFiles[i];

             //图片上传判断非空
            if(!multipartFile.isEmpty()){
                //获取原文件名
                String fileName = multipartFile.getOriginalFilename();

                //获取后缀
                String prifix = FilenameUtils.getExtension(fileName);

                //判断文件后缀
                if("jpg".equals(prifix)||"png".equals(prifix)) {

                    //判断大小
                    if (multipartFile.getSize() < 5000 * 1024) {
                        flag=true;
                        //新的文件名
                        String newFilName=System.currentTimeMillis()+new Random().nextInt(10000)+"_Personal.jpg";
                        logger.debug("newfile:"+newFilName);
                        //创建文件夹
                        new File(realPath).mkdirs();
                        File newFile=new File(realPath,newFilName);

                        //保存
                        try {
                            multipartFile.transferTo(newFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if(i==0){
//                            proPath=realPath+File.separator+newFilName;
                            provider.setProPath("statics"+File.separator+"upload"+File.separator+newFilName);
                        }else if(i==1){
//                            workPath=realPath+File.separator+newFilName;
                            provider.setWorkPath("statics"+File.separator+"upload"+File.separator+newFilName);
                        }

                    } else {
                        flag=false;
                        model.addAttribute("msg", "文件不能大于5M");
                        return "provideradd";
                    }
                }else {
                    flag=false;
                    model.addAttribute("msg","上传文件不支持该格式");
                    return "provideradd";
                }
            }

        }
        int add = providerService.add(provider);
        if(add>0){
            return "redirect:/provider/providerList.html";
        }else {
            model.addAttribute("msg","增加不成功" );
            return "provideradd";
        }

    }

    //跳到修改页面
    @RequestMapping(value = "/modify/{proId}")
    public String modify(@PathVariable(value = "proId") Integer proId,Model model){
        Provider proById = providerService.findProById(proId);
        logger.debug("proById:"+proById);
        model.addAttribute("provider", proById);
        return "providermodify";
    }

    //修改处理
    @RequestMapping(value = "/modify.do",method = RequestMethod.POST)
    public String modifyDo(@Valid Provider provider, HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        logger.debug("provider:"+provider);
        logger.debug("user:"+user);
        provider.setModifyBy(user.getId());
        provider.setModifyDate(new Date());
        int update = providerService.update(provider);
        if(update!=0){
            return "redirect:/provider/providerList.html";
        }else {
            model.addAttribute("msg","修改供应商不成功" );
            return "providermodify";
        }
    }


}
