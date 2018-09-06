package cn.bdqn.controller;
import cn.bdqn.pojo.Bill;
import cn.bdqn.service.BillService;
import cn.bdqn.util.PageBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/bill")
public class BillController {

    @Resource(name="billService")
    private BillService billService;


    @RequestMapping(value = "/billList.html")
    public String billList(Model model,
                           @RequestParam(value = "productName",required = false) String productName,
                           @RequestParam(value = "providerId",required = false) Integer providerId,
                           @RequestParam(value = "isPayment",required = false)Integer isPayment,
                           @RequestParam(value = "pageIndex",required = false,defaultValue = "1") Integer pageIndex,
                           @RequestParam(value = "pageSize",required = false,defaultValue = "5")Integer pageSize
                           ){
        PageBean<Bill> billPage = billService.findBillPage(productName, providerId, isPayment, pageIndex, pageSize);
        model.addAttribute("pageBean",billPage);
        return "billlist";


    }
}
