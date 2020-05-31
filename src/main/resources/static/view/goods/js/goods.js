let commonLayer;
let choosePic=[];
let indexNum = 0;
$(function () {
    layui.use('laydate', function () {
        var laydate = layui.laydate;
        //常规用法
        laydate.render({
            elem: '#searchStarteTime'
        });

        laydate.render({
            elem: '#searchEndTime'
        });

        laydate.render({
            elem: '#inputDate'
        });
    });

    loadGoodsInfoTable();
});


/**
 * 加载商品信息
 */
function loadGoodsInfoTable() {
    layui.use(['table', 'layer', 'form'], function () {
        let table = layui.table
            , layer = layui.layer
            , form = layui.form;


        // 表格初始化
        table.render({
            elem: '#goodsTable'   // 表格 ID
            , url: '../../goods/goods/getAllGoodsInfo.json'
            , title: '数据列表'
            , page: true    // 开启分页
            , toolbar: '#goodsToolBar'    // 工具栏
            , cols: [[  // 表头
                {type: 'checkbox', fixed: 'left'}
                , {field: 'goodsId', title: '编号', width: "10%", sort: true}
                , {field: 'goodsName', title: '商品名称', width: "15%", sort: true}
                , {field: 'catRouteStr', title: '分类', width: "16%", sort: true}
                , {field: 'inputUser', title: '录入人', width: "10%", sort: true}
                , {field: 'surveyor', title: '鉴定人', width: "10%", sort: true}
                , {field: 'assessor', title: '评估人', width: "10%", sort: true}
                , {field: 'inputDate', title: '录入时间', width: "16%", sort: true}
                , {field: 'goodsStat', title: '状态', width: "10%", sort: true}
            ]]
        });


        // 监听商品大类工具栏
        table.on('toolbar(goodsTableFilter)', function (obj) {
            let checkStatus = table.checkStatus(obj.config.id);
            let data = checkStatus.data; // 获取当前选中行
            switch (obj.event) {
                case 'showAddGoodsInfoWin':         // 新增
                    showAddGoodsInfoWin();
                    break;
                case 'commitGoodsInfo':             // 提交
                    if (data.length === 0) {
                        layer.msg('请选择一行');
                    } else if (data.length > 1) {
                        layer.msg('只能同时编辑一个');
                    } else {
                        let goodsIds = [];
                        for(let goods of  data)
                        {
                            goodsIds.push(goods.goodsId);
                        }
                        commitGoodsInfo(goodsIds);
                    }
                    break;
                case 'showUpdateGoodsInfoWin':      // 更新
                    if (data.length === 0) {
                        layer.msg('请选择一行');
                    } else if (data.length > 1) {
                        layer.msg('只能同时编辑一个');
                    } else {
                        showUpdateGoodsInfoWin(checkStatus.data[0]);
                    }
                    break;
                case 'showSurveyGoodsInfoWin':   // 鉴定
                    if (data.length === 0) {
                        layer.msg('请选择一行');
                    } else if (data.length > 1) {
                        layer.msg('只能同时编辑一个');
                    } else {
                        showSurveyGoodsInfoWin(checkStatus.data[0]);
                    }
                    break;
                case 'showAssessGoodsInfoWin':   // 评估
                    if (data.length === 0) {
                        layer.msg('请选择一行');
                    } else if (data.length > 1) {
                        layer.msg('只能同时编辑一个');
                    } else {
                        showAssessGoodsInfoWin(checkStatus.data[0]);
                    }
                    break;
                case 'deleteGoodsInfo':         // 删除
                    if (data.length === 0) {
                        layer.msg('请选择一行');
                    } else {
                        let goodsIds = [];
                        for (let gi of data)
                        {
                            goodsIds.push(gi.goodsId);
                        }
                        deleteGoodsInfo(goodsIds);
                    }
                    break;
            }
            ;
        });

    });
}

function searchGoodsInfoByCondition()
{
    layui.use(['layer', 'table', 'form'], function () {
        let table = layui.table;
        table.reload('goodsTable', {
            where: {
                "goodsStat": $('#goodsStat').val(),
                "inputUser": $('#inputUserStr').val(),
                "keyWord": $('#keyWord').val(),
                "searchStarteTime": $('#searchStarteTime').val(),
                "searchEndTime": $('#searchEndTime').val(),
            }
            , page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    })
}

/**
 * 添加商品信息
 */
function showAddGoodsInfoWin()
{
    layui.use(['form','layer','table'], function () {
        let form = layui.form
            , layer = layui.layer
            , table = layui.table;

        // 情况表格信息
        resetForm();

        // 切换到第一个选项卡
        loadFirstCard();

        /* 隐藏商品鉴定和商品评价选项卡 */
        $('#goodsAssess').css("display","none");
        $('#goodsSurvey').css("display","none");
        $('#goodsSurveyInfo').css("display","none");
        $('#goodsAssessInfo').css("display","none");

        // 动态加载门店信息
        loadAsynSelect("../../base/shop/getAllShopInfoByNoPage.json", $('#shopCode'), 'shopCode', 'shopName');

        // 动态加载所有一级分类
        loadAsynSelect("../../product/productcat/getAllBigCategoryInfoByNoPage.json", $('#catCode'), 'catCode', 'catName');

        openLayer("添加商品信息", ['auto','auto'], $("#goodsInfoWin"), $('#goodsInfoBtn'));

        // 监听一级分类列表,动态加载二级和三级
        getProductCatByPCatCode("catCodeFilter", $('#subCatCode'));
        getProductCatByPCatCode("subCatCodeFilter", $('#detailCatCode'));

        // 监听属性组
        form.on('select(attrGroupFilter)', function (data) {
            loadGroupAttrConfInfo(data.value);
        });

        //监听提交
        form.on('submit(goodsInfoFilter)', function (data) {
            let obj = data.field;
            let str = "";
            for (prop in data.field)
            {
                if (parseInt(prop) > 0)
                {
                    str += (prop + "=" + data.field[prop]) + "&";
                }
            }
            obj['attrConfs'] = str;
            obj['picInfo'] = JSON.stringify(choosePic);
            $.post('../../goods/goods/addGoodsInfo.json',obj,function (data) {
                if (data.result > 0)
                {
                    // 关闭窗口
                    layer.close(commonLayer);
                    commonLayer = "";
                    // 情况表单
                    $("#goodsInfoForm")[0].reset();
                    // 重新加载 table
                    table.reload('goodsTable', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });

                    choosePic=[];
                    // 弹出提示信息
                    layer.alert("商品信息添加成功...");
                }
            },'json');
            return false;
        });

    });
}



/**
 * 商品信息提交
 * @param goodsIds
 */
function commitGoodsInfo(goodsIds) {
    layui.use(['layer', 'table'], function () {
        let layer = layui.layer
            , table = layui.table;

        $.post("../../goods/goods/commitGoodsInfo.json", {"goodsIds": goodsIds}, function (data) {
            if (data.row > 0) {
                table.reload('goodsTable', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
                // 弹出提示信息
                layer.alert("商品信息提交成功...");
            }
        }, 'json');
    });
}

/**
 * 更新商品
 * @param data
 */
function showUpdateGoodsInfoWin(datas)
{
    layui.use(['form','layer','table','element'], function () {
        let form = layui.form
            , layer = layui.layer
            , table = layui.table
            , element = layui.element;

        // 情况表格信息
        resetForm();

        // 切换到第一个选项卡
        loadFirstCard();

        /* 隐藏商品鉴定和商品评价选项卡 */
        $('#goodsAssess').css("display","none");
        $('#goodsSurvey').css("display","none");
        $('#goodsSurveyInfo').css("display","none");
        $('#goodsAssessInfo').css("display","none");

        formDataInit(datas);


        openLayer("更新商品信息", ['auto','auto'], $("#goodsInfoWin"), $('#goodsInfoBtn'));



        layui.use(['layer', 'table', 'form','transfer'], function () {
            let form = layui.form;
            form.val("goodsInfoFilter", datas)
        });
        element.init();
        form.render();


        //监听提交
        form.on('submit(goodsInfoFilter)', function (data) {
            let obj = data.field;
            let str = "";
            for (prop in data.field)
            {
                if (parseInt(prop) > 0)
                {
                    str += (prop + "=" + data.field[prop]) + "&";
                }
            }
            obj['goodsId'] = datas.goodsId;
            obj['attrConfs'] = str;
            obj['picInfo'] = JSON.stringify(choosePic);
            $.post('../../goods/goods/updateGoodsInfo.json',obj,function (data) {
                if (data.row > 0)
                {
                    // 关闭窗口
                    layer.close(commonLayer);
                    commonLayer = "";
                    // 情况表单
                    $("#goodsInfoForm")[0].reset();
                    // 重新加载 table
                    table.reload('goodsTable', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });

                    choosePic=[];
                    // 弹出提示信息
                    layer.alert("商品信息更新成功...");
                }
            },'json');
            return false;
        });

    });
}

function formDataInit(data)
{
    // 动态加载门店信息
    loadAsynSelect("../../base/shop/getAllShopInfoByNoPage.json", $('#shopCode'), 'shopCode', 'shopName');

    // 动态加载所有一级分类
    loadAsynSelect("../../product/productcat/getAllBigCategoryInfoByNoPage.json", $('#catCode'), 'catCode', 'catName');

    // 加载品牌信息
    loadAsynSelect("../../product/brand/getAllBrandByCatCode.json?catCode=" + data.catCode, $('#brandCode'), 'brandCode', 'brandName');
    // 加载属性信息
    loadAsynSelect("../../product/attrgroup/getAllAttrGroupByCatCode.json?catCode=" + data.catCode, $('#attrdGroupSelect'), 'groupCode', 'groupName')
    // 二级分类
    loadAsynSelect("../../product/productcat/getAllProductCatByPCatCode.json?pCatCode=" + data.catCode, $('#subCatCode'), 'catCode', 'catName');
    // 三级分类
    loadAsynSelect("../../product/productcat/getAllProductCatByPCatCode.json?pCatCode=" + data.subCatCode, $('#detailCatCode'), 'catCode', 'catName');
    // 加载图片信息
    loadEvalPicDef(data.catCode);
    // 加载属性组详细信息
    loadGroupAttrConfInfo(data.groupCode);

    // 获取当前商品的所有属性信息
    let  attrData = getAttrConfByGoodsId(data.goodsId);
    for (let i = 0; i< attrData.length ; i ++)
    {
        data[(attrData[i].attrCode)] = attrData[i].attrValue;
    }
}

function getAttrConfByGoodsId(goodsId)
{
    let attrData;
    $.ajax({
        type: 'GET',
        url: '../../goods/goods/getAttrConfByGoodsId.json?goodsId=' + goodsId,
        async: false,
        dataType: 'json',
        success: function (data) {
            attrData=data.result;
        }
    });

    return attrData;
}

/**
 * 鉴定商品
 */
function showSurveyGoodsInfoWin(datas)
{
    layui.use(['form','layer','element','table'], function() {
        let element = layui.element
            , form = layui.form
            , layer = layui.layer
            , table = layui.table;

        $('#goodsAssess').css("display","none");
        $('#goodsSurvey').css("display","");
        $('#goodsSurveyInfo').css("display","");
        $('#goodsAssessInfo').css("display","none");

        // 表格初始化
        table.render({
            elem: '#goodsSurveyInfoTable'   // 表格 ID
            , url: '../../goods/goods/getAllGoodsSurveyInfo.json?goodsId=' + datas.goodsId
            , width: '600'
            , height: '350'
            , title: '数据列表'
            , page: true    // 开启分页
            , cols: [[  // 表头
                  {field: 'idenityId', title: '编号', width: "8%", sort: true}
                , {field: 'identifyResult', title: '真假', width: "15%", sort: true}
                , {field: 'goodsQuality', title: '新旧程度', width: "10%", sort: true}
                , {field: 'createBy', title: '评估人', width: "25%", sort: true}
                , {field: 'createTime', title: '鉴定时间', width: "18%", sort: true}
                , {field: 'identifyDesc', title: '备注', width: "20%", sort: true}
            ]]
        });

        // 切换到第一个选项卡
        loadFirstCard();

        resetForm();

        formDataInit(datas);

        openLayer("鉴定商品信息", ['auto','auto'], $("#goodsInfoWin"), $('#goodsInfoBtn'));
        form.val("goodsInfoFilter", datas);
        //监听提交
        form.on('submit(goodsInfoFilter)', function (data) {

            data.field['goodsId'] = datas.goodsId;
            $.post('../../goods/goods/surveyGoodsInfo.json',data.field,function (data) {
                if (data.row > 0){
                    // 关闭窗口
                    layer.close(commonLayer);
                    commonLayer = "";
                    // 情况表单
                    $("#goodsInfoForm")[0].reset();
                    // 重新加载 table
                    table.reload('goodsTable', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });

                    choosePic=[];
                    // 弹出提示信息
                    layer.alert("商品信息鉴定成功...");
                }
            },'json');
            return false;
        });
    });
}

/**
 * 商品评估
 * @param datas
 */
function showAssessGoodsInfoWin(datas)
{
    layui.use(['form','layer','element','table'], function() {
        let element = layui.element
            , form = layui.form
            , layer = layui.layer
            , table = layui.table;

        $('#goodsAssess').css("display","");
        $('#goodsSurvey').css("display","none");
        $('#goodsSurveyInfo').css("display","none");
        $('#goodsAssessInfo').css("display","");

        // 表格初始化
        table.render({
            elem: '#goodsAssessInfoTable'   // 表格 ID
            , url: '../../goods/goods/getAllGoodsAssessInfo.json?goodsId=' + datas.goodsId
            , width: '600'
            , height: '350'
            , title: '数据列表'
            , page: true    // 开启分页
            , cols: [[  // 表头
                {field: 'appraiseId', title: '编号', width: "10%", sort: true}
                , {field: 'officialPrice', title: '官方价', width: "15%", sort: true}
                , {field: 'valuationPrice', title: '评估价', width: "15%", sort: true}
                , {field: 'createBy', title: '评估人', width: "20%", sort: true}
                , {field: 'createTime', title: '评估时间', width: "18%", sort: true}
                , {field: 'appraiseDesc', title: '备注', width: "17%", sort: true}
            ]]
        });

        // 切换到第一个选项卡
        loadFirstCard();

        resetForm();

        formDataInit(datas);

        openLayer("评估商品信息", ['auto','auto'], $("#goodsInfoWin"), $('#goodsInfoBtn'));
        form.val("goodsInfoFilter", datas);
        //监听提交
        form.on('submit(goodsInfoFilter)', function (data) {
            data.field['goodsId'] = datas.goodsId;

            $.post('../../goods/goods/assessGoodsInfo.json',data.field,function (data) {
                if (data.row > 0){
                    // 关闭窗口
                    layer.close(commonLayer);
                    commonLayer = "";
                    // 情况表单
                    $("#goodsInfoForm")[0].reset();
                    // 重新加载 table
                    table.reload('goodsTable', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });

                    choosePic=[];
                    // 弹出提示信息
                    layer.alert("商品信息评估成功...");
                }
            },'json');
            return false;
        });
    });
}

function deleteGoodsInfo(goodsIds) {
    layui.use(['form','layer','element','table'], function() {
        let element = layui.element
            , form = layui.form
            , layer = layui.layer
            , table = layui.table;
        $.post("../../goods/goods/deleteGoodsInfoByGoodsIds.json", {"goodsIds": goodsIds}, function (data) {
            if (data.row > 0) {

                // 重新加载 table
                table.reload('goodsTable', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
                // 弹出提示信息
                layer.alert("商品信息删除成功...");
            }
        }, 'json');
    });
}

/**
 * 清理表格所有信息
 */
function resetForm()
{
    $("#goodsInfoForm")[0].reset();
    $('#attrdGroupSelect').html("");
    $('#attrs').html("");
    $('#upPicsDiv').html("");
    $('#subCatCode').html("");
    $('#detailCatCode').html("");
    $('#brandCode').html("");
}

/**
 * 更加父级菜单编号获取下级菜单
 */
function getProductCatByPCatCode(filterStr, element) {
    layui.use(['form'], function () {
        let form = layui.form;

        form.on('select(' + filterStr + ')', function (data) {

            if (filterStr == "catCodeFilter") {
                // 加载品牌信息
                loadAsynSelect("../../product/brand/getAllBrandByCatCode.json?catCode=" + data.value, $('#brandCode'), 'brandCode', 'brandName');
                // 加载属性信息
                loadAsynSelect("../../product/attrgroup/getAllAttrGroupByCatCode.json?catCode=" + data.value, $('#attrdGroupSelect'), 'groupCode', 'groupName')
                // 加载图片信息
                loadEvalPicDef(data.value);
            }

            loadAsynSelect("../../product/productcat/getAllProductCatByPCatCode.json?pCatCode=" + data.value, element, 'catCode', 'catName');
        });
    });
}

/**
 * 获取当前分类的鉴定定义
 * @param catCode
 */
function loadEvalPicDef(catCode) {
    $.get('../../product/productcat/getProductCatByCatCode.json?catCode=' + catCode, function (data) {

        let evalPicDef = JSON.parse(data.result.evalPicDef);

        // 将鉴定图定义渲染到页面上
        loadPicUpCard(evalPicDef);

    }, 'json');
}

/**
 * 图片上传
 * @param evalPicDef
 */
function loadPicUpCard(evalPicDef) {
    $('#upPicsDiv').html("");

    let htmlStr = "";
    indexNum = 0;
    for (let obj of evalPicDef) {
        htmlStr += "<div class=\"layui-upload\">\n" +
            "  <button type=\"button\" class=\"layui-btn\" id=\"test" + indexNum + "\">" + obj.defName + "</button>\n" +
            "  <div class=\"layui-upload-list\" style='width: 150px;height: 150px;'>\n" +
            "    <img class=\"layui-upload-img\" id=\"demo" + indexNum + "\" style='width: 150px;height: 150px;'>\n" +
            "    <p id=\"demoText" + indexNum + "\"></p>\n" +
            "  </div>\n" +
            "</div>  ";


        indexNum++;
    }

    $('#upPicsDiv').html(htmlStr);

    layui.use('form', function () {
        var form = layui.form;
        form.render()
    })

    layui.use('upload', function () {
        var $ = layui.jquery
            , upload = layui.upload;

        indexNum = 0;
        for (let obj of evalPicDef) {
            let str = "#demo" + indexNum;
            //普通图片上传
            var uploadInst = upload.render({
                elem: '#test' + indexNum
                , url: '../../goods/goods/upFiles.json?defName='+ obj.defName //改成您自己的上传接口
                , before: function (obj) {
                    //预读本地文件示例，不支持ie8
                    obj.preview(function (index, file, result) {
                        $(str).attr('src', result); //图片链接（base64）
                    });
                }
                , done: function (res) {
                    //如果上传失败
                    if (res.code > 0) {
                        return layer.msg('上传失败');
                    }
                    //上传成功
                    let fileName = res.fileName;
                    let defName = res.defName;
                    console.log(fileName + "=" + defName);

                    let tag = -1;
                    for (let i = 0; i < choosePic.length; i++) {
                       if(choosePic[i].indexOf(defName) > 0)
                       {
                           choosePic[i]=fileName;
                           tag=1;
                       }
                    }
                    if (tag == -1)
                    {
                        choosePic.push(fileName);
                    }

                }
                , error: function () {
                    //演示失败状态，并实现重传
                    var demoText = $('#demoText' + indexNum);
                    demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
                    demoText.find('.demo-reload').on('click', function () {
                        uploadInst.upload();
                    });
                }
            });
            indexNum++;
        }
    });
}

/**
 * 属性组详细信息回显
 * @param gourpCode
 */
function loadGroupAttrConfInfo(gourpCode) {
    $.ajax({
        type: 'GET',
        url: "../../product/attrconf/getAllAttrConfByGroupCode.json?groupCode=" + gourpCode,
        async: false,
        dataType: 'json',
        success: function (data) {
            $("#attrs").html("");
            let str = ""
            for (let da of data.result) {
                if (da.attrType == '唯一属性') {
                    str += "    <div class=\"layui-form-item\">\n" +
                        "                            <label class=\"layui-form-label\">" + da.attrName + "</label>\n" +
                        "                            <div class=\"layui-input-block\">\n" +
                        "                                <input type=\"text\" name='" + da.attrCode + "' required lay-verify=\"required\" placeholder=" + da.attrName + " autocomplete=\"off\"\n" +
                        "                                       class=\"layui-input\">\n" +
                        "                            </div>\n" +
                        "                        </div>"
                } else if (da.attrType == '单选属性') {
                    str +=
                        "                        <div class=\"layui-form-item\">\n" +
                        "                            <label class=\"layui-form-label\">" + da.attrName + "</label>\n" +
                        "                            <div class=\"layui-input-block\">\n" +
                        "                                <select  name='" + da.attrCode + "' >\n"
                    for (let d of da.options.split(",")) {
                        str += " <option value='" + d + "'>" + d + "</option>";
                    }
                    str +=
                        "                                </select>\n" +
                        "                            </div>\n" +
                        "                        </div>"
                } else if (da.attrType == '复选属性') {
                    str +=
                        "  <div class=\"layui-form-item\">\n" +
                        "    <label class=\"layui-form-label\">" + da.attrName + "</label>\n" +
                        "    <div class=\"layui-input-block\">\n"
                    for (let d of da.options.split(",")) {
                        str += "<input type=\"checkbox\" name='" + da.attrCode + "' value='" + d + "'  title='" + d + "'>"
                    }
                    str += " </div> </div>"
                }
            }
            $("#attrs").html(str)
            layui.use('form', function () {
                var form = layui.form;
                form.render()
            })
        }
    });
}

/**
 * 通用下拉列表加载
 */
function loadAsynSelect(url, element, code, name) {
    $.ajax({
        type: 'GET',
        url: url,
        async: false,
        dataType: 'json',
        success: function (data) {
            let arr = data.result;

            let hrmlStr = "<option value=''>请选择</option>";
            for (let obj of arr) {
                hrmlStr += "<option value='" + obj[code] + "'>" + obj[name] + "</option>"
            }
            element.html(hrmlStr);

            layui.use(['form'], function () {
                let form = layui.form;
                form.render('select');
            });
        }
    });
}

/**
 * 默认切换到第一个选项卡
 */
function loadFirstCard()
{
    layui.use('element', function(){
        var element = layui.element;
        //获取hash来切换选项卡，假设当前地址的hash为lay-id对应的值
        var layid = location.hash.replace(/^#current=/, '');//current为刚才定义的lay-filter
        element.tabChange('current', 'one'); //假设当前地址为：http://a.com#current=two，那么选项卡会自动切换到“资产发现”这一项
        //监听Tab切换，以改变地址hash值
        element.on('tab(current)', function(){
            location.hash = 'current='+ this.getAttribute('lay-id');
        });
    });
}

/**
 * 通用窗口显示
 * @param title 窗口标题
 * @param area  窗口大小
 * @param divElement    窗口元素
 */
function openLayer(title, area, divElement, btn) {
    layui.use(['layer', 'form'], function () {
        let layer = layui.layer
            , form = layui.form;    // 弹出层

        commonLayer = layer.open({
            type: 1     // 基本层类型
            , title: title   // 标题
            , content: divElement  // 内容
            , area: area     // 宽高
            , closeBtn: 0   // 关闭按钮
            , shade: 0.3    // 遮罩
            , id: 'LAY_layuipro'    // 唯一 Id 标识
            , btn: ['确认', '关闭']
            , resize: true     // 不允许拉伸
            , moveOut: false     // 允许拖至屏外
            , moveType: 1
            , yes: function () {
                btn.click();
            }
            , btn2: function () { // 关闭操作
                choosePic=[];
            }
        });
    });
}



