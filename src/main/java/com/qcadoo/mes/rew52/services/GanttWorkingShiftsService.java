package com.qcadoo.mes.rew52.services;

import org.springframework.stereotype.Service;

import com.qcadoo.view.api.ComponentState;
import com.qcadoo.view.api.ViewDefinitionState;

@Service
public class GanttWorkingShiftsService {

    public void showGanttWorkingShiftCalendar(final ViewDefinitionState viewDefinitionState, final ComponentState triggerState,
                                       final String[] args) {
        System.out.println("showGanttWorkingShiftCalendar");
        viewDefinitionState.openModal("../page/ganttForShifts/ganttForShifts.html");
    }
}
