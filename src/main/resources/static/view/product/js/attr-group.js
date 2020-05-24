$(function () {
    loadProductPropInfoTable();
});

/**
 * 加载属性组信息
 */
function loadProductPropInfoTable()
{
    layui.use(['table', 'layer'], function () {
        let table = layui.table         // 表格
            , layer = layui.layer;      // 弹出层

        // 表格初始化
        table.render({
            elem: '#attrGroupTable'   // 表格 ID
            , url: '../../product/attrgroup/getAllAttrGroupInfo.json'
            , title: '数据列表'
            , page: true    // 开启分页
            , toolbar: '#attrGroupToolBar'    // 工具栏
            , cols: [[  // 表头
                {type: 'checkbox', fixed: 'left'}
                , {field: 'groupCode', title: '分组编号', width: "10%", sort: true}
                , {field: 'groupName', title: '分组类型名称', width: "18%", sort: true}
                , {field: 'sortNo', title: '排序', width: "18%", sort: true}
                , {field: 'bigCatName', title: '适用商品大类', width: "18%", sort: true}
                , {field: 'countAttr', title: '属性数量', width: "10%", sort: true}
                , {field: 'groupStat', title: '状态', width: "10%", sort: true}
                , {fixed: 'right', width: '12%', align: 'center', title: "操作", toolbar: '#barDemo'}
            ]]
        });


        // 监听商品大类工具栏
        table.on('toolbar(attrGroupTableFilter)', function (obj) {
            let checkStatus = table.checkStatus(obj.config.id);
            let data = checkStatus.data; // 获取当前选中行
            switch (obj.event) {
                case 'showAddAttrGroupInfoWin':
                    showAddAttrGroupInfoWin();
                    break;
                case 'showUpdateAttrGroupInfoWin':
                    if (data.length === 0) {
                        layer.msg('请选择一行');
                    } else if (data.length > 1) {
                        layer.msg('只能同时编辑一个');
                    } else {
                        // layer.alert('编辑 [id]：' + checkStatus.data[0].shopCode);
                        showUpdateAttrGroupInfoWin(checkStatus.data[0]);
                    }
                    break;
                case 'deleteAttrGroupInfo':
                    if (data.length === 0) {
                        layer.msg('请选择一行');
                    } else {
                        let groupCodes = [];
                        for (let attrGroup of data) {
                            groupCodes.push(attrGroup.groupCode);
                        }
                        deleteAttrGroupInfo(groupCodes);
                    }
                    break;
            }
            ;
        });


        //监听行工具事件
        table.on('tool(attrGroupTableFilter)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            let data = obj.data //获得当前行数据
                , layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent === 'detail') {
                location.href="./attr-conf.html?groupCode=" + data.groupCode +"&groupName=" + data.groupName;
                //console.log(data);
            }
        });

    });
}

/**
 * 条件查询属性组信息
 */
function searchAttrGroupInfoByCondition()
{
    layui.use(['table'], function () {
        let table = layui.table;

        table.reload('attrGroupTable', {
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
 * 显示新增属性分组对话框
 */

let addAttrGroupLayer;
function showAddAttrGroupInfoWin()
{
    layui.use(['layer', 'transfer'], function () {
        let layer = layui.layer
            , transfer = layui.transfer;    // 弹出层

        // 同步加载所有一级分类信息
        let arr = [];
        $.ajax({
            url: "../../product/productcat/getAllBigCategoryInfoByNoPage.json",
            type: 'get',
            dataType: 'json',
            async: false, // 同步
            success: function (data) {
                let result = data.result;
                // 遍历所有仓库信息
                for (let row of result) {
                    let obj = {"value": row.catCode, "title": row.catName};
                    arr.push(obj);
                }
            }
        });


        addAttrGroupLayer = layer.open({
            type: 1     // 基本层类型
            , title: '新增大类信息'   // 标题
            , content: $('#addAttrGroupInfoWin')  // 内容
            , area: ['auto', '500px']     // 宽高
            , closeBtn: 1   // 关闭按钮
            , shade: 0.3    // 遮罩
            , id: 'LAY_layuipro'    // 唯一 Id 标识
            , resize: true     // 不允许拉伸
            , moveOut: false     // 允许拖至屏外
            , moveType: 1
            , cancel: function () { // 右上关闭操作

            }
        });

        //显示搜索框
        transfer.render({
            elem: '#test4'
            , data: arr
            , title: ['待选商品大类', '适用商品大类']
            , showSearch: true
            , height: '230'
            , id: 'selectProductCat'
        })

    });
}

/**
 * 新增属性分组
 */
function addAttrGroupInfo()
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
            checkBrandCode: function (value) {
                let row = 0;
                $.ajax({
                    url: "../../product/attrgroup/checkGroupCode.json?groupCode=" + value,
                    type: 'get',
                    dataType: 'json',
                    async: false, // 同步
                    success: function (result) {
                        row = result.row;
                    }
                });
                if (row > 0) {
                    return "品牌编号已存在";
                }
            }
        });


        //监听提交
        form.on('submit(addAttrGroupInfoBtn)', function (data) {


            let getData = transfer.getData('selectProductCat'); //获取右侧数据

            let bigCategoryCodes = [];

            // 获取仓库的 Code
            for (let cat of getData) {
                bigCategoryCodes.push(cat.value)
            }

            let obj = data.field;

            obj["bigCategoryCodes"] = bigCategoryCodes;


            // 发生 ajax 请求向后台提交数据
            $.post('../../product/attrgroup/addAttrGroupInfo.json', obj, function (result) {
                if (result.row > 0) {
                    // 关闭窗口
                    layer.close(addAttrGroupLayer);
                    addAttrGroupLayer = "";
                    // 情况表单
                    $("#addAttrGroupInfoForm")[0].reset();
                    // 重新加载 table
                    table.reload('attrGroupTable', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    // 弹出提示信息
                    layer.alert("品牌信息添加成功!!!")
                }
            }, 'json');

            return false;
        });

    });
}


/**
 * 显示更新面板
 */
let updateAttrGroupLayer;

function showUpdateAttrGroupInfoWin(data) {
    layui.use(['layer', 'transfer', 'form'], function () {
        let layer = layui.layer
            , transfer = layui.transfer
            , form = layui.form;    // 弹出层


        $('#test5').html("");

        form.val("updateAttrGroupInfoFilter", data)

        // 同步加载所有一级分类信息
        let arr = [];
        $.ajax({
            url: "../../product/productcat/getAllBigCategoryInfoByNoPage.json",
            type: 'get',
            dataType: 'json',
            async: false, // 同步
            success: function (data) {
                let result = data.result;
                // 遍历所有仓库信息
                for (let row of result) {
                    let obj = {"value": row.catCode, "title": row.catName};
                    arr.push(obj);
                }
            }
        });

        let values = [];
        $.ajax({
            url: "../../product/attrgroup/getAllCatCodeByGroupCode.json?groupCode=" + data.groupCode,
            type: 'get',
            dataType: 'json',
            async: false, // 同步
            success: function (data) {
                let result = data.result;
                // 遍历所有仓库信息
                for (let value of result) {
                    values.push(value);
                }
            }
        });


        updateAttrGroupLayer = layer.open({
            type: 1     // 基本层类型
            , title: '新增大类信息'   // 标题
            , content: $('#updateAttrGroupInfoWin')  // 内容
            , area: ['auto', '500px']     // 宽高
            , closeBtn: 1   // 关闭按钮
            , shade: 0.3    // 遮罩
            , id: 'LAY_layuipro'    // 唯一 Id 标识
            , resize: true     // 不允许拉伸
            , moveOut: false     // 允许拖至屏外
            , moveType: 1
            , cancel: function () { // 右上关闭操作
            }
        });

        //显示搜索框
        transfer.render({
            elem: '#test5'
            , data: arr
            , value: values
            , title: ['待选商品大类', '适用商品大类']
            , showSearch: true
            , height: '230'
            , id: 'updateProductCat'
        })
    })
}

/**
 * 更新属性组信息
 */
function updateAttrGroupInfo() {
    layui.use(['form', 'layer', 'util', 'transfer', 'table'], function () {
        let layer = layui.layer;
        let table = layui.table;
        let form = layui.form;
        let transfer = layui.transfer;


        //监听提交
        form.on('submit(updateAttrGroupInfoBtn)', function (data) {


            transfer.render();
            let getData = transfer.getData('updateProductCat'); //获取右侧数据

            let bigCategoryCodes = [];

            // 获取仓库的 Code
            for (let cat of getData) {
                bigCategoryCodes.push(cat.value)
            }

            let obj = data.field;

            obj["bigCategoryCodes"] = bigCategoryCodes;




            // 发生 ajax 请求向后台提交数据
            $.post('../../product/attrgroup/updateAttrGroupInfo.json', obj, function (result) {
                if (result.row > 0) {
                    // 关闭窗口
                    layer.close(updateAttrGroupLayer);
                    updateAttrGroupLayer = "";
                    // 情况表单
                    $("#updateAttrGroupInfoForm")[0].reset();
                    // 重新加载 table
                    table.reload('attrGroupTable', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                    });
                    // 弹出提示信息
                    layer.alert("属性组信息更新成功!!!")
                }
            }, 'json');

            return false;
        });
    });
}

function deleteAttrGroupInfo(groupCodes)
{
    layui.use(['table','layer'],function () {
        let table = layui.table;
        let layer = layui.layer;
        $.post('../../product/attrgroup/deleteAttrGroupInfoByGroupCodes.json',{"groupCodes":groupCodes},function (data) {
            if (data.row > 0) {
                table.reload('attrGroupTable', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
                layer.alert("成功删除属性组信息"+data.row+"条数据!!!")
            }
        },'json');
    });
}