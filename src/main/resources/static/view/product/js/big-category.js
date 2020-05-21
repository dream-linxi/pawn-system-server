let index = 1;  // 元素拼接索引

$(function () {
    loadBigCategoryInfoTable();
});

/**
 * 初始化数据：加载产品一级分类
 */
function loadBigCategoryInfoTable() {
    layui.use(['table', 'layer'], function () {
        let table = layui.table         // 表格
            , layer = layui.layer;      // 弹出层

        // 表格初始化
        table.render({
            elem: '#BigCategoryTable'   // 表格 ID
            , url: '../../product/productcat/getAllBigCategoryInfo.json'
            , title: '数据列表'
            , page: true    // 开启分页
            , toolbar: '#BifCategoryToolBar'    // 工具栏
            , cols: [[  // 表头
                {type: 'checkbox', fixed: 'left'}
                , {field: 'catCode', title: '大类编号', width: "10%", sort: true}
                , {field: 'catName', title: '大类名称', width: "20%", sort: true}
                , {field: 'sortNo', title: '排序', width: "12%", sort: true}
                , {field: 'catDesc', title: '备注', width: "53%", sort: true}
            ]]
        });


        // 监听商品大类工具栏
        table.on('toolbar(BigCategoryTableFilter)', function (obj) {
            let checkStatus = table.checkStatus(obj.config.id);
            let data = checkStatus.data; // 获取当前选中行
            switch (obj.event) {
                case 'showAddBigCategoryInfoWin':
                    showAddBigCategoryInfoWin();
                    break;
                case 'showUpdateBigCategoryInfoWin':
                    if (data.length === 0) {
                        layer.msg('请选择一行');
                    } else if (data.length > 1) {
                        layer.msg('只能同时编辑一个');
                    } else {
                        // layer.alert('编辑 [id]：' + checkStatus.data[0].shopCode);
                        showUpdateBigCategoryInfoWin(checkStatus.data[0]);
                    }
                    break;
                case 'update':
                    layer.msg('编辑');
                    break;
            }
            ;
        });


    });
}

/**
 * 根据条件查询对应的商品大类信息
 */
function searchProductBigCatByCondition() {
    layui.use(['table'], function () {
        let table = layui.table;

        table.reload('BigCategoryTable', {
            where: {
                "keyWord": $('#keyWord').val()
            }
            , page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    })
}

/**
 * 显示添加商品大类信息窗口
 */
let addBigCategoryLayer;

function showAddBigCategoryInfoWin() {
    layui.use(['layer'], function () {
        let layer = layui.layer;    // 弹出层


        $('#evalPicDef').html("");
        let headLabel = "<label class=\"layui-form-label\">鉴定图定义</label>";
        $('#evalPicDef').append($(headLabel));
        addSubDefDiv();


        addBigCategoryLayer = layer.open({
            type: 1     // 基本层类型
            , title: '新增大类信息'   // 标题
            , content: $('#addBigCategoryInfoWin')  // 内容
            , area: ['auto', 'auto']     // 宽高
            , closeBtn: 1   // 关闭按钮
            , shade: 0.3    // 遮罩
            , id: 'LAY_layuipro'    // 唯一 Id 标识
            , resize: true     // 不允许拉伸
            , moveOut: false     // 允许拖至屏外
            , moveType: 1
            , cancel: function () { // 右上关闭操作

            }
        });
    });
}

/**
 * 添加分类信息
 */
function addBigCategoryInfo() {
    layui.use(['form', 'layer', 'table'], function () {
        let form = layui.form
            , layer = layui.layer
            , table = layui.table;

        //自定义验证规则
        form.verify({
            checkCatCode: function (value) {
                let row = 0;
                $.ajax({
                    url: "../../product/productcat/checkCatCode.json?catCode=" + value,
                    type: 'get',
                    dataType: 'json',
                    async: false, // 同步
                    success: function (result) {
                        row = result.row;
                    }
                });
                if (row > 0) {
                    return "门店编号已存在";
                }
            }
        });

        form.on('submit(addBigCategoryBtn)', function (data) {

            let bcForm = $('#addBigCategoryInfoForm').serializeArray();

            let jsonData = [];
            let obj = {};

            for (let j = 0; j < bcForm.length; j++) {
                if (bcForm[j].name == 'defName') {
                    obj[bcForm[j].name] = bcForm[j].value;
                }
                if (bcForm[j].name == 'defValue') {
                    obj[bcForm[j].name] = bcForm[j].value;
                    jsonData.push(obj);
                }
            }

            let dataSub = data.field;
            dataSub["evalPicDef"] = JSON.stringify(jsonData);

            delete dataSub.defName;
            delete dataSub.defValue;

            // 发生 ajax 请求向后台提交数据
            $.post('../../product/productcat/addProductCat.json', dataSub, function (result) {
                if (result.row > 0) {
                    // 关闭窗口
                    layer.close(addBigCategoryLayer);
                    addBigCategoryLayer = "";
                    // 情况表单
                    $("#addBigCategoryInfoForm")[0].reset();
                    // 重新加载 table
                    table.reload('BigCategoryTable', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    // 弹出提示信息
                    layer.alert("分类信息添加成功!!!")
                }
            }, 'json');

            return false;

        });

    });
}

/**
 * 打开大类信息更新窗口
 */
let updateBigCategoryLayer;

function showUpdateBigCategoryInfoWin(data) {
    layui.use(['layer', 'form'], function () {
        let layer = layui.layer
            , form = layui.form;


        let jsonData = JSON.parse(data.evalPicDef);

        $('#updateEvalPicDef').html("");
        let headLabel = "<label class=\"layui-form-label\">鉴定图定义</label>";
        $('#updateEvalPicDef').append($(headLabel));

        for (let def of jsonData) {
            loadUpdateSubDefDiv(def);
        }

        form.val('updateBigCategoryInfoFilter', data);

        updateBigCategoryLayer = layer.open({
            type: 1     // 基本层类型
            , title: '新增大类信息'   // 标题
            , content: $('#updateBigCategoryInfoWin')  // 内容
            , area: ['auto', 'auto']     // 宽高
            , closeBtn: 1   // 关闭按钮
            , shade: 0.3    // 遮罩
            , id: 'LAY_layuipro'    // 唯一 Id 标识
            , resize: true     // 不允许拉伸
            , moveOut: false     // 允许拖至屏外
            , moveType: 1
            , cancel: function () { // 右上关闭操作

            }
        });

    });
}

/**
 * 更新大类信息
 */
function updateBigCategoryInfo() {

    layui.use(['form', 'layer', 'table'], function () {
        let form = layui.form
            , layer = layui.layer
            , table = layui.table;


        form.on('submit(updateBigCategoryBtn)', function (data) {

            let bcForm = $('#updateBigCategoryInfoForm').serializeArray();

            let jsonData = [];
            let obj = {};

            for (let j = 0; j < bcForm.length; j++) {
                if (bcForm[j].name == 'defName') {
                    obj[bcForm[j].name] = bcForm[j].value;
                    j ++;
                }
                if (bcForm[j].name == 'defValue') {
                    obj[bcForm[j].name] = bcForm[j].value;
                    jsonData.push(obj);
                }
                obj = {};
            }
            console.log(jsonData);
            let dataSub = data.field;
            dataSub["evalPicDef"] = JSON.stringify(jsonData);

            delete dataSub.defName;
            delete dataSub.defValue;

            // 发生 ajax 请求向后台提交数据
            $.post('../../product/productcat/updateBigCateGoryProductCat.json', dataSub, function (result) {
                if (result.row > 0) {
                    // 关闭窗口
                    layer.close(updateBigCategoryLayer);
                    updateBigCategoryLayer = "";
                    // 情况表单
                    $("#updateBigCategoryInfoForm")[0].reset();
                    // 重新加载 table
                    table.reload('BigCategoryTable', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    // 弹出提示信息
                    layer.alert("分类信息更新成功!!!")
                }
            }, 'json');

            return false;

        });
    });
}


/**
 * 加载 def
 * @param def
 */
function loadUpdateSubDefDiv(def) {
    let divId = "evalPicDef" + index;

    let $div = $(createUpdateElement(divId, def));

    $('#updateEvalPicDef').append($div);
    index++;
    // 重载表单
    layui.use('form', function () {
        let form = layui.form;
        form.render();
    })
}

/**
 * 根据 DIV 的 ID 删除对应元素
 * @param element
 */
function deleteSubDefDiv(divId) {
    $('#' + divId).next().remove();
    $('#' + divId).remove();
}

/**
 * 动态添加 SubDef div 元素
 */
function addSubDefDiv() {
    // 设置 div 唯一 id 表示
    let divId = "evalPicDef" + index;

    let $div = $(createElement(divId));

    $('#evalPicDef').append($div);
    index++;
    // 重载表单
    layui.use('form', function () {
        let form = layui.form;
        form.render();
    })
}

/**
 * 添加更新面板的 div
 */
function addUpdateSubDefDiv() {
    // 设置 div 唯一 id 表示
    let divId = "evalPicDef" + index;

    let $div = $(createElement(divId));

    $('#updateEvalPicDef').append($div);
    index++;
    // 重载表单
    layui.use('form', function () {
        let form = layui.form;
        form.render();
    })
}

/**
 * 创建节点
 */
function createElement(divId) {

    let element = "<div id=\"" + divId + "\" class=\"layui-block\" style=\"margin-top: 5px;\">\n" +
        "                <div class=\"layui-input-inline\">\n" +
        "                    <input type=\"text\" name=\"defName\" style=\"width: 100px;\" lay-verify=\"required\" autocomplete=\"off\"\n" +
        "                           class=\"layui-input\">\n" +
        "\n" +
        "                </div>\n" +
        "\n" +
        "                <div class=\"layui-input-inline\" style=\"width: 120px;\">\n" +
        "                    <select name=\"defValue\" lay-verify=\"required\">\n" +
        "                        <option value=\"1\">必选</option>\n" +
        "                        <option value=\"0\">可选</option>\n" +
        "                    </select>\n" +
        "                </div>\n" +
        "\n" +
        "                <button onclick=\"deleteSubDefDiv('" + divId + "')\" type=\"button\" class=\"layui-btn layui-btn-danger\">删除</button>\n" +
        "            </div>\n" +
        "            <label class=\"layui-form-label\"></label>";

    return element;
}

/**
 * 创建不为空的节点
 * @param divId
 * @param def
 * @returns {string}
 */
function createUpdateElement(divId, def) {
    let element = "<div id=\"" + divId + "\" class=\"layui-block\" style=\"margin-top: 5px;\">\n" +
        "                <div class=\"layui-input-inline\">\n" +
        "                    <input type=\"text\" name=\"defName\" value='" + def.defName + "' style=\"width: 100px;\" lay-verify=\"required\" autocomplete=\"off\"\n" +
        "                           class=\"layui-input\">\n" +
        "\n" +
        "                </div>\n" +
        "\n" +
        "                <div class=\"layui-input-inline\" style=\"width: 120px;\">\n" +
        "                    <select name=\"defValue\" lay-verify=\"required\">\n" +
        "                        <option value=\"1\" " + (def.defValue == "1" ? "selected" : '') + ">必选</option>\n" +
        "                        <option value=\"0\" " + (def.defValue == "0" ? "selected" : '') + ">可选</option>\n" +
        "                    </select>\n" +
        "                </div>\n" +
        "\n" +
        "                <button onclick=\"deleteSubDefDiv('" + divId + "')\" type=\"button\" class=\"layui-btn layui-btn-danger\">删除</button>\n" +
        "            </div>\n" +
        "            <label class=\"layui-form-label\"></label>";
    return element;
}