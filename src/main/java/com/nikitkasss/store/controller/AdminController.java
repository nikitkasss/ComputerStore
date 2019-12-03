package com.nikitkasss.store.controller;

import com.nikitkasss.store.dto.position.PositionInfoDto;
import com.nikitkasss.store.dto.position.PositionNameDto;
import com.nikitkasss.store.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private PositionService positionService;

    @RequestMapping(value="/addPosition", method = RequestMethod.GET)
    public String addPosition(Model model){
        PositionInfoDto dto = new PositionInfoDto();
        model.addAttribute("position", dto);
        return "admin/addPosition";
    }

    @RequestMapping(value="/addPosition", method = RequestMethod.POST)
    public String addPosition(@ModelAttribute PositionInfoDto dto, BindingResult errors, Model model) throws Exception {
        positionService.add(dto);
        return "redirect:/all/positions";
    }

    @RequestMapping(value = "/editPosition", method = RequestMethod.GET)
    public String editPosition(@RequestParam(value = "id", required = true) Long id, Model model) {
        PositionInfoDto dto = positionService.getById(id);
        model.addAttribute("position", dto);
        return "admin/editPosition";
    }

    @RequestMapping(value="/editPosition", method = RequestMethod.POST)
    public String editPosition(@ModelAttribute PositionInfoDto dto, BindingResult errors, Model model) throws Exception {
        positionService.edit(dto);
        return "redirect:/all/positions";
    }

    @RequestMapping(value = "/deletePosition", method = RequestMethod.GET)
    public String deletePosition(@RequestParam (value = "id", required = false) Long id,  Model model){
        if(id != null){
            PositionInfoDto dto = positionService.getById(id);
            positionService.delete(dto);
        }
        List<PositionInfoDto> positions = positionService.allPositions();
        model.addAttribute("positions", positions);
        return "admin/deletePosition";
    }


}
