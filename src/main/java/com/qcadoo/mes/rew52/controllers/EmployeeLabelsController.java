package com.qcadoo.mes.rew52.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "rew52", method = RequestMethod.GET)
public class EmployeeLabelsController {

    @RequestMapping(value = "employeeLabelsReport.pdf")
    public final ModelAndView employeeLabelsReportPdf(@RequestParam("ids") final List<Long> ids) {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("employeeLabelsReportPdf"); // tên view map tới @Component
        mav.addObject("ids", ids); // truyền danh sách ID sang view

        return mav;
    }

}
