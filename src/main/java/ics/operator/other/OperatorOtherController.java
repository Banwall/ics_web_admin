package ics.operator.other;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/operator")
public class OperatorOtherController {

	@RequestMapping("/other")
	public String main(Model model) {
		return "operator/other/operatorOther";
	}
}
