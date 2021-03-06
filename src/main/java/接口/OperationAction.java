package 接口;



import 逻辑.CommonURLService;
import 逻辑.NursingService;
import 逻辑.OperService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 手术Action
 *
 * @author 余涛
 * @date 2021/1/19
 */

public class OperationAction {

    private static final long serialVersionUID = 1L;

    private OperService operService;

    private CommonURLService commonURLService;

    private NursingService nursingService;
    /**
     * @Description 某次就诊的手术记录
     */
    public void getPatientVisitOperas() {
        int pno = StringUtils.isBlank(getParameter("pno")) ? 0 : Integer.parseInt(getParameter("pno"));
        //参数 患者编号 就诊次 就诊类型
        Page<Map<String, String>> result = operService.getOperList(patientId, visitType, visitId, "OPER_START_TIME",
                "desc", pno, pageSize);
        //响应
        renderJson(JsonUtil.getJSONString(result));
    }

    /**
     * @Description 某次手术的术前访视信息
     */
    public void getOperVisit() {
        //参数  医嘱号 手术序号
        String orderNo = getParameter("orderNo");
        String operNo = getParameter("operNo");
        Map<String, Object> result = operService.getOperVisit(patientId, orderNo, operNo);
        //响应
        renderJson(JsonUtil.getJSONString(result));
    }

    /**
     * @Description 某次手术的麻醉信息
     */
    public void getOperAnaes() {
        //参数  医嘱号 手术序号
        String orderNo = getParameter("orderNo");
        String operNo = getParameter("operNo");
        Map<String, Object> result = operService.getOperAnaes(patientId, orderNo, operNo);
        //响应
        renderJson(JsonUtil.getJSONString(result));
    }

    /**
     * @Description 某次手术的术中信息
     */
    public void getOperProcess() {
        //参数  医嘱号 手术序号
        String orderNo = getParameter("orderNo");
        String operNo = getParameter("operNo");
        Map<String, Object> result = operService.getOperProcess(patientId, orderNo, operNo);
        //响应
        renderJson(JsonUtil.getJSONString(result));
    }

    /**
     * @Description 某次手术的手术用药
     */
    public void getOperDrug() {
        //参数  医嘱号 手术序号
        String orderNo = getParameter("orderNo");
        String operNo = getParameter("operNo");
        Page<Map<String, String>> result = operService.getOperDrug(patientId, orderNo, operNo, pageNo, pageSize);
        //响应
        renderJson(JsonUtil.getJSONString(result));
    }

    /**
     * @Description 某次手术的恢复室信息
     */
    public void getOperRecovery() {
        //参数  医嘱号 手术序号
        String orderNo = getParameter("orderNo");
        String operNo = getParameter("operNo");
        Map<String, Object> result = operService.getOperRecovery(patientId, orderNo, operNo);
        //响应
        renderJson(JsonUtil.getJSONString(result));
    }

    /**
     * @Description 某次手术的术后访视信息
     */
    public void getOperAfter() {
        //参数  医嘱号 手术序号
        String orderNo = getParameter("orderNo");
        String operNo = getParameter("operNo");
        Map<String, Object> result = operService.getOperAfter(patientId, orderNo, operNo);
        //响应
        renderJson(JsonUtil.getJSONString(result));
    }

    /**
     * @Description 获得患者所有手术记录
     */
    public void getOpers() {
        String outPatientId = getParameter("outPatientId");
        String visitType = getParameter("visitType");
        Page<Map<String, String>> page = operService.getOpers(patientId, visitType, pageNo, pageSize, orderBy, orderDir,
                outPatientId);
        //响应
        renderJson(JsonUtil.getJSONString(page));
    }

    /**
     * 是否配置外部手术url
     */
    public void getOperConfig() {
        Map<String, String> list = operService.getOperConfig();
        //响应
        renderJson(JsonUtil.getJSONString(list));
    }

    /**
     * 若配置外部url,获取配置的外部url
     */
    public void getOperConfigUrl() {
        Map<String,String> paramMap = new HashMap<String,String>();
        String pn = getParameter("inp_no");
        if(StringUtils.isNotBlank(pn)) {
            paramMap.put("pn", pn);
        }
        Map<String, String> url = commonURLService.getCommonUrl(patientId, visitId, "OP", paramMap);
        //响应
        renderJson(JsonUtil.getJSONString(url));
    }

    /**
     * 若未配置外部url,获取手术麻醉单配置
     */
    public void getOperMenuConfig() {
    	List<Map<String, String>> map = operService.getAnesthesiaConfig();
        //响应
        renderJson(JsonUtil.getJSONString(map));
    }

    /**
     * 获取手术麻醉单数据
     */
    public void getAnesthesiaData(){
        String order_no = getParameter("operNo");
        String pn = getParameter("inpNo");
        Map<String,String> paramMap = new HashMap<String,String>();
        if(StringUtils.isNotBlank(pn)) {
            paramMap.put("pn", pn);
        }
        Map<String, String> url = commonURLService.getCommonUrl(patientId, visitId, "ANES", paramMap);
        System.out.println("****************url:"+url.get("url"));
        if(StringUtils.isNotBlank(url.get("url"))){
            //前端需要type去判断手麻的显示类型，这里暂时这样处理一下
            url.put("type", CommonConfig.getLinkType("ANES"));
            renderJson(JsonUtil.getJSONString(url));
        }else {
            List<Map<String, Object>> listMap = operService.getAnesthesiaData(patientId, order_no);
            renderJson(JsonUtil.getJSONString(listMap));
        }
    }

    /**
     * 是否显示手术列表
     */
    public void  getOperListConfig(){
       String config = Config.getSHOW_OPER_LIST();
        Map<String, String> configMap = new HashMap<String, String>();
        configMap.put("showList",config);
        renderJson(JsonUtil.getJSONString(configMap));
    }

    /**
     *
     * 住院的就诊列表
     * @param patientId 患者id
     * @param visitType 就诊类型，01门诊，02住院
     * @param year 年份
     */
    public void getAllINVisits(String patientId,String visitType,String year) {

       /*将参数传给service层接口*/
         nursingService.getAllINVisitsForOper(patientId,visitType, year);

    }

}
