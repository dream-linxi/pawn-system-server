let groupCode;
let groupName;
$(function () {
    let params = decodeURI(location.search);
    let arr = params.split('&');
    groupCode = (arr[0].split('='))[1];
    groupName = (arr[1].split('='))[1];

    loadAttrConfInfoTable();
});

/**
 * 加载属性信息
 */
function loadAttrConfInfoTable()
{
    layui.use(['table', 'layer'], function () {
        let table = layui.table         // 表格
            , layer = layui.layer;      // 弹出层

        // 表格初始化
        table.render({
            elem: '#attrConfTable'   // 表格 ID
            , url: '../../product/attrconf/getAllAttrConfInfo.json?groupCode=' + groupCode
            , title: '数据列表'
            , page: true    // 开启分页
            , toolbar: '#attrConfToolBar'    // 工具栏
            , cols: [[  // 表头
                {type: 'checkbox', fixed: 'left'}
                , {field: 'attrCode', title: '编号', width: "12%", sort: true}
                , {field: 'attrName', title: '属性名称', width: "24%", sort: true}
                , {field: 'attrType', title: '属性是否可选', width: "23%", sort: true}
                , {field: 'options', title: '可选值列表', width: "23%", sort: true}
                , {field: 'sortNo', title: '排序', width: "13%", sort: true}
            ]]
        });


        // 监听商品大类工具栏
        table.on('toolbar(attrConfTableFilter)', function (obj) {
            let checkStatus = table.checkStatus(obj.config.id);
            let data = checkStatus.data; // 获取当前选中行
            switch (obj.event) {
                case 'showAddAttrConfInfoWin':
                    showAddAttrConfInfoWin();
                    break;
                case 'showUpdateAttrConfInfoWin':
                    if (data.length === 0) {
                        layer.msg('请选择一行');
                    } else if (data.length > 1) {
                        layer.msg('只能同时编辑一个');
                    } else {
                        showUpdateAttrConfInfoWin(checkStatus.data[0]);
                    }
                    break;
                case 'deleteAttrConfInfo':
                    if (data.length === 0) {
                        layer.msg('请选择一行');
                    } else {
                        let attrCodes = [];
                        for (let attrGroup of data) {
                            attrCodes.push(attrGroup.attrCode);
                        }
                        deleteAttrConfInfo(attrCodes);
                    }
                    break;returnAttrGroup
                case 'returnAttrGroup':
                    location.href="./attr-group.html"
                    break;
            }
            ;
        });
    });
}

function searchAttrConfInfoByCondition() {
    layui.use(['table'], function () {
        let table = layui.table;

        table.reload('attrConfTable', {
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
 * 显示新增属性面板
 */
let addAttrConfLayer;
function showAddAttrConfInfoWin()
{
    addAttrConfLayer = layer.open({
        type: 1     // 基本层类型
        , title: '新增属性信息'   // 标题
        , content: $('#addAttrConfInfoWin')  // 内容
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
}

/**
 * 新增商品属性
 */
function addAttrConfInfo()
{
    layui.use(['form', 'layer', 'util', 'transfer', 'table'], function () {
        let $ = layui.$
            , form = layui.form
            , layer = layui.layer
            , util = layui.util
            , transfer = layui.transfer
            , table = layui.table;

        //自定义验证规则
        form.verify({
            checkAttrCode: function (value) {
                let row = 0;
                $.ajax({
                    url: "../../product/attrconf/checkAttrCode.json?attrCode=" + value,
                    type: 'get',
                    dataType: 'json',
                    async: false, // 同步
                    success: function (result) {
                        row = result.row;
                    }
                });
                if (row > 0) {
                    return "属性编号已存在";
                }
            }
        });


        //监听提交
        form.on('submit(addAttrConfInfoBtn)', function (data) {

            let obj = data.field;

            obj["groupCode"] = groupCode;


            // 发生 ajax 请求向后台提交数据
            $.post('../../product/attrconf/addAttrConfInfo.json', obj, function (result) {
                if (result.row > 0) {
                    // 关闭窗口
                    layer.close(addAttrConfLayer);
                    addAttrConfLayer = "";
                    // 情况表单
                    $("#addAttrConfInfoForm")[0].reset();
                    // 重新加载 table
                    table.reload('attrConfTable', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    // 弹出提示信息
                    layer.alert("属性信息添加成功!!!")
                }
            }, 'json');

            return false;
        });

    });
}

/**
 * 显示修改属性窗口
 * @param data
 */
let updateAttrConfLayer;
function showUpdateAttrConfInfoWin(data)
{
    layui.use(['layer', 'transfer', 'form'], function () {
        let layer = layui.layer
            , transfer = layui.transfer
            , form = layui.form;    // 弹出层

        form.val("updateAttrConfInfoFilter", data)

        updateAttrConfLayer = layer.open({
            type: 1     // 基本层类型
            , title: '新增大类信息'   // 标题
            , content: $('#updateAttrConfInfoWin')  // 内容
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
    })
}


function updateAttrConfInfo()
{
    layui.use(['form', 'layer', 'table'], function () {
        let $ = layui.$
            , form = layui.form
            , layer = layui.layer
            , table = layui.table;


        //监听提交
        form.on('submit(updateAttrConfInfoBtn)', function (data) {

            let obj = data.field;

            obj["groupCode"] = groupCode;


            // 发生 ajax 请求向后台提交数据
            $.post('../../product/attrconf/updateAttrConfInfo.json', obj, function (result) {
                if (result.row > 0) {
                    // 关闭窗口
                    layer.close(updateAttrConfLayer);
                    updateAttrConfLayer = "";
                    // 情况表单
                    $("#updateAttrConfInfoForm")[0].reset();
                    // 重新加载 table
                    table.reload('attrConfTable', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    // 弹出提示信息
                    layer.alert("属性信息更新成功!!!")
                }
            }, 'json');

            return false;
        });

    });
}

function deleteAttrConfInfo(attrCodes)
{
    layui.use(['table','layer'],function () {
        let table = layui.table;
        let layer = layui.layer;
        $.post('../../product/attrconf/deleteAttrConfInfoByAttrCodes.json',{"attrCodes":attrCodes},function (data) {
            if (data.row > 0) {
                table.reload('attrConfTable', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
                layer.alert("成功删除属性信息"+data.row+"条数据!!!")
            }
        },'json');
    });
}