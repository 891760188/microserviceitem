package com.zpc.item.controller;

import com.zpc.item.entity.Item;
import com.zpc.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 查询商品信息
     * @param id 商品编号
     * @return  商品
     */
    @GetMapping(value = "item/{id}")
    public Item queryItemById(@PathVariable("id") Long id) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Item item = this.itemService.queryItemById(id);
        if(null != item){
            Field [] fields = item.getClass().getDeclaredFields();
            Map<String,Object> map = new HashMap<>();
            for (Field field : fields){
                String name = field.getName();
                name = name.substring(0,1).toUpperCase() + name.substring(1);//将属性首字母大写，方便构造get set方法
                Method m = item.getClass().getMethod("get"+name);
                map.put(name, m.invoke(item));
            }
            System.out.println(map);
        }
       return item;
    }

}
