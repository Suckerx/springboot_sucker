package com.sucker.commonutils;

/**
 * Controller响应信息
 *
 * @author rzcllove
 */

public class ResponseMsg {
    /**
     * NULL 没有响应信息
     */
    public static String NULL = "";

    /**
     * 上传文件失败
     */
    public static  String UPLOAD_FAIL="上传失败,请稍后重试!";

    /**
     * 用户不存在
     */
    public static String LOGINFAIL = "账户或密码错误";
    public static String NOUSER = "账户不存在";

    public static String ID_IS_EMPTY = "id为空";

    /**
     * 该帐号已经存在
     */
    public static String USER_HAS_EXIST = "该帐号已经存在";


    /**
     * 网关路由不存在
     */
    public static String ROUTER_NOT_EXIST = "网关路由不存在";

    /**
     * 未登录
     */
    public static String NOLOGIN = "你当前未登录,禁止请求";
    /**
     * 令牌无效
     */
    public static String TOKENERR = "令牌无效";
    /**
     * 令牌与用户不匹配
     */
    public static String TOKEN_USER_ERR = "令牌与用户不匹配";
    /**
     * 账户不可用
     */
    public static String DISABLE = "当前账户不可用";
    /**
     * 无权限操作
     */
    public static String NOAUTH = "无权限操作";
    /**
     * 踢出用户
     */
    public static String LOGINOUT = "当前账号已在其他设备登录";
    /**
     * 用户未登录
     */
    public static String NOLOGIN_1 = "用户未登录";
    /**
     * 用户重新登录
     */
    public static String LOGIN_AGAIN = "请重新登录";
    /**
     * 登录超时
     */
    public static String LOGINOUT_TIME_OUT = "登录超时,请重新登录!";

    /**
     * 登录成功
     */
    public static String LOGIN_SUCCESS = "登录成功!";


    /**
     * 操作成功
     */
    public static String OPERATION_SUCCESS = "操作成功";
    /**
     * 操作失败
     */
    public static String OPERATION_FAIL = "操作失败";
    /**
     * 服务器异常
     */
    public static String SERVER_EXCEPTION = "服务器繁忙，请稍候重试";

    /**
     * 操作频繁
     */
    public static String OPERATION_TOO_OFTEN= "操作太频繁了！";
    /**
     * 无效参数
     */
    public static String ILLEGAL_PARAM = "无效参数";
    public static String MISS_PARAM = "缺少参数";
    public static String ERROR_PARAM = "参数错误，请检查参数";
    public static String DATE_ERROR_FORMAT = "日期格式错误";
    /**
     * 无效id
     */
    public static String ILLEGAL_PARAM_ID = "ID不存在";
    /**
     * 参数为空
     */
    public static String EMPTY_PARAM = "参数为空";


    /**
     * 用户注册登录相关提示
     */
    public static String INVITATIONCODE_ERROR = "验证码错误!";
    public static String INVITATIONCODE_ISEMPTY = "验证码未填写";
    public static String REGISTER_FILE = "用户注册失败！";
    public static String REGISTER_EMAIL_FILE = "用户邮箱绑定失败！";
    public static String SEND_EMAIL_FILE = "邮箱发送邮件失败！";
    public static String HIGH_USER_STATUS = "验证码无效！";
    public static String CANCLE_EMAIL_FILE = "用户邮箱解绑失败！";

    /**
     * 交易相关
     */
    public static String DEAL_PWD_ERROR = "交易密码错误！";
    public static String DEAL_BILL_ERROR = "订单信息错误！";
    public static String DEAL_NUM_LITTER = "交易数量不足！";
    public static String DEAL_COIN_LITTER = "账户%s资产不足！";
    public static String BILL_NOT_CANCEL = "订单已经不能取消";
    public static String BILL_HAVE_CANCEL = "订单已经取消";

    /************************角色*************************************/

    public static  String ROLE_HAVE_EXIST="该角色已经存在";
    public static  String ROLE_HAVE_OCCUPY_NO_DEL="该角色被占用，不能删除";

    /***********************权限**************************************/
    public static  String PERMISSION_NO_HAVE_PARENT_ID="该权限的父级id不存在！";
    public static  String FIRST_REMOVE_LOWER_PERMISSION="请先删除下级权限记录！";



    /*********************** 短信发送 ********************************/
    public static  String SMS_NO_TIME_MINUTE_1="请等60s后在发送！";

    /************************支付方式**********************************/
    public static  String PAY_WAY_TYPE_NO="没有该支付类型！";



    //============================地址管理===================
    public static String ADDR_HAS_EXIST = "该地址已经存在！";
    public static String NO_HAS_CODE = "没有该币种！";
    public static String ADDR_IS_NO_VALID = "该地址为无效地址";
    public static String DELETE_FAIL = "地址删除失败，请检查是否是有效地址";
    public static String CREATE_ADDR_FAIL = "创建地址失败，稍后重试";

    //====================转账 划转==================================
    public static String TRANSFER_NUMBER_NOT_ENOUGH  = "转出%s数量不足！";
    public static String TRANSFER_NOT_TO_SELF = "自己不能转给自己！";
    public static String TRANSFER_RATIO_NOT_EXIST = "划转汇率不正确，请联系管理员确认！";
    public static String TRANSFER_NOT_EQ_CODE = "相等币种之间不能划转！";
    public static String COIN_TRANSFER_NOT_EXIST = "转账记录不存在！";
    public static String COIN_TRANSFER_CHECK_FAIL = "审核记录状态不是待审核";


    //====================聊天==================================
    public static String STOCK_USER_IS_NOT_OPEN="当前用户未上线,不能发送消息";

    //===================修改密码===============================
    public static String OLD_NEW_PASSWORD_NOT_EQUAL="新密码与原密码不能相同";
    public static String OLD_PASSWORD_IS_ERROR="原密码错误";


    //==================数据库操作失败===============================
    public static String RESULT_NOT_EXIST="相关内容不存在";
    public static String INSERT_ERROR = "创建失败";
    public static String UPDATE_ERROR = "修改失败";
    public static String DELETE_ERROR = "删除失败";


    //===================K8s相关===============================
    public static String READ_CONFIG_ERROR="读取kubeConfigPath异常";
    public static String CREATE_CLIENT_ERROR="构建K8s-Client异常";
    public static String LOAD_FILE_ERROR="文件异常";
    public static String CREATE_POD_ERROR="创建Pod异常";
    public static String CREATE_POD_SYS_ERROR="创建Pod系统异常";
    public static String CREATE_SVC_ERROR="创建service异常";
    public static String CREATE_SVC_SYS_ERROR="创建service系统异常";
    public static String CREATE_IGS_ERROR="创建ingress异常";
    public static String CREATE_IGS_SYS_ERROR="创建ingress系统异常";
    public static String CREATE_NAMESPACE_ERROR="创建namespace异常";
    public static String GET_PODLITS_ERROR="获取podList异常";
    public static String GET_SVCLITS_ERROR="获取svcList异常";
    public static String Name_IS_EXISTED="容器名称重复！";
    public static String GET_NAMESPACELITS_ERROR="获namespaceList异常！";

    public static String DELETE_DEPLOYMENT_ERROR="删除Deployment异常！";
    public static String DELETE_SERVICE_ERROR="删除Service异常！";
    public static String DELETE_INGRESS_ERROR="删除Ingress异常！";



}
