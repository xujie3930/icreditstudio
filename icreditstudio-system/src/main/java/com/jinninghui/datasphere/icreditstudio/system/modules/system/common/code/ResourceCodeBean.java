package com.jinninghui.datasphere.icreditstudio.system.modules.system.common.code;

import com.jinninghui.datasphere.icreditstudio.framework.systemcode.SystemCode;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liyanhui
 */
@SystemCode
public class ResourceCodeBean {

    public enum ResourceCode {

        RESOURCE_CODE_10000000("10000000", "非法请求参数"),
        RESOURCE_CODE_10000001("10000001", "请求参数错误"),
        RESOURCE_CODE_10000002("10000002", "接口返回异常"),
        RESOURCE_CODE_52000101("52000101", "新增应用失败"),
        RESOURCE_CODE_52000102("52000102", "编辑应用失败"),
        RESOURCE_CODE_52000103("52000103", "应用启用失败"),
        RESOURCE_CODE_52000104("52000104", "禁用应用失败"),
        RESOURCE_CODE_52000105("52000105", "查询应用详情失败"),
        RESOURCE_CODE_52000106("52000106", "查询应用列表失败"),
        RESOURCE_CODE_52000107("52000107", "分页查询应用失败"),
        RESOURCE_CODE_52000108("52000108", "审核应用失败"),
        RESOURCE_CODE_52001101("52001101", "存在可用的应用服务接口,删除应用服务失败"),
        RESOURCE_CODE_52001102("52001102", "删除应用服务失败"),
        RESOURCE_CODE_52001103("52001103", "添加应用服务失败"),
        RESOURCE_CODE_52001104("52001104", "编辑应用服务失败"),
        RESOURCE_CODE_52001105("52001105", "查询应用服务失败"),
        RESOURCE_CODE_52000111("52000111", "应用添加接口失败"),
        RESOURCE_CODE_52000112("52000112", "编辑应用接口失败"),
        RESOURCE_CODE_52000113("52000113", "删除应用接口失败"),
        RESOURCE_CODE_52000114("52000114", "启用应用接口失败"),
        RESOURCE_CODE_52000115("52000115", "禁用应用接口失败"),
        RESOURCE_CODE_52000116("52000116", "查询应用接口失败"),
        RESOURCE_CODE_52000117("52000117", "分页检索接口失败"),
        RESOURCE_CODE_52000118("52000118", "查询应用接口详情失败"),
        RESOURCE_CODE_52000201("52000201", "给应用新增授权服务失败"),
        RESOURCE_CODE_52000202("52000202", "同意应用授权服务失败"),
        RESOURCE_CODE_52000203("52000203", "拒绝应用授权服务失败"),
        RESOURCE_CODE_52000204("52000204", "删除应用授权的服务失败"),
        RESOURCE_CODE_52000205("52000205", "查询应用授权服务列表失败"),
        RESOURCE_CODE_52000206("52000206", "分页查询应用授权服务失败"),
        RESOURCE_CODE_52000256("52000256", "查询应用资源失败"),
        RESOURCE_CODE_50002101("50002101", "新增资源信息失败"),
        RESOURCE_CODE_50002102("50002102", "编辑资源信息失败"),
        RESOURCE_CODE_50002103("50002103", "禁用资源信息失败"),
        RESOURCE_CODE_50002104("50002104", "启用资源信息失败"),
        RESOURCE_CODE_50002105("50002105", "查询资源信息失败"),
        RESOURCE_CODE_50002107("50002107", "查询资源详情失败"),
        RESOURCE_CODE_50002108("50002108", "删除资源信息失败"),
        RESOURCE_CODE_50002109("50002109", "分页查询资源信息失败"),
        RESOURCE_CODE_50002110("50002110", "树形查询资源信息失败"),
        RESOURCE_CODE_50003101("50003101", "新增组织机构失败"),
        RESOURCE_CODE_50003102("50003102", "编辑组织机构失败"),
        RESOURCE_CODE_50003103("50003103", "禁用组织机构失败"),
        RESOURCE_CODE_50003104("50003104", "启用组织机构失败"),
        RESOURCE_CODE_50003105("50003105", "获取组织机构详情失败"),
        RESOURCE_CODE_50003106("50003106", "查询组织机构列表失败"),
        RESOURCE_CODE_50003107("50003107", "分页查询组织机构失败"),
        RESOURCE_CODE_50003108("50003108", "变更父级机构失败"),
        RESOURCE_CODE_50003109("50003109", "父级机构ID为空"),
        RESOURCE_CODE_50003110("50003110", "机构名称已存在"),
        RESOURCE_CODE_50004101("50004101", "保存登录信息失败"),
        RESOURCE_CODE_50004102("50004102", "刷新登录信息失败"),
        RESOURCE_CODE_50004103("50004103", "删除登录信息失败"),
        RESOURCE_CODE_50004104("50004104", "获取登录详情失败"),
        RESOURCE_CODE_50005101("50005101", "新增企业信息失败"),
        RESOURCE_CODE_50005102("50005102", "编辑企业信息失败"),
        RESOURCE_CODE_50005103("50005103", "查询企业详情失败"),
        RESOURCE_CODE_50005104("50005104", "分页查询企业信息失败"),
        RESOURCE_CODE_50005105("50005105", "查询企业列表信息失败"),
        RESOURCE_CODE_50007101("50007101", "新增用户分组失败"),
        RESOURCE_CODE_50007102("50007102", "编辑用户分组失败"),
        RESOURCE_CODE_50007103("50007103", "禁用用户分组失败"),
        RESOURCE_CODE_50007104("50007104", "启用用户分组失败"),
        RESOURCE_CODE_50007105("50007105", "查询用户分组列表失败"),
        RESOURCE_CODE_50007106("50007106", "分页查询用户分组失败"),
        RESOURCE_CODE_50007107("50007107", "删除用户分组失败"),
        RESOURCE_CODE_50007108("50007108", "查询用户详情失败"),
        RESOURCE_CODE_50007109("50007109", "给分组添加角色失败"),
        RESOURCE_CODE_50007110("50007110", "删除分组中角色失败"),
        RESOURCE_CODE_50007111("50007111", "查询分组的角色列表失败"),
        RESOURCE_CODE_50007113("50007113", "给分组添加用户失败"),
        RESOURCE_CODE_50007114("50007114", "删除分组中用户失败"),
        RESOURCE_CODE_50007115("50007115", "查询分组的用户失败"),
        RESOURCE_CODE_50008101("50008101", "新增用户失败"),
        RESOURCE_CODE_50008102("50008102", "编辑用户失败"),
        RESOURCE_CODE_50008103("50008103", "删除用户失败"),
        RESOURCE_CODE_50008104("50008104", "查询用户列表失败"),
        RESOURCE_CODE_50008105("50008105", "分页查询用户失败"),
        RESOURCE_CODE_50008106("50008106", "查询用户详情失败"),
        RESOURCE_CODE_50008107("50008107", "新增用户账户失败"),
        RESOURCE_CODE_50008108("50008108", "编辑用户账户失败"),
        RESOURCE_CODE_50008109("50008109", "启用用户账户失败"),
        RESOURCE_CODE_50008110("50008110", "禁用用户账户失败"),
        RESOURCE_CODE_50008111("50008111", "获取用户账户详情失败"),
        RESOURCE_CODE_50008112("50008112", "查询用户账户列表失败"),
        RESOURCE_CODE_50008113("50008113", "分页查询用户账户失败"),
        RESOURCE_CODE_50008114("50008114", "给用户新增角色失败"),
        RESOURCE_CODE_50008115("50008115", "从用户角色中删除角色失败"),
        RESOURCE_CODE_50008116("50008116", "查询用户角色列表失败"),
        RESOURCE_CODE_50008118("50008118", "查询用户资源列表失败"),
        RESOURCE_CODE_50008119("50008119", "树形化用户资源列表失败"),
        RESOURCE_CODE_50008120("50008120", "删除用户账号失败"),
        RESOURCE_CODE_50009101("50009101", "新增角色失败"),
        RESOURCE_CODE_50009102("50009102", "删除角色失败"),
        RESOURCE_CODE_50009103("50009103", "启用角色失败"),
        RESOURCE_CODE_50009104("50009104", "禁用角色失败"),
        RESOURCE_CODE_50009105("50009105", "查询角色列表失败"),
        RESOURCE_CODE_50009106("50009106", "分页查询角色失败"),
        RESOURCE_CODE_50009107("50009107", "查询角色详情失败"),
        RESOURCE_CODE_50009108("50009108", "编辑角色失败"),
        RESOURCE_CODE_50009109("50009109", "给角色添加权限失败"),
        RESOURCE_CODE_50009110("50009110", "删除角色中权限失败"),
        RESOURCE_CODE_50009111("50009111", "查询角色权限列表失败"),
        RESOURCE_CODE_50009112("50009112", "上级角色不能是其子角色"),
        RESOURCE_CODE_50009113("50009113", "角色已启用不能删除"),
        RESOURCE_CODE_50009114("50009114", "上级角色不能是当前角色"),
        RESOURCE_CODE_50009115("50009115", "无权进行此操作"),
        RESOURCE_CODE_50009201("50009201", "新增权限失败"),
        RESOURCE_CODE_50009202("50009202", "编辑权限失败"),
        RESOURCE_CODE_50009203("50009203", "禁用权限失败"),
        RESOURCE_CODE_50009204("50009204", "启用权限失败"),
        RESOURCE_CODE_50009205("50009205", "查询权限列表失败"),
        RESOURCE_CODE_50009206("50009206", "分页查询权限失败"),
        RESOURCE_CODE_50009207("50009207", "获取权限详情失败"),
        RESOURCE_CODE_50009208("50009208", "权限添加资源失败"),
        RESOURCE_CODE_50009209("50009209", "删除权限资源失败"),
        RESOURCE_CODE_50009210("50009210", "查询权限资源失败"),
        RESOURCE_CODE_50009313("50009313", "添加权限资源失败，当前权限没有父资源"),
        RESOURCE_CODE_50000000("50000000", "应用编号为空"),
        RESOURCE_CODE_50000001("50000001", "应用编号长度不超过32个字符"),
        RESOURCE_CODE_50000002("50000002", "应用编号列表长度不超过999"),
        RESOURCE_CODE_50000003("50000003", "应用编码为空"),
        RESOURCE_CODE_50000004("50000004", "应用编码长度不超过32个字符"),
        RESOURCE_CODE_50000005("50000005", "应用类型为空"),
        RESOURCE_CODE_50000006("50000006", "应用类型取值必须0或1"),
        RESOURCE_CODE_50000007("50000007", "应用名称为空"),
        RESOURCE_CODE_50000008("50000008", "应用名称长度不超过30个字符"),
        RESOURCE_CODE_50000009("50000009", "应用实例名为空"),
        RESOURCE_CODE_50000010("50000010", "应用实例名长度不超过32个字符"),
        RESOURCE_CODE_50000011("50000011", "应用备注长度不超过200个字符"),
        RESOURCE_CODE_50000012("50000012", "应用拥有者为空"),
        RESOURCE_CODE_50000013("50000013", "应用拥有者长度不超过32个字符"),
        RESOURCE_CODE_50000014("50000014", "应用拥有者邮箱为空            "),
        RESOURCE_CODE_50000015("50000015", "应用拥有者邮箱格式错误          "),
        RESOURCE_CODE_50000016("50000016", "应用编号或实例编号已存在         "),
        RESOURCE_CODE_50000017("50000017", "应用状态取值错误             "),
        RESOURCE_CODE_50000018("50000018", "应用状态值为空              "),
        RESOURCE_CODE_50000019("50000019", "邮箱长度不超过200个字符        "),
        RESOURCE_CODE_50000020("50000020", "系统自定义编号长度不超过32个字符    "),
        RESOURCE_CODE_50000021("50000021", "应用id为空"),
        RESOURCE_CODE_50000050("50000050", "授权应用编号为空             "),
        RESOURCE_CODE_50000051("50000051", "授权理应编号列表长度不超过999     "),
        RESOURCE_CODE_50000100("50000100", "应用接口名称为空             "),
        RESOURCE_CODE_50000101("50000101", "应用接口名称长度不超过120个字符    "),
        RESOURCE_CODE_50000102("50000102", "应用接口访问路俓为空           "),
        RESOURCE_CODE_50000103("50000103", "应用接口访问路俓长度不超过120个字符  "),
        RESOURCE_CODE_50000104("50000104", "应用接口图标长度不超过120个字符    "),
        RESOURCE_CODE_50000105("50000105", "应用接口是否对外开放为空         "),
        RESOURCE_CODE_50000106("50000106", "应用接口是否对外开放只能为0或1     "),
        RESOURCE_CODE_50000107("50000107", "应用接口备注长度不超过120个字符    "),
        RESOURCE_CODE_50000108("50000108", "应用接口编号为空             "),
        RESOURCE_CODE_50000109("50000109", "应用接口编号长度不超过32个字符     "),
        RESOURCE_CODE_50000110("50000110", "应用接口编号列表长度不超过999     "),
        RESOURCE_CODE_50002001("50002001", "资源名称为空               "),
        RESOURCE_CODE_50002002("50002002", "资源名称长度不超过120个字       "),
        RESOURCE_CODE_50002003("50002003", "资源编号为空               "),
        RESOURCE_CODE_50002004("50002004", "资源编号长度不超过32个字符       "),
        RESOURCE_CODE_50002005("50002005", "资源编号列表长度不超过999       "),
        RESOURCE_CODE_50002006("50002006", "备注长度不超过200个字符        "),
        RESOURCE_CODE_50002007("50002007", "资源路经长度不超过200字符       "),
        RESOURCE_CODE_50002008("50002008", "资源状态为空               "),
        RESOURCE_CODE_50002009("50002009", "资源状态取值必须是0或1         "),
        RESOURCE_CODE_50002010("50002010", "资源图标长度不超过200个字符      "),
        RESOURCE_CODE_50002011("50002011", "上级资源编号不超过32个字符       "),
        RESOURCE_CODE_50002012("50002012", "资源类型取值必须0或1          "),
        RESOURCE_CODE_50003001("50003001", "企业编号为空               "),
        RESOURCE_CODE_50003002("50003002", "企业编号不超过32个字符         "),
        RESOURCE_CODE_50003003("50003003", "机构或部门名称为空            "),
        RESOURCE_CODE_50003004("50003004", "机构或部门名称不超过200个字      "),
        RESOURCE_CODE_50003005("50003005", "机构类型为空               "),
        RESOURCE_CODE_50003006("50003006", "备注长度不超过200个字         "),
        RESOURCE_CODE_50003007("50003007", "地址长度不超过200个字         "),
        RESOURCE_CODE_50003008("50003008", "邮政编码不超过10个字符         "),
        RESOURCE_CODE_50003009("50003009", "电话不超过16个字符           "),
        RESOURCE_CODE_50003010("50003010", "传真号码长度不超过16个字符       "),
        RESOURCE_CODE_50003011("50003011", "联系人姓名不超过100个字        "),
        RESOURCE_CODE_50003012("50003012", "联系人部门不超过20个字         "),
        RESOURCE_CODE_50003013("50003013", "联系人职务不超过20个字         "),
        RESOURCE_CODE_50003014("50003014", "联系人电话不超过25个字符        "),
        RESOURCE_CODE_50003015("50003015", "联系人又像不超过200个字        "),
        RESOURCE_CODE_50003016("50003016", "组织机构或部门列表为空          "),
        RESOURCE_CODE_50003017("50003017", "组织机构或部门列表不超过999个     "),
        RESOURCE_CODE_50003018("50003018", "组织机构编号为空             "),
        RESOURCE_CODE_50003019("50003019", "组织机构或部门编号不超过32个字符    "),
        RESOURCE_CODE_50003020("50003020", "机构状态取值必须为0或1         "),
        RESOURCE_CODE_50003021("50003021", "上级组织机构不存在            "),
        RESOURCE_CODE_50004001("50004001", "登录用户名为空              "),
        RESOURCE_CODE_50004002("50004002", "登录用户名长度不超过64个字符      "),
        RESOURCE_CODE_50004003("50004003", "登录序列号为空              "),
        RESOURCE_CODE_50004004("50004004", "登录序列号长度不超过64个字符      "),
        RESOURCE_CODE_50004005("50004005", "登录Token为空            "),
        RESOURCE_CODE_50004006("50004006", "登录Token长度不超过64个字符    "),
        RESOURCE_CODE_50004007("50004007", "最后使用时间为空             "),
        RESOURCE_CODE_50005001("50005001", "企业名称为空               "),
        RESOURCE_CODE_50005002("50005002", "企业名称长度不超过120个字       "),
        RESOURCE_CODE_50005003("50005003", "企业编号为空               "),
        RESOURCE_CODE_50005004("50005004", "企业编号长度不超过120个字       "),
        RESOURCE_CODE_50005005("50005005", "企业地址长度不超过200个字       "),
        RESOURCE_CODE_50005006("50005006", "企业所属分类不超过100个字       "),
        RESOURCE_CODE_50005007("50005007", "联系人长度不超过50个字         "),
        RESOURCE_CODE_50005008("50005008", "企业规模长度不超过50个字        "),
        RESOURCE_CODE_50005009("50005009", "企业编号为空               "),
        RESOURCE_CODE_50005010("50005010", "企业编号长度不超过32个字符       "),
        RESOURCE_CODE_50005011("50005011", "企业编号列表为空             "),
        RESOURCE_CODE_50005012("50005012", "企业编号列表长度不超过999       "),
        RESOURCE_CODE_50005013("50005013", "企业名称或注册号已存在          "),
        RESOURCE_CODE_50007001("50007001", "用户分组名称为空             "),
        RESOURCE_CODE_50007002("50007002", "用户分组名称不超过120个字符      "),
        RESOURCE_CODE_50007003("50007003", "备注长度不超过120个字符        "),
        RESOURCE_CODE_50007004("50007004", "用户分组编号为空             "),
        RESOURCE_CODE_50007005("50007005", "用户分组编号长度不超过32个字符     "),
        RESOURCE_CODE_50007006("50007006", "用户分组状态为空             "),
        RESOURCE_CODE_50007007("50007007", "用户分组编号列表为空           "),
        RESOURCE_CODE_50007008("50007008", "用户分组编号列表长度不超过999     "),
        RESOURCE_CODE_50007009("50007009", "用户分组状态必须为0或1         "),
        RESOURCE_CODE_50008001("50008001", "用户编号列表为空             "),
        RESOURCE_CODE_50008002("50008002", "用户名称为空               "),
        RESOURCE_CODE_50008003("50008003", "用户名称长度不超过20个字符      "),
        RESOURCE_CODE_50008004("50008004", "用户编码长度不超过32个字符       "),
        RESOURCE_CODE_50008005("50008005", "用户邮箱地址格式错误           "),
        RESOURCE_CODE_50008006("50008006", "邮箱地址长度不超过120个字符      "),
        RESOURCE_CODE_50008007("50008007", "电话长度不超过120个字符        "),
        RESOURCE_CODE_50008008("50008008", "职位信息不超过120个字符        "),
        RESOURCE_CODE_50008009("50008009", "备注长度不超过120个字符        "),
        RESOURCE_CODE_50008010("50008010", "用户登录类型输入错误           "),
        RESOURCE_CODE_50008011("50008011", "用户类型为空               "),
        RESOURCE_CODE_50008012("50008012", "用户账号为空               "),
        RESOURCE_CODE_50008013("50008013", "用户账号长度不超过120个字符      "),
        RESOURCE_CODE_50008014("50008014", "用户密码为空               "),
        RESOURCE_CODE_50008015("50008015", "用户密码长度不超过200个字符      "),
        RESOURCE_CODE_50008016("50008016", "用户账号已存在             "),
        RESOURCE_CODE_50008017("50008017", "该用户编码、邮箱或电话已经存在      "),
        RESOURCE_CODE_50008018("50008018", "用户编号为空               "),
        RESOURCE_CODE_50008019("50008019", "用户编号长度不超过32个字符       "),
        RESOURCE_CODE_50008020("50008020", "账户类型为空               "),
        RESOURCE_CODE_50008021("50008021", "账户类型错误               "),
        RESOURCE_CODE_50008022("50008022", "登录方式参数为空             "),
        RESOURCE_CODE_50008023("50008023", "登录方式参数错误             "),
        RESOURCE_CODE_50008024("50008024", "账户编号为空               "),
        RESOURCE_CODE_50008025("50008025", "账户编号长度不超过32个字符       "),
        RESOURCE_CODE_50008026("50008026", "账户凭证过期时间为空           "),
        RESOURCE_CODE_50008027("50008027", "账户超时过期时间为空           "),
        RESOURCE_CODE_50008028("50008028", "账户编号列表长度不超过999       "),
        RESOURCE_CODE_50008029("50008029", "用户编号列表长度不超过999       "),
        RESOURCE_CODE_50008030("50008030", "登录IP为空               "),
        RESOURCE_CODE_50008031("50008031", "最后登录时间为空             "),
        RESOURCE_CODE_50008032("50008032", "性别输入有误               "),
        RESOURCE_CODE_50008033("50008033", "生日长度不超过32个字符         "),
        RESOURCE_CODE_50008034("50008034", "人像图片路径长度不超过200个字符    "),
        RESOURCE_CODE_50008035("50008035", "个人签名不超过200个字符    "),
        RESOURCE_CODE_50008036("50008036", "个人简介不超过200个字符    "),
        RESOURCE_CODE_50008037("50008037", "用户不存在    "),
        RESOURCE_CODE_50008038("50008038", "用户被禁用   "),
        RESOURCE_CODE_50008039("50008039", "个人用户头像不超过2M   "),
        RESOURCE_CODE_50008040("50008040", "用户已存在   "),
        RESOURCE_CODE_50009001("50009001", "角色名称为空               "),
        RESOURCE_CODE_50009002("50009002", "角色名称长度不超过20个字符      "),
        RESOURCE_CODE_50009003("50009003", "备注长度不超过120个字符        "),
        RESOURCE_CODE_50009004("50009004", "该角色名称已存在             "),
        RESOURCE_CODE_50009005("50009005", "角色编号为空               "),
        RESOURCE_CODE_50009006("50009006", "角色编号长度不超过32个字符       "),
        RESOURCE_CODE_50009007("50009007", "角色状态不能为空             "),
        RESOURCE_CODE_50009008("50009008", "角色状态只能是0或者1          "),
        RESOURCE_CODE_50009009("50009009", "角色列表为空               "),
        RESOURCE_CODE_50009010("50009010", "角色列表最大长度不超过999       "),
        RESOURCE_CODE_50009011("50009011", "权限列表为空               "),
        RESOURCE_CODE_50009012("50009012", "权限列表长度最大不超过999       "),
        RESOURCE_CODE_50009013("50009013", "角色中已存在该权限            "),
        RESOURCE_CODE_50009014("50009014", "父角色ID长度不超过32个字符"),
        RESOURCE_CODE_50009015("50009015", "父角色ID不存在"),
        RESOURCE_CODE_50009016("50009016", "父角色ID不能为空"),
        RESOURCE_CODE_50009221("50009221", "权限名称为空               "),
        RESOURCE_CODE_50009211("50009211", "权限名称的长度不超过120个字符     "),
        RESOURCE_CODE_50009212("50009212", "备注长度不超过120个字符        "),
        RESOURCE_CODE_50009213("50009213", "权限编号为空               "),
        RESOURCE_CODE_50009214("50009214", "权限编号长度不超过32个字符       "),
        RESOURCE_CODE_50009215("50009215", "权限状态为空               "),
        RESOURCE_CODE_50009216("50009216", "权限状态必须是0或1           "),
        RESOURCE_CODE_50009217("50009217", "应用编号列表长度不超过999       "),
        RESOURCE_CODE_50009218("50009218", "该权限名称已存在             "),
        RESOURCE_CODE_50009219("50009219", "查询权限关联资源失败           "),
        RESOURCE_CODE_50009220("50009220", "权限已经存在相关资源           "),
        RESOURCE_CODE_50009222("50009222", "父权限ID长度不超过32个字符"),
        RESOURCE_CODE_50009223("50009223", "父权限ID不存在"),
        RESOURCE_CODE_50009301("50009301", "获取第三方接入账号失败          "),
        RESOURCE_CODE_50009302("50009302", "编辑第三方接入账号失败          "),
        RESOURCE_CODE_50009303("50009303", "Client主键编号为空         "),
        RESOURCE_CODE_50009304("50009304", "Client主键长度不超过32个字符   "),
        RESOURCE_CODE_50009305("50009305", "client_id编号为空        "),
        RESOURCE_CODE_50009306("50009306", "client_id编号长度不超过16个字符"),
        RESOURCE_CODE_50009307("50009307", "Client会调地址长度不超过200个字符"),
        RESOURCE_CODE_50009308("50009308", "刷新Token的有效时间不能小于0    "),
        RESOURCE_CODE_50009309("50009309", "访问Token的有效时间不能小于0    "),
        RESOURCE_CODE_50009310("50009310", "新增第三方接入账号失败          "),
        RESOURCE_CODE_50009311("50009311", "client_id列表长度不超过999  "),
        RESOURCE_CODE_50009312("50009312", "查询第三方接入账号列表失败        "),
        RESOURCE_CODE_50009314("50009314", "原密码错误        "),
        RESOURCE_CODE_50009315("50009315", "新增权限或权限资源失败"),
        RESOURCE_CODE_50009316("50009316", "角色ID不能为空"),
        RESOURCE_CODE_50009317("50009317", "数据类型不能为空"),
        RESOURCE_CODE_50009318("50009318", "角色数据ID不能为空"),
        RESOURCE_CODE_50009319("50009319", "编辑数据权限失败"),
        RESOURCE_CODE_50009320("50009320", "输入参数为空"),
        RESOURCE_CODE_50009321("50009321", "精确条件查询用户信息失败"),
        RESOURCE_CODE_50009322("50009322", "登录获取用户信息，资源，并更新登录时间失败"),
        RESOURCE_CODE_50009323("50009323", "父权限不允许复制"),
        RESOURCE_CODE_50009324("50009324", "用户角色数据信息查询失败"),
        RESOURCE_CODE_50009325("50009325", "该用户没有角色信息"),
        RESOURCE_CODE_50009326("50009326", "当前权限不存在"),
        RESOURCE_CODE_50009327("50009327", "不能超过指定层级的权限"),
        RESOURCE_CODE_50009328("50009328", "菜单名称已存在  "),
        RESOURCE_CODE_50009329("50009329", "组织机构名称已存在  "),
        RESOURCE_CODE_50009330("50009330", "按钮的唯一标识已存在  "),
        RESOURCE_CODE_50009331("50009331", "启用禁用不能为空"),
        RESOURCE_CODE_50009332("50009332", "必填参数为空"),
        RESOURCE_CODE_50009333("50009333", "所删除角色下还有角色存在，无法删除！"),
        RESOURCE_CODE_50009334("50009334", "所删除角色下还有用户信息，无法删除！"),
        RESOURCE_CODE_50009335("50009335", "用户名已存在"),
        RESOURCE_CODE_50009336("50009336", "部门名称已存在"),
        RESOURCE_CODE_50009337("50009337", "字典已启用无法删除"),
        RESOURCE_CODE_50009338("50009338", "ID不能为空"),
        RESOURCE_CODE_50009339("50009339", "部门名称长度超过20个字符"),
        RESOURCE_CODE_50009340("50009340", "部门编码已存在"),
        RESOURCE_CODE_50009341("50009341", "上级部门不能是其子部门"),
        RESOURCE_CODE_50009342("50009342", "部门下有子部门不能删除"),
        RESOURCE_CODE_50009343("50009343", "部门下有用户不能删除"),
        RESOURCE_CODE_50009344("50009344", "已启用的部门不能删除"),
        RESOURCE_CODE_50009345("50009345", "模块类型不能为空"),
        RESOURCE_CODE_50009346("50009346", "模块名称不能为空"),
        RESOURCE_CODE_50009347("50009347", "未选择删除内容"),
        RESOURCE_CODE_50009348("50009348", "存在子模块"),
        RESOURCE_CODE_50009349("50009349", "已启用的模块不能删除"),
        RESOURCE_CODE_50009350("50009350", "角色名称不能超过20个字符"),
        RESOURCE_CODE_50009351("50009351", "用户已启用不能删除"),
        RESOURCE_CODE_50009352("50009352", "手机号格式不正确"),
        RESOURCE_CODE_50009353("50009353", "上级模块不能是其子模块"),
        RESOURCE_CODE_50009354("50009354", "用户名长度不超过20个字符"),
        RESOURCE_CODE_50009355("50009355", "用户编码不超过20个字符"),
        RESOURCE_CODE_50009356("50009356", "手机号格式有误"),
        RESOURCE_CODE_50009357("50009357", "接口请求类型不能为空"),
        RESOURCE_CODE_50009358("50009358", "接口名称不能为空"),
        RESOURCE_CODE_50009359("50009359", "接口是否鉴权不能为空"),
        RESOURCE_CODE_50009360("50009360", "接口归属模块不能为空"),
        RESOURCE_CODE_50009361("50009361", "接口鉴权方式不能为空"),
        RESOURCE_CODE_50009362("50009362", "接口地址不能为空"),
        RESOURCE_CODE_50009363("50009363", "接口类型不能为空"),
        RESOURCE_CODE_50009364("50009364", "用户生日格式有误"),

        RESOURCE_CODE_50009365("50009365", "用户ID不能为空"),
        RESOURCE_CODE_50009366("50009366", "消息类型为空"),
        RESOURCE_CODE_50009367("50009367", "阅读状态为空"),
        RESOURCE_CODE_50009368("50009368", "消息标题长度超过200字符"),
        RESOURCE_CODE_50009369("50009369", "消息内容长度超过2000字符"),
        RESOURCE_CODE_50009370("50009370", "消息标题为空"),
        RESOURCE_CODE_50009371("50009371", "发送人ID为空"),
        RESOURCE_CODE_50009372("50009372", "消息ID为空"),
        RESOURCE_CODE_50009373("50009373", "字典类型为空"),
        RESOURCE_CODE_50009374("50009374", "字典类型长度超过100字符"),

        RESOURCE_CODE_50009375("50009375", "系统版权信息不得超过200个字符"),
        RESOURCE_CODE_50009376("50009376", "系统名称不得超过100个字符"),
        RESOURCE_CODE_50009377("50009377", "系统logo不得大于2M"),
        RESOURCE_CODE_50009378("50009378", "系统logo文件格式仅限：'png', 'jpg', 'jpeg', 'bmp', 'webp'"),

        RESOURCE_CODE_50009379("50009379", "表单ID为空"),
        RESOURCE_CODE_50009380("50009380", "发布状态才能停用"),
        RESOURCE_CODE_50009381("50009381", "控件标识已存在"),
        RESOURCE_CODE_50009382("50009382", "表单名称不超过50字符"),
        RESOURCE_CODE_50009383("50009383", "表单描述不超过200字符"),
        RESOURCE_CODE_50009384("50009384", "未找到账户信息"),
        ;

        public final String code;
        public final String message;

        ResourceCode(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public static ResourceCode find(String code) {
            if (StringUtils.isNotBlank(code)) {
                for (ResourceCode value : ResourceCode.values()) {
                    if (code.equals(value.getCode())) {
                        return value;
                    }
                }
            }
            return null;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
