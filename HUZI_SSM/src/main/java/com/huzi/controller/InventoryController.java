package com.huzi.controller;


import com.huzi.domain.Warehouse.Inventory;
import com.huzi.service.InventoryService;
import com.sun.imageio.plugins.tiff.TIFFNullCompressor;
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


    //2更改库存
    @RequestMapping("/updateInventory.do")
    public ModelAndView updateInventory(Inventory inventory){
        ModelAndView mv = new ModelAndView();
        String tip = null ;
        if(inventoryService.updateInventory(inventory)>0){
            tip = "更新成功";
        }

        mv.addObject("result",tip);
        mv.setViewName("result");
        return mv;
    }
}
