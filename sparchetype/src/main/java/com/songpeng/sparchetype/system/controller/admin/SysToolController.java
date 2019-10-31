package com.songpeng.sparchetype.system.controller.admin;


import com.songpeng.sparchetype.common.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 前端工具集控制器
 * </p>
 *
 * @author SongPeng
 * @since 2019-10-31
 */
@Controller("adminSysToolController")
@RequestMapping("/admin/sys/tool")
@Slf4j
public class SysToolController extends BaseController {

	/**
	 * 图标列表
	 *
	 * @param model
	 * @return
	 */
	@GetMapping("/icon-ui")
	public String iconUI(Model model) {
		return "system/tool/icon";
	}

	/**
	 * 图标选择
	 *
	 * @param model
	 * @return
	 */
	@GetMapping("/icon-picker-ui")
	public String iconPickerUI(Model model) {
		return "system/tool/iconPicker";
	}

	/**
	 * 颜色选择
	 *
	 * @param model
	 * @return
	 */
	@GetMapping("/color-select-ui")
	public String colorSelectUI(Model model) {
		return "system/tool/colorSelect";
	}

	/**
	 * 颜色选择
	 *
	 * @param model
	 * @return
	 */
	@GetMapping("/editor-ui")
	public String editorUI(Model model) {
		return "system/tool/editor";
	}

}