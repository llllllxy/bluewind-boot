/**
 * date:2021/06/19
 * author: liuxingyu01
 * description: 此处放layui自定义扩展 和一些公共的方法
 * version: 2.0.4
 */
window.rootPath = (function (src) {
    src = document.scripts[document.scripts.length - 1].src;
    return src.substring(0, src.lastIndexOf("/") + 1);
})();

layui.config({
    base: rootPath + "lay-module/",
    version: true
}).extend({
    miniAdmin: "layuimini/miniAdmin", // layuimini后台扩展
    miniMenu: "layuimini/miniMenu", // layuimini菜单扩展
    miniTab: "layuimini/miniTab", // layuimini tab扩展
    miniTheme: "layuimini/miniTheme", // layuimini 主题扩展
    miniTongji: "layuimini/miniTongji", // layuimini 统计扩展
    step: 'step-lay/step', // 分步表单扩展
    tableSelect: 'tableSelect/tableSelect', // table选择扩展
    iconPickerFa: 'iconPicker/iconPickerFa', // fa图标选择扩展
    echarts: 'echarts/echarts', // echarts图表扩展
    echartsTheme: 'echarts/echartsTheme', // echarts图表主题扩展
    wangEditor: 'wangEditor/wangEditor', // wangEditor富文本扩展
    layarea: 'layarea/layarea', //  省市县区三级联动下拉选择器
    layNotify: 'layNotify/layNotify', // layui提示层
    treeTable: 'treeTable/treeTable', // table树形扩展
    eleTree: 'eleTree/eleTree', // eleTree 树组件
    nprogress: 'nprogress/nprogress',
    layCascader: 'cascader/cascader', // 级联选择器
});


/**
 * 封装nprogress进度条模块
 */
layui.use(['nprogress', 'layer'], function () {
    var nprogress = layui.nprogress;
    var layer = layui.layer;

    /**
     * 页面加载进度条
     */
    nprogress.start();
    if (document.readyState == "complete" || document.readyState == "interactive") {
        // 进度条结束
        nprogress.done();
    }

    /**
     * 鼠标移入，显示弹窗
     */
    // let tipsIndex;
    // $('body').on('mouseover', 'tbody .layui-table-cell', function () {
    //     let that = this;
    //     if (!$(this).hasClass('laytable-cell-numbers') && 0 == $(this).find('a').length && !!filterHTMLTag($(this).html())) {
    //         tipsIndex = layer.tips(filterHTMLTag($(this).html()), that, {tips: [1, '#4BB2FF'], time: 5000});
    //     }
    // });

    /**
     * 鼠标移出，关闭弹窗
     */
    // $('body').on('mouseout', 'tbody .layui-table-cell', function () {
    //     if (!$(this).hasClass('laytable-cell-numbers') && 0 == $(this).find('a').length && !!filterHTMLTag($(this).html())) {
    //         layer.close(tipsIndex);
    //     }
    // });

});


/**
 * 搜索信息框，展开，收起
 */
$('legend').click(function () {
    $('#search-div').slideToggle('1000');
});


/**
 * 配置Layui-Table点击行时选中复选框
 */
$(document).on("click", ".layui-table-body table.layui-table tbody tr", function () {
    var index = $(this).attr('data-index');
    var tableBox = $(this).parents('.layui-table-box');
    var tableDiv;
    // 存在固定列
    if (tableBox.find(".layui-table-fixed.layui-table-fixed-l").length > 0) {
        tableDiv = tableBox.find(".layui-table-fixed.layui-table-fixed-l");
    } else {
        tableDiv = tableBox.find(".layui-table-body.layui-table-main");
    }
    var checkCell = tableDiv.find("tr[data-index=" + index + "]").find("td div.laytable-cell-checkbox div.layui-form-checkbox I");
    if (checkCell.length > 0) {
        checkCell.click();
    }
});
// 对td的单击事件进行拦截停止，防止事件冒泡再次触发上述的单击事件（Table的单击行事件不会拦截，依然有效）
$(document).on("click", "td div.laytable-cell-checkbox div.layui-form-checkbox", function (e) {
    e.stopPropagation();
});


/**
 * 设置AJAX的全局默认选项，
 * 当AJAX请求会话过期时，跳转到登陆页面
 */
$.ajaxSetup({
    complete: function(XMLHttpRequest, textStatus){
        if (XMLHttpRequest.status == 302) {
            layer.alert('会话已过期，请重新登录', function(index){
                layer.close(index);
                window.location.href = Util.ctx + "admin/login";
            });
        }
    }
} );

/**
 * 配置layer.open的宽度和高度
 */
const layerwidth = 800;
const layerheight = ($(window).height() - 50);

/**
 * Ajax请求封装
 */
const Util = {
    ctx: /*[[@{/}]]*/'',

    /**
     * GET 请求
     */
    get: function (options) {
        if (!options.url) {
            alert('请求错误，url不可为空!');
            return false;
        }
        options.type = 'GET';
        options.timeout = options.timeout || 5000; // 设置本地的请求超时时间（以毫秒计）
        options.async = options.async || true; // 布尔值，表示请求是否异步处理。默认是 true
        options.cache = options.cache || false; // 布尔值，表示浏览器是否缓存被请求页面，默认是true
        options.dataType = options.dataType || 'json';
        $.ajax({
            url: options.url,
            type: options.type,
            timeout: options.timeout,
            async: options.async,
            cache: options.cache,
            dataType: options.dataType,
            success: function (data, textStatus, jqXHR) {
                // 成功回调
                options.success(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                // 错误回调
                options.error("错误提示： " + XMLHttpRequest.status + " " + XMLHttpRequest.statusText);
            }
        });
    },


    /**
     * POST 请求
     */
    post: function (options) {
        if (!options.url) {
            alert('请求错误，url不可为空!');
            return false;
        }
        options.type = 'POST';
        options.timeout = options.timeout || 5000;
        options.async = options.async || true;
        options.cache = options.cache || false;
        options.dataType = options.dataType || 'json';
        options.contentType = options.contentType || 'application/x-www-form-urlencoded';
        options.data = options.data || '';
        $.ajax({
            url: options.url,
            type: options.type,
            timeout: options.timeout,
            async: options.async,
            cache: options.cache,
            dataType: options.dataType,
            data: options.data,
            contentType: options.contentType, // 发送数据到服务器时所使用的内容类型。默认是："application/x-www-form-urlencoded"
            success: function (data, textStatus, jqXHR) {
                // 成功回调
                options.success(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                // 错误回调
                options.error("错误提示： " + XMLHttpRequest.status + " " + XMLHttpRequest.statusText);
            }
        });
    },


    /**
     * upload 文件上传 请求
     */
    upload: function (options) {
        if (!options.url) {
            alert('请求错误，url不可为空!');
            return false;
        }
        options.type = 'POST';
        options.timeout = options.timeout || 5000;
        options.async = options.async || true;
        options.cache = options.cache || false;
        options.dataType = options.dataType || 'json';
        options.contentType = options.contentType || false;
        options.processData = options.processData || false;
        options.data = options.data || new FormData();
        $.ajax({
            url: options.url,
            type: options.type,
            timeout: options.timeout,
            async: options.async,
            cache: options.cache,
            dataType: options.dataType,
            processData: options.processData,
            data: options.data,
            contentType: options.contentType,
            success: function (data, textStatus, jqXHR) {
                // 成功回调
                options.success(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                // 错误回调
                options.error("错误提示： " + XMLHttpRequest.status + " " + XMLHttpRequest.statusText);
            }
        });
    }
}


/**
 * 判断对象是否为空
 * @param obj
 * @returns {boolean}
 */
function isEmpty(obj) {
    if (typeof obj === 'undefined' || obj == null || obj === '') {
        return true;
    } else {
        return false;
    }
}


/**
 * 判断对象是否不为空
 * @param obj
 * @returns {boolean}
 */
function isNotEmpty(obj) {
    return !isEmpty(obj);
}


/**
 * 下划线转换驼峰
 * @param name
 * @returns {*}
 */
function toHump(name) {
    return name.replace(/\_(\w)/g, function (all, letter) {
        return letter.toUpperCase();
    });
}


/**
 * 驼峰转换下划线
 * @param name
 * @returns {string}
 */
function toLine(name) {
    return name.replace(/([A-Z])/g, "_$1").toLowerCase();
}


/**
 * 获取当前日期 yyyyMMdd
 * @returns {string}
 */
function getCurrentDate() {
    let date = new Date();
    let month = date.getMonth() + 1;
    let strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    month = month + "";
    strDate = strDate + "";
    return date.getFullYear() + month + strDate;
}


/**
 * 获取当前时间yyyyMMddHHmmss
 * @returns {string}
 */
function getCurrentTime() {
    let date = new Date();
    let month = date.getMonth() + 1;
    let strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    let strHour = date.getHours();
    if (strHour >= 0 && strHour <= 9) {
        strHour = "0" + strHour;
    }
    let strMinute = date.getMinutes();
    if (strMinute >= 0 && strMinute <= 9) {
        strMinute = "0" + strMinute;
    }
    let strSecond = date.getSeconds();
    if (strSecond >= 0 && strSecond <= 9) {
        strSecond = "0" + strSecond;
    }
    month = month + "";
    strDate = strDate + "";
    strHour = strHour + "";
    strMinute = strMinute + "";
    strSecond = strSecond + "";
    return date.getFullYear() + month + strDate
        + strHour + strMinute + strSecond;
}


/**
 * 转化时间戳或日期对象为日期格式字符
 * time：可以是日期对象，也可以是毫秒数
 * format：日期字符格式（默认：yyyy-MM-dd HH:mm:ss），可随意定义，如：yyyy年MM月dd日
 */
function toDateString(time, format) {
    //若 null 或空字符，则返回空字符
    if (time === null || time === '') return '';
    var that = this
        , date = new Date(function () {
        if (!time) return;
        return isNaN(time) ? time : (typeof time === 'string' ? parseInt(time) : time)
    }() || new Date())
        , ymd = [
        that.digit(date.getFullYear(), 4)
        , that.digit(date.getMonth() + 1)
        , that.digit(date.getDate())
    ]
        , hms = [
        that.digit(date.getHours())
        , that.digit(date.getMinutes())
        , that.digit(date.getSeconds())
    ];

    if (!date.getDate()) {
        console.error('Invalid Msec for "util.toDateString(Msec)');
        return '';
    }

    format = format || 'yyyy-MM-dd HH:mm:ss';
    return format.replace(/yyyy/g, ymd[0])
        .replace(/MM/g, ymd[1])
        .replace(/dd/g, ymd[2])
        .replace(/HH/g, hms[0])
        .replace(/mm/g, hms[1])
        .replace(/ss/g, hms[2]);
}

/**
 * 数字前置补零(配合toDateString使用)
 * @param num
 * @param length
 * @returns {string}
 */
function digit(num, length){
    var str = '';
    num = String(num);
    length = length || 2;
    for(var i = num.length; i < length; i++){
        str += '0';
    }
    return num < Math.pow(10, length) ? str + (num|0) : num;
}

/**
 * 去掉字符串的HTML标签
 * @param str
 * @returns {string}
 */
function filterHTMLTag(str) {
    let msg = str.replace(/<\/?[^>]*>/g, '');
    msg = msg.replace(/[|]*\n/, '');
    msg = msg.replace(/&nbsp;/ig, '');
    return msg;
}


/**
 * 保留小数位数
 * @param n 原数字
 * @param s 保存位数
 * @returns {string}
 */
function toFixed(n, s) {
    if (s > 6) {
        s = 6;
    }
    var changenum;
    if (n > 0) {
        changenum = (parseInt(n * Math.pow(10, s) + 0.5) / Math.pow(10, s)).toString();
    } else {
        changenum = (parseInt(n * Math.pow(10, s) - 0.5) / Math.pow(10, s)).toString();
    }

    var index = changenum.indexOf(".");
    if (index < 0 && s > 0) {
        changenum = changenum + ".";
        for (var i = 0; i < s; i++) {
            changenum = changenum + "0";
        }
    } else {
        index = changenum.length - index;
        for (var i = 0; i < (s - index) + 1; i++) {
            changenum = changenum + "0";
        }
    }
    return changenum;
}


/**
 * 校验电话号码是否正确
 * @param val
 * @returns {boolean}
 */
function checkTel(val) {
    var isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
    var isMob = /^(?:(?:\+|00)86)?1(?:(?:3[\d])|(?:4[5-7|9])|(?:5[0-3|5-9])|(?:6[5-7])|(?:7[0-8])|(?:8[\d])|(?:9[1|8|9]))\d{8}$/;
    if (isMob.test(val) || isPhone.test(val)) {
        return true;
    } else {
        return false;
    }
}


/**
 * js除法计算
 * @param arg1
 * @param arg2
 * @returns {number}
 */
function accDiv(arg1, arg2) {
    let t1 = 0, t2 = 0, r1, r2;
    try {
        t1 = arg1.toString().split(".")[1].length
    } catch (e) {
    }
    try {
        t2 = arg2.toString().split(".")[1].length
    } catch (e) {
    }
    r1 = Number(arg1.toString().replace(".", ""))
    r2 = Number(arg2.toString().replace(".", ""))
    return (r1 / r2) * Math.pow(10, t2 - t1);
}


/**
 * js乘法计算
 * @param arg1
 * @param arg2
 * @returns {*}
 */
function accMul(arg1, arg2) {
    let m = 0, s1 = arg1.toString(), s2 = arg2.toString();
    try {
        m += s1.split(".")[1].length
    } catch (e) {
    }
    try {
        m += s2.split(".")[1].length
    } catch (e) {
    }
    return accDiv(Number(s1.replace(".", "")) * Number(s2.replace(".", "")), Math.pow(10, m));
}


/**
 * js加法计算
 * @param arg1
 * @param arg2
 * @returns {*}
 */
function accAdd(arg1, arg2) {
    let r1, r2, m;
    try {
        r1 = arg1.toString().split(".")[1].length
    } catch (e) {
        r1 = 0
    }
    try {
        r2 = arg2.toString().split(".")[1].length
    } catch (e) {
        r2 = 0
    }
    m = Math.pow(10, Math.max(r1, r2));
    return accDiv((accMul(arg1, m) + accMul(arg2, m)), m);
}


/**
 * js减法计算
 * @param arg1
 * @param arg2
 * @returns {*}
 */
function accSub(arg1, arg2) {
    let r1, r2, m;
    try {
        r1 = arg1.toString().split(".")[1].length
    } catch (e) {
        r1 = 0
    }
    try {
        r2 = arg2.toString().split(".")[1].length
    } catch (e) {
        r2 = 0
    }
    m = Math.pow(10, Math.max(r1, r2));
    return accDiv((accMul(arg1, m) - accMul(arg2, m)), m);
}


/**
 * 数字转大写金额
 * @param n 数字 9101.11
 * @returns {string} 大写金额 玖千壹百零壹元壹角壹分
 */
function numToDX(n) {
    if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(n))
        return "数据非法";
    let unit = "千百拾亿千百拾万千百拾元角分", str = "";
    n += "00";
    let p = n.indexOf('.');
    if (p >= 0)
        n = n.substring(0, p) + n.substr(p + 1, 2);
    unit = unit.substr(unit.length - n.length);
    for (let i = 0; i < n.length; i++)
        str += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)) + unit.charAt(i);
    return str.replace(/零(千|百|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").replace(/(亿)万|壹(拾)/g, "$1$2").replace(/^元零?|零分/g, "").replace(/元$/g, "元整");
}


/**
 * 数字每三位加逗号
 * @param num 9999
 * @returns {string} 999,9
 */
function toThousands(num) {
    var num = (num || 0).toString(), result = '';
    while (num.length > 3) {
        result = ',' + num.slice(-3) + result;
        num = num.slice(0, num.length - 3);
    }
    if (num) {
        result = num + result;
    }
    return result;
}

/**
 * 根据参数名称获取url中参数值(会自动解码)
 * @param url https://translate.google.cn/?sl=zh-CN&tl=en&text=%E8%A1%A5%E8%B4%B4&op=translate
 * @param key text
 * @returns {string|null} 补贴
 */
function getUrlParamValue(url, key) {
    var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)", "i");
    var r = url.match(reg);
    if (r != null) {
        return decodeURIComponent(r[2]);
    } else {
        return null;
    }
}

/**
 * 根据参数名称获取当前url：location.href中参数值(会自动解码)
 * @param key 参数键
 * @returns {string | null}
 */
function getUrlKeyValue(key) {
    return decodeURIComponent((new RegExp('[?|&]' + key + '=' + '([^&;]+?)(&|#|;|$)').exec(location.href) || [, ""])[1].replace(/\+/g, '%20')) || null
}

/**
 * 获取UUID
 * @returns {string}
 */
function uuid() {
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "-";

    var uuid = s.join("");
    return uuid;
}

/**
 * 获取UUID-V4版本
 * @returns {string}
 */
function uuidV4() {
    return "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(/[xy]/g, (c) => {
        const r = (Math.random() * 16) | 0; // 取整
        const v = c === "x" ? r : (r & 0x3 | 0x8);
        return v.toString();
    });
}

