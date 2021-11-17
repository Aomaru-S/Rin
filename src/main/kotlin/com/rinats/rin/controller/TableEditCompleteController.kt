package com.rinats.rin.controller

import com.rinats.rin.annotation.NonAuth
import com.rinats.rin.service.TableEditService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PostMapping
import javax.servlet.http.HttpServletRequest

@Controller
class TableEditCompleteController(
    @Autowired
    val tableEditService: TableEditService
) {
    @NonAuth
//    @PostMapping("/table_edit_complete")
    fun tableEditComplete(request: HttpServletRequest, model: Model,): String {
        val name = request.getParameter("name")
        val numOfPeople = Integer.parseInt(request.getParameter("numOfPeople"))
        println("################")
        tableEditService.tableUpdate(name, numOfPeople)
        return "TableEditCompletePage"
    }
}