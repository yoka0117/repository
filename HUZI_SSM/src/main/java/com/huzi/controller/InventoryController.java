package com.huzi.controller;


import com.huzi.domain.Warehouse.Inventory;
import com.huzi.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/inventory")
public class InventoryController {


    @Autowired
    private InventoryService inventoryService;


    //1新建商品库存
    @RequestMapping("/insertInventory.do")
    public ModelAndView insertInventory(Inventory inventory){
        ModelAndView mv = new ModelAndView();
        String tip = "新增失败";
        int num = inventoryService.insertInventory(inventory);
        if (num > 0 ) {
            tip = "新增成功";
        }
        mv.addObject("result",tip);
        mv.setViewName("result");
        return mv;
    }


    //仓库管理员管理仓库状态
    @RequestMapping("/inventoryState.do")
    public ModelAndView inventoryState(){
        ModelAndView mv = new ModelAndView();
        String tip = "";



        mv.addObject("result",tip);
        mv.setViewName("result");
        return mv;
    }
}
