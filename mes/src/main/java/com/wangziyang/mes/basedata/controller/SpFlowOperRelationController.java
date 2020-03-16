package com.wangziyang.mes.basedata.controller;


import com.wangziyang.mes.basedata.entity.SpFlow;
import com.wangziyang.mes.basedata.entity.SpOper;
import com.wangziyang.mes.basedata.request.SpTableManagerReq;
import com.wangziyang.mes.basedata.service.ISpFlowService;
import com.wangziyang.mes.basedata.service.ISpOperService;
import com.wangziyang.mes.basedata.vo.SpOperVo;
import com.wangziyang.mes.common.BaseController;
import com.wangziyang.mes.common.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 流程与工序关系控制器
 * </p>
 *
 * @author WangZiYang
 * @since 2020-03-14
 */
@Controller
@RequestMapping("/basedata/flow/process")
public class SpFlowOperRelationController extends BaseController {
    /**
     * 流程基础数据服务
     */
    @Autowired
    public ISpFlowService iSpFlowService;

    /**
     * 工序基础数据服务
     */
    @Autowired
    public ISpOperService iSpOperService;

    /**
     * 流程与工序关系管理界面
     *
     * @param model 模型
     * @return 流程与工序关系管理界面
     */
    @ApiOperation("流程与工序关系管理界面UI")
    @ApiImplicitParams({@ApiImplicitParam(name = "model", value = "模型", defaultValue = "模型")})
    @GetMapping("/list-ui")
    public String listUI(Model model) {
        return "basedata/flowprocess/list";
    }


    /**
     * 流程与工序关系管理编辑界面
     *
     * @param model  模型
     * @param record 平台表对象
     * @return 更改界面
     */
    @ApiOperation("流程与工序关系管理编辑界面")
    @GetMapping("/add-or-update-ui")
    public String addOrUpdateUI(Model model, SpFlow record) {
        List<SpOper> operList = iSpOperService.list();
        List<SpOperVo> spOperVos = new ArrayList<>();
        //得出全部的工序数据
        for (SpOper spOper : operList) {
            SpOperVo operVo = new SpOperVo();
            operVo.setValue(spOper.getId());
            operVo.setTitle(spOper.getOper());
            spOperVos.add(operVo);
        }
        model.addAttribute("allOper", spOperVos);
        if (StringUtils.isNotEmpty(record.getId())) {
            SpFlow flowbyId = iSpFlowService.getById(record.getId());
            //当前流程信息
            model.addAttribute("flow", flowbyId);
            // model.addAttribute("current", spOperVos);
        }
        return "basedata/flowprocess/addOrUpdate";
    }


    /**
     * 流程信息分页查询
     *
     * @param req 请求参数
     * @return Result 执行结果
     */
    @ApiOperation("主数据表头分页查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "req", value = "请求参数", defaultValue = "请求参数")})
    @PostMapping("/page")
    @ResponseBody
    public Result page(SpTableManagerReq req) {
        //   IPage result = iSpTableManagerService.page(req);
        return Result.success();
    }

}
