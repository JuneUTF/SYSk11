package kk.soft.co.jp.sys.controller;

import kk.soft.co.jp.sys.model.MessageInfoMstModel;
import kk.soft.co.jp.sys.service.MessageInfoMstService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/messageInfoMst")
@Slf4j
public class MainController {

    @Resource
    private MessageInfoMstService messageInfoMstService;

    @GetMapping
    public String indexInit() {
        return "index";
    }

    @PostMapping("/save")
    public String save(MessageInfoMstModel messageInfoMstModel, Model model){

        messageInfoMstService.insert(messageInfoMstModel);

        model.addAttribute("msg","登録OK");

       return "index";
    }

}
