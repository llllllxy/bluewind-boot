/**
 * date:2021/06/19
 * author: liuxingyu01
 * description:此处放layui自定义扩展 和一些公共的方法
 * version:2.0.4
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
    nprogress: 'nprogress/nprogress'
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
    let tipsIndex;
    $('body').on('mouseover', 'tbody .layui-table-cell', function () {
        let that = this;
        if (!$(this).hasClass('laytable-cell-numbers') && 0 == $(this).find('a').length && !!filterHTMLTag($(this).html())) {
            tipsIndex = layer.tips(filterHTMLTag($(this).html()), that, {tips: [1, '#4BB2FF'], time: 5000});
        }
    });

    /**
     * 鼠标移出，关闭弹窗
     */
    $('body').on('mouseout', 'tbody .layui-table-cell', function () {
        if (!$(this).hasClass('laytable-cell-numbers') && 0 == $(this).find('a').length && !!filterHTMLTag($(this).html())) {
            layer.close(tipsIndex);
        }
    });

});


/**
 * 搜索信息，展开，收起
 */
$('legend').click(function () {
    $('#search-div').slideToggle('1000');
});


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
    upload: function (options, succback, errback) {
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
    var isMob = /^((\+?86)|(\(\+86\)))?(13[0123456789][0-9]{8}|15[012356789][0-9]{8}|18[0123456789][0-9]{8}|14[57][0-9]{8}|17[678][0-9]{8})$/;
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